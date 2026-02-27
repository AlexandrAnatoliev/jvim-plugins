package com.vimstat;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class to get working stats
 *
 * @version 0.8.37
 * @since 27.02.2026
 * @author AlexandrAnatoliev
 */
public abstract class Stats {
  protected String pathToCounter;
  protected static final String ERROR = "[" + Colors.RED.apply("ERROR") + "]";
  protected static final Logger LOGGER = LoggerFactory.getLogger(Stats.class);

  /**
   * Stats class constructor
   *
   * @param pathToCounter Path to temporary file for count value storage
   */
  protected Stats(String pathToCounter) {
    this.pathToCounter = pathToCounter;
  }

  /**
   * Writes long value to temporary file
   *
   * @param value Long value to write to file
   */
  public void writeValue(Long value) {
    try (FileWriter writer = new FileWriter(pathToCounter)) {
      writer.write(value.toString());
    } catch (Exception e) {
      LOGGER.error(ERROR + " Writing long: " + e.getMessage());
    }
  }

  /**
   * Reads long value from temporary file
   *
   * @return Long value from file, Or 0 if file does not exist or contains invalid data
   */
  public long readLongValue() {
    try (BufferedReader reader = new BufferedReader(new FileReader(this.pathToCounter))) {

      return Long.parseLong(reader.readLine());

    } catch (IOException | NumberFormatException e) {
      LOGGER.error(ERROR + " Reading long: " + e.getMessage());
      return 0;
    }
  }

  /**
   * Retrieves the creation date of the file
   *
   * @param pathToFile Path to temporary file
   * @return LocalDate Representing file creation time Or null if has error
   */
  public LocalDate getFileDate(String pathToFile) {
    Path path = Paths.get(pathToFile);
    try {
      BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
      return attrs.creationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    } catch (Exception e) {
      LOGGER.error(ERROR + " Getting file date: " + e.getMessage());
      return null;
    }
  }

  /**
   * Checks if temporary file exists
   *
   * @param pathToFile Path to temporary file
   * @return true If file exists, false otherwise
   */
  public boolean isFileExists(String pathToFile) {
    return new File(pathToFile).exists();
  }
}
