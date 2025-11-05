package bowlingVisual;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.ResourceFinder;
import visual.statik.sampled.ImageFactory;

public class ScoreBoardReader
{

  private ResourceFinder finder;

  /**
   * Explicit Value Constructor.
   *
   * @param finder The ResourceFinder to use
   */
  public ScoreBoardReader(final ResourceFinder finder)
  {
    this.finder = finder;
  }

  public BufferedImage read() throws IOException
  {
    HashMap<String, Image> images = new HashMap<String, Image>();
    ImageFactory imageFactory = new ImageFactory(finder);
    Image image = imageFactory.createBufferedImage(".png", 4);
    if (image != null)
      images.put(".png", image);
    return images;
  }
}
