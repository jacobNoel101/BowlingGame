package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import app.BowlingApplication;
import auditory.sampled.*;
import bowlingVisual.*;
import io.ResourceFinder;
import music.MusicPlayer;
import resources.Marker;
import visual.dynamic.described.Stage;

public class StartScreen extends Stage
{
  private BowlingStart startContent;
  private MusicPlayer mp;
  private BowlingApplication app; // to help switch screens

  public StartScreen(final int timeStep, final BowlingApplication app)
  {
    super(timeStep);
    this.app = app;
    Background bg = buildBackground();
    add(bg);
    startContent = buildStart();
    add(startContent);
    mp = buildMusic();
    try
    {
      mp.read();
    }
    catch (UnsupportedAudioFileException | IOException e)
    {
      e.printStackTrace();
    }
    mp.update();
    getView().addMouseListener(new StartScreenClickListener(this));
  }

  private Background buildBackground()
  {
    return new Background(Color.BLACK);
  }

  private BowlingStart buildStart()
  {
    return new BowlingStart();
  }

  private MusicPlayer buildMusic()
  {
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    return new IntroMusicPlayer(finder);
  }

  @Override
  public void handleTick(final int time)
  {
    startContent.handleTick(time);
    getView().repaint();
  }

  public void handleMouseClick(final Point2D point)
  {
    if (startContent.isStartClicked(point))
      app.launchBowlingScreen(); // switch screens when mouse clicks start
  }

  // --- here to listen for a mouse click to start ---
  private static class StartScreenClickListener extends MouseAdapter
  {
    private final StartScreen screen;

    public StartScreenClickListener(StartScreen screen)
    {
      this.screen = screen;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
      screen.handleMouseClick(e.getPoint());
    }
  }

  // --- here for intro music setup ---
  private static class IntroMusicPlayer extends MusicPlayer
  {
    public IntroMusicPlayer(final ResourceFinder finder)
    {
      super(finder);
    }

    @Override
    public void read() throws UnsupportedAudioFileException, IOException
    {
      ResourceFinder finder = ResourceFinder.createInstance(new Marker());
      BufferedSoundFactory factory = new BufferedSoundFactory(finder);
      BufferedSound intro = factory.createBufferedSound("intro_sound.wav");
      setMusic(intro);
      setBoomBox(new BoomBox(intro));
    }
  }

}
