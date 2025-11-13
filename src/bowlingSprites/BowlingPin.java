package bowlingSprites;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import visual.dynamic.described.*;
import visual.statik.described.AggregateContent;
import visual.statik.described.Content;

public class BowlingPin extends DescribedSprite
{
  Content pinContent;
  public BowlingPin() {
    Rectangle2D shape = new Rectangle2D.Double(0,0, 500, 350);
    
    Rectangle2D bounds = shape.getBounds2D();
    AffineTransform at = new AffineTransform();
    Shape centeredShape = at.createTransformedShape(shape);
    Point2D center = new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());

                                                           // 255)
    // Create Content for the first key time
    Content cloudShapeContent = new Content(centeredShape, Color.black, Color.black,
        new BasicStroke(1.0f));

    // Wrap it in AggregateContent
    AggregateContent aggregateCloudContent = new AggregateContent();
    aggregateCloudContent.add(cloudShapeContent);
    aggregateCloudContent.setLocation(100, 100);
    Point2D location = new Point2D.Double(x, y);
    addKeyTime(0, center, null, 1.0, aggregateCloudContent);

    

  }
  
  
  @Override
  public void render(Graphics g)
  {
    super.render(g);
    Graphics2D g2 = (Graphics2D) g;
    pinContent.render(g2);
  }
  
}
