package bowling;

import java.util.*;

public class GameState implements BowlingSubject
{

  private List<BowlingObserver> observers = new ArrayList<>();
  private int score; // total score
  private int pinsKnocked; // pins knocked in current frame
  private int set; // current frame 0-9
  private boolean isGameOver; // is the game finished

  public GameState()
  {
    resetGame();
  }

  // Observer pattern
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
    for (BowlingObserver observer : observers)
    {
      observer.update();
    }
  }

  // Called whenever a pin is knocked down
  public void pinKnocked()
  {
    pinsKnocked++;
    System.out.println("Pins knocked: " + pinsKnocked);
  }

  // Call this at the end of a roll/frame
  public void endFrame()
  {
    score += pinsKnocked; // add frame pins to score
    pinsKnocked = 0; // reset for next frame
    set++;
    if (set >= 10)
    {
      isGameOver = true;
    }
    notifyObservers();
  }

  public int getScore()
  {
    return score;
  }

  public int getSet()
  {
    return set;
  }

  public boolean isGameOver()
  {
    return isGameOver;
  }

  public void resetPins()
  {
    pinsKnocked = 0;
  }

  public void resetGame()
  {
    score = 0;
    pinsKnocked = 0;
    set = 0;
    isGameOver = false;
    notifyObservers();
  }
}
