package app;

import java.awt.event.*;
import gui.*;

public class BowlingApplication extends JApplication implements ActionListener
{
  public static final int WIDTH = 1000;
  public static final int HEIGHT = 700;
  private StartScreen startScreen;
  private BowlingScreen bowlingScreen;

  public BowlingApplication(final String[] args)
  {
    super(WIDTH, HEIGHT);
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
    bowlingScreen = new BowlingScreen(30);
    bowlingScreen.getView().setBounds(0, 0, WIDTH, HEIGHT);
    getContentPane().add(bowlingScreen.getView());
    bowlingScreen.start();
    getContentPane().revalidate();
    getContentPane().repaint();
    bowlingScreen.getView().requestFocusInWindow();
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
