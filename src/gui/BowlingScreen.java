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

public class BowlingScreen extends Stage implements BowlingBallController
{
  double offset = 0;
  private static final int PIN_RESET_DELAY_TICKS = 10; // ~500ms if timestep = 50
  private int pinResetCounter = -1; // counter for delayed pin reset
  ArrayList<BowlingPin> pins;
  private GameState gameState;
  private BowlingBall ball;

  public BowlingScreen(final int timeStep)
  {
    super(timeStep); // set tick interval
    this.gameState = new GameState();
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
    gameState.addObserver(scoreboard);

    this.pins = buildPins();
    this.ball = buildBall();
    this.ball.setGameState(gameState);

    for (BowlingPin pin : pins)
    {
      pin.setGameState(gameState);
      ball.addAntagonist(pin);
      for (BowlingPin other : pins) {
        if (other != pin) {
          pin.addAntagonist(other);
        }
      }
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
    gameState.setBallController(this);

  }

  private ScoreBoard buildScoreBoard()
  {
    GameState gameState = this.gameState;
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    ScoreBoardReader reader = new ScoreBoardReader(finder);
    Point2D location = new Point2D.Double(177, 40); // starting pos for score
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

    double[][] positions = {{490, 210}, // row 1
        {455, 195}, {525, 195}, // row 2
        {435, 180}, {490, 180}, {545, 180}, // row 3
        {415, 170}, {465, 170}, {515, 170}, {565, 170} // row 4
    };

    ArrayList<BowlingPin> pins = new ArrayList<>();
    for (int i = positions.length - 1; i >= 0; i--)
    {
      int row;
      if (i == 0)
        row = 1;
      else if (i <= 2)
        row = 2;
      else if (i <= 5)
        row = 3;
      else
        row = 4;
      CompositeContent content = new CompositeContent();
      content.add(new Content(topPin, Color.WHITE, Color.BLACK, null));
      content.add(new Content(frontPin, Color.WHITE, Color.BLACK, null));
      BowlingPin pin = new BowlingPin(content, row, positions[i][0], positions[i][1]);
      pins.add(pin);
    }

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
  
  
  private boolean pinsNeedReset = false;

  public void schedulePinReset() {
    pinResetCounter = PIN_RESET_DELAY_TICKS;
  }

  @Override
  public void handleTick(final int delay)
  {
    super.handleTick(delay);
    
    if (pinResetCounter >= 0) {
      pinResetCounter--;
      if (pinResetCounter <= 0) {
          resetPins();
          pinResetCounter = -1;
      }
    }
  }

  @Override
  public void startRoll(double angle)
  {
    ball.startRoll(angle);
  }

  @Override
  public void resetBall()
  {
    ball.resetBall();
  }

  @Override
  public void resetPins()
  {
    pins = null;
    pins = buildPins();

    for (BowlingPin p : pins)
    {
        p.setGameState(gameState);
        ball.addAntagonist(p);
        for (BowlingPin other : pins) {
          if (other != p) {
            p.addAntagonist(other);
          }
        }
        add(p);
    }
  }

}
