package bowlingSprites;

import java.awt.*;
import java.awt.geom.*;
import visual.dynamic.described.DescribedSprite;
import visual.statik.described.AggregateContent;
import visual.statik.described.Content;

public class BowlingGutter extends DescribedSprite
{
  private final int screenW = 1000;
  private final int screenH = 900;
  private final int gutterWidth = 40;
  private final int backWallY = 240;
  private final int backWallHeight = 120;
  private final int laneTopWidth = 260;
  private final int laneBottomWidth = 580;

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
    Graphics2D g2 = (Graphics2D) g;
    int laneTopY = backWallY + backWallHeight;
    int laneBottomY = screenH;
    int laneTopLeftX = (screenW - laneTopWidth) / 2;
    int laneTopRightX = laneTopLeftX + laneTopWidth;
    int laneBottomLeftX = (screenW - laneBottomWidth) / 2;
    int laneBottomRightX = laneBottomLeftX + laneBottomWidth;
    // left gutter aligned with outskirts of left lane
    Polygon leftGutter = new Polygon();
    leftGutter.addPoint(laneBottomLeftX - gutterWidth, laneBottomY);
    leftGutter.addPoint(laneBottomLeftX + 20, laneBottomY);
    leftGutter.addPoint(laneTopLeftX, laneTopY);
    leftGutter.addPoint(laneTopLeftX - gutterWidth, laneTopY);
    // right gutter mirrored to left
    Polygon rightGutter = new Polygon();
    rightGutter.addPoint(laneBottomRightX - 20, laneBottomY);
    rightGutter.addPoint(laneBottomRightX + gutterWidth, laneBottomY);
    rightGutter.addPoint(laneTopRightX + gutterWidth, laneTopY);
    rightGutter.addPoint(laneTopRightX, laneTopY);
    // fill the gutters dark gray
    g2.setColor(new Color(60, 60, 60));
    g2.fill(leftGutter);
    g2.fill(rightGutter);
    // black outline around gutters
    g2.setStroke(new BasicStroke(4f));
    g2.setColor(Color.BLACK);
    g2.draw(leftGutter);
    g2.draw(rightGutter);
  }

}
