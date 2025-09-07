import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
* DayTimer - class for measuring Vim daily working time 
* 
* @version  0.1.5 
* @since    07.09.2025
* @author   AlexandrAnatoliev
*/
public class DayTimer {
  private String pathToFile;

  /** 
  * Session class constructor
  *
  * @param  pathToFile - path to temporary file for store of value
  */
  DayTimer(String pathToFile) {
    this.pathToFile = pathToFile;
  }

  public boolean fileIsNotExist() {
    File file = new File(pathToFile);

    try {
      return !file.exists();
    } catch (Exception e) {
        System.out.println("Ошибка проверки файла: " 
            + e.getMessage());
    }
 
    return false;
  }

  public LocalDate getFileDate() {
    File file = new File(pathToFile);

    try {
      BasicFileAttributes attrs = Files.readAttributes(
        file.toPath(), BasicFileAttributes.class);

      LocalDate fileDate = attrs.creationTime().toInstant()
        .atZone(ZoneId.systemDefault()).toLocalDate();

      return fileDate;
      
    } catch (Exception e) {
        System.out.println("Ошибка проверки файла: " 
            + e.getMessage());
    }

    return LocalDate.now();
  }
  
  /**
  * Writes time value to temporary file 
  *
  * @param  varue - tlme value to write
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
} 
