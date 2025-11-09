package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.BowlingScreen;

public class BowlingApplication extends JApplication implements ActionListener
{

  public BowlingApplication(int width, int height)
  {
    super(width, height);
    BowlingScreen screen = new BowlingScreen(30);
    screen.start();
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void init()
  {
    // TODO Auto-generated method stub
  }

  public static void main(final String[] args)
  {
    JApplication app = new BowlingApplication(800, 600);
    JApplication.invokeInEventDispatchThread(app);
  }

}
