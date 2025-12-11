package bowling;

/**
 * Stores a single leaderboard score.
 *
 * @author Jacob Noel and Tristan Apgar
 * @version Fall 2025
 *
 *          Honor Statement: This code adheres to JMU Policy.
 */
public class LeaderboardEntry
{
  private String username;
  private int score;

  /**
   * Creates a leaderboard entry.
   *
   * @param u
   *          the player's username
   * @param s
   *          the player's score
   */
  public LeaderboardEntry(final String u, final int s)
  {
    setUsername(u);
    setScore(s);
  }

  /**
   * Get the score.
   *
   * @return int the score
   */
  public int getScore()
  {
    return score;
  }

  /**
   * Set the score.
   *
   * @param score
   *          the player's score
   */
  public void setScore(final int score)
  {
    this.score = score;
  }

  /**
   * Get the username.
   *
   * @return string the username
   */
  public String getUsername()
  {
    return username;
  }

  /**
   * Set the score.
   *
   * @param username
   *          the player's name
   */
  public void setUsername(final String username)
  {
    this.username = username;
  }

}
