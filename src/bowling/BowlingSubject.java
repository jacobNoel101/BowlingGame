package bowling;

/**
 * BowlingSubject for Bowling Game.
 * 
 * Honor Statement: This code adheres to JMU Policy.
 * 
 * @author Jacob Noel and Tristan Apgar
 */
public interface BowlingSubject
{
  /**
   * Add observer to the subject.
   *
   * @param observer to be added
   */
  public void addObserver(final BowlingObserver observer);

  /**
   * Remove observer from the subject.
   *
   * @param observer to be removed
   */
  public void removeObserver(final BowlingObserver observer);

  /**
   * Notify the observers.
   */
  public void notifyObservers();
}
