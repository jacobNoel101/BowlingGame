package bowlingSprites;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.*;
import visual.dynamic.described.DescribedSprite;
import visual.dynamic.described.RuleBasedSprite;
import visual.statik.described.*;

public class BowlingBall extends RuleBasedSprite implements KeyListener
{
  private double x, y;
  private Double speed;
  

  public BowlingBall(TransformableContent content, Double speed) 
  {
    super(content);
    if (speed == null) {
      this.speed = 10.0;
    } else {
      this.speed = speed;
    }
    this.x = 500;
    this.y = 500;
    setLocation(x,y);
  }

  @Override
  public void handleTick(int time)
  {
    setLocation(x, y);
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
      x += 10;
    }
//    else if (code == KeyEvent.VK_UP)
//    {
//      y -= 10;
//    }
//    else if (code == KeyEvent.VK_DOWN)
//    {
//      y += 10;
//    }
    
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
