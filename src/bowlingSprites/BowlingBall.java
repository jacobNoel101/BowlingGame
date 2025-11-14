package bowlingSprites;

import java.awt.*;
import java.awt.geom.*;
import visual.dynamic.described.DescribedSprite;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.described.*;

public class BowlingBall extends RuleBasedSprite
{
  private final int radius = 25; // ball size
  private double cx = 500.0; // x pos on screen
  private double cy = 650.0; // y pos on screen
  double maxX;
  double maxY;
  Double speed;
  

  public BowlingBall(TransformableContent content, double width, double height, Double speed) 
  {
    super(content);
    this.maxX = width;
    this.maxY = height;
    if (speed == null) {
      this.speed = 10.0;
    } else {
      this.speed = speed;
    }
    setLocation(maxX, maxY);
  }

  @Override
  public void handleTick(int time)
  {
    // currently nothing
  }

}
