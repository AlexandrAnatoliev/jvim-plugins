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
* @version  0.6.3
* @since    25.11.2025
* @author   AlexandrAnatoliev
*/
public class Main {
      private static final String PATH_TO_MONITOR = 
            "/.vim/pack/my-plugins/start/pomodoro/data/monitor.txt";
    /** Main method that handles command line arguments
    *
    * @param  args command line arguments - use "start" to start timer     
    *         any other value to stop and erase information from temporary file
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
    * Erases old information from temporary file, and starts the timer .
    */
    public static void start() {
        String homeDir = System.getProperty("user.home");
        PomodoroTimer pomodoroTimer = new PomodoroTimer(
              homeDir + PATH_TO_MONITOR, "colorscheme blue", 25L);
        pomodoroTimer.writeCommand("");
        pomodoroTimer.startTimer();
    }

    /**
    * Erases old information from temporary file
    */
    public static void stop() {
        String homeDir = System.getProperty("user.home");
        PomodoroTimer pomodoroTimer = new PomodoroTimer(
              homeDir + PATH_TO_MONITOR, "colorscheme blue", 25L);
        pomodoroTimer.writeCommand("");
    }
}

