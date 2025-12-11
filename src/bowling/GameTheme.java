package bowling;

import java.awt.Color;

/**
 * Manages color themes for the bowling game.
 *
 * @author Jacob Noel and Tristan Apgar
 * @version Fall 2025
 *
 *          Honor Statement: This code adheres to JMU Policy.
 */
public class GameTheme
{
  /**
   * Available theme types.
   */
  public enum ThemeType
  {
    BASIC, JMU, PINK, REDBLUE
  }

  private Color backgroundColor;
  private Color ballInnerColor;
  private Color ballOuterColor;
  private Color laneColor;
  private Color pinColor;
  private ThemeType type;

  /**
   * Creates a theme.
   *
   * @param t
   *          the theme type
   */
  public GameTheme(final ThemeType t)
  {
    this.type = t;
    load(t);
  }

  /**
   * Gets the theme type.
   *
   * @return the theme type
   */
  public ThemeType getType()
  {
    return type;
  }

  /**
   * Loads colors for a theme.
   *
   * @param t
   *          the theme type
   */
  private void load(final ThemeType t)
  {
    switch (t)
    {
      case BASIC:
        setBackgroundColor(Color.LIGHT_GRAY);
        setLaneColor(new Color(181, 101, 29));
        setBallOuterColor(new Color(30, 80, 200));
        setBallInnerColor(new Color(40, 100, 210));
        setPinColor(Color.BLACK);
        break;
      case JMU:
        setBackgroundColor(new Color(69, 0, 132));
        setLaneColor(new Color(203, 182, 119));
        setBallOuterColor(new Color(120, 0, 200));
        setBallInnerColor(new Color(160, 60, 240));
        setPinColor(new Color(203, 195, 227));
        break;
      case PINK:
        setBackgroundColor(new Color(255, 200, 220));
        setLaneColor(new Color(255, 150, 180));
        setBallOuterColor(new Color(255, 100, 150));
        setBallInnerColor(new Color(255, 140, 170));
        setPinColor(new Color(255, 192, 203));
        break;
      case REDBLUE:
        setBackgroundColor(new Color(100, 100, 240));
        setLaneColor(new Color(200, 0, 0));
        setBallOuterColor(new Color(0, 80, 255));
        setBallInnerColor(new Color(100, 150, 255));
        setPinColor(new Color(0, 100, 255));
        break;
      default:
        break;
    }
  }

  /**
   * Get the score.
   *
   * @return int the score
   */
  public Color getPinColor()
  {
    return pinColor;
  }

  /**
   * Set the pin color.
   *
   * @param pinColor
   *          the pin color
   */
  public void setPinColor(final Color pinColor)
  {
    this.pinColor = pinColor;
  }

  /**
   * Get the lane color.
   *
   * @return color the lane color
   */
  public Color getLaneColor()
  {
    return laneColor;
  }

  /**
   * Set the lane color.
   *
   * @param laneColor
   *          the lane color
   */
  public void setLaneColor(final Color laneColor)
  {
    this.laneColor = laneColor;
  }

  /**
   * Get the ball outer color.
   *
   * @return color the ball outer color.
   */
  public Color getBallOuterColor()
  {
    return ballOuterColor;
  }

  /**
   * Set the ball outer color.
   *
   * @param ballOuterColor
   *          the outer color
   */
  public void setBallOuterColor(final Color ballOuterColor)
  {
    this.ballOuterColor = ballOuterColor;
  }

  /**
   * Get the ball inner color.
   *
   * @return color the ball inner color.
   */
  public Color getBallInnerColor()
  {
    return ballInnerColor;
  }

  /**
   * Set the ball inner color.
   *
   * @param ballInnerColor
   *          the inner color
   */
  public void setBallInnerColor(final Color ballInnerColor)
  {
    this.ballInnerColor = ballInnerColor;
  }

  /**
   * Get the bg color.
   *
   * @return color the bg color.
   */
  public Color getBackgroundColor()
  {
    return backgroundColor;
  }

  /**
   * Set the bg color.
   *
   * @param backgroundColor
   *          the bg color
   */
  public void setBackgroundColor(final Color backgroundColor)
  {
    this.backgroundColor = backgroundColor;
  }

}
