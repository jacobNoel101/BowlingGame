package music;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import auditory.sampled.*;
import io.ResourceFinder;
import bowling.BowlingObserver;

public class MusicPlayer implements BowlingObserver
{
  protected BoomBox boomBox;
  protected BufferedSound music;
  private ResourceFinder finder;
  private Thread loopThread;

  public MusicPlayer(final ResourceFinder finder)
  {
    this.finder = finder;
  }

  protected void setMusic(BufferedSound music)
  {
    this.music = music;
  }

  protected void setBoomBox(BoomBox boomBox)
  {
    this.boomBox = boomBox;
  }

  protected BufferedSound getMusic()
  {
    return music;
  }

  protected BoomBox getBoomBox()
  {
    return boomBox;
  }

  public void playLoop()
  {
    if (boomBox == null)
      return;
    loopThread = new Thread(() -> {
      try
      {
        boomBox.start(true);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    });
    loopThread.setDaemon(true);
    loopThread.start();
  }

  public void stop()
  {
    boomBox = null;
  }

  public void read() throws UnsupportedAudioFileException, IOException
  {
    BufferedSoundFactory factory = new BufferedSoundFactory(finder);
    music = factory.createBufferedSound("bowlingscreen_bg_music.wav");
    boomBox = new BoomBox(music);
  }

  @Override
  public void reset()
  {
  }

  @Override
  public void update()
  {
    try
    {
      boomBox.start(false);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

}
