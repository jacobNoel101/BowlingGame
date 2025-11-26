package bowlingSprites;

import java.util.*;
import bowling.GameState;
import visual.dynamic.described.*;
import visual.statik.described.*;

public class BowlingPin extends RuleBasedSprite
{

  private boolean hit = false; // touched by ball or other pin
  private boolean falling = false; // actively tipping
  private boolean knocked = false; // finished falling
  private double x, y;
  private double fallDirection = 1; // 1 = right, -1 = left
  private final double originalX, originalY;
  private double tiltBack = 0; // visual backward tilt angle
  private double tiltBackVelocity = 0;
  private double velocityX = 0;
  private double velocityY = 0;
  private double rotation = 0; // current rotation
  private double angularVelocity = 0; // rotation speed
  private final double radius;
  private ArrayList<Sprite> antagonists = new ArrayList<>();
  private GameState gameState;

  public BowlingPin(TransformableContent content, double startX, double startY, double radius)
  {
    super(content);
    this.x = startX;
    this.y = startY;
    this.originalX = startX;
    this.originalY = startY;
    this.radius = radius;
    setLocation(x, y);
    setVisible(true);
  }

  public void setGameState(GameState gameState)
  {
    this.gameState = gameState;
  }

  // called when a pin is hit by the ball
  public void hitByBall(BowlingBall ball)
  {
    if (hit)
    {
      return;
    }
    hit = true;

    // compute linear velocity away from ball
    double dx = x - ball.getX();
    fallDirection = (dx >= 0) ? 1 : -1; // ball hits left → fall right, ball hits right → fall left
    double dy = y - ball.getY();
    double length = Math.hypot(dx, dy);
    double speed = 3 + Math.random() * 2; // small random for realism
    velocityX = dx / length * speed;
    velocityY = dy / length * speed;
    falling = true; // start tipping over
    angularVelocity = 0.05 + Math.random() * 0.05;
    tiltBackVelocity = 0.05 + Math.random() * 0.03;
    tiltBackVelocity *= fallDirection; // apply left/right
  }

  // called when another pin collides
  public void startMoving(double vx, double vy)
  {
    if (!hit)
    {
      hit = true;
    }
    velocityX += vx;
    velocityY += vy;
    falling = true;
    if (angularVelocity == 0)
    {
      angularVelocity = 0.03 + Math.random() * 0.02;
    }
  }

  // pin to pin collision
  public boolean intersectsPin(BowlingPin other)
  {
    double dx = other.x - x;
    double dy = other.y - y;
    return Math.hypot(dx, dy) < (this.radius + other.radius);
  }

  public void handleTick(int time)
  {
    if (falling)
    {
      // linear motion
      x += velocityX;
      y += velocityY;
      velocityX *= 0.92; // friction
      velocityY *= 0.92;

      // rotation (tipping)
      rotation += angularVelocity * fallDirection;
      angularVelocity *= 0.95; // rotational friction
      if (Math.abs(angularVelocity) < 0.001)
      {
        angularVelocity = 0;
      }
      // small scale change to exaggerate fall
      tiltBack += tiltBackVelocity;
      tiltBackVelocity *= 0.95;
      if (tiltBackVelocity < 0.001)
      {
        tiltBackVelocity = 0;
      }
      setScale(1.0 + 0.1 * Math.sin(tiltBack));

      // stops when almost stationary
      if (Math.hypot(velocityX, velocityY) < 0.2 && angularVelocity < 0.2 && !knocked)
      {
        knocked = true;
        falling = false;
        setVisible(false);

        if (gameState != null)
        {
          gameState.pinKnocked(this);
        }
      }

      // collisions with other pins
      for (Sprite s : antagonists)
      {
        if (s instanceof BowlingPin)
        {
          BowlingPin other = (BowlingPin) s;
          if (other != this && !other.hit && intersectsPin(other))
          {
            double transfer = 0.7;
            other.startMoving(velocityX * transfer, velocityY * transfer);
          }
        }
      }
    }
    setLocation(x, y);
    setRotation(rotation); // visual tipping
  }

  public void resetPin()
  {
    hit = false;
    falling = false;
    knocked = false;
    velocityX = 0;
    velocityY = 0;
    angularVelocity = 0;
    tiltBack = 0; // visual backward tilt angle
    tiltBackVelocity = 0;
    rotation = 0;
    x = originalX;
    y = originalY;
    setLocation(x, y);
    setRotation(0);
    setVisible(true);
  }

  public void addAntagonist(Sprite s)
  {
    antagonists.add(s);
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  public double getRadius()
  {
    return radius;
  }

  public boolean isKnocked()
  {
    return knocked;
  }

  public boolean isHit()
  {
    return hit;
  }

  public double getVelocityX()
  {
    return velocityX;
  }

  public double getVelocityY()
  {
    return velocityY;
  }

  public double getAngularVelocity()
  {
    return angularVelocity;
  }

}
