package com.vimstat;

import java.nio.file.*;
import java.util.Scanner;

/**
 * The class to get git stats
 *
 * @version 0.8.34
 * @since 24.02.2026
 * @author AlexandrAnatoliev
 */
public class GitStats extends Stats {
  protected String pathToStringValue;

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
  public void writeStringValue(String hash) {
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
  public String readStringValue() {
    try {
      return Files.readString(Paths.get(this.pathToStringValue));
    } catch (Exception e) {
      LOGGER.error(ERROR + " Reading string: " + e.getMessage());
      return "";
    }
  }

  /**
   * Get last commit added or deleted lines
   *
   * @param command Type of lines (added / deleted)
   * @return Added or deleted lines value
   */
  public long getLastCommitLines(String command) {
      int num;
      if (command.equalsIgnoreCase("added")) {
          num = 1;
      }
      else if (command.equalsIgnoreCase("added")) {
          num = 2;
      }
      else {
          return 0;
      }
    ProcessBuilder pb =
        new ProcessBuilder(
            "/usr/bin/bash",
            "-c",
            "/usr/bin/git",
            "show --numstat | awk '/^[0-9]/ {add+=$num} END {print add+0}'");
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

  /**
   * Get last commit deleted lines
   *
   * @return Deleted lines value
   */
  public long getLastCommitDeletedLines() {
    ProcessBuilder pb =
        new ProcessBuilder(
            "/usr/bin/bash",
            "-c",
            "/usr/bin/git",
            "show --numstat | awk '/^[0-9]/ {deleted+=$2} END {print deleted+0}'");
    try {
      Process p = pb.start();
      p.waitFor();
      try (Scanner scanner = new Scanner(p.getInputStream())) {
        return scanner.hasNextLong() ? scanner.nextLong() : 0;
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
      LOGGER.error(ERROR + " Getting last commit deleted lines: " + e.getMessage());
      return 0;
    }
  }
}
