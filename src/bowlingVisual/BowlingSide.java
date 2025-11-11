package bowlingVisual;

import java.awt.*;
import visual.statik.SimpleContent;

public class BowlingSide implements SimpleContent
{
  private final int screenW = 1000;
  private final int screenH = 900;
  private final int backWallY = 240;
  private final int laneTopWidth = 260;
  private final int laneBottomWidth = 580;
  private final int gutterWidth = 40;
  private final Color sidingColor = new Color(200, 200, 200);

  @Override
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int backWallTopY = backWallY;
    int laneBottomY = screenH;
    int laneTopLeftX = (screenW - laneTopWidth) / 2;
    int laneTopRightX = laneTopLeftX + laneTopWidth;
    int laneBottomLeftX = (screenW - laneBottomWidth) / 2;
    int laneBottomRightX = laneBottomLeftX + laneBottomWidth;
    int topLeftSidingX = laneTopLeftX - gutterWidth;
    int topRightSidingX = laneTopRightX + gutterWidth;
    int bottomLeftSidingX = laneBottomLeftX - gutterWidth;
    int bottomRightSidingX = laneBottomRightX + gutterWidth;
    Polygon leftSide = new Polygon();
    leftSide.addPoint(0, backWallTopY);
    leftSide.addPoint(topLeftSidingX, backWallTopY);
    leftSide.addPoint(bottomLeftSidingX, laneBottomY);
    leftSide.addPoint(0, laneBottomY);
    Polygon rightSide = new Polygon();
    rightSide.addPoint(topRightSidingX, backWallTopY);
    rightSide.addPoint(screenW, backWallTopY);
    rightSide.addPoint(screenW, laneBottomY);
    rightSide.addPoint(bottomRightSidingX, laneBottomY);
    g2.setColor(sidingColor);
    g2.fill(leftSide);
    g2.fill(rightSide);
    g2.setStroke(new BasicStroke(4f));
    g2.setColor(Color.BLACK);
    g2.draw(leftSide);
    g2.draw(rightSide);
  }

}
