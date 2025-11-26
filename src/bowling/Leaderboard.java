package bowling;

import java.util.*;

public class Leaderboard
{
  public static class Entry
  {
    public final String user;
    public final int score;

    public Entry(String user, int score)
    {
      this.user = user;
      this.score = score;
    }
  }

  private final List<Entry> entries = new ArrayList<>();

  public void addEntry(String user, int score)
  {
    entries.add(new Entry(user, score));
    entries.sort(Comparator.comparingInt(e -> -e.score));
  }

  public List<Entry> getEntries()
  {
    return Collections.unmodifiableList(entries);
  }

}
