package gui;

import java.awt.*;
import java.awt.geom.*;

class ThemeButton implements visual.statik.SimpleContent
{
  private Rectangle2D box = new Rectangle2D.Double(380, 430, 240, 45);

  @Override
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.BLACK); // fill
    g2.fill(box);
    g2.setColor(Color.WHITE); // outline
    g2.draw(box);
    g2.setFont(new Font("Arial", Font.BOLD, 24));
    String txt = "THEME";
    int textX = (int) (box.getX() + (box.getWidth() - g2.getFontMetrics().stringWidth(txt)) / 2);
    int textY = (int) (box.getY() + ((box.getHeight() - g2.getFontMetrics().getHeight()) / 2)
        + g2.getFontMetrics().getAscent());
    g2.drawString(txt, textX, textY);
  }

  public boolean clicked(Point2D p)
  {
    return box.contains(p);
  }

}
