package bowling;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;

public class ScoreboardWriter
{
  private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
  private static final int SPACING = 25;
  private static final int SET_SPACING = 24;
  private static String text;
  private static final String GUTTER = "-";
  private static final String STRIKE = "X";

  public static void renderScore(Point2D location, List<Integer> rollScores, Graphics2D g2)
  {
    g2.setFont(FONT);
    FontMetrics metrics = g2.getFontMetrics(FONT);
    int xStart = (int) location.getX();
    int y = (int) location.getY();
    g2.setColor(Color.BLACK);
    int counter = 0;

    for (Integer score : rollScores)
    {
      if (score == 0)
      {
        text = GUTTER;
      }
      else if (score == 10)
      {
        text = STRIKE;
      }
      else
      {
        text = Integer.toString(score);
      }
      int set = counter / 2;
      int x = xStart + counter * SPACING + set * SET_SPACING;
      int textX = x - metrics.stringWidth(text) / 2;
      int textY = y + metrics.getAscent() / 2;
      g2.drawString(text, textX, textY);
      counter++;
    }
  }

}
