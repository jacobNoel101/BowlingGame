package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import visual.dynamic.described.DescribedSprite;
import visual.dynamic.described.Stage;
import visual.statik.described.AggregateContent;
import visual.statik.described.Content;

public class BowlingScreen extends Stage
{
  private DescribedSprite laneSprite;

  public BowlingScreen(final int timeStep)
  {
    super(timeStep);
    laneSprite = buildLaneSprite();
    add(laneSprite);
  }

  private DescribedSprite buildLaneSprite()
  {
    AggregateContent laneAggregate = new AggregateContent();
    int width = 800;
    int height = 600;
    int navbar = 100;
    int laneTopWidth = 340;
    int laneBottomWidth = 340;
    int laneStartY = navbar;
    int laneEndY = height;
    int gutter = 30;

    // light-gray background
    Color wallColor = new Color(180, 180, 180);
    Content bg = new Content();
    bg.setShape(new java.awt.geom.Rectangle2D.Double(0, navbar, width, height - navbar));
    bg.setPaint(wallColor);
    laneAggregate.add(bg);

    // score board section
    Content bar = new Content();
    bar.setShape(new java.awt.geom.Rectangle2D.Double(0, 0, width, navbar));
    bar.setPaint(new Color(150, 150, 150));
    laneAggregate.add(bar);

    // shape of the lane
    Polygon lane = new Polygon();
    lane.addPoint(width / 2 - laneBottomWidth / 2, laneEndY);
    lane.addPoint(width / 2 + laneBottomWidth / 2, laneEndY);
    lane.addPoint(width / 2 + laneTopWidth / 2, laneStartY);
    lane.addPoint(width / 2 - laneTopWidth / 2, laneStartY);
    Content laneC = new Content();
    laneC.setShape(lane);
    laneC.setPaint(new Color(181, 101, 29));
    laneC.setStroke(new BasicStroke(2f));
    laneAggregate.add(laneC);

    // left-side gutter
    Polygon leftBlack = new Polygon();
    leftBlack.addPoint(width / 2 - laneBottomWidth / 2 - gutter, laneEndY);
    leftBlack.addPoint(width / 2 - laneBottomWidth / 2, laneEndY);
    leftBlack.addPoint(width / 2 - laneTopWidth / 2, laneStartY);
    leftBlack.addPoint(width / 2 - laneTopWidth / 2 - gutter, laneStartY);
    Content lb = new Content();
    lb.setShape(leftBlack);
    lb.setPaint(Color.BLACK);
    laneAggregate.add(lb);

    // right-side gutter
    Polygon rightBlack = new Polygon();
    rightBlack.addPoint(width / 2 + laneBottomWidth / 2, laneEndY);
    rightBlack.addPoint(width / 2 + laneBottomWidth / 2 + gutter, laneEndY);
    rightBlack.addPoint(width / 2 + laneTopWidth / 2 + gutter, laneStartY);
    rightBlack.addPoint(width / 2 + laneTopWidth / 2, laneStartY);
    Content rb = new Content();
    rb.setShape(rightBlack);
    rb.setPaint(Color.BLACK);
    laneAggregate.add(rb);

    // pit for behind cones
    Polygon wall = new Polygon();
    wall.addPoint(width / 2 - laneTopWidth / 2 - gutter - 40, laneStartY);
    wall.addPoint(width / 2 + laneTopWidth / 2 + gutter + 40, laneStartY);
    wall.addPoint(width / 2 + laneTopWidth / 2 + gutter, laneStartY + 40);
    wall.addPoint(width / 2 - laneTopWidth / 2 - gutter, laneStartY + 40);
    Content w = new Content();
    w.setShape(wall);
    w.setPaint(new Color(50, 50, 50));
    laneAggregate.add(w);

    DescribedSprite sprite = new DescribedSprite();
    sprite.addKeyTime(0, new Point2D.Double(0, 0), 0.0, 1.0, laneAggregate);
    sprite.addKeyTime(50, new Point2D.Double(0, 0), 0.0, 1.0, laneAggregate);
    return sprite;
  }

}
