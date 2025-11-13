package bowlingSprites;

import java.awt.*;
import java.awt.geom.*;
import visual.dynamic.described.DescribedSprite;
import visual.statik.described.*;

public class BowlingBall extends DescribedSprite
{
  private final int radius = 25; // ball size
  private double cx = 500.0; // x pos on screen
  private double cy = 650.0; // y pos on screen

  public BowlingBall()
  {
    // create agg content to hold ball and finger holes
    AggregateContent agg = new AggregateContent();
    Content ballShape = new Content(); // main circle

    // create shape of the ball and set color to blue
    ballShape.setShape(new Ellipse2D.Double(-radius, -radius, radius * 2, radius * 2));
    ballShape.setPaint(Color.BLUE);
    agg.add(ballShape); // add circle to agg content

    // add color inside of circle for texture
    Content highlight = new Content();
    highlight
        .setShape(new Ellipse2D.Double(-radius * 0.7, -radius * 0.8, radius * 1.4, radius * 1.4));
    highlight.setPaint(new Color(255, 255, 255, 60));
    agg.add(highlight);

    double holeR = 2.25; // size of finger holes

    // first finger hole
    Content h1 = new Content();
    h1.setShape(new Ellipse2D.Double(-holeR - 5, -radius / 2.0, holeR * 2, holeR * 2));
    h1.setPaint(Color.BLACK);
    agg.add(h1);
    // second finger hole (mirrored to first)
    Content h2 = new Content();
    h2.setShape(new Ellipse2D.Double(5 - holeR, -radius / 2.0, holeR * 2, holeR * 2));
    h2.setPaint(Color.BLACK);
    agg.add(h2);
    // third finger hole (centered lower between h1 and h2)
    Content h3 = new Content();
    h3.setShape(new Ellipse2D.Double(-holeR, -radius / 2.0 + 10, holeR * 2, holeR * 2));
    h3.setPaint(Color.BLACK);
    agg.add(h3);

    addKeyTime(0, new Point2D.Double(0, 0), 0.0, 1.0, agg);
    addKeyTime(1, new Point2D.Double(0, 0), 0.0, 1.0, agg);
    setLocation(cx, cy); // place on screen
    setEndState(DescribedSprite.REMAIN);
  }

  @Override
  public void handleTick(int time)
  {
    // currently nothing
  }

}
