package bowling;

public interface BowlingSubject
{
  public void addObserver(BowlingObserver observer);

  public void removeObserver(BowlingObserver observer);

  public void notifyObservers();
}
