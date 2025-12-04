/**
* Pomodoro utility for work time self-management
* 
* When starting work with Vim, the class launches a timer and after 25 minutes
* writes a command to a temporary file. This command changes Vim's color scheme, 
* reminding you that it's time to take a break.
*
* Usage:
*   java Main start     - erases information from temporary file, and starts timer 
*   java Main show_time - displays elapsed time of current session 
*   java Main stop      - erases information from temporary file
*
* @version  0.6.6
* @since    4.12.2025
* @author   AlexandrAnatoliev
*/
public class Main {
      private static final String PATH_TO_MONITOR = 
            "/.vim/pack/my-plugins/start/pomodoro/data/monitor.txt";
      private static final String PATH_TO_START_TIME = 
            "/.vim/pack/my-plugins/start/pomodoro/data/start_time.txt";
    /** Main entry point for the Pomodoro Timer application
    *
    * @param args command line arguments - first argument determines operation mode 
    *   "start"     to begin new work session     
    *   "show_time" to display current session duration     
    *   "stop"      or any other argument to clears session data     
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
    * Creates and configures a PomodoroTimer interface with standart settings.
    *
    * Uses 25-minute work sessions and "colorscheme blue" command for break
    * notification. File paths are resolved relative to user's home directory.
    * 
    * @return configured PomodoroTimer instance ready for use
    */
    private static PomodoroTimer createPomodoroTimer() {
        String homeDir = System.getProperty("user.home");
        return new PomodoroTimer(
                homeDir + PATH_TO_MONITOR, 
                homeDir + PATH_TO_START_TIME, 
                "colorscheme blue", 
                25L);
    }

    /**
    * Starts a new Pomodoro work session.
    */
    public static void start() {
        PomodoroTimer pomodoroTimer = createPomodoroTimer();
        long elapsedTime = pomodoroTimer.getElapsedTime();
        
        // start timer if elapsed time more 25 minutes
        if(elapsedTime > 25 * 60) {
            pomodoroTimer.writeCommand("");
            pomodoroTimer.startTimer();
        }
    }

    /**
    * Displays elapsed time of current work session.
    */
    public static void showTime() {
        PomodoroTimer pomodoroTimer = createPomodoroTimer();
        long currentTime = pomodoroTimer.getElapsedTime(); 

        if (currentTime == -1) {
            System.out.println("No active session found");
            return;
        }

        long sessionHours = currentTime / 3600;
        long sessionMinutes = (currentTime % 3600) / 60;
        long sessionSeconds = currentTime % 60;
        System.out.printf("Time per session:   %2d h %2d min %2d sec\n",
                sessionHours, sessionMinutes, sessionSeconds);
    }


    /**
    * Clears information from temporary file
    */
    public static void stop() {
        PomodoroTimer pomodoroTimer = createPomodoroTimer();
        pomodoroTimer.writeCommand("");
    }
}

