package bowling;

import java.util.ArrayList;
import java.util.List;

public class GameState implements BowlingSubject
{
  private List<BowlingObserver> observers = new ArrayList<>();
  private int score;
  private int totalScore;
  private boolean isGameOver = false;
  private GameTheme gameTheme;

  public GameState()
  {
    this.gameTheme = new GameTheme();
    this.totalScore = 0;
    this.score = 0;
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

  public void totalScore(int pins) {
    this.totalScore += pins;
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

  public void resetGame()
  {
    score = 0;
    isGameOver = false;
    notifyObservers();
  }

}
