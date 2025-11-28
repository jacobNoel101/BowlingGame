package bowling;

import java.util.*;

import bowlingSprites.BowlingPin;
import gui.BowlingScreen;

public class GameState implements BowlingSubject
{

  private List<BowlingObserver> observers = new ArrayList<>();
  private BowlingBallController ballController;
  private List<Integer> rollScores = new ArrayList<>();
  private BowlingScreen screen;

  // --- FRAME / ROLL STATE ---
  private int set; // 1–10
  private int rollInSet; // 1 or 2
  private String userName = "username";

  // --- PIN TRACKING ---
  private int pinsStanding; // how many pins remain standing
  private int pinsDownThisRoll; // pins knocked only this roll
  private int pinsDownInSet; // total pins knocked in this frame
  // --- BALL STATE ---
  private boolean ballIsRolling;
  private boolean waitingForBallToStop;

  private boolean waitingForPlayerAim;

  // --- PINS ---
  private Map<Integer, PinData> pins = new HashMap<>(); // Map pinID -> PinData
  private ArrayList<Integer> totalScore = new ArrayList<>();

  /** Wrapper for pin state */
  public static class PinData
  {
    public BowlingPin pin;
    public boolean knocked;

    public PinData(BowlingPin pin)
    {
      this.pin = pin;
      this.knocked = false;
    }
  }

  public GameState()
  {
    resetGame();
  }

  private void updateTotalScores()
  {
    totalScore.clear();
    int runningTotal = 0;

    for (int setIndex = 0; setIndex < 10; setIndex++)
    { // max 10 sets
      int roll1Index = setIndex * 2;
      int roll2Index = roll1Index + 1;

      if (roll1Index >= rollScores.size())
        break; // no rolls yet
      int roll1 = rollScores.get(roll1Index);
      int roll2 = (roll2Index < rollScores.size()) ? rollScores.get(roll2Index) : 0;

      int setTotal = roll1 + roll2;

      // Strike bonus
      if (roll1 == 10)
      {
        int bonus1 = (roll2Index < rollScores.size()) ? rollScores.get(roll2Index) : 0;
        int bonus2 = (roll2Index + 1 < rollScores.size()) ? rollScores.get(roll2Index + 1) : 0;
        setTotal = 10 + bonus1 + bonus2;
      }
      // Spare bonus
      else if (roll1 + roll2 == 10)
      {
        int bonus = (roll2Index + 1 < rollScores.size()) ? rollScores.get(roll2Index + 1) : 0;
        setTotal = 10 + bonus;
      }

      runningTotal += setTotal;
      totalScore.add(runningTotal);
    }
  }

  public void setBallController(BowlingBallController controller)
  {
    this.ballController = controller;
  }

  public void startAiming()
  {
    if (ballIsRolling || waitingForBallToStop)
      return;
    waitingForPlayerAim = true;
  }

  public Collection<PinData> getPins()
  {
    return pins.values();
  }

  public boolean isAiming()
  {
    return waitingForPlayerAim;
  }

  public void playerRollRequested(double angle)
  {
    if (!waitingForPlayerAim || ballIsRolling || waitingForBallToStop)
      return;

    ballIsRolling = true;
    waitingForBallToStop = true;
    pinsDownThisRoll = 0;

    if (ballController != null)
      ballController.startRoll(angle);
  }

  public void pinKnocked(BowlingPin pin)
  {
    for (PinData pd : pins.values())
    {
      if (pd.pin == pin && !pd.knocked)
      {
        pd.knocked = true;
        pinsDownThisRoll++;
        pinsStanding = Math.max(0, pinsStanding - 1);
        break;
      }
    }
  }

  public ArrayList<Integer> getTotalScore()
  {
    return totalScore;
  }

  public void ballStopped()
  {
    ballIsRolling = false;
    waitingForBallToStop = false;
    pinsDownInSet += pinsDownThisRoll;
    rollScores.add(pinsDownThisRoll);
    updateTotalScores();
    boolean isStrike = (rollInSet == 1 && pinsDownThisRoll == 10);
    boolean isSpare = (rollInSet == 2 && pinsDownInSet == 10);
    // show message
    if (ballController instanceof BowlingScreen)
    {
      BowlingScreen screen = (BowlingScreen) ballController;
      if (isStrike)
        screen.showMessage("Nice Strike!");
      else if (isSpare)
        screen.showMessage("Nice Spare!");
    }

    if (set == 10)
    {
      // after 2nd roll, stop the game
      if (rollInSet == 2)
      {
        waitingForPlayerAim = false;
        if (screen != null)
        {
          int finalScore = totalScore.isEmpty() ? 0 : totalScore.get(totalScore.size() - 1);
          screen.showEndGamePopup(finalScore);
        }
        notifyObservers();
        return; // stop further rolls
      }
      else
      {
        // first roll only, go to roll 2
        rollInSet = 2;
        waitingForPlayerAim = false;
        if (ballController != null)
          ballController.resetBall();
        notifyObservers();
        return;
      }
    }

    if (isStrike || isSpare || rollInSet == 2)
    {
      endFrameAndResetSet();
    }
    else
    {
      rollInSet = 2;
      waitingForPlayerAim = false;
      if (ballController != null)
        ballController.resetBall();
    }

    notifyObservers();
  }

  public boolean anyPinsHit()
  {
    for (PinData pd : pins.values())
    {
      if (pd.pin.isHit())
        return true;
    }
    return false;
  }

  private void endFrameAndResetSet()
  {
    set++;
    rollInSet = 1;
    pinsStanding = 10;
    pinsDownInSet = 0;
    pinsDownThisRoll = 0;
    waitingForPlayerAim = false;
    ballIsRolling = false;
    waitingForBallToStop = false;

    for (PinData pd : pins.values())
    {
      pd.knocked = false; // reset knocked state
      if (pd.pin != null)
        pd.pin.resetPin(); // reset visual state
    }

    if (ballController != null)
    {
      if (ballController instanceof BowlingScreen)
      {
        ((BowlingScreen) ballController).schedulePinReset();
        ballController.resetBall();

      }
    }

    notifyObservers();
  }

  @Override
  public void addObserver(BowlingObserver observer)
  {
    observers.add(observer);
  }

  @Override
  public void removeObserver(BowlingObserver observer)
  {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers()
  {
    for (BowlingObserver obs : observers)
      obs.update();
  }

  /** Add a pin to the game state */
  public void addPin(int id, BowlingPin pin)
  {
    pins.put(id, new PinData(pin));
  }

  public ArrayList<Integer> getRollScores()
  {
    return new ArrayList<>(rollScores);
  }

  public void resetGame()
  {
    set = 1;
    rollInSet = 1;
    pinsStanding = 10;
    pinsDownInSet = 0;
    pinsDownThisRoll = 0;
    waitingForPlayerAim = false;
    ballIsRolling = false;
    waitingForBallToStop = false;
    rollScores.clear();

    // Reset all pins
    for (PinData pd : pins.values())
    {
      if (pd.pin != null)
      {
        pd.pin.resetPin();
        pd.knocked = false;
      }
    }

    notifyObservers();
  }

  public int getSet()
  {
    return set;
  }

  public int getRollInSet()
  {
    return rollInSet;
  }

  public int getPinsStanding()
  {
    return pinsStanding;
  }

  public int getPinsDownInSet()
  {
    return pinsDownInSet;
  }

  public int getPinsDownThisRoll()
  {
    return pinsDownThisRoll;
  }

  public String getUserName()
  {
    return userName;
  }

  public void setBowlingScreen(BowlingScreen screen)
  {
    this.screen = screen;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }
}
