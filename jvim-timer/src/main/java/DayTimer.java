import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
* DayTimer - class for measuring Vim daily working time 
* 
* Handles daily time tracking operations including 
* file existence check, date validation, 
* and time value storage / retrieval 
*
* @version  0.1.9 
* @since    22.09.2025
* @author   AlexandrAnatoliev
*/
public class DayTimer {
  private String pathToFile;

  /** 
  * Constructs a DayTimer instance with specified file path
  *
  * @param  pathToFile - path to temporary file for storing time values
  */
  public DayTimer(String pathToFile) {
    this.pathToFile = pathToFile;
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
  
  /**
  * Writes time value to temporary file 
  *
  * @param  value - tlme value in seconds to write to file
  * @throws Exception if unexpected error 
  */
  void writeToFile(Long value) {
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
  * @return time value from file in seconds, i
  * or 0 if file does not exist or contains invalid data
  * @throws Exception if unexpected error 
  */
  long readFromFile() {
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
} 
