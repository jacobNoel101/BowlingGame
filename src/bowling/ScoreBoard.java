package bowling;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import bowlingVisual.ScoreBoardReader;
import io.ResourceFinder;
import visual.dynamic.described.SampledSprite;
import visual.statik.SimpleContent;
import visual.statik.sampled.Content;

public class ScoreBoard implements SimpleContent, BowlingObserver
{
  private GameState gameState;
  private ScoreboardWriter scoreBoardWriter;
  private ScoreBoardReader scoreBoardReader;
  private ResourceFinder finder;
  BufferedImage imgBoard;
  
  public ScoreBoard(GameState gameState, ScoreboardWriter scoreBoardWriter, ScoreBoardReader scoreBoardReader, ResourceFinder finder) throws IOException {
    this.finder = finder;
    this.scoreBoardReader = new ScoreBoardReader(finder);
    this.imgBoard = scoreBoardReader.read();
    this.scoreBoardWriter = new ScoreboardWriter();
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
    
    scoreBoardWriter.renderScore(imgBoard, gameState.getScore());

   System.out.println(printedScore);
    
  }

  @Override
  public void render(Graphics arg0)
  {
    // TODO Auto-generated method stub
    
  }
}
