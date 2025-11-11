package bowlingVisual;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import visual.statik.SimpleContent;

public class BowlingLane implements SimpleContent
{
  private final Color laneColor = new Color(181, 101, 29);
  private final Color backWallColor = Color.BLACK;
  private final int screenW = 1000;
  private final int screenH = 900;
  private final int backWallWidth = 320;
  private final int backWallHeight = 120;
  private final int laneBottomWidth = 500;

  @Override
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    int backWallX = (screenW - backWallWidth) / 2;
    int backWallY = 240;
    Rectangle2D backWall = new Rectangle2D.Double(backWallX, backWallY, backWallWidth,
        backWallHeight);
    g2.setColor(backWallColor);
    g2.fill(backWall);
    int laneTopY = backWallY + backWallHeight;
    int laneBottomY = screenH;
    int laneTopLeftX = backWallX;
    int laneTopRightX = backWallX + backWallWidth;
    int laneBottomLeftX = (screenW - laneBottomWidth) / 2;
    int laneBottomRightX = (screenW + laneBottomWidth) / 2;
    Polygon lane = new Polygon();
    lane.addPoint(laneBottomLeftX, laneBottomY);
    lane.addPoint(laneBottomRightX, laneBottomY);
    lane.addPoint(laneTopRightX, laneTopY);
    lane.addPoint(laneTopLeftX, laneTopY);
    g2.setColor(laneColor);
    g2.fill(lane);
    g2.setStroke(new BasicStroke(4f));
    g2.setColor(Color.BLACK);
    g2.draw(backWall);
    g2.draw(lane);
  }

}
