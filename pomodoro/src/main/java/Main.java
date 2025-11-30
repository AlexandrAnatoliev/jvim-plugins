/**
* Pomodoro utility for work time self-management
* 
* When starting work with Vim, the class launches a timer and after 25 minutes
* writes a command to a temporary file. This command changes Vim's color scheme, 
* reminding you that it's time to take a break.
*
* Usage:
*   java Main start  -  the information is erased from temporary file, 
*                       and the timer starts.
*   java Main stop   -  the information is erased from temporary file
*
* @version  0.6.5
* @since    25.11.2025
* @author   AlexandrAnatoliev
*/
public class Main {
      private static final String PATH_TO_MONITOR = 
            "/.vim/pack/my-plugins/start/pomodoro/data/monitor.txt";
      private static final String PATH_TO_START_TIME = 
            "/.vim/pack/my-plugins/start/pomodoro/data/start_time.txt";
    /** Main method that handles command line arguments
    *
    * @param  args command line arguments - use "start" to start timer     
    *         any other value to stop and erase information from temporary file
    *
    */
    public static void main(String[] args) {
        if (args.length > 0 && "start".equals(args[0])) {
            start();
        } else if (args.length > 0 && "show_time".equals(args[0])) {
            showTime();
        } else {
            stop();
        }
    }

    /**
    * Erases old information from temporary file, and starts the timer .
    */
    public static void start() {
        String homeDir = System.getProperty("user.home");
        PomodoroTimer pomodoroTimer = new PomodoroTimer(
              homeDir + PATH_TO_MONITOR, 
              homeDir + PATH_TO_START_TIME, 
              "colorscheme blue", 
              25L);
        pomodoroTimer.writeCommand("");
        pomodoroTimer.startTimer();
    }

    public static void showTime() {
        String homeDir = System.getProperty("user.home");
        PomodoroTimer pomodoroTimer = new PomodoroTimer(
              homeDir + PATH_TO_MONITOR, 
              homeDir + PATH_TO_START_TIME, 
              "colorscheme blue", 
              25L);
        long currentTime = pomodoroTimer.getElapcedTime(); 
        long sessionHours = currentTime / 3600;
        long sessionMinutes = (currentTime % 3600) / 60;
        long sessionSeconds = currentTime % 60;
        System.out.printf("Time per session:   %2d h %2d min %2d sec\n",
                sessionHours, sessionMinutes, sessionSeconds);
    }


    /**
    * Erases old information from temporary file
    */
    public static void stop() {
        String homeDir = System.getProperty("user.home");
        PomodoroTimer pomodoroTimer = new PomodoroTimer(
              homeDir + PATH_TO_MONITOR, 
              homeDir + PATH_TO_START_TIME, 
              "colorscheme blue", 
              25L);
        pomodoroTimer.writeCommand("");
    }
}

