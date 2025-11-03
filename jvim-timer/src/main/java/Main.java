import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
* Utility for measuring Vim working time 
* 
* The class manages Vim session by storing start time in a temporary file, 
* calculating session duration, displaying the result 
* and maintaining daily totals. 
*
* Usage:
*   java Main start  - records start time in session file
*   java Main stop   - calculates and displays session duration
*
* @version  0.2.0
* @since    03.10.2025
* @author   AlexandrAnatoliev
*/
public class Main {
  private static final String SESSION_FILE_PATH = 
    "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_session_time.txt";
  private static final String DAY_FILE_PATH = 
    "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_day_time.txt";
  private static final String MONTH_FILE_PATH = 
    "/.vim/pack/my-plugins/start/jvim-timer/data/jvim_month_time.txt";

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
    String pathToMonthTime = homeDir + MONTH_FILE_PATH;
    
    SessionTimer sessionTimer = new SessionTimer(homeDir + SESSION_FILE_PATH);
    sessionTimer.writeToFile(System.currentTimeMillis() / 1000);
    
    DayTimer dayTimer = new DayTimer(pathToDayTime);
    DayTimer monthTimer = new DayTimer(pathToMonthTime);
    LocalDate today = LocalDate.now();
    
    if(dayTimer.fileIsNotExist() || 
      !dayTimer.getFileDate().equals(today)) {
        dayTimer.writeToFile(0L);
    }

    if(monthTimer.fileIsNotExist()) {
        monthTimer.writeToFile(0L);
    }

    if(!monthTimer.getFileDate().equals(today)) {
      long monthTime = monthTimer.readFromFile() * 29;
      monthTimer.writeToFile(
          (monthTime + dayTimer.readFromFile()) / 30);     
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
    String pathToMonthTime = homeDir + MONTH_FILE_PATH;

    SessionTimer sessionTimer = new SessionTimer(homeDir + SESSION_FILE_PATH);
            
    long duration = sessionTimer.getSessionTime(); 

    DayTimer dayTimer = new DayTimer(pathToDayTime);

    if(dayTimer.fileIsNotExist()) {
      dayTimer.writeToFile(0L);
    }

    long dayTime = dayTimer.readFromFile() + duration;

    dayTimer.writeToFile(dayTime);

    DayTimer monthTimer = new DayTimer(pathToMonthTime);
    if(monthTimer.fileIsNotExist()) {
      monthTimer.writeToFile(0L);
    }
    long monthTime = monthTimer.readFromFile();

    long sessionHours = duration / 3600;
    long sessionMinutes = (duration % 3600) / 60;
    long sessionSeconds = duration % 60;

    long dayHours = dayTime / 3600;
    long dayMinutes = (dayTime % 3600) / 60;
    long daySeconds = dayTime % 60;

    long monthHours = monthTime / 3600;
    long monthMinutes = (monthTime % 3600) / 60;
    long monthSeconds = monthTime % 60;

    System.out.println("\n");
    System.out.println("  =====================================");
    System.out.println("            Время работы Vim:           ");
    System.out.println("  -------------------------------------");
    System.out.printf( "  - за сеанс: %2d ч %2d мин %2d сек\n",
                            sessionHours, sessionMinutes, sessionSeconds);
    System.out.printf( "  - за день:  %2d ч %2d мин %2d сек\n",
                            dayHours, dayMinutes, daySeconds);
    System.out.printf( "  - за день:  %2d ч %2d мин %2d сек\n",
                            monthHours, monthMinutes, monthSeconds);
    System.out.println("  =====================================");
            
    sessionTimer.deleteFile();
  }
}
