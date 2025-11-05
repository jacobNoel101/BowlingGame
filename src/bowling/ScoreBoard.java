package bowling;

import java.awt.Graphics;

import visual.dynamic.described.SampledSprite;
import visual.statik.SimpleContent;
import visual.statik.sampled.Content;

public class ScoreBoard implements SimpleContent, BowlingObserver
{
  private GameState gameState;
  
  public ScoreBoard(GameState gameState) {
    
    this.gameState = gameState;
    gameState.addObserver(this);
  }

  @Override
  public void reset()
  {
  }

  @Override
  public void update()
  {
    String printedScore = null;
    if (gameState.getScore() == 0) printedScore = "-";
    if (gameState.getScore() == 10) printedScore = "X";
    else printedScore = Integer.toString(gameState.getScore());
    
   System.out.println(printedScore);
    
  }

  @Override
  public void render(Graphics arg0)
  {
    // TODO Auto-generated method stub
    
  }
}
