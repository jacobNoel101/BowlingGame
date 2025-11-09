package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import visual.dynamic.described.DescribedSprite;
import visual.dynamic.described.Stage;
import visual.statik.described.AggregateContent;
import visual.statik.described.Content;

public class BowlingScreen extends Stage
{
  private static final double LANE_WIDTH = 100;
  private static final double LANE_HEIGHT = 400;
  private static final double GUTTER_WIDTH = 20;
  private static final double LANE_X = 150;
  private static final double LANE_Y = 50;

  public BowlingScreen(int timeStep)
  {
    super(timeStep);
    AggregateContent laneAggregate = new AggregateContent();
    Content laneContent = new Content(
        new Rectangle2D.Double(LANE_X, LANE_Y, LANE_WIDTH, LANE_HEIGHT), Color.BLACK,
        new Color(200, 180, 120), new BasicStroke(2.0f));
    Content leftGutterContent = new Content(
        new Rectangle2D.Double(LANE_X - GUTTER_WIDTH, LANE_Y, GUTTER_WIDTH, LANE_HEIGHT),
        Color.BLACK, Color.DARK_GRAY, new BasicStroke(2.0f));
    Content rightGutterContent = new Content(
        new Rectangle2D.Double(LANE_X + LANE_WIDTH, LANE_Y, GUTTER_WIDTH, LANE_HEIGHT), Color.BLACK,
        Color.DARK_GRAY, new BasicStroke(2.0f));
    laneAggregate.add(leftGutterContent);
    laneAggregate.add(laneContent);
    laneAggregate.add(rightGutterContent);
    DescribedSprite laneSprite = new DescribedSprite();
    laneSprite.addKeyTime(0, new Point2D.Double(0, 0), null, null, laneAggregate);
    add(laneSprite);
  }

}
