package bowlingVisual;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import visual.statik.SimpleContent;

public class Background implements SimpleContent
{
  private Color color;

  public Background(final Color color)
  {
    this.color = color;
  }

  @Override
  public void render(final Graphics g)
  {
    Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, 1015, 715);
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(color);
    g2.fill(rect);
    g2.draw(rect);
  }

}
