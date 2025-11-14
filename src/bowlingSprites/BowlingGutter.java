package bowlingSprites;

import java.awt.*;
import java.awt.geom.*;
import visual.dynamic.described.DescribedSprite;
import visual.statik.described.*;

public class BowlingGutter extends DescribedSprite
{
  private final int screenW = 1000;
  private final int screenH = 900;
  private final int gutterWidth = 100;
  private final int backWallY = 100;
  private final int backWallHeight = 120;
  private final int laneTopWidth = 180;
  private final int laneBottomWidth = 1100;

  public BowlingGutter()
  {
    AggregateContent placeholder = new AggregateContent();
    placeholder.add(new Content());
    addKeyTime(0, new Point2D.Double(0, 0), 0.0, 1.0, placeholder);
    addKeyTime(1, new Point2D.Double(0, 0), 0.0, 1.0, placeholder);
    setEndState(DescribedSprite.REMAIN);
  }

  @Override
  public void render(Graphics g)
  {
    // creating local variables to help map coords
    Graphics2D g2 = (Graphics2D) g;
    int wallBottomY = backWallY + backWallHeight;
    int laneTopY = wallBottomY;
    int laneBottomY = screenH;
    int laneTopLeftX = (screenW - laneTopWidth) / 2;
    int laneTopRightX = laneTopLeftX + laneTopWidth;
    int laneBottomLeftX = (screenW - laneBottomWidth) / 2;
    int laneBottomRightX = laneBottomLeftX + laneBottomWidth;
    double taper = (double) laneTopWidth / laneBottomWidth;
    int topGutterWidth = (int) (gutterWidth * taper);

    // construct points for left side gutter
    Polygon leftGutter = new Polygon();
    leftGutter.addPoint(laneBottomLeftX - gutterWidth, laneBottomY);
    leftGutter.addPoint(laneBottomLeftX, laneBottomY);
    leftGutter.addPoint(laneTopLeftX, laneTopY);
    leftGutter.addPoint(laneTopLeftX - topGutterWidth, laneTopY);

    // construct points for right side gutter
    Polygon rightGutter = new Polygon();
    rightGutter.addPoint(laneBottomRightX, laneBottomY);
    rightGutter.addPoint(laneBottomRightX + gutterWidth, laneBottomY);
    rightGutter.addPoint(laneTopRightX + topGutterWidth, laneTopY);
    rightGutter.addPoint(laneTopRightX, laneTopY);

    // color in gutters gray
    g2.setColor(new Color(60, 60, 60));
    g2.fill(leftGutter);
    g2.fill(rightGutter);

    // color in outlines
    g2.setColor(Color.BLACK);
    g2.setStroke(new BasicStroke(4f));
    g2.draw(leftGutter);
    g2.draw(rightGutter);
  }
}
