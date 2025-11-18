package bowlingSprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import event.Metronome;

import visual.dynamic.described.*;
import visual.statik.described.*;

public class BowlingPin extends RuleBasedSprite implements BowlingBallObserver
{
  private boolean live = false;
  private int tick;

  private boolean knocked = false;
  private Metronome metronome;


  private int row;

  Content pinContent;
  protected ArrayList<Integer> keyTimes;
  protected ArrayList<Point2D> locations;
  protected ArrayList<Double> rotations, scalings;
  private double x;
  private double y;

  public BowlingPin(TransformableContent content, int row, double startX, double startY)
  {
    super(content);
    this.tick = 0;
    this.x = startX;
    this.y = startY;

    this.row = row;

    this.keyTimes = new ArrayList<Integer>();
    this.locations = new ArrayList<Point2D>();
    this.rotations = new ArrayList<Double>();
    this.scalings = new ArrayList<Double>();
    setLocation(x, y);
  }
  
  public boolean isLive() {
    return live;
  }
  public void setLive(boolean live) {
    this.live = live;
  }
  
  public int getRow() {
    return row;
  }
  
  
  

  @Override
  public boolean intersects(Sprite s) {
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
    if ( (maxx < minxO) || (minx > maxxO) ||
    (maxy < minyO) || (miny > maxyO) ) retval = false;
    return retval;
  }

  @Override
  public void handleTick(int time)
  {
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
      }
    }
    this.tick = time;
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
  public void onBallHit(BowlingBall ball) {
    movePin(tick);
  }
  
  
  public void setMetronome(Metronome m) {
    this.metronome = m;
  }

  private void movePin(int time)
  {
//    if (metronome != null) {
//      metronome.reset();
//      metronome.start();
//    }
    keyTimes.clear();
    locations.clear();
    rotations.clear();
    scalings.clear();
    addKeyTime(time, new Point2D.Double(x, y), 0.0, 1.0);
    addKeyTime(time + 200, new Point2D.Double(x + 10, y), Math.PI/2, 0.8);
    //knocked = false;

    addKeyTime(time + 200, new Point2D.Double(x + 200, y), 0.0, 1.0);
    knocked = false;
  }

}
