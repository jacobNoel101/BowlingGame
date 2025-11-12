package gui;

import java.awt.*;
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
  private BowlingApplication app;

  public StartScreen(final int timeStep, BowlingApplication app)
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
    getView().addMouseListener(new java.awt.event.MouseAdapter()
    {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent e)
      {
        handleMouseClick(e.getPoint());
        mp.stop();
      }
    });
  }

  private Background buildBackground()
  {
    Background content = new Background(Color.BLACK);
    return content;
  }

  private BowlingStart buildStart()
  {
    return new BowlingStart();
  }

  private MusicPlayer buildMusic()
  {
    ResourceFinder finder = ResourceFinder.createInstance(new Marker());
    return new MusicPlayer(finder)
    {
      @Override
      public void read() throws UnsupportedAudioFileException, IOException
      {
        BufferedSoundFactory factory = new BufferedSoundFactory(finder);
        BufferedSound intro = factory.createBufferedSound("possible_intro_music.wav");
        setMusic(intro);
        setBoomBox(new BoomBox(intro));
      }
    };
  }

  @Override
  public void handleTick(final int time)
  {
    startContent.handleTick(time);
    getView().repaint();
  }

  public void handleMouseClick(Point2D point)
  {
    if (startContent.isStartClicked(point))
    {
      mp.stop();
      app.launchBowlingScreen();
    }
  }

}
