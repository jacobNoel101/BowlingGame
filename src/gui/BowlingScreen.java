package gui;

import java.awt.Color;
import java.awt.geom.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import bowling.*;
import bowlingSprites.*;
import bowlingVisual.*;
import io.ResourceFinder;
import visual.dynamic.described.*;
import resources.Marker;

public class BowlingScreen extends Stage
{

  public BowlingScreen(final int timeStep)
  {
    super(timeStep);
    Background bg = buildBackground();
    add(bg);
    BowlingSide side = buildSide();
    add(side);
    BowlingLane lane = buildLane();
    add(lane);
    BowlingGutter bowlingGutter = buildGutter();
    add(bowlingGutter);
    ScoreBoard scoreboard = buildScoreBoard();
    add(scoreboard);
    BowlingBall ball = new BowlingBall();
    add(ball);
    //ArrayList<BowlingPin> pins = buildPins();
    //for (BowlingPin pin : pins) {
     // add(pin);
    //}
  }

  private ScoreBoard buildScoreBoard()
  {
    GameState gameState = new GameState();
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    ScoreBoardReader reader = new ScoreBoardReader(finder);
    Point2D location = new Point2D.Double(255, 5);
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
    return new BowlingGutter();
  }
  
  //private ArrayList<BowlingPin> buildPins()
 // {
    ArrayList<BowlingPin> pins = new ArrayList<BowlingPin>();
    //for (int i = 0; i < 9; i++) {
      //BowlingPin pin = new BowlingPin();
      //pins.add(pin);
    //}
    //return pins;
  //}

  private BowlingSide buildSide()
  {
    return new BowlingSide();
  }

  private BowlingLane buildLane()
  {
    return new BowlingLane();
  }

  private Background buildBackground()
  {
    Background content = new Background(Color.lightGray);
    return content;
  }

  @Override
  public void handleTick(int time)
  {
  }

}
