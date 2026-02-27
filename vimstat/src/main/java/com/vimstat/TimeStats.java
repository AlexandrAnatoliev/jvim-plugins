package com.vimstat;

import java.io.*;

/**
 * TimeStats - class for measuring Vim working time
 *
 * <p>The class provides methods for reading start time from temporary file, calculates running
 * duration, and managing the temporary file.
 *
 * @version 0.8.34
 * @since 22.02.2026
 * @author AlexandrAnatoliev
 */
public class TimeStats extends Stats {

  /**
   * TimeStats class constructor
   *
   * @param pathToCounter - path to temporary file for store of count value
   */
  public TimeStats(String pathToCounter) {
    super(pathToCounter);
  }

  /**
   * Calculates the duration of the current Vim session in seconds Reads the start time from
   * temporary file and subtracts it from current time
   *
   * @return Duration in seconds, or 0 if start time 0
   */
  public long getSessionTime() {
    long startTime = readLongValue();

    if (startTime == 0) {
      return 0;
    }

    long currentTime = System.currentTimeMillis() / 1000;

    return currentTime - startTime;
  }

  /** Deletes temporary file */
  public void deleteFile() {
    boolean result = new File(this.pathToCounter).delete();
    if (!result) {
      LOGGER.error(ERROR + " Deleting file");
    }
  }
}
