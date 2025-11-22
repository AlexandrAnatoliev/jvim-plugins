public class Main {
      private static final String PATH_TO_MONITOR = 
            "/.vim/pack/my-plugins/start/jvim-pomodoro/data/monitor.txt";
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

    public static void start() {
        String homeDir = System.getProperty("user.home");
        PomodoroTimer pomodoroTimer = new PomodoroTimer(
              homeDir + PATH_TO_MONITOR, "colorscheme blue", 25L);
        pomodoroTimer.writeCommand("");
        pomodoroTimer.startTimer();
    }

  public static void stop() {
        String homeDir = System.getProperty("user.home");
        PomodoroTimer pomodoroTimer = new PomodoroTimer(
              homeDir + PATH_TO_MONITOR, "colorscheme blue", 25L);
        pomodoroTimer.writeCommand("");
    }
}

