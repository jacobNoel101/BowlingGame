package app;

import java.awt.event.*;
import gui.*;
import visual.*;

public class BowlingApplication extends JApplication implements ActionListener
{
  public static final int WIDTH  = 1000;
  public static final int HEIGHT = 800;

  private BowlingScreen screen;

  public BowlingApplication(String[] args)
  {
    super(WIDTH, HEIGHT);
    this.screen = new BowlingScreen(30);
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void init()
  {
    
    screen.getView().setBounds(0, 0, WIDTH, HEIGHT);
    getContentPane().add(screen.getView());
    screen.getView().setVisible(true);

    screen.start();
    getContentPane().revalidate();
    getContentPane().repaint();
  }

  public static void main(final String[] args)
  {
    JApplication app = new BowlingApplication(args);
    JApplication.invokeInEventDispatchThread(app);
  }

}
