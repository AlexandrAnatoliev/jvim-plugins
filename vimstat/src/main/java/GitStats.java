import java.nio.file.*;
import java.util.Scanner;

/**
 * The class to get git stats
 *
 * @version 0.8.11
 * @since 09.02.2026
 * @author AlexandrAnatoliev
 */
public class GitStats extends Stats {
  protected String pathToString;

  /**
   * GitStats class constructor
   *
   * @param pathToString Path to temporary file for String value storage
   */
  public GitStats(String pathToString, String pathToLong) {
    super(pathToLong);
    this.pathToString = pathToString;
  }

  /**
   * Get last commit hash
   *
   * @return Last commit hash, or "" if error
   */
  public String getLastCommitHash() {
    ProcessBuilder pb = new ProcessBuilder("git", "rev-parse", "HEAD");
    try {
      Process p = pb.start();
      p.waitFor();
      try (Scanner scanner = new Scanner(p.getInputStream())) {
        return scanner.hasNext() ? scanner.next() : "";
      }
    } catch (Exception e) {
      System.out.println(
          ERROR + " Getting last commit hash: " + e.getMessage());
      return "";
    }
  }

  /**
   * Writes string value to a temporary file
   *
   * @param hash String to write to the file
   */
  public void writeString(String hash) {
    try {
      String content = (hash == null) ? "" : hash;
      Files.writeString(Paths.get(pathToString), content);
    } catch (Exception e) {
      System.out.println(ERROR + " Writing string: " + e.getMessage());
    }
  }

  /**
   * Reads string value from temporary file
   *
   * @return String value from file
   */
  public String readString() {
    try {
      return Files.readString(Paths.get(this.pathToString));
    } catch (Exception e) {
      System.out.println(ERROR + " Reading string: " + e.getMessage());
      return "";
    }
  }
}
