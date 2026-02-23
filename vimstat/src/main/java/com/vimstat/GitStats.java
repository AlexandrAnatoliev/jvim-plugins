package com.vimstat;

import java.nio.file.*;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class to get git stats
 *
 * @version 0.8.34
 * @since 23.02.2026
 * @author AlexandrAnatoliev
 */
public class GitStats extends Stats {
  protected String pathToStringValue;
  private static final Logger LOGGER = LoggerFactory.getLogger(GitStats.class);

  /**
   * GitStats class constructor
   *
   * @param pathToStringValue Path to temporary file for String value storage
   */
  public GitStats(String pathToStringValue, String pathToLongValue) {
    super(pathToLongValue);
    this.pathToStringValue = pathToStringValue;
  }

  /**
   * Get last commit hash
   *
   * @return Last commit hash, or "" if error
   */
  public String getLastCommitHash() {
    ProcessBuilder pb = new ProcessBuilder("/usr/bin/git", "rev-parse", "HEAD");
    try {
      Process p = pb.start();
      p.waitFor();
      try (Scanner scanner = new Scanner(p.getInputStream())) {
        return scanner.hasNext() ? scanner.next() : "";
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
      LOGGER.error(ERROR + " Getting last commit hash: " + e.getMessage());
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
      Files.writeString(Paths.get(pathToStringValue), content);
    } catch (Exception e) {
      LOGGER.error(ERROR + " Writing string: " + e.getMessage());
    }
  }

  /**
   * Reads string value from temporary file
   *
   * @return String value from file
   */
  public String readString() {
    try {
      return Files.readString(Paths.get(this.pathToStringValue));
    } catch (Exception e) {
      LOGGER.error(ERROR + " Reading string: " + e.getMessage());
      return "";
    }
  }

  /**
   * Get last commit added lines
   *
   * @return Added lines value
   */
  public long getLastCommitAddedLines() {
    ProcessBuilder pb = new ProcessBuilder(
            "bash", "-c", 
            "git show --numstat | awk '/^[0-9]/ {add+=$1} END {print add}'");
    try {
      Process p = pb.start();
      p.waitFor();
      try (Scanner scanner = new Scanner(p.getInputStream())) {
        return scanner.hasNextLong() ? scanner.nextLong() : 0;
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
      LOGGER.error(ERROR + " Getting last commit added lines: " + e.getMessage());
      return 0;
    }
  }
}
