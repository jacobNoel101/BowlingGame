package bowlingVisual;

import java.awt.*;
import java.awt.geom.*;
import visual.statik.SimpleContent;

public class BowlingStart implements SimpleContent
{
  private final String title = "BOWLING";
  private String displayed = "";
  private int letterIndex = 0;
  private int timer = 0;
  private boolean finishedTitle = false;

  @Override
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    int screenW = 1000;
    int screenH = 900;
    g2.setColor(Color.WHITE);
    g2.setFont(new Font("SansSerif", Font.BOLD, 140));
    FontMetrics fm = g2.getFontMetrics();
    int titleX = (screenW - fm.stringWidth(displayed)) / 2;
    int titleY = screenH / 2 - 100;
    g2.drawString(displayed, titleX, titleY);
    if (finishedTitle)
    {
      int btnWidth = 240;
      int btnHeight = 80;
      int btnX = (screenW - btnWidth) / 2;
      int btnY = titleY + 150;
      g2.setColor(Color.BLACK);
      g2.fillRect(btnX, btnY, btnWidth, btnHeight);
      g2.setColor(Color.WHITE);
      g2.setStroke(new BasicStroke(4));
      g2.drawRect(btnX, btnY, btnWidth, btnHeight);
      String text = "START";
      g2.setFont(new Font("SansSerif", Font.BOLD, 40));
      FontMetrics fmBtn = g2.getFontMetrics();
      int textX = btnX + (btnWidth - fmBtn.stringWidth(text)) / 2;
      int textY = btnY + ((btnHeight - fmBtn.getHeight()) / 2) + fmBtn.getAscent();
      g2.drawString(text, textX, textY);
    }
  }

  public void handleTick(int time)
  {
    timer++;
    if (timer % 15 == 0 && letterIndex < title.length())
    {
      displayed += title.charAt(letterIndex++);
      if (letterIndex == title.length())
        finishedTitle = true;
    }
  }

  public boolean isStartClicked(Point2D point)
  {
    if (!finishedTitle)
      return false;
    int btnWidth = 240;
    int btnHeight = 80;
    int btnX = (1000 - btnWidth) / 2;
    int btnY = (900 / 2 - 100) + 150;
    return point.getX() >= btnX && point.getX() <= btnX + btnWidth && point.getY() >= btnY
        && point.getY() <= btnY + btnHeight;
  }

}
