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
* @version  0.1.5 
* @since    07.09.2025
* @author   AlexandrAnatoliev
*/
public class JvimTimer {
  private static final String SESSION_FILE_PATH = 
    "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_session_time.txt";
  private static final String DAY_FILE_PATH = 
    "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_day_time.txt";

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
    String pathToDayTime = homeDir + DAY_FILE_PATH;
    
    SessionTimer vimSession = new SessionTimer(homeDir + SESSION_FILE_PATH);

    vimSession.writeToFile(System.currentTimeMillis() / 1000);
    
    DayTimer dayTimer = new DayTimer(pathToDayTime);
    LocalDate today = LocalDate.now();
    
    if(dayTimer.fileIsNotExist() || 
      !dayTimer.getFileDate().equals(today)) {
        dayTimer.writeToFile(0L);
    }

    return;
  }

  /**
  * Reads start time from session file, calculates duration, 
  * displays result and cleans up temporary session file
  */
  public static void stop() {
    String homeDir = System.getProperty("user.home");
    String pathToDayTime = homeDir + DAY_FILE_PATH;

    SessionTimer vimSession = new SessionTimer(homeDir + SESSION_FILE_PATH);
            
    long duration = vimSession.getSessionTime(); 

    DayTimer dayTimer = new DayTimer(pathToDayTime);

    if(dayTimer.fileIsNotExist()) {
      dayTimer.writeToFile(0L);
    }

    long dayTime = dayTimer.readFromFile() + duration;

    dayTimer.writeToFile(dayTime);

    long hours = duration / 3600;
    long minutes = (duration % 3600) / 60;
    long seconds = duration % 60;

    long dayHours = dayTime / 3600;
    long dayMinutes = (dayTime % 3600) / 60;
    long daySeconds = dayTime % 60;

    System.out.println("\n");
    System.out.println("  =====================================");
    System.out.println("            Время работы Vim:           ");
    System.out.println("  -------------------------------------");
    System.out.printf( "  - за сеанс: %2d ч %2d мин %2d сек\n",
                            hours, minutes, seconds);
    System.out.printf( "  - за день:  %2d ч %2d мин %2d сек\n",
                            dayHours, dayMinutes, daySeconds);
    System.out.println("  =====================================");
            
    vimSession.deleteFile();
  }
}
