package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;
import app.BowlingApplication;
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
  private static final int PIN_RESET_DELAY_TICKS = 20;
  private int pinResetCounter = -1; // counter for delayed pin reset
  ArrayList<BowlingPin> pins;
  ArrayList<visual.dynamic.sampled.Superimposition> superImpositions;
  private GameTheme theme;
  private GameState gameState;
  private BowlingBall ball;
  private BowlingSuperImpositions currentMessage = null;
  private EndGamePopup endGamePopup = null;
  private BowlingApplication app;

  public BowlingScreen(final int timeStep, GameTheme theme, BowlingApplication app)
  {
    super(timeStep); // set tick interval
    this.theme = theme;
    this.gameState = new GameState();
    this.gameState.setBowlingScreen(this);
    this.superImpositions = new ArrayList<>();
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
    this.app = app;
    this.pins = buildPins();
    this.ball = buildBall();
    this.ball.setGameState(gameState);

    for (int i = 0; i < pins.size(); i++)
    {
      gameState.addPin(i, pins.get(i));
    }
    for (BowlingPin pin : pins)
    {

      pin.setGameState(gameState);
      ball.addAntagonist(pin);
      for (BowlingPin other : pins)
      {
        if (other != pin)
        {
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
    add(ball.reflectionSprite); // add reflection to stage

  }

  public void showEndGamePopup(int finalScore)
  {
    if (endGamePopup == null)
    {
      endGamePopup = new EndGamePopup();
      this.add(endGamePopup);

      this.getView().addMouseListener(new MouseAdapter()
      {
        @Override
        public void mouseClicked(MouseEvent e)
        {
          if (endGamePopup != null && endGamePopup.clicked(e.getPoint()))
          {
            app.launchEndScreen(finalScore);
          }
        }
      });
    }
  }

  public void addSuperimposition(visual.dynamic.sampled.Superimposition si)
  {
    superImpositions.add(si);
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
    Color outerColor = theme.ballOuterColor;
    TransformableContent outerContent = new Content(outer, outerColor, outerColor, null);
    // inner circle
    Ellipse2D inner = new Ellipse2D.Double(-10, -10, 10, 10);
    Color innerColor = theme.ballInnerColor;
    TransformableContent innerContent = new Content(inner, innerColor, innerColor, null);

    return new BowlingBall(outerContent, null, innerContent);
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
    double radius = 10; // collision radius

    for (int i = positions.length - 1; i >= 0; i--)
    {
      CompositeContent content = new CompositeContent();
      Color outline = (theme.getType() == GameTheme.ThemeType.BASIC) ? Color.WHITE : Color.BLACK;
      content.add(new Content(topPin, outline, theme.pinColor, null));
      content.add(new Content(frontPin, outline, theme.pinColor, null));

      // Correct order: content, X, Y, radius
      BowlingPin pin = new BowlingPin(content, positions[i][0], positions[i][1], radius);
      pins.add(pin);

      // Add to visual stage
      add(pin);
    }

    return pins;
  }

  private BowlingSide buildSide()
  {
    return new BowlingSide();
  }

  private BowlingLane buildLane()
  {
    return new BowlingLane(theme.laneColor);
  }

  private Background buildBackground()
  {
    return new Background(theme.backgroundColor);
  }

  private MusicPlayer buildMusic()
  {
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    return new MusicPlayer(finder);
  }

  public void schedulePinReset()
  {
    pinResetCounter = PIN_RESET_DELAY_TICKS;
  }

  @Override
  public void handleTick(final int delay)
  {
    super.handleTick(delay);

    if (pinResetCounter >= 0)
    {
      pinResetCounter--;
      if (pinResetCounter <= 0)
      {
        resetPins();
        pinResetCounter = -1;
      }
    }
    if (currentMessage != null)
    {
      currentMessage.tick();
      if (currentMessage.isExpired())
      {
        remove(currentMessage); // remove from Stage
        currentMessage = null;
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
    for (BowlingPin p : pins)
    {
      p.resetPin();
    }
  }

  public void showMessage(String message)
  {
    Point2D center = new Point2D.Double(500, 350);
    int durationTicks = 120;
    currentMessage = new BowlingSuperImpositions(message, center, durationTicks);
    add(currentMessage);
  }

  private class EndGamePopup implements visual.statik.SimpleContent
  {
    private Rectangle2D box = new Rectangle2D.Double(300, 250, 400, 120);

    @Override
    public void render(Graphics g)
    {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(new Color(0, 0, 0, 150));
      g2.fill(new Rectangle2D.Double(0, 0, 1000, 700));
      g2.setColor(Color.WHITE);
      g2.fill(box);
      g2.setColor(Color.BLACK);
      g2.draw(box);
      g2.setFont(new Font("Arial", Font.BOLD, 36));
      g2.drawString("END GAME", 390, 320);
      g2.setFont(new Font("Arial", Font.PLAIN, 20));
      g2.drawString("Click to continue", 420, 350);
    }

    public boolean clicked(Point2D p)
    {
      return box.contains(p);
    }
  }

}
