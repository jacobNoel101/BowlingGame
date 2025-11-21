package bowlingSprites;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.*;
import java.util.*;

import bowling.GameState;
import visual.dynamic.described.*;
import visual.statik.described.*;

public class BowlingPin extends RuleBasedSprite implements BowlingBallObserver
{
  private boolean live = false;
  private int tick;
  private boolean knocked = false;
  protected ArrayList<AggregateContent> content;
  private int row;
  Content pinContent;
  private GameState gameState;

  protected ArrayList<Integer> keyTimes;
  protected ArrayList<Point2D> locations;
  protected ArrayList<Double> rotations, scalings;
  private double x;
  private double y;
  private double originalX;
  private double originalY;

  public BowlingPin(TransformableContent content, int row, double startX, double startY)
  {
    super(content);
    this.content = new ArrayList<>();
    this.tick = 0;
    this.x = startX;
    this.y = startY;
    this.row = row;
    this.keyTimes = new ArrayList<Integer>();
    this.locations = new ArrayList<Point2D>();
    this.rotations = new ArrayList<Double>();
    this.scalings = new ArrayList<Double>();
    setLocation(x, y);
    originalX = startX;
    originalY = startY;
  }

  public void setGameState(GameState gameState)
  {
    this.gameState = gameState;
  }

  public boolean isLive()
  {
    return live;
  }

  public void setLive(boolean live)
  {
    this.live = live;
  }

  public int getRow()
  {
    return row;
  }

  @Override
  public void handleTick(int time)
  {
    boolean tweeningActive = false;

    if (!keyTimes.isEmpty())
    {
      int i = 0;
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
        tweeningActive = true; // still animating

      }
    }

    if (!tweeningActive && knocked)
    {
      setVisible(false);
    }
    this.tick = time;
    setLocation(x, y);
    Iterator<Sprite> i;
    Sprite sprite;
    i = antagonists.iterator();
    while (i.hasNext())
    {
      sprite = i.next();
      if (sprite instanceof BowlingBall)
      {
        if (intersects(sprite) && !knocked)
        {
          knocked = true;
          row++;
          movePin(time, (BowlingBall) sprite);
        }

      }
      if (sprite instanceof BowlingPin)
      {
        BowlingPin other = (BowlingPin) sprite;
        // Only collide if in same row
        if (!intersects(other))
          continue;
        // This pin is knocked, other is NOT so knock the other
        if (this.knocked && !other.knocked)
        {
          other.knocked = true;
          other.row++;
          other.movePin(time, this);
          if (gameState != null)
          {
            gameState.pinKnocked(); // notify GameState
          }
          continue;
        }
      }

    }

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
    if (s instanceof BowlingPin)
    {
      BowlingPin pin = (BowlingPin) s;

      // Only collide if in SAME row
      if (pin.row != this.row)
      {
        return false;
      }

      // Basic bounding box overlap
      if ((maxx < minxO) || (minx > maxxO) || (maxy < minyO) || (miny > maxyO))
      {
        return false;
      }
    }
    return true;

  }

  private double lerp(double a, double b, double t)
  {
    return a + (b - a) * t;
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
    {
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

  @Override
  public void onBallHit(BowlingBall ball)
  {
    if (!knocked)
    {
      knocked = true; // mark pin as knocked
      movePin(tick, ball); // animate the pin falling
      if (gameState != null)
      {
        gameState.pinKnocked(); // notify GameState
      }

    }
  }

  private void movePin(int time, Sprite sprite)
  {

    keyTimes.clear();
    locations.clear();
    rotations.clear();
    scalings.clear();

    if (sprite instanceof BowlingBall)
    {
      BowlingBall ball = (BowlingBall) sprite;

      double ballX = ball.getBounds2D().getX();
      double pinCenterX = this.x + getBounds2D(true).getWidth() / 2;

      boolean fallRight = ballX < pinCenterX;

      double push = fallRight ? 20 : -20; // how far it moves
      double rotation = fallRight ? Math.PI / 2 : -Math.PI / 2; // 90° or -90°

      addKeyTime(time, new Point2D.Double(x, y), 0.0, 1.0);

      // Keyframe: tipping over
      addKeyTime(time + 100, new Point2D.Double(x + push, y - 10), rotation, 0.8);
      return;
    }
    if (sprite instanceof BowlingPin)
    {
      BowlingPin pin = (BowlingPin) sprite;

      Rectangle2D thisBounds = getBounds2D(true);
      Rectangle2D otherBounds = pin.getBounds2D(true);

      boolean fallRight = otherBounds.getMaxX() < thisBounds.getCenterX();

      double push = fallRight ? 30 : -30;
      double rotation = fallRight ? Math.PI / 2 : -Math.PI / 2; // 90° or -90°

      addKeyTime(time, new Point2D.Double(x, y), 0.0, 1.0);

      addKeyTime(time + 100, new Point2D.Double(x + push, y - 10), rotation, 0.8);
      return;
    }
  }

  public void resetPin()
  {
    knocked = false;
    setRotation(0);
    setScale(1.0);
    setLocation(originalX, originalY);
    keyTimes.clear();
    locations.clear();
    rotations.clear();
    scalings.clear();
    setVisible(true);

  }

  public double getOriginalX()
  {
    // TODO Auto-generated method stub
    return originalX;
  }

  public double getOriginalY()
  {
    // TODO Auto-generated method stub
    return originalY;
  }
}
