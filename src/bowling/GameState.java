package bowling;

import java.util.*;

import gui.BowlingScreen;

public class GameState implements BowlingSubject
{
  private List<BowlingObserver> observers = new ArrayList<>();

  private BowlingBallController ballController;
  private List<Integer> rollScores; // Stores pins knocked down for each roll


  // --- FRAME / ROLL STATE ---
  private int set; // 1–10
  private int rollInSet; // 1 or 2

  // --- PIN TRACKING ---
  private int pinsStanding; // how many pins remain standing
  private int pinsDownThisRoll; // pins knocked only this roll
  private int pinsDownInFrame; // total pins knocked entire frame

  // --- BALL STATE ---
  private boolean ballIsRolling;
  private boolean waitingForBallToStop;
  private boolean waitingForPlayerAim;
  
  private int lastRoll = 1;

  
  

  public GameState()
  {
    resetGame();
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

  public boolean isAiming()
  {
    return waitingForPlayerAim;
  }

  public void playerRollRequested(double angle)
  {
    if (!waitingForPlayerAim)
      return;
    if (ballIsRolling)
      return;
    if (waitingForBallToStop)
      return;

    ballIsRolling = true;
    waitingForBallToStop = true;

    // Reset pins hit counter for this new roll
    pinsDownThisRoll = 0;

    if (ballController != null)
      ballController.startRoll(angle);
  }

  public void pinKnocked()
  {
    if (!waitingForBallToStop)
      return;

    pinsDownThisRoll++;
    pinsStanding = Math.max(0, pinsStanding - 1);
  }


  public void ballStopped() {
    ballIsRolling = false;
    waitingForBallToStop = false;

    pinsDownInFrame += pinsDownThisRoll;
    rollScores.add(pinsDownThisRoll);


    // Strike (first roll only)
    if (rollInSet == 1 && pinsDownThisRoll == 10) {
        endFrameAndReset(); // we can schedule reset there
        return;
    }
    

    // Second roll ends frame
    if (rollInSet == 2) {
        endFrameAndReset(); // schedule delayed reset
        return;
    }

    // Otherwise, start second roll
    rollInSet = 2;
    waitingForPlayerAim = false;


    if (ballController != null)
        ballController.resetBall(); // reset ball for second roll

    notifyObservers();
  }

  private void endFrameAndReset()
  {
    set++;
    if (set > 10)
      set = 10;

    rollInSet = 1;
    pinsStanding = 10;
    pinsDownInFrame = 0;
    waitingForPlayerAim = false;
    ballIsRolling = false;
    waitingForBallToStop = false;

    if (ballController != null)
    {
      ballController.resetBall();
      if (ballController instanceof BowlingScreen) {
        ((BowlingScreen) ballController).schedulePinReset();
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
  
  public ArrayList<Integer> getRollScores() {
    return (ArrayList<Integer>) rollScores;
  }

  public void resetGame()
  {
    set = 1;
    rollInSet = 1;
    pinsStanding = 10;
    pinsDownInFrame = 0;
    pinsDownThisRoll = 0;
    waitingForPlayerAim = false;
    ballIsRolling = false;
    waitingForBallToStop = false;
    rollScores = new ArrayList<>(); // Reset roll history

    

    notifyObservers();
  }

  // --- GETTERS ---
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

  public int getPinsDownInFrame()
  {
    return pinsDownInFrame;
  }

  public int getPinsDownThisRoll()
  {
    return pinsDownThisRoll;
  }
}
