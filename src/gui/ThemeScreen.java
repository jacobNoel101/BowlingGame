package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import app.BowlingApplication;
import bowling.GameTheme;
import visual.dynamic.described.Stage;
import visual.statik.SimpleContent;

public class ThemeScreen extends Stage
{
  private BowlingApplication app;
  private Rectangle2D basicBox = new Rectangle2D.Double(300, 200, 300, 60);
  private Rectangle2D jmuBox = new Rectangle2D.Double(300, 290, 300, 60);
  private Rectangle2D pinkBox = new Rectangle2D.Double(300, 380, 300, 60);
  private Rectangle2D redBlueBox = new Rectangle2D.Double(300, 470, 300, 60);

  public ThemeScreen(int timeStep, BowlingApplication app)
  {
    super(timeStep);
    this.app = app;
    add(new ThemeMenuContent());
    getView().addMouseListener(new MouseHandler());
  }

  private class MouseHandler extends MouseAdapter
  {
    @Override
    public void mouseClicked(MouseEvent e)
    {
      Point2D p = e.getPoint();
      if (basicBox.contains(p))
      {
        app.setTheme(GameTheme.ThemeType.BASIC);
        app.launchStartScreen();
      }
      else if (jmuBox.contains(p))
      {
        app.setTheme(GameTheme.ThemeType.JMU);
        app.launchStartScreen();
      }
      else if (pinkBox.contains(p))
      {
        app.setTheme(GameTheme.ThemeType.PINK);
        app.launchStartScreen();
      }
      else if (redBlueBox.contains(p))
      {
        app.setTheme(GameTheme.ThemeType.REDBLUE);
        app.launchStartScreen();
      }
    }
  }

  private class ThemeMenuContent implements SimpleContent
  {
    @Override
    public void render(Graphics g)
    {
      Graphics2D g2 = (Graphics2D) g;
      Rectangle2D full = new Rectangle2D.Double(0, 0, 1015, 715);
      g2.setColor(Color.DARK_GRAY);
      g2.fill(full);
      g2.draw(full);
      g2.setColor(Color.WHITE);
      g2.setFont(new Font("Arial", Font.BOLD, 36));
      g2.drawString("Select Theme", 360, 150);
      drawButton(g2, basicBox, "BASIC", Color.LIGHT_GRAY);
      drawButton(g2, jmuBox, "JMU", new Color(69, 0, 132));
      drawButton(g2, pinkBox, "PINK", new Color(255, 150, 180));
      drawButton(g2, redBlueBox, "RED & BLUE", new Color(80, 80, 200));
    }

    private void drawButton(Graphics2D g2, Rectangle2D r, String label, Color fill)
    {
      g2.setColor(fill);
      g2.fill(r);
      g2.setColor(Color.BLACK);
      g2.draw(r);
      g2.setFont(new Font("Arial", Font.BOLD, 24));
      g2.drawString(label, (int) (r.getX() + 20), (int) (r.getY() + 38));
    }
  }

}
