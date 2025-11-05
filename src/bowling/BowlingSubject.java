package bowling;

import java.util.ArrayList;
import java.util.List;

public interface BowlingSubject
{  
  public void addObserver(BowlingObserver observer);
  
  public void removeObserver(BowlingObserver observer);
  
  public void notifyObservers();

}
