import java.io.*;

public class PomodoroTimer {
    protected String pathToMonitor;
    protected String defaultCommand;
    protected long time; // in minutes

    /** 
    * Timer class constructor
    *
    * @param  pathToFile - path to temporary file for store of value
    */
    public PomodoroTimer(String pathToMonitor, String defaultCommand, long time) {
        this.pathToMonitor = pathToMonitor;
        this.defaultCommand = defaultCommand;
        this.time = time;
    }

    public void writeCommand(String command) {
        try {
            FileWriter writer = new FileWriter(pathToMonitor);
            writer.write(command);
      
            writer.close();

        } catch (Exception e) {
            System.out.println("Ошибка записи: " + e.getMessage());
        }
    }

    public void startTimer() {
        try {
            Thread.sleep(60000 * time);
            writeCommand(defaultCommand);
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted");
        }
    }
}
