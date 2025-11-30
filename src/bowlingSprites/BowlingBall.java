package bowlingSprites;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import bowling.GameState;
import event.Metronome;
import visual.dynamic.described.*;
import visual.statik.described.*;

public class BowlingBall extends RuleBasedSprite implements KeyListener
{
  private double rollingAngle; // in radians
  private double aimOffset; // horizontal offset while aiming
  private boolean rolling;
  private Metronome metronome;
  private boolean showArrow;
  private GameState gameState;
  private boolean waitingForPins = false; // ball is moving, independent of pins
  private boolean inGutter = false;
  private boolean gutterLeft = false;
  private double gutterSpeedY = 6.0;
  private boolean gutterFinished = false;
  private double x, y;
  protected ArrayList<Integer> keyTimes;
  protected ArrayList<Point2D> locations;
  protected ArrayList<Double> rotations, scalings;
  public BallReflection reflectionSprite;

  public BowlingBall(TransformableContent content, Double speed, TransformableContent reflection)
  {
    super(content);
    this.gameState = null;
    this.rollingAngle = 0.0;
    this.aimOffset = 0;
    this.keyTimes = new ArrayList<Integer>();
    this.locations = new ArrayList<Point2D>();
    this.rotations = new ArrayList<Double>();
    this.scalings = new ArrayList<Double>();
    this.x = 495;
    this.y = 650;
    this.reflectionSprite = new BallReflection(reflection);

    setLocation(x, y);
  }

  public void setMetronome(Metronome m)
  {
    this.metronome = m;
  }

  public void setGameState(GameState gameState)
  {
    this.gameState = gameState;
  }

  public void handleTick(int time)
  {
    if (reflectionSprite != null)
    {
      reflectionSprite.setOrigin(x, y); // offset to look like a reflection
      reflectionSprite.handleTick(time);
    }

    if (inGutter)
    {
      updateGutterMovement();
      return;
    }

    if (rolling && !keyTimes.isEmpty())
    {
      int i = 0;
      while (i < keyTimes.size() - 1 && time > keyTimes.get(i + 1))
        i++;
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

      setLocation(x, y);
      if (rolling && checkGutterCollision())
      {
        inGutter = true;
        gutterFinished = false;
        rolling = false;
        updateGutterMovement();
        return;
      }

      if (time >= keyTimes.get(keyTimes.size() - 1))
      {
        rolling = false;
      }
    }
    // Check collisions with pins
    for (Sprite s : antagonists)
    {
      if (s instanceof BowlingPin)
      {
        BowlingPin pin = (BowlingPin) s;
        if (intersects(pin) && !pin.isKnocked())
        {
          pin.hitByBall(this);
        }
      }
    }
    // Wait for pins to settle after the ball finishes
    if (!rolling && waitingForPins && gameState != null)
    {
      boolean allPinsStopped = true;
      for (GameState.PinData pd : gameState.getPins())
      {
        BowlingPin pin = pd.pin;
        if (pin.isHit() && !pin.isKnocked() && (Math.abs(pin.getVelocityX()) > 0.1
            || Math.abs(pin.getVelocityY()) > 0.1 || Math.abs(pin.getAngularVelocity()) > 0.001))
        {
          allPinsStopped = false;
          break;
        }
      }
      if (allPinsStopped)
      {
        waitingForPins = false;
        gameState.ballStopped(); // now scores update correctly
      }
    }

  }

  private void updateGutterMovement()
  {
    // 1. Slide upward
    y -= gutterSpeedY;
    if (y < 220)
      y = 220; // clamp to top of lane
    // Compute lane geometry (same as BowlingGutter)
    int screenW = 1000;
    int screenH = 900;
    int backWallY = 100;
    int laneTopWidth = 180;
    int laneBottomWidth = 1100;
    int wallBottomY = backWallY + 120;
    int laneTopY = wallBottomY;
    int laneBottomY = screenH;
    double t = (y - laneBottomY) / (laneTopY - laneBottomY);
    t = Math.max(0, Math.min(1, t));
    double laneTopLeftX = (screenW - laneTopWidth) / 2.0;
    double laneTopRightX = laneTopLeftX + laneTopWidth;
    double laneBottomLeftX = (screenW - laneBottomWidth) / 2.0;
    double laneBottomRightX = laneBottomLeftX + laneBottomWidth;
    double targetX;
    if (gutterLeft)
    {
      targetX = laneBottomLeftX + t * (laneTopLeftX - laneBottomLeftX);
      x = targetX - 20;
    }
    else
    {
      targetX = laneBottomRightX + t * (laneTopRightX - laneBottomRightX);
      x = targetX + 20;
    }
    setLocation(x, y);
    if (inGutter)
    {
      double scale = 1.0 - 0.7 * t;
      setScale(scale);
    }
    else
    {
      double scale = 1.0 - 0.6 * t;
      setScale(scale);
    }
    if (y <= laneTopY && !gutterFinished)
    {
      gutterFinished = true;
      inGutter = false;

      if (gameState != null)
      {
        if (!gameState.anyPinsHit())
        {
          gameState.ballStopped();
        }
        else
        {
          waitingForPins = true;
        }
      }
    }
  }

