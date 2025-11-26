package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import app.BowlingApplication;
import bowling.LeaderboardEntry;
import visual.dynamic.described.Stage;
import visual.statik.SimpleContent;

public class LeaderboardScreen extends Stage
{
  private Rectangle2D playAgainBox;
  private Rectangle2D quitBox;
  private List<LeaderboardEntry> entries;
  private BowlingApplication app;

  public LeaderboardScreen(int timestep, BowlingApplication app, List<LeaderboardEntry> entries)
  {
    super(timestep);
    this.app = app;
    this.entries = entries;
    playAgainBox = new Rectangle2D.Double(250, 600, 220, 60);
    quitBox = new Rectangle2D.Double(550, 600, 220, 60);
    add(new LeaderboardContent());
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
      app.launchStartScreen();
    else if (quitBox.contains(p))
      System.exit(0);
  }

  private class LeaderboardContent implements SimpleContent
  {
    @Override
    public void render(Graphics g)
    {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.BLACK);
      g2.fillRect(0, 0, 1015, 715);
      g2.setColor(Color.WHITE);
      g2.setFont(new Font("Arial", Font.BOLD, 60));
      g2.drawString("LEADERBOARD", 275, 100);
      g2.setFont(new Font("Arial", Font.BOLD, 32));
      g2.drawString("USER", 200, 160);
      g2.drawString("SCORE", 650, 160);
      g2.setFont(new Font("Arial", Font.PLAIN, 28));
      int y = 220;
      int rank = 1;
      for (LeaderboardEntry e : entries)
      {
        g2.drawString(rank + ". " + e.username, 180, y);
        g2.drawString(Integer.toString(e.score), 700, y);
        y += 40;
        rank++;
      }
      drawButton(g2, playAgainBox, "PLAY AGAIN");
      drawButton(g2, quitBox, "QUIT");
    }

    private void drawButton(Graphics2D g2, Rectangle2D r, String text)
    {
      g2.setColor(Color.DARK_GRAY);
      g2.fill(r);
      g2.setColor(Color.WHITE);
      g2.draw(r);
      g2.setFont(new Font("Arial", Font.BOLD, 28));
      FontMetrics m = g2.getFontMetrics();
      int tx = (int) (r.getX() + r.getWidth() / 2 - m.stringWidth(text) / 2);
      int ty = (int) (r.getY() + r.getHeight() / 2 + m.getAscent() / 2 - 5);
      g2.drawString(text, tx, ty);
    }
  }

}
