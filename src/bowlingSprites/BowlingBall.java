package bowlingSprites;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import event.Metronome;
import visual.dynamic.described.RuleBasedSprite;
import visual.dynamic.described.Sprite;
import visual.statik.described.*;

public class BowlingBall extends RuleBasedSprite implements KeyListener
{
  private boolean rolling;
  private Metronome metronome;
  private boolean showArrow;
  private int roll;
  private double x, y;
  private Double speed;
  protected ArrayList<Integer> keyTimes;
  protected ArrayList<Point2D> locations;
  protected ArrayList<Double> rotations, scalings;


  public BowlingBall(TransformableContent content, Double speed)
  {
    super(content);
    if (speed == null)
    {
      this.speed = 10.0;
    }
    else
    {
      this.speed = speed;
    }
    this.keyTimes = new ArrayList<Integer>();
    this.locations = new ArrayList<Point2D>();
    this.rotations = new ArrayList<Double>();
    this.scalings = new ArrayList<Double>();
    this.x = 500;
    this.y = 650;
    setLocation(x, y);
    this.roll = 1;
  }
  
 

  public void setMetronome(Metronome m) {
      this.metronome = m;
  }

  @Override
  public void handleTick(int time) {
      // --- Tweening logic ---
      if (rolling && !keyTimes.isEmpty()) {
          int i = 0;
          // find the current segment
          while (i < keyTimes.size() - 1 && time > keyTimes.get(i + 1)) {
              i++;
          }

          if (i < keyTimes.size() - 1) {
              int t0 = keyTimes.get(i);
              int t1 = keyTimes.get(i + 1);

              Point2D p0 = locations.get(i);
              Point2D p1 = locations.get(i + 1);

              double r0 = rotations.get(i);
              double r1 = rotations.get(i + 1);

              double s0 = scalings.get(i);
              double s1 = scalings.get(i + 1);

              double t = (time - t0) / (double) (t1 - t0);

              if (p0 != null && p1 != null) {
                  x = lerp(p0.getX(), p1.getX(), t);
                  y = lerp(p0.getY(), p1.getY(), t);
              }

              double rotation = lerp(r0, r1, t);
              setRotation(rotation);

              double scale = lerp(s0, s1, t);
              setScale(scale);
          }
      }
      
      setLocation(x,y);
      for (Sprite pin : antagonists) {
        if (this.intersects(pin)) {   // make sure intersects() is correct
            ((BowlingPin) pin).onBallHit(this);              // move only this pin
        }
      }    
  }
  @Override
  public void render(Graphics g)
  {
    super.render(g);
    
    if (showArrow) {
      int arrowX = (int) x;        // x-coordinate of the ball
      int arrowY = (int) y - 50;   // position above the ball
      int arrowLength = 40;         // length of the line

      g.setColor(Color.RED);
      g.drawLine(arrowX, arrowY, arrowX, arrowY - arrowLength); // vertical line

      // Draw simple triangle arrowhead at the top
      Polygon arrowHead = new Polygon();
      arrowHead.addPoint(arrowX, arrowY - arrowLength);
      arrowHead.addPoint(arrowX - 5, arrowY - arrowLength + 10);
      arrowHead.addPoint(arrowX + 5, arrowY - arrowLength + 10);
      g.fillPolygon(arrowHead);
      TransformableContent arrowContent = new Content(arrowHead, Color.BLACK, Color.BLACK, null);
      arrowContent.render(g);

      
    }

  }

  private double lerp(double a, double b, double t) {
    return a + (b - a) * t;
  }
  @Override
  public void keyPressed(KeyEvent e)
  {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT) x -= 10;
    else if (code == KeyEvent.VK_RIGHT) x += 10;
    else if (code == KeyEvent.VK_SPACE)
    {
      if (roll == 0) {
        initiateRoll();
        roll = 1;
      } else {
        showRotationIndicator();
        roll = 0;
      }
    }
    setLocation(x,y);
  }
  
  private void showRotationIndicator()
  {
    showArrow = true;
  }

  public int addKeyTime(int keyTime, Point2D location, Double rotation, Double scaling) {
    int existingKT = -1;
    int i = 0;
    boolean keepLooking = true;

    while ((i < keyTimes.size()) && keepLooking) {
        existingKT = keyTimes.get(i);
        if (existingKT >= keyTime) keepLooking = false;
        else i++;
    }

    if ((existingKT == i) && !keepLooking) { // Duplicate
        i = -1;
    } else {
        keyTimes.add(i, keyTime);
        locations.add(i, location);
        rotations.add(i, rotation);
        scalings.add(i, scaling);
    }
    return i;
  }
  
  private void initiateRoll() {
    if (metronome != null) {
      metronome.reset();
      metronome.start();
    }
    int endTick = 1500;
    rolling = true;
    keyTimes.clear();
    locations.clear();
    rotations.clear();
    scalings.clear();
    showArrow = false;
    addKeyTime(0, new Point2D.Double(x, y), 0.0, 1.0); // moves up
    addKeyTime(endTick, new Point2D.Double(x, y - 420), 0.0, .4); // moves up
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
  public void keyTyped(KeyEvent e)
  {

  }

  @Override
  public void keyReleased(KeyEvent e)
  {
  }

}