  private boolean checkGutterCollision()
  {
    double yPos = this.y;
    // Lane geometry (MATCHES BowlingGutter)
    int screenW = 1000;
    int screenH = 900;
    int backWallY = 100;
    int laneTopWidth = 180;
    int laneBottomWidth = 1100;
    int wallBottomY = backWallY + 120;
    // interpolation factor
    double t = (yPos - wallBottomY) / (screenH - wallBottomY);
    t = Math.max(0, Math.min(1, t));
    double halfTop = laneTopWidth / 2.0;
    double halfBottom = laneBottomWidth / 2.0;
    double halfLane = halfTop + (halfBottom - halfTop) * t;
    double laneCenter = screenW / 2.0;
    // Left gutter
    if (x < laneCenter - halfLane)
    {
      gutterLeft = true;
      return true;
    }
    // Right gutter
    if (x > laneCenter + halfLane)
    {
      gutterLeft = false;
      return true;
    }
    return false;
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  @Override
  public void render(Graphics g)
  {
    super.render(g);
    if (showArrow)
    {
      int arrowX = (int) x + 5; // x coord
      int arrowY = (int) y - 50; // distance from ball
      int arrowLength = 50; // arrow line length
      int tipx = arrowX + (int) (arrowLength * Math.sin(rollingAngle));
      int tipY = arrowY - (int) (arrowLength * Math.cos(rollingAngle));

      g.setColor(Color.BLACK);
      g.drawLine(arrowX, arrowY, tipx, tipY); // draw line
      // plot and draw triangular arrowhead
      Polygon arrowHead = new Polygon();
      g.setColor(Color.RED);

      double angle = Math.atan2(tipY - arrowY, tipx - arrowX);
      int headSize = 10;
      arrowHead.addPoint(tipx, tipY);
      arrowHead.addPoint(tipx - (int) (headSize * Math.cos(angle + Math.PI / 6)),
          tipY - (int) (headSize * Math.sin(angle + Math.PI / 6)));
      arrowHead.addPoint(tipx - (int) (headSize * Math.cos(angle - Math.PI / 6)),
          tipY - (int) (headSize * Math.sin(angle - Math.PI / 6)));
      g.fillPolygon(arrowHead);
      TransformableContent arrowContent = new Content(arrowHead, Color.BLACK, Color.BLACK, null);
      arrowContent.render(g);
    }
  }

  private double lerp(double a, double b, double t)
  {
    return a + (b - a) * t;
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    int code = e.getKeyCode();
    if (code == KeyEvent.VK_LEFT)
    {
      if (showArrow)
      {
        aimOffset -= 10;
        updateArrowAngle();
      }
      else
      {
        double newX = x - 10;

        // Only allow movement if newX is still inside the lane
        if (!wouldBeInGutter(newX, y))
          x = newX;
      }
    }
    else if (code == KeyEvent.VK_RIGHT)
    {
      if (showArrow)
      {
        aimOffset += 10;
        updateArrowAngle();
      }
      else
      {
        double newX = x + 10;

        if (!wouldBeInGutter(newX, y))
          x = newX;
      }
    }
    else if (code == KeyEvent.VK_SPACE)
    {
      if (gameState != null)
      {
        if (!gameState.isAiming())
        {
          gameState.startAiming();
          showArrow = true;
        }
        else
        {
          gameState.playerRollRequested(rollingAngle);
          showArrow = false;
        }
      }
    }
    setLocation(x, y);
  }

  private void updateArrowAngle()
  {
    rollingAngle = Math.atan2(aimOffset, 420);
  }

  public void startRoll(double angle)
  {
    this.rollingAngle = angle;
    rolling = true; // animation starts
    waitingForPins = true;
    initiateRoll();
  }

  private boolean wouldBeInGutter(double futureX, double yPos)
  {
    int screenW = 1000;
    int screenH = 900;
    int backWallY = 100;
    int laneTopWidth = 180;
    int laneBottomWidth = 1100;
    int wallBottomY = backWallY + 120;
    double t = (yPos - wallBottomY) / (screenH - wallBottomY);
    t = Math.max(0, Math.min(1, t));
    double halfTop = laneTopWidth / 2.0;
    double halfBottom = laneBottomWidth / 2.0;
    double halfLane = halfTop + (halfBottom - halfTop) * t;
    double laneCenter = screenW / 2.0;
    double radius = 35;
    if (futureX - radius < laneCenter - halfLane)
      return true;
    if (futureX + radius > laneCenter + halfLane)
      return true;
    return false;
  }

  public void resetBall()
  {
    this.x = 495;
    this.y = 650;
    setLocation(x, y);
    setRotation(0);
    setScale(1.0);
    showArrow = false;
    aimOffset = 0;
    rollingAngle = 0;
    setVisible(true);
    keyTimes.clear();
    locations.clear();
    rotations.clear();
    scalings.clear();
    rolling = false;
    waitingForPins = false;

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

  private void initiateRoll()
  {
    if (metronome != null)
    {
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
    double totalDistanceY = 460;
    double totalDistanceX = Math.tan(rollingAngle) * totalDistanceY;
    addKeyTime(0, new Point2D.Double(x, y), 0.0, 1.0);
    addKeyTime(endTick, new Point2D.Double(x + totalDistanceX, y - totalDistanceY), 0.0, .4);
  }

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

  @Override
  public void keyTyped(KeyEvent e)
  {
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
  }

}
