package bowlingVisual;

import java.awt.*;
import visual.statik.SimpleContent;

public class BowlingSide implements SimpleContent
{
  private final int screenW = 1000; // screen W
  private final int screenH = 900; // screen H
  private final int backWallY = 240;
  private final int laneTopWidth = 260;
  private final int laneBottomWidth = 580;
  private final int gutterWidth = 40; // how thick the gutters appear
  private final Color sidingColor = new Color(200, 200, 200);

  @Override
  public void render(Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    // back wall and bottom of screen
    int backWallTopY = backWallY;
    int laneBottomY = screenH;
    
    // trapezoid shape for the lane
    int laneTopLeftX = (screenW - laneTopWidth) / 2;
    int laneTopRightX = laneTopLeftX + laneTopWidth;
    int laneBottomLeftX = (screenW - laneBottomWidth) / 2;
    int laneBottomRightX = laneBottomLeftX + laneBottomWidth;
    
    // outer edges for the side walls
    int topLeftSidingX = laneTopLeftX - gutterWidth;
    int topRightSidingX = laneTopRightX + gutterWidth;
    int bottomLeftSidingX = laneBottomLeftX - gutterWidth;
    int bottomRightSidingX = laneBottomRightX + gutterWidth;
    
    // construct left side (to left-gutter)
    Polygon leftSide = new Polygon();
    leftSide.addPoint(0, backWallTopY);
    leftSide.addPoint(topLeftSidingX, backWallTopY);
    leftSide.addPoint(bottomLeftSidingX, laneBottomY);
    leftSide.addPoint(0, laneBottomY);
    
    // construct right side (to right-gutter)
    Polygon rightSide = new Polygon();
    rightSide.addPoint(topRightSidingX, backWallTopY);
    rightSide.addPoint(screenW, backWallTopY);
    rightSide.addPoint(screenW, laneBottomY);
    rightSide.addPoint(bottomRightSidingX, laneBottomY);
    
    // fill the side colors
    g2.setColor(sidingColor);
    g2.fill(leftSide);
    g2.fill(rightSide);
    
    // 4px black stroke
    g2.setStroke(new BasicStroke(4f));
    g2.setColor(Color.BLACK);
    g2.draw(leftSide);
    g2.draw(rightSide);
  }

}
