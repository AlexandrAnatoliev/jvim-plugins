import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
* JvimTimer - Utility for measuring Vim working time 
* 
* The class manages Vim session by storing start time in a temporary file, 
* calculating session duration, displaying the result 
* and maintaining daily totals. 
*
* Usage:
*   java JvimTimer start  - records start time in session file
*   java JvimTimer stop   - calculates and displays session duration
*
* @version  0.1.4 
* @since    06.09.2025
* @author   AlexandrAnatoliev
*/
public class JvimTimer {

  /** Main method that handles command line arguments
  *
  * @param  args command line arguments - use "start" to begin timing
  *         any other value to stop and display duration
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
  * and checks daily time file for date consistency
  */
  public static void start() {
    String homeDir = System.getProperty("user.home");
    
    Session vimSession = new Session(homeDir +
      "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_session_time.txt");

    vimSession.writeToFile(System.currentTimeMillis() / 1000);
    
    String pathToDayTime = homeDir +
      "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_day_time.txt";

    DayTimer dayTimer = new DayTimer(pathToDayTime);
    LocalDate today = LocalDate.now();
    
    if(dayTimer.fileIsNotExist() || 
      !dayTimer.getFileDate().equals(today)) {
        dayTimer.writeToFile(0L);
    }

    return;


//     checkFileDate(homeDir + 
//       "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_day_time.txt");
  }

  /**
  * Check the creation date of daily time file and resents the counter
  * if file date doesn't match current date
  * 
  * @param pathToFile - path to daily time file
  */
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
  * Reads start time from session file, calculates duration, 
  * displays result and cleans up temporary session file
  */
  public static void stop() {
    try {
      String homeDir = System.getProperty("user.home");

      Session vimSession = new Session(homeDir +
      "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_session_time.txt");
            
      long duration = vimSession.getSessionTime(); 
      long hours = duration / 3600;
      long minutes = (duration % 3600) / 60;
      long seconds = duration % 60;

      System.out.println("\n");
      System.out.println("  =====================================");
      System.out.println("            Время работы Vim:           ");
      System.out.println("  -------------------------------------");
      System.out.printf( "  - за сеанс: %2d ч %2d мин %2d сек\n",
                        hours, minutes, seconds);
            
      vimSession.deleteFile();

      printDayTime(duration, homeDir + 
        "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_day_time.txt");
            
    } catch (Exception e) {
        System.out.println("Ошибка таймера: " + e.getMessage());
    }
  }

  /**
  * Updates and displays total working time for current day
  *
  * @param duration - current session duration in seconds
  * @param pathToFile - path to daily time storage file
  */
  public static void printDayTime(long duration, String pathToFile) {
    try {
      File file = new File(pathToFile);

      if(!file.exists()) {
        FileWriter writer = new FileWriter(pathToFile);
        writer.write("0");
        writer.close();
      }

      BufferedReader reader = 
        new BufferedReader(new FileReader(pathToFile));

      Long dayTime = Long.parseLong(reader.readLine()) + duration * 1000;
      reader.close();

      FileWriter writer = new FileWriter(pathToFile);
      writer.write(dayTime.toString());
      writer.close();
      
      long hours = dayTime / 3600000;
      long minutes = (dayTime % 3600000) / 60000;
      long seconds = (dayTime % 60000) / 1000;
            
      System.out.printf( "  - за день:  %2d ч %2d мин %2d сек\n",
                        hours, minutes, seconds);
      System.out.println("  =====================================");
      
    } catch (Exception e) {
        System.out.println("Ошибка вывода дневного времени: " 
          + e.getMessage());
    }
  }  
}
