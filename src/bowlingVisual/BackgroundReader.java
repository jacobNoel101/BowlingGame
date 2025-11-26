package bowlingVisual;

import java.awt.image.BufferedImage;
import java.io.IOException;
import io.ResourceFinder;
import visual.statik.sampled.ImageFactory;

public class BackgroundReader
{
  private ResourceFinder finder;

  /**
   * Explicit Value Constructor.
   *
   * @param finder
   *          The ResourceFinder to use
   */
  public BackgroundReader(final ResourceFinder finder)
  {
    this.finder = finder;
  }

  public BufferedImage read() throws IOException
  {
    ImageFactory imageFactory = new ImageFactory(finder);
    BufferedImage image = imageFactory.createBufferedImage("bern.jpg", 4);
    return image;
  }

}
