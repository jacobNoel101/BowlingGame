package app;

import java.awt.event.*;
import gui.*;
import visual.*;

public class BowlingApplication extends JApplication implements ActionListener
{
  private BowlingScreen screen;

  public BowlingApplication(final int width, final int height)
  {
    super(width, height);
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
    screen.getView().setBounds(0, 0, 1024, 900);
    getContentPane().add(screen.getView());
    screen.getView().setVisible(true);

    screen.start();
    getContentPane().revalidate();
    getContentPane().repaint();
  }

  public static void main(final String[] args)
  {
    JApplication app = new BowlingApplication(1024, 900);
    JApplication.invokeInEventDispatchThread(app);
  }

}
