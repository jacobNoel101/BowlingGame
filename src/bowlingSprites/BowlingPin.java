package bowlingSprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import visual.dynamic.described.*;
import visual.statik.described.Content;
import visual.statik.described.TransformableContent;

public class BowlingPin extends RuleBasedSprite
{
  private int row;
  Content pinContent;


  public BowlingPin(TransformableContent content, int row)
  {
    super(content);
    this.row = row;
  }

  @Override
  public void render(Graphics g)
  {
    double rx, ry;
    Polygon topPin = new Polygon();
    Rectangle2D bounds;
    visual.statik.TransformableContent tc;
    if (visible)
    {
      tc = getContent();
      if (tc != null)
      {
        // Find the point to rotate around
        if (rotationPoint)
        {
          rx = rotationX;
          ry = rotationY;
        }
        else
        {
          bounds = tc.getBounds2D(false);
          rx = bounds.getWidth() / 2.0;
          ry = bounds.getHeight() / 2.0;
        }
        // Transform
        topPin.addPoint(0, 0);
        topPin.addPoint(30, 0);
        topPin.addPoint(30 - 5, -5); // x , y  -> reduce y to make top smaller
        topPin.addPoint(0 + 5, -5);

        TransformableContent topPinContent = new Content(topPin, Color.black, Color.gray, null);
        topPinContent.setLocation(x, y);
        topPinContent.setRotation(angle, rx, ry);
        topPinContent.setScale(scaleX, scaleY);
        tc.setLocation(x, y);
        tc.setRotation(angle, rx, ry);
        tc.setScale(scaleX, scaleY);
        // Render
        tc.render(g);
        topPinContent.render(g);
      }
    }

  }
  // setLocation(maxX, maxY);
  // Rectangle2D shape = new Rectangle2D.Double(0,0, 500, 350);
  //
  // Rectangle2D bounds = shape.getBounds2D();
  // AffineTransform at = new AffineTransform();
  // Shape centeredShape = at.createTransformedShape(shape);
  // Point2D center = new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
  //
  // // 255)
  // // Create Content for the first key time
  // Content cloudShapeContent = new Content(centeredShape, Color.black, Color.black,
  // new BasicStroke(1.0f));
  //
  // // Wrap it in AggregateContent
  // AggregateContent aggregateCloudContent = new AggregateContent();
  // aggregateCloudContent.add(cloudShapeContent);
  // aggregateCloudContent.setLocation(100, 100);
  // Point2D location = new Point2D.Double(x, y);
  // //addKeyTime(0, center, null, 1.0, aggregateCloudContent);

  @Override
  public void handleTick(int arg0)
  {
    // TODO Auto-generated method stub

  }

}
