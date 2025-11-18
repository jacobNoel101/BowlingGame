package bowling;

import java.awt.*;
import java.awt.geom.Point2D;

public class ScoreboardWriter {

    private static final Font FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);

    public static void renderScore(Point2D location, int score, Graphics2D g2) {
        g2.setFont(FONT);
        String text = Integer.toString(score);
        FontMetrics metrics = g2.getFontMetrics(FONT);
        int x = (int) (location.getX() - metrics.stringWidth(text) / 2);
        int y = (int) (location.getY() + metrics.getHeight() / 2);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }
}
