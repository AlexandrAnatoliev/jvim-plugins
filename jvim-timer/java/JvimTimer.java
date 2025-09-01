import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
* JvimTimer - Utility for measuring vim working time 
* 
* Class reads Vim start time from a temporary file, 
* calculates running duration, outputs the result 
* and deletes the temporary file. 
*
* Usage:
*   java JvimTimer start  - records start time
*   java JvimTimer stop   - calculates and displays duration
*
* @version  0.1.3 
* @since    01.09.2025
* @author   AlexandrAnatoliev
*/
public class JvimTimer {

  /** Main method that handles command line arguments
  *
  * @param  args command line arguments - use "start" to begin timing
  *
  */
  public static void main(String[] args) {
    if (args.length > 0 && "start".equals(args[0])) {
      start();
    } else {
      stop();
    }
  }

  /**
  * Records the current time as start time in temporary file
  */
  public static void start() {
    try {
      FileWriter writer = new FileWriter("/tmp/jvim_start_time.txt");
      Long startTime = System.currentTimeMillis();
      writer.write(startTime.toString());
      writer.close();

    } catch (Exception e) {
        System.out.println("Ошибка записи: " + e.getMessage());
    }
  }

  public static void checkFileDate(String pathToFile) {
    File file = new File(pathToFile);

    try {
      if(!file.exists()) {
        FileWriter writer = new FileWriter(pathToFile);
        writer.write("0");
        writer.close();
        return;
      }

      BasicFileAttributes attrs = Files.readAttributes(
        file.toPath(), BasicFileAttributes.class);

      LocalDate fileDate = attrs.creationTime().toInstant()
        .atZone(ZoneId.systemDefault()).toLocalDate();

      LocalDate today = LocalDate.now();

      if(!fileDate.equals(today)) {
        FileWriter writer = new FileWriter(pathToFile);
        writer.write("0");
        writer.close();
      }

      } catch (Exception e) {
          System.out.println("Ошибка проверки файла: " 
            + e.getMessage());
      }
    }

  /**
  * Reads start time, calculates duration, displays result
  * and cleans up temporary file
  */
  public static void stop() {
    try {
      BufferedReader reader = 
        new BufferedReader(new FileReader("/tmp/jvim_start_time.txt"));

      long startTime = Long.parseLong(reader.readLine());
      reader.close();
            
      long duration = System.currentTimeMillis() - startTime;
      long hours = duration / 3600000;
      long minutes = (duration % 3600000) / 60000;
      long seconds = (duration % 60000) / 1000;
            
      System.out.printf("Время работы Vim: %d ч %d мин %d сек\n",
                        hours, minutes, seconds);
            
      new File("/tmp/jvim_start_time.txt")
      .delete();
            
    } catch (Exception e) {
        System.out.println("Ошибка таймера: " + e.getMessage());
    }
  }
}
