package bowling;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;

public class ScoreboardWriter
{
  private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 14);

  /**
   * Draw the score information onto the given image.
   *
   * @param image
   *          The BufferedImage to draw on
   * @param score
   *          The score to render
   */
  public static void renderScore(final Point2D location, final int score, final Graphics2D g2,
      final Image image)
  {
    String text = Integer.toString(score);
    FontRenderContext frc = g2.getFontRenderContext();
    Rectangle2D textBounds = FONT.getStringBounds(text, frc);
    double x = location.getX() - textBounds.getWidth() / 2.0;
    double y = location.getY() + image.getHeight(null) / 2.0 + textBounds.getHeight();
    g2.drawString(text, (float) x, (float) y);
  }

  public void reset()
  {
    // TODO Auto-generated method stub
  }

}
