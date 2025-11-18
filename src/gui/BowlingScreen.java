package gui;

import java.awt.*;
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
  ArrayList<BowlingPin> pins;

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
    this.pins = buildPins();
    BowlingBall ball = buildBall();
    for (BowlingPin pin : pins)
    {
      //ball.addObserver(pin);
      ball.addAntagonist(pin);
      //pin.addAntagonist(ball);
      pin.setMetronome(getMetronome());
      add(pin);
    }
    add(ball);
    ball.setMetronome(getMetronome());

    getView().addKeyListener(ball);
    getView().setFocusable(true);
    SwingUtilities.invokeLater(() -> getView().requestFocusInWindow());
    
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
    // outer circle
    Ellipse2D outer = new Ellipse2D.Double(-30, -30, 70, 70);
    Color outerColor = new Color(30, 80, 200); // darker blue
    TransformableContent outerContent = new Content(outer, outerColor, outerColor, null);
    // inner circle
    Ellipse2D inner = new Ellipse2D.Double(-25, -25, 50, 50);
    Color innerColor = new Color(40, 100, 210); // lighter blue
    TransformableContent innerContent = new Content(inner, innerColor, innerColor, null);
    // make both into one content
    visual.statik.described.CompositeContent ballContent = new visual.statik.described.CompositeContent();
    ballContent.add(outerContent);
    ballContent.add(innerContent);
    BowlingBall ball = new BowlingBall(ballContent, null);
    return ball;
  }

  private ArrayList<BowlingPin> buildPins()
  {
      Rectangle2D frontPin = new Rectangle2D.Double(0, 0, 20, 60);
      Polygon topPin = new Polygon();
      topPin.addPoint(0, 0);
      topPin.addPoint(20, 0);
      topPin.addPoint(15, -3);
      topPin.addPoint(5, -3);

      ArrayList<BowlingPin> pins = new ArrayList<>();

      for (int i = 10; i > 6; i--)
      {
          CompositeContent content = new CompositeContent();
          content.add(new Content(topPin, Color.WHITE, Color.BLACK, null));
          content.add(new Content(frontPin, Color.WHITE, Color.BLACK, null));

          BowlingPin pin = new BowlingPin(content, i, 415 + offset, 170);
          offset += 50;
          pins.add(pin);
      }

      offset = 0;
      for (int i = 6; i > 3; i--)
      {
          CompositeContent content = new CompositeContent();
          content.add(new Content(topPin, Color.WHITE, Color.BLACK, null));
          content.add(new Content(frontPin, Color.WHITE, Color.BLACK, null));

          BowlingPin pin = new BowlingPin(content, i, 435 + offset, 180);
          offset += 55;
          pins.add(pin);
      }

      offset = 0;
      for (int i = 3; i > 1; i--)
      {
          CompositeContent content = new CompositeContent();
          content.add(new Content(topPin, Color.WHITE, Color.BLACK, null));
          content.add(new Content(frontPin, Color.WHITE, Color.BLACK, null));

          BowlingPin pin = new BowlingPin(content, i, 455 + offset, 195);
          offset += 70;
          pins.add(pin);
      }

      CompositeContent content = new CompositeContent();
      content.add(new Content(topPin, Color.WHITE, Color.BLACK, null));
      content.add(new Content(frontPin, Color.WHITE, Color.BLACK, null));
      BowlingPin pin = new BowlingPin(content, 1, 490, 210);
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
//    int lastKeyTime = 2500;
//    if (delay >= lastKeyTime)
//    {
//      super.getMetronome().reset();
//    }
    
  }

}
