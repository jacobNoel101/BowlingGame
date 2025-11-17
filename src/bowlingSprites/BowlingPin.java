package bowlingSprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import visual.dynamic.described.*;
import visual.statik.described.Content;
import visual.statik.described.TransformableContent;

public class BowlingPin extends RuleBasedSprite
{
  private Point2D initialPosition;

  private boolean knocked = false;

  private int row;
  Content pinContent;
  protected ArrayList<Integer> keyTimes;
  protected ArrayList<Point2D> locations;
  protected ArrayList<Double> rotations, scalings;

  public BowlingPin(TransformableContent content, int row)
  {
    super(content);
    initialPosition = new Point2D.Double(x, y);

    this.row = row;
    this.keyTimes = new ArrayList<Integer>();
    this.locations = new ArrayList<Point2D>();
    this.rotations = new ArrayList<Double>();
    this.scalings = new ArrayList<Double>();
    setLocation(x,y);

  }
  
  @Override
  public boolean intersects(Sprite s)
  {
    boolean retval;
    double maxx, maxy, minx, miny;
    double maxxO, maxyO, minxO, minyO;

    Rectangle2D r;
    retval = true;
    r = getBounds2D(true);
    minx = r.getX();
    miny = r.getY();
    maxx = minx + r.getWidth();
    maxy = miny + r.getHeight();
    r = s.getBounds2D(true);
    minxO = r.getX();
    minyO = r.getY();
    maxxO = minxO + r.getWidth();
    maxyO = minyO + r.getHeight();
    if ((maxx < minxO) || (minx > maxxO) || (maxy < minyO) || (miny > maxyO))
      retval = false;
    return retval;

  }

  // @Override
  // public void render(Graphics g)
  // {
  // double rx, ry;
  // Polygon topPin = new Polygon();
  // Rectangle2D bounds;
  // visual.statik.TransformableContent tc;
  // if (visible)
  // {
  // tc = getContent();
  // if (tc != null)
  // {
  // // Find the point to rotate around
  // if (rotationPoint)
  // {
  // rx = rotationX;
  // ry = rotationY;
  // }
  // else
  // {
  // bounds = tc.getBounds2D(false);
  // rx = bounds.getWidth() / 2.0;
  // ry = bounds.getHeight() / 2.0;
  // }
  // // Transform
  // topPin.addPoint(0, 0);
  // topPin.addPoint(20, 0);
  // topPin.addPoint(20 - 5, -3); // x , y -> reduce y to make top smaller
  // topPin.addPoint(0 + 5, -3);
  //
  // TransformableContent topPinContent = new Content(topPin, Color.BLACK, Color.RED, null);
  // topPinContent.setLocation(x, y);
  // topPinContent.setRotation(angle, rx, ry);
  // topPinContent.setScale(scaleX, scaleY);
  // tc.setLocation(x, y);
  // tc.setRotation(angle, rx, ry);
  // tc.setScale(scaleX, scaleY);
  // // Render
  // tc.render(g);
  // topPinContent.render(g);
  // }
  // }
  //
  // }
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
  public void handleTick(int time)
  {

    if (!keyTimes.isEmpty())
    {
      int i = 0;
      // find the current segment
      while (i < keyTimes.size() - 1 && time > keyTimes.get(i + 1))
      {
        i++;
      }

      if (i < keyTimes.size() - 1)
      {
        int t0 = keyTimes.get(i);
        int t1 = keyTimes.get(i + 1);

        Point2D p0 = locations.get(i);
        Point2D p1 = locations.get(i + 1);

        double r0 = rotations.get(i);
        double r1 = rotations.get(i + 1);

        double s0 = scalings.get(i);
        double s1 = scalings.get(i + 1);

        double t = (time - t0) / (double) (t1 - t0);

        if (p0 != null && p1 != null)
        {
          x = lerp(p0.getX(), p1.getX(), t);
          y = lerp(p0.getY(), p1.getY(), t);
        }

        double rotation = lerp(r0, r1, t);
        setRotation(rotation);

        double scale = lerp(s0, s1, t);
        setScale(scale);
      }
    }
    setLocation(x, y);
    Iterator<Sprite> i;
    Sprite ball;
    i = antagonists.iterator();
    while (i.hasNext())
    {
      ball = i.next();

      if (intersects(ball) && !knocked)
      {
        knocked = true;
        movePin(time);
        
      }
    }
    setLocation(x, y);
  }

  private double lerp(double a, double b, double t)
  {
    return a + (b - a) * t;
  }
  
  private void clear() {
    keyTimes.clear();
    locations.clear();
    rotations.clear();
    scalings.clear();
  }

  public int addKeyTime(int keyTime, Point2D location, Double rotation, Double scaling)
  {
    int existingKT = -1;
    int i = 0;
    boolean keepLooking = true;

    while ((i < keyTimes.size()) && keepLooking)
    {
      existingKT = keyTimes.get(i);
      if (existingKT >= keyTime)
        keepLooking = false;
      else
        i++;
    }

    if ((existingKT == i) && !keepLooking)
    { // Duplicate
      i = -1;
    }
    else
    {
      keyTimes.add(i, keyTime);
      locations.add(i, location);
      rotations.add(i, rotation);
      scalings.add(i, scaling);
    }
    return i;
  }

  private void movePin(int time)
  {
    keyTimes.clear();
    locations.clear();
    rotations.clear();
    scalings.clear();
    addKeyTime(time, new Point2D.Double(x, y), 0.0, 1.0);
    addKeyTime(time + 200, new Point2D.Double(x + 200, y), 0.0, 1.0);
    knocked = false;

  }
}
