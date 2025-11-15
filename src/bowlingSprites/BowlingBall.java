package bowlingSprites;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.described.*;

public class BowlingBall extends RuleBasedSprite implements KeyListener
{
  private int roll = 0;
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
  }

  @Override
  public void handleTick(int time) {
      // --- Tweening logic ---
      if (!keyTimes.isEmpty()) {
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

              // Optional: apply rotation/scale if your engine supports it
              // setRotation(lerp(r0, r1, t));
              // setScale(lerp(s0, s1, t));
          }
      }

      setLocation(x, y);
  }
  
  
  private double lerp(double a, double b, double t) {
    return a + (b - a) * t;
  }
  @Override
  public void keyPressed(KeyEvent e)
  {
    int code = e.getKeyCode();

    if (code == KeyEvent.VK_LEFT)
    {
      x -= 10;
    }
    else if (code == KeyEvent.VK_RIGHT)
    {
      x += 10;
    }
    else if (code == KeyEvent.VK_SPACE)
    {
      roll = 1;
      if (roll == 1) {
        initiateRoll();
        
        roll = 0;
      } else {
        roll = 1;

      }
    }
    // else if (code == KeyEvent.VK_UP)
    // {
    // y -= 10;
    // }
    // else if (code == KeyEvent.VK_DOWN)
    // {
    // y += 10;
    // }
  }
  
  protected int addKeyTime(int keyTime, Point2D location, Double rotation, Double scaling) {
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
    // Example: move from current position to top over time
    int startTick = 0;
    int endTick = 200;

    addKeyTime(startTick, new Point2D.Double(x, y), 0.0, 1.0);
    addKeyTime(endTick, new Point2D.Double(x, 0), 0.0, 1.0); // moves up
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
