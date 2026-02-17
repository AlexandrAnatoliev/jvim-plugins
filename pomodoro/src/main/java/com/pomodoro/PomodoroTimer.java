package com.pomodoro;

import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class to launch a timer and write a command to a temporary file.
 *
 * @version 0.8.30
 * @since 17.02.2026
 * @author AlexandrAnatoliev
 */
public class PomodoroTimer {
  protected String pathToMonitor;
  protected String pathToStartTime;
  protected String defaultCommand;
  protected long time;
  protected static final String ERROR = "[" + Colors.RED.apply("ERROR") + "]";
  private static final Logger LOGGER = LoggerFactory.getLogger(PomodoroTimer.class);

  /**
   * PomodoroTimer class constructor
   *
   * @param pathToMonitor Path to temporary file for command storage
   * @param pathToStartTime Path to file for start time storage
   * @param defaultCommand Default command to store
   * @param time Work time of timer (in minutes)
   */
  public PomodoroTimer(
      String pathToMonitor, String pathToStartTime, String defaultCommand, long time) {
    this.pathToMonitor = pathToMonitor;
    this.pathToStartTime = pathToStartTime;
    this.defaultCommand = defaultCommand;
    this.time = time;
  }

  /**
   * Writes a command to a temporary file
   *
   * @param command Command to write to the file
   */
  public void writeCommand(String command) {
    try (FileWriter writer = new FileWriter(pathToMonitor)) {
      writer.write(command);
    } catch (Exception e) {
      LOGGER.error(ERROR + " Writing command: " + e.getMessage());
    }
  }

  /**
   * Writes a time to a temporary file
   *
   * @param time Time to write to the file
   */
  public void writeTime(Long time) {
    try (FileWriter writer = new FileWriter(pathToStartTime)) {
      writer.write(time.toString());
    } catch (Exception e) {
      LOGGER.error(ERROR + " Writing time: " + e.getMessage());
    }
  }

  /** Starts a timer and then writes a command to a temporary file */
  public void startTimer() {
    try {
      long startTime = System.currentTimeMillis() / 1000; // in seconds
      writeTime(startTime);
      Thread.sleep(60000 * time);
      writeCommand(defaultCommand);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error(ERROR + " Timer interrupted");
    }
  }

  /**
   * Read a start time from a temporary file
   *
   * @throws Exception If is error reading or invalid data in file
   * @return Start time, or -1 if error
   */
  public long getStartTime() {
    try (BufferedReader reader = new BufferedReader(new FileReader(this.pathToStartTime))) {
      String line = reader.readLine();
      if (line != null) {
        return Long.parseLong(line);
      }
    } catch (Exception e) {
      LOGGER.error(ERROR + " Getting start time: " + e.getMessage());
    }
    return -1;
  }

  /**
   * Calculate elapsed time since start
   *
   * @return Elapsed time in seconds, or -1
   */
  public long getElapsedTime() {
    long startTime = getStartTime();
    if (startTime == -1) {
      return -1;
    }
    return System.currentTimeMillis() / 1000 - startTime;
  }
}
