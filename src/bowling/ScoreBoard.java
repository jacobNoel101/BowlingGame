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
    Graphics2D g2 = null;
    ScoreboardWriter.renderScore(location, gameState.getScore(), g2, image);
  }

  @Override
  public void render(final Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    Color oldColor = g2.getColor();
    g2.setColor(color);
    double width = 1000;
    double height = 100;
    g2.drawImage(image, (int) 0, (int) 0, (int) width, (int) height, null);
    g2.setColor(oldColor);
  }

}
