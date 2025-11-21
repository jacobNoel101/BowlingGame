package bowling;

public interface BowlingBallController {
    void startRoll(double angle);
    void resetBall();
    void resetPins();
    void showMessage(String message);

}
