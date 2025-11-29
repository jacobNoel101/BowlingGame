package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
//import javax.sound.sampled.UnsupportedAudioFileException;
import app.BowlingApplication;
import io.ResourceFinder;
//import music.MusicPlayer;
import resources.Marker;
import visual.dynamic.described.Stage;
//import auditory.sampled.*;
import bowlingVisual.BackgroundReader;

public class EndScreen extends Stage
{
  private BowlingApplication app;
  private int finalScore;
  private Rectangle2D playAgainBox;
  private Rectangle2D quitBox;
  private Rectangle2D leaderboardBox;
  // private MusicPlayer mp;
  private Image backgroundImage;

  public EndScreen(int timeStep, BowlingApplication app, int finalScore)
  {
    super(timeStep);
    this.app = app;
    this.finalScore = finalScore;
    String username = "PLAYER";
    app.addScore(username, finalScore);
    try
    {
      ResourceFinder finder = ResourceFinder.createInstance(new Marker());
      BackgroundReader reader = new BackgroundReader(finder);
      backgroundImage = reader.read();
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    playAgainBox = new Rectangle2D.Double(150, 560, 220, 60);
    leaderboardBox = new Rectangle2D.Double(390, 560, 220, 60);
    quitBox = new Rectangle2D.Double(630, 560, 220, 60);
    add(new EndScreenContent());
    // mp = buildMusic();
    // try
    // {
    // mp.read();
    // mp.playLoop();
    // }
    // catch (Exception e)
    // {
    // e.printStackTrace();
    // }
    // mp.update();
    getView().addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        handleClick(e.getPoint());
      }
    });
  }

  private void handleClick(Point2D p)
  {
    if (playAgainBox.contains(p))
    {
      app.launchStartScreen();
    }
    else if (leaderboardBox.contains(p))
    {
      app.launchLeaderboardScreen();
    }
    else if (quitBox.contains(p))
    {
      System.exit(0);
    }
  }

  // private MusicPlayer buildMusic()
  // {
  // return new MusicPlayer(ResourceFinder.createInstance(new Marker()))
  // {
  // @Override
  // public void read() throws UnsupportedAudioFileException, IOException
  // {
  // ResourceFinder finder = ResourceFinder.createInstance(new Marker());
  // BufferedSoundFactory factory = new BufferedSoundFactory(finder);
  // BufferedSound snd = factory.createBufferedSound("outro_music.wav");
  // setMusic(snd);
  // setBoomBox(new BoomBox(snd));
  // }
  // };
  // }

  private class EndScreenContent implements visual.statik.SimpleContent
  {
    @Override
    public void render(Graphics g)
    {
      Graphics2D g2 = (Graphics2D) g;
      g2.drawImage(backgroundImage, 0, 0, 1015, 715, null);
      g2.setColor(Color.WHITE);
      g2.setFont(new Font("Arial", Font.BOLD, 64));
      String scoreText = "FINAL SCORE: " + finalScore;
      int w = g2.getFontMetrics().stringWidth(scoreText);
      g2.drawString(scoreText, 500 - w / 2, 150);
      drawButton(g2, playAgainBox, "PLAY AGAIN", Color.DARK_GRAY);
      drawButton(g2, leaderboardBox, "LEADERBOARD", Color.DARK_GRAY);
      drawButton(g2, quitBox, "QUIT", Color.DARK_GRAY);
    }

    private void drawButton(Graphics2D g2, Rectangle2D r, String label, Color fill)
    {
      g2.setColor(fill);
      g2.fill(r);
      g2.setColor(Color.WHITE);
      g2.draw(r);
      g2.setFont(new Font("Arial", Font.BOLD, 28));
      FontMetrics m = g2.getFontMetrics();
      int tx = (int) (r.getX() + r.getWidth() / 2 - m.stringWidth(label) / 2);
      int ty = (int) (r.getY() + r.getHeight() / 2 + m.getAscent() / 2 - 5);
      g2.drawString(label, tx, ty);
    }
  }

}
