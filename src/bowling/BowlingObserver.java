package bowling;

/**
 * BowlingObserver for Bowling Game.
 * 
 * Honor Statement: This code adheres to JMU Policy.
 * 
 * @author Jacob Noel and Tristan Apgar
 */
public interface BowlingObserver
{
  /**
   * Reset observers if notified.
   */
  public abstract void reset();

  /**
   * Update after being notified.
   */
  public abstract void update();
}
