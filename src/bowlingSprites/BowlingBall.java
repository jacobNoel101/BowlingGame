package bowlingSprites;

import java.awt.*;
import java.awt.geom.*;
import visual.dynamic.described.*;
import visual.statik.described.*;

public class BowlingBall extends DescribedSprite
{
  public BowlingBall()
  {
    AggregateContent agg = new AggregateContent();
    Content shape = new Content();
    double ballX = 1024 / 2.0 - 30;
    double ballY = 800;
    shape.setShape(new Ellipse2D.Double(ballX, ballY, 55, 55));
    shape.setPaint(Color.BLUE);
    agg.add(shape);
    addKeyTime(0, new Point2D.Double(0, 0), 0.0, 1.0, agg);
    addKeyTime(1, new Point2D.Double(0, 0), 0.0, 1.0, agg);
  }

}
