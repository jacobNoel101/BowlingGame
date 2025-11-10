package gui;

import java.awt.geom.*;
import java.awt.image.BufferedImage;
import bowlingSprites.*;
import io.ResourceFinder;
import visual.dynamic.described.*;
import visual.statik.sampled.*;

public class BowlingScreen extends Stage
{

  public BowlingScreen(final int timeStep)
  {
    super(timeStep);
    SampledSprite lane = buildLane();
    add(lane);
    SampledSprite scoreboard = buildScoreBoard();
    add(scoreboard);
    add(new BowlingBall());
    // for (BowlingPin p : makePins())
    // add(p);
  }

  private SampledSprite buildScoreBoard()
  {
    ResourceFinder finder = ResourceFinder.createInstance(this);
    ImageFactory factory = new ImageFactory(finder);
    BufferedImage img = factory.createBufferedImage("/resources/scoreSheet.jpg", 1);
    Content scoreboardImage = new Content(img, 0, 0);
    SampledSprite sprite = new SampledSprite();
    sprite.addKeyTime(0, new Point2D.Double(0, 0), 0.0, 1.0, scoreboardImage);
    sprite.addKeyTime(1, new Point2D.Double(0, 0), 0.0, 1.0, scoreboardImage);
    return sprite;
  }

  private SampledSprite buildLane()
  {
    ResourceFinder finder = ResourceFinder.createInstance(this);
    ImageFactory factory = new ImageFactory(finder);
    BufferedImage img = factory.createBufferedImage("/resources/bowlingLane.jpg", 1);
    Content laneImage = new Content(img, 0, 0);
    SampledSprite sprite = new SampledSprite();
    sprite.addKeyTime(0, new Point2D.Double(0, 0), 0.0, 1.0, laneImage);
    sprite.addKeyTime(1, new Point2D.Double(0, 0), 0.0, 1.0, laneImage);
    return sprite;
  }
  //
  // private BowlingPin[] makePins()
  // {
  // BowlingPin[] arr = new BowlingPin[10];
  // double x = 507;
  // double y = 280;
  // arr[0] = new BowlingPin(x, y);
  // arr[1] = new BowlingPin(x - 20, y - 20);
  // arr[2] = new BowlingPin(x + 20, y - 20);
  // arr[3] = new BowlingPin(x - 40, y - 40);
  // arr[4] = new BowlingPin(x, y - 40);
  // arr[5] = new BowlingPin(x + 40, y - 40);
  // arr[6] = new BowlingPin(x - 45, y - 60);
  // arr[7] = new BowlingPin(x - 25, y - 60);
  // arr[8] = new BowlingPin(x + 25, y - 60);
  // arr[9] = new BowlingPin(x + 45, y - 60);
  // return arr;
  // }

  @Override
  public void handleTick(int time)
  {
  }

}
