package app;

import java.awt.event.*;
import bowling.GameTheme;
import gui.*;

public class BowlingApplication extends JApplication implements ActionListener
{
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 700;
  private GameTheme currentTheme = new GameTheme(GameTheme.ThemeType.BASIC);
  private StartScreen startScreen;
  private BowlingScreen bowlingScreen;

  public BowlingApplication(final String[] args)
  {
    super(WIDTH, HEIGHT);
  }

  public GameTheme getTheme()
  {
    return currentTheme;
  }

  public void setTheme(GameTheme.ThemeType t)
  {
    this.currentTheme = new GameTheme(t);
  }

  @Override
  public void init()
  {
    startScreen = new StartScreen(17, this);
    startScreen.getView().setBounds(0, 0, WIDTH, HEIGHT);
    getContentPane().add(startScreen.getView());
    startScreen.start();
    getContentPane().revalidate();
    getContentPane().repaint();
  }

  public void launchBowlingScreen()
  {
    getContentPane().remove(startScreen.getView());
    bowlingScreen = new BowlingScreen(30, currentTheme);
    bowlingScreen.getView().setBounds(0, 0, WIDTH, HEIGHT);
    getContentPane().add(bowlingScreen.getView());
    bowlingScreen.start();
    getContentPane().revalidate();
    getContentPane().repaint();
    bowlingScreen.getView().requestFocusInWindow();
  }

  public void launchStartScreen()
  {
    getContentPane().removeAll();
    startScreen = new StartScreen(17, this);
    startScreen.getView().setBounds(0, 0, WIDTH, HEIGHT);
    getContentPane().add(startScreen.getView());
    startScreen.start();
    getContentPane().revalidate();
    getContentPane().repaint();
  }

  public void launchThemeScreen()
  {
    getContentPane().removeAll();
    ThemeScreen theme = new ThemeScreen(17, this);
    theme.getView().setBounds(0, 0, WIDTH, HEIGHT);
    getContentPane().add(theme.getView());
    theme.start();
    getContentPane().revalidate();
    getContentPane().repaint();
  }

  public static void main(final String[] args)
  {
    JApplication app = new BowlingApplication(args);
    JApplication.invokeInEventDispatchThread(app);
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
  }

}