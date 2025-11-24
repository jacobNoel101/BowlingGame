package bowling;

import java.awt.geom.Ellipse2D;
import java.util.*;

import bowlingSprites.BowlingPin;
import gui.BowlingScreen;

public class GameState implements BowlingSubject {

    private List<BowlingObserver> observers = new ArrayList<>();
    private BowlingBallController ballController;
    private List<Integer> rollScores = new ArrayList<>();

    // --- FRAME / ROLL STATE ---
    private int set; // 1–10
    private int rollInSet; // 1 or 2

    // --- PIN TRACKING ---
    private int pinsStanding; // how many pins remain standing
    private int pinsDownThisRoll; // pins knocked only this roll
    private int pinsDownInSet; // total pins knocked in this frame

    // --- BALL STATE ---
    private boolean ballIsRolling;
    private boolean waitingForBallToStop;
    private boolean waitingForPinStop = false;

    private boolean waitingForPlayerAim;

    // --- PINS ---
    private Map<Integer, PinData> pins = new HashMap<>(); // Map pinID -> PinData

    /** Wrapper for pin state */
    public static class PinData {
        public BowlingPin pin;
        public boolean knocked;

        public PinData(BowlingPin pin) {
            this.pin = pin;
            this.knocked = false;
        }
    }

    public GameState() {
        resetGame();
    }

    public void setBallController(BowlingBallController controller) {
        this.ballController = controller;
    }

    public void startAiming() {
        if (ballIsRolling || waitingForBallToStop) return;
        waitingForPlayerAim = true;
    }
    
    public Collection<PinData> getPins() {
      return pins.values();
    }


    public boolean isAiming() {
        return waitingForPlayerAim;
    }

    public void playerRollRequested(double angle) {
        if (!waitingForPlayerAim || ballIsRolling || waitingForBallToStop) return;

        ballIsRolling = true;
        waitingForBallToStop = true;
        pinsDownThisRoll = 0;

        if (ballController != null)
            ballController.startRoll(angle);
    }

    public void pinKnocked(BowlingPin pin) {
      for (PinData pd : pins.values()) {
          if (pd.pin == pin && !pd.knocked) {
              pd.knocked = true;
              pinsDownThisRoll++;
              pinsStanding = Math.max(0, pinsStanding - 1);
              break;
          }
      }
    }
    




    public void ballStopped() {
      ballIsRolling = false;
      waitingForBallToStop = false;

      pinsDownInSet += pinsDownThisRoll;
      rollScores.add(pinsDownThisRoll);

      boolean isStrike = (rollInSet == 1 && pinsDownThisRoll == 10);
      boolean isSpare = (rollInSet == 2 && pinsDownInSet == 10);

      if (isStrike || isSpare || rollInSet == 2) {
          endFrameAndResetSet();
      } else {
          // start second roll
          rollInSet = 2;
          waitingForPlayerAim = false;
          if (ballController != null) ballController.resetBall();
      }

      notifyObservers();
    }


    private void endFrameAndResetSet() {
        set++;
        if (set > 10) set = 10; // Game end logic can go here

        rollInSet = 1;
        pinsStanding = 10;
        pinsDownInSet = 0;
        pinsDownThisRoll = 0;
        waitingForPlayerAim = false;
        ballIsRolling = false;
        waitingForBallToStop = false;
        
        for (PinData pd : pins.values()) {
          pd.knocked = false;        // reset knocked state
          if (pd.pin != null) pd.pin.resetPin(); // reset visual state
        }


        if (ballController != null) {
            if (ballController instanceof BowlingScreen) {
                ((BowlingScreen) ballController).schedulePinReset();
                ballController.resetBall();

            }
        }

        notifyObservers();
    }

    @Override
    public void addObserver(BowlingObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(BowlingObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (BowlingObserver obs : observers) obs.update();
    }

    /** Add a pin to the game state */
    public void addPin(int id, BowlingPin pin) {
        pins.put(id, new PinData(pin));
    }


    public ArrayList<Integer> getRollScores() {
        return new ArrayList<>(rollScores);
    }

    public void resetGame() {
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
        for (PinData pd : pins.values()) {
            if (pd.pin != null) {
                pd.pin.resetPin();
                pd.knocked = false;
            }
        }

        notifyObservers();
    }

    public int getSet() { return set; }
    public int getRollInSet() { return rollInSet; }
    public int getPinsStanding() { return pinsStanding; }
    public int getPinsDownInSet() { return pinsDownInSet; }
    public int getPinsDownThisRoll() { return pinsDownThisRoll; }
}
