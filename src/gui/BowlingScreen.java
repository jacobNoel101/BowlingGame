package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import bowling.*;
import bowlingSprites.*;
import bowlingVisual.*;
import io.ResourceFinder;
import visual.dynamic.described.*;
import visual.statik.described.Content;
import resources.Marker;

public class BowlingScreen extends Stage
{

  public BowlingScreen(final int timeStep)
  {
    super(timeStep);
    add(buildBackground());
    visual.statik.sampled.Content lane = buildLane();
    add(lane);
    ScoreBoard scoreboard = buildScoreBoard();
    add(scoreboard);
    BowlingGutter bowlingGutter = buildGutter();
    add(bowlingGutter);
    BowlingBall ball = new BowlingBall();
    add(ball);
  }

  private ScoreBoard buildScoreBoard()
  {
    GameState gameState = new GameState();
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    ScoreBoardReader reader = new ScoreBoardReader(finder);
    Point2D location = new Point2D.Double(255, 10);
    ScoreBoard scoreboard = null;
    try
    {
      scoreboard = new ScoreBoard(gameState, reader.read(), Color.blue, location);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return scoreboard;
  }

  private BowlingGutter buildGutter()
  {
    return null;
  }

  private visual.statik.sampled.Content buildLane()
  {
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    BowlingLaneReader reader = new BowlingLaneReader(finder);
    visual.statik.sampled.Content content = null;
    double width = 0;
    double height = 0;
    try
    {
      BufferedImage image = reader.read();
      width = 1000.0 / image.getWidth();
      height = 900.0 / image.getHeight();
      content = new visual.statik.sampled.Content(image, 0, 0);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    content.setScale(width, height);
    return content;
  }

  private Background buildBackground()
  {
    Background content = new Background(Color.black);
    return content;
  }
  

  @Override
  public void handleTick(int time)
  {
  }

}
