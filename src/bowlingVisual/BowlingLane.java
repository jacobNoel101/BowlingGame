package bowlingVisual;

import java.awt.*;
import java.awt.geom.*;
import visual.statik.SimpleContent;

public class BowlingLane implements SimpleContent
{
  private final Color laneColor = new Color(181, 101, 29);
  private final Color backWallColor = Color.BLACK;
  private final int screenW = 1000;
  private final int screenH = 900;
  private final int backWallWidth = 340;
  private final int backWallHeight = 120;
  private final int laneTopWidth = 260;
  private final int laneBottomWidth = 580;
  private final int numStrips = 9;

  @Override
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int backWallX = (screenW - backWallWidth) / 2;
    int backWallY = 240;
    Rectangle2D backWall = new Rectangle2D.Double(backWallX, backWallY, backWallWidth,
        backWallHeight);
    g2.setColor(backWallColor);
    g2.fill(backWall);
    int laneTopY = backWallY + backWallHeight;
    int laneBottomY = screenH;
    int laneTopLeftX = (screenW - laneTopWidth) / 2;
    int laneTopRightX = laneTopLeftX + laneTopWidth;
    int laneBottomLeftX = (screenW - laneBottomWidth) / 2;
    int laneBottomRightX = laneBottomLeftX + laneBottomWidth;
    Polygon lane = new Polygon();
    lane.addPoint(laneBottomLeftX, laneBottomY);
    lane.addPoint(laneBottomRightX, laneBottomY);
    lane.addPoint(laneTopRightX, laneTopY);
    lane.addPoint(laneTopLeftX, laneTopY);
    g2.setColor(laneColor);
    g2.fill(lane);
    g2.setStroke(new BasicStroke(2f));
    g2.setColor(new Color(160, 90, 20));
    for (int i = 1; i < numStrips; i++)
    {
      double t = (double) i / numStrips;
      int xTop = (int) (laneTopLeftX + t * (laneTopWidth));
      int xBottom = (int) (laneBottomLeftX + t * (laneBottomWidth));
      Line2D strip = new Line2D.Double(xTop, laneTopY, xBottom, laneBottomY);
      g2.draw(strip);
    }
    g2.setStroke(new BasicStroke(4f));
    g2.setColor(Color.BLACK);
    g2.draw(backWall);
    g2.draw(lane);
  }

}
