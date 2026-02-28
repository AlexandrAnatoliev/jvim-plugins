package com.vimstat;

import java.nio.file.*;
import java.util.Scanner;

/**
 * The class to get git stats
 *
 * @version 0.8.37
 * @since 27.02.2026
 * @author AlexandrAnatoliev
 */
public class GitStats extends Stats {
  protected String pathToHash;

  /**
   * GitStats class constructor
   *
   * @param pathToHash Path to temporary file for hash storage
   */
  public GitStats(String pathToHash, String pathToCounter) {
    super(pathToCounter);
    this.pathToHash = pathToHash;
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
   * Writes hash value to a temporary file
   *
   * @param hash to write to the file
   */
  public void write(String hash) {
    try {
      String content = (hash == null) ? "" : hash;
      Files.writeString(Paths.get(pathToHash), content);
    } catch (Exception e) {
      LOGGER.error(ERROR + " Writing hash: " + e.getMessage());
    }
  }

  /**
   * Reads hash from temporary file
   *
   * @return hash from file
   */
  public String readHash() {
    try {
      return Files.readString(Paths.get(this.pathToHash));
    } catch (Exception e) {
      LOGGER.error(ERROR + " Reading hash: " + e.getMessage());
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
    } else if (command.equalsIgnoreCase("deleted")) {
      num = 2;
    } else {
      return 0;
    }
    String awkCommand =
        String.format("git show --numstat | awk '/^[0-9]/ {add+=$%d} END {print add+0}'", num);

    ProcessBuilder pb = new ProcessBuilder("/usr/bin/bash", "-c", awkCommand);
    try {
      Process p = pb.start();
      p.waitFor();
      try (Scanner scanner = new Scanner(p.getInputStream())) {
        return scanner.hasNextLong() ? scanner.nextLong() : 0;
      }
    } catch (Exception e) {
      Thread.currentThread().interrupt();
      LOGGER.error(ERROR + " Getting last commit " + command + " lines: " + e.getMessage());
      return 0;
    }
  }

  /**
   * Create and initial files default values if is not exit
   */
  @Override
  public void createFiles() {
    if (!isFileExists(super.pathToCounter)) {
      super.write(0L);
    }
    if (!isFileExists(this.pathToHash)) {
      this.write("");
    }
  }
}
