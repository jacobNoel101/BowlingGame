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
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void init()
  {
    screen = new BowlingScreen(30);
    PlainVisualizationRenderer renderer = new PlainVisualizationRenderer();
    VisualizationView view = new VisualizationView(screen, renderer);
    view.setBounds(0, 0, 1024, 900);
    getContentPane().add(view);
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
