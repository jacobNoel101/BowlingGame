package music;

import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import auditory.sampled.*;
import io.ResourceFinder;
import bowling.BowlingObserver;

public class MusicPlayer implements BowlingObserver
{
  private BoomBox boomBox;
  private BufferedSound music;
  private ResourceFinder finder;

  public MusicPlayer(final ResourceFinder finder)
  {
    this.finder = finder;
    try
    {
      read();
      boomBox.start(false);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void playLoop()
  {
    new Thread(() -> {
      while (true)
      {
        try
        {
          boomBox.start(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
          break;
        }
      }
    }).start();
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
