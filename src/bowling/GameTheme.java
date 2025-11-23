package bowling;

import java.awt.Color;

public class GameTheme
{
  public enum ThemeType
  {
    BASIC, JMU, PINK, REDBLUE
  }

  private ThemeType type;
  public Color backgroundColor;
  public Color laneColor;
  public Color ballOuterColor;
  public Color ballInnerColor;
  public Color pinColor;

  public GameTheme(ThemeType t)
  {
    this.type = t;
    load(t);
  }

  public ThemeType getType()
  {
    return type;
  }

  private void load(ThemeType t)
  {
    switch (t)
    {
      case BASIC:
        backgroundColor = Color.LIGHT_GRAY;
        laneColor = new Color(181, 101, 29);
        ballOuterColor = new Color(30, 80, 200);
        ballInnerColor = new Color(40, 100, 210);
        pinColor = Color.BLACK;
        break;

      case JMU:
        backgroundColor = new Color(69, 0, 132);
        laneColor = new Color(203, 182, 119);
        ballOuterColor = new Color(120, 0, 200);
        ballInnerColor = new Color(160, 60, 240);
        pinColor = new Color(203, 195, 227);
        break;

      case PINK:
        backgroundColor = new Color(255, 200, 220);
        laneColor = new Color(255, 150, 180);
        ballOuterColor = new Color(255, 100, 150);
        ballInnerColor = new Color(255, 140, 170);
        pinColor = new Color(255, 192, 203);
        break;

      case REDBLUE:
        backgroundColor = new Color(20, 20, 60);
        laneColor = new Color(200, 0, 0);
        ballOuterColor = new Color(0, 80, 255);
        ballInnerColor = new Color(100, 150, 255);
        pinColor = new Color(0, 100, 255);
        break;
    }
  }

}
