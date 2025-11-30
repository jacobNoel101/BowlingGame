package bowlingSprites;

import visual.dynamic.described.RuleBasedSprite;
import visual.statik.described.TransformableContent;

public class BallReflection extends RuleBasedSprite {
    private double originX, originY;

    public BallReflection(TransformableContent reflection) {
        super(reflection);
    }

    public void setOrigin(double x, double y) {
        originX = x;
        originY = y;
        setLocation(originX, originY);
    }

    @Override
    public void handleTick(int millis) {
        double scale = 1.0 - 0.8 * (originY / 650.0); // adjust factor
        setScale(scale);
        setLocation(originX, originY);
    }
}
