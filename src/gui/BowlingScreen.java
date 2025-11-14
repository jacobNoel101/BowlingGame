package gui;

import java.awt.Color;
import java.awt.geom.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import bowling.*;
import bowlingSprites.*;
import bowlingVisual.*;
import io.ResourceFinder;
import music.MusicPlayer;
import visual.dynamic.described.*;
import visual.statik.described.*;
import resources.Marker;

public class BowlingScreen extends Stage
{
  double offset = 0;

  public BowlingScreen(final int timeStep)
  {
    super(timeStep); // set tick interval

    // call helpers and add the content to the screen
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
    BowlingBall ball = buildBall();
    add(ball);
    getView().addKeyListener(ball);
    getView().setFocusable(true);
    SwingUtilities.invokeLater(() -> getView().requestFocusInWindow());
    ArrayList<BowlingPin> pins = buildPins();
    for (BowlingPin pin : pins)
    {
      add(pin);
    }
    MusicPlayer mp = buildMusic();
    try
    {
      mp.read();
      mp.playLoop();
    }
    catch (UnsupportedAudioFileException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    mp.update();
  }

  private ScoreBoard buildScoreBoard()
  {
    GameState gameState = new GameState();
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    ScoreBoardReader reader = new ScoreBoardReader(finder);
    Point2D location = new Point2D.Double(255, 5); // pos on screen
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
    Rectangle2D s = new Rectangle2D.Double(0, 0, 1, 1);
    TransformableContent content = new Content(s, null, null, null);
    BowlingGutter gutter = new BowlingGutter(content);
    getView().addKeyListener(gutter);
    return gutter;
  }

  private BowlingBall buildBall()
  {
    Ellipse2D shape = new Ellipse2D.Double(-30, -30, 60, 60);
    TransformableContent content = new Content(shape, Color.BLUE, Color.BLUE, null);
    BowlingBall ball = new BowlingBall(content, null);
    return ball;
  }

  private ArrayList<BowlingPin> buildPins()
  {
    Rectangle2D shape = new Rectangle2D.Double(0, 0, 20.0, 60.0);
    TransformableContent pinContent = new Content(shape, Color.black, Color.gray, null);
    ArrayList<BowlingPin> pins = new ArrayList<BowlingPin>();
    for (int i = 10; i > 6; i--)
    {
      BowlingPin pin = new BowlingPin(pinContent, i);
      pin.setLocation(415 + offset, 170); // 422 , 185
      offset += 50;
      pins.add(pin);
    }
    offset = 0;
    for (int i = 6; i > 3; i--)
    {
      BowlingPin pin = new BowlingPin(pinContent, i);
      pin.setLocation(435 + offset, 180); // 422 , 185
      offset += 55;
      pins.add(pin);
    }
    offset = 0;
    for (int i = 3; i > 1; i--)
    {
      BowlingPin pin = new BowlingPin(pinContent, i);
      pin.setLocation(455 + offset, 195); // 422 , 185
      offset += 70;
      pins.add(pin);
    }
    BowlingPin pin = new BowlingPin(pinContent, 1);
    pin.setLocation(490, 210); // 422 , 185
    pins.add(pin);
    return pins;
  }

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

  private MusicPlayer buildMusic()
  {
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    return new MusicPlayer(finder);
  }

  @Override
  public void handleTick(final int delay)
  {
    super.handleTick(delay);
    int lastKeyTime = 4000;
    if (delay >= lastKeyTime)
    {
      super.getMetronome().reset();
    }
  }

}
