import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
* Timer - class for measuring Vim working time 
* 
* The class provides methods for reading start time from temporary file, 
* calculates running duration, and managing the temporary file. 
*
* @version  0.2.2 
* @since    03.11.2025
* @author   AlexandrAnatoliev
*/
public class Timer {
  protected String pathToFile;

  /** 
  * Timer class constructor
  *
  * @param  pathToFile - path to temporary file for store of value
  */
  public Timer(String pathToFile) {
    this.pathToFile = pathToFile;
  }
  
  /**
  * Writes time value to temporary file 
  *
  * @param  value - time value in seconds to write to file
  * @throws Exception if unexpected error 
  */
  public void writeToFile(Long value) {
    try {
      FileWriter writer = new FileWriter(pathToFile);
      writer.write(value.toString());
      
      writer.close();

    } catch (Exception e) {
        System.out.println("Ошибка записи: " + e.getMessage());
    }
  }
  
  /**
  * Reads time value from temporary file 
  *
  * @return time value from file in seconds, 
  * or 0 if file does not exist or contains invalid data
  * @throws Exception if unexpected error 
  */
  public long readFromFile() {
    try {
      BufferedReader reader = new BufferedReader(
                          new FileReader(this.pathToFile));
      long value = Long.parseLong(reader.readLine());

      reader.close();

      return value;

    } catch (Exception e) {
        System.out.println("Ошибка чтения: " + e.getMessage());
    }
    return 0;
  }

  /**
  * Reads start time from file  and calculates session duration
  * in seconds
  *
  * @return - Vim session duration in seconds
  */
  long getSessionTime() {
    long startTime = readFromFile();
    long duration = (System.currentTimeMillis() / 1000) - startTime;

    return duration;
  }

  /**
  * Deletes temporary file 
  */
  void deleteFile() {
    new File(this.pathToFile).delete();
  }

  /**
  * Checks if temporary file does not exist
  *
  * @return true if file does not exists
  *         false if file exists
  * @throws Exception if file does not exists
  */
  public boolean fileIsNotExist() {
    File file = new File(pathToFile);

    try {
      return !file.exists();
    } catch (Exception e) {
        System.out.println("Ошибка проверки наличия файла: " 
            + e.getMessage());
    }
 
    return false;
  }

  /**
  * Retrieves the creation date of the file
  *
  * @return LocalDate representing file creation time
  * @throws Exception if unexpected error 
  */
  public LocalDate getFileDate() {
    File file = new File(pathToFile);

    try {
      BasicFileAttributes attrs = Files.readAttributes(
        file.toPath(), BasicFileAttributes.class);

      LocalDate fileDate = attrs.creationTime().toInstant()
        .atZone(ZoneId.systemDefault()).toLocalDate();

      return fileDate;
      
    } catch (Exception e) {
        System.out.println("Ошибка проверки даты создания файла: " 
            + e.getMessage());
    }

    return LocalDate.now();
  }
}

