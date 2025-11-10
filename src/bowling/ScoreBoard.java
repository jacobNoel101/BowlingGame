package bowling;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import bowlingVisual.ScoreBoardReader;
import io.ResourceFinder;
import resources.Marker;
import visual.statik.SimpleContent;

public class ScoreBoard implements SimpleContent, BowlingObserver
{
  protected Color color;
  protected Image image;
  protected Point2D location;
  private GameState gameState;

  public ScoreBoard(GameState gameState, Image image, Color color, Point2D location) throws IOException
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
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;

    Color oldColor = g2.getColor();
    g2.setColor(color);

    // Render the Image
    double y = location.getY() - image.getHeight(null) / 2.0;
    double x = location.getX() - image.getWidth(null) / 2.0;
    g.drawImage(image, (int) x, (int) y, null);

    g2.setColor(oldColor);
    
  }

}
