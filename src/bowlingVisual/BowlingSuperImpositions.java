package bowlingVisual;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import visual.statik.SimpleContent;

public class BowlingSuperImpositions implements SimpleContent {

    private String message;
    private Point2D location;
    private Font font;
    private int remainingTicks;

    private float opacity = 0f; // current opacity (0–1)
    private int fadeInTicks = 20;   // first 20 ticks fade in
    private int fadeOutTicks = 20;  // last 20 ticks fade out

    private int totalDuration;

    public BowlingSuperImpositions(String message, Point2D location, int durationTicks) {
        this.message = message;
        this.location = location;
        this.font = new Font("SansSerif", Font.BOLD, 50);
        this.remainingTicks = durationTicks;
        this.totalDuration = durationTicks;
    }

    public boolean isExpired() {
        return remainingTicks <= 0;
    }

    public void tick() {
        remainingTicks--;

        int elapsed = totalDuration - remainingTicks;

        // Fade in
        if (elapsed < fadeInTicks) {
            opacity = (float) elapsed / fadeInTicks;
        }
        // Fade out
        else if (remainingTicks < fadeOutTicks) {
            opacity = (float) remainingTicks / fadeOutTicks;
        } 
        // Fully visible
        else {
            opacity = 1f;
        }
    }

    @Override
    public void render(Graphics g) {
        if (remainingTicks <= 0 || message == null || message.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setFont(font);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        TextLayout layout = new TextLayout(message, font, g2.getFontRenderContext());
        Shape outline = layout.getOutline(null);

        Rectangle2D bounds = outline.getBounds2D();
        double x = location.getX() - bounds.getWidth() / 2;
        double y = location.getY() + bounds.getHeight() / 2;
        g2.translate(x, y);

        g2.setColor(Color.BLACK);
        g2.draw(outline);
        g2.setColor(Color.YELLOW);
        g2.fill(outline);

        g2.dispose();
    }
}
