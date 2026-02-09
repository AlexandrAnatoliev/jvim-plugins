import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.time.*;

/**
 * The class to get working stats
 *
 * @version 0.8.11
 * @since 09.02.2026
 * @author AlexandrAnatoliev
 */
public abstract class Stats {
  protected String pathToLong;
  protected static final String ERROR = "[" + Colors.RED.apply("ERROR") + "]";

  /**
   * Stats class constructor
   *
   * @param pathToLong Path to temporary file for long value storage
   */
  public Stats(String pathToLong) {
    this.pathToLong = pathToLong;
  }

  /**
   * Writes long value to temporary file
   *
   * @param value Long value to write to file
   */
  public void writeLong(Long value) {
    try (FileWriter writer = new FileWriter(pathToLong)) {
      writer.write(value.toString());
    } catch (Exception e) {
      System.out.println(ERROR + " Writing long: " + e.getMessage());
    }
  }

  /**
   * Reads long value from temporary file
   *
   * @return Long value from file, Or 0 if file does not exist or contains invalid data
   */
  public long readLong() {
    try (BufferedReader reader = new BufferedReader(new FileReader(this.pathToLong))) {

      return Long.parseLong(reader.readLine());

    } catch (IOException | NumberFormatException e) {
      System.out.println(ERROR + " Reading long: " + e.getMessage());
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
      LocalDate fileDate =
          attrs.creationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
      return fileDate;
    } catch (Exception e) {
      System.out.println(ERROR + " Getting file date: " + e.getMessage());
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
