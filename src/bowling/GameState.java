package bowling;

import java.util.*;

public class GameState implements BowlingSubject
{
  private static int pinsKnocked;
  private List<BowlingObserver> observers = new ArrayList<>();
  private int score;
  private boolean isGameOver = false;
  private int[] firstRoll;
  private int[] secondRoll;
  private boolean[] strike;
  private boolean[] spare;
  private int set;

  public GameState()
  {
    this.score = 0;
    firstRoll = new int[10];
    secondRoll = new int[10];
    strike = new boolean[10];
    spare = new boolean[10];
    isGameOver = false;
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
    for (BowlingObserver observer : observers)
    {
      observer.update();
    }
  }

  public static void recordRoll(int pins)
  {
//    if (isGameOver)
//      return;
//    if (pins == 10)
//    {
//      strike[set] = true;
//      set++;
//      firstRoll[set] = 0;
//      secondRoll[set] = 0;
//    }
//    if (firstRoll[set] == (Integer) null)
//    {
//      firstRoll[set] = pins;
//    }
//    else if (secondRoll[set] == (Integer) null)
//    {
//      secondRoll[set] = pins;
//      if (secondRoll[set] + firstRoll[set] == 10)
//      {
//        spare[set] = true;
//      }
//      set++;
//    }
//    if (firstRoll[set] != (Integer) null && secondRoll[set] != (Integer) null)
//    {
//      isGameOver = true;
//    }
//    notifyObservers();

  }

  public void addScore(int pins)
  {
    score += pins;
    notifyObservers();
  }

  public void endGame()
  {
    isGameOver = true;
    notifyObservers();
  }

  public int getScore()
  {
    return score;
  }

  public boolean isGameOver()
  {
    return isGameOver;
  }
  

  public void pinKnocked() {
      pinsKnocked++;
      System.out.println("Pins knocked: " + pinsKnocked);
      //also check for end of frame, update score
  }

  public int getPinsKnocked() {
      return pinsKnocked;
  }

  public void resetPins() {
      pinsKnocked = 0;
  }


  public void resetGame()
  {
    firstRoll = new int[10];
    secondRoll = new int[10];
    strike = new boolean[10];
    spare = new boolean[10];
    score = 0;
    isGameOver = false;
    notifyObservers();
  }

}
