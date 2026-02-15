package com.vimstat;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TimeStats - class for measuring Vim working time
 *
 * <p>The class provides methods for reading start time from temporary file, calculates running
 * duration, and managing the temporary file.
 *
 * @version 0.8.29
 * @since 15.02.2026
 * @author AlexandrAnatoliev
 */
public class TimeStats extends Stats {
  private static final Logger LOGGER = LoggerFactory.getLogger(GitStats.class);

  /**
   * TimeStats class constructor
   *
   * @param pathToLong - path to temporary file for store of value
   */
  public TimeStats(String pathToLong) {
    super(pathToLong);
  }

  /**
   * Calculates the duration of the current Vim session in seconds Reads the start time from
   * temporary file and subtracts it from current time
   *
   * @return Duration in seconds, or 0 if start time 0
   */
  public long getSessionTime() {
    long startTime = readLong();

    if (startTime == 0) {
      return 0;
    }

    long currentTime = System.currentTimeMillis() / 1000;

    return currentTime - startTime;
  }

  /** Deletes temporary file */
  public void deleteFile() {
    boolean result = new File(this.pathToLong).delete();
    if (!result) {
      LOGGER.error(ERROR + " Deleting file");
    }
  }
}
