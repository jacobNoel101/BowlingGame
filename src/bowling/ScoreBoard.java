package bowling;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import visual.statik.SimpleContent;

public class ScoreBoard implements SimpleContent, BowlingObserver
{
  protected Color color;
  protected Image image;
  protected Point2D location;
  private GameState gameState;

  public ScoreBoard(final GameState gameState, final Image image, final Color color,
      final Point2D location) throws IOException
  {
    this.color = color;
    this.location = location;
    this.image = image;
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
  }

  @Override
  public void render(final Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    // Draw background
    g2.setColor(color);
    g2.drawImage(image, 0, 0, 1000, 100, null);
    // Draw current score
    g2.setColor(Color.white);

    ScoreboardWriter.renderScore(location, gameState.getRollScores(), gameState.getTotalScore(),
        g2);
  }

}
