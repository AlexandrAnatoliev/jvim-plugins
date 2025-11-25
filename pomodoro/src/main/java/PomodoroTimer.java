import java.io.*;

/**
* The class to launch a timer and write a command to a temporary file. 
*
* @version  0.6.3
* @since    25.11.2025
* @author   AlexandrAnatoliev
*/
public class PomodoroTimer {
    protected String pathToMonitor;
    protected String defaultCommand;
    protected long time;                

    /** 
    * PomodoroTimer class constructor
    *
    * @param  pathToMonitor Path to temporary file for command storage
    * @param  defaultCommand Default command to store 
    * @param  time Work time of timer (in minutes)  
    */
    public PomodoroTimer(String pathToMonitor, String defaultCommand, long time) {
        this.pathToMonitor = pathToMonitor;
        this.defaultCommand = defaultCommand;
        this.time = time;
    }

    /**
    * Writes a command to a temporary file
    * 
    * @param command Command to write to the file
    * @throws Exception if is error writing
    */
    public void writeCommand(String command) {
        try {
            FileWriter writer = new FileWriter(pathToMonitor);
            writer.write(command);
      
            writer.close();

        } catch (Exception e) {
            System.out.println("Error writing: " + e.getMessage());
        }
    }

    /**
    * Starts a timer and then writes a command to a temporary file
    * 
    * @throws InterruptedException if timer is interrupted
    */
    public void startTimer() {
        try {
            Thread.sleep(60000 * time);
            writeCommand(defaultCommand);
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted");
        }
    }
}
