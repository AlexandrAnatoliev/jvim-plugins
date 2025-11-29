import java.io.*;

/**
* The class to launch a timer and write a command to a temporary file. 
*
* @version  0.6.5
* @since    29.11.2025
* @author   AlexandrAnatoliev
*/
public class PomodoroTimer {
    protected String pathToMonitor;
    protected String pathToStartTime;
    protected String defaultCommand;
    protected long time;                

    /** 
    * PomodoroTimer class constructor
    *
    * @param  pathToMonitor Path to temporary file for command storage
    * @param  defaultCommand Default command to store 
    * @param  time Work time of timer (in minutes)  
    */
    public PomodoroTimer(
            String pathToMonitor, String pathToStartTime, String defaultCommand, long time) {
        this.pathToMonitor = pathToMonitor;
        this.pathToStartTime = pathToStartTime;
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

    public void writeTime(Long time) {
        try {
            FileWriter writer = new FileWriter(pathToStartTime);
            writer.write(time.toString());
      
            writer.close();

        } catch (Exception e) {
            System.out.println("Error writing: " + e.getMessage());
        }
    }


    public long getStartTime() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(this.pathToStartTime));
            long value = Long.parseLong(reader.readLine());

            reader.close();

            return value;

        } catch (Exception e) {
            System.out.println("Ошибка чтения: " + e.getMessage());
        }
        return 0;
    }

    /**
    * Starts a timer and then writes a command to a temporary file
    * 
    * @throws InterruptedException if timer is interrupted
    */
    public void startTimer() {
        try {
            long startTime = System.currentTimeMillis() / 1000; // in seconds
            writeTime(startTime);
            Thread.sleep(60000 * time);
            writeCommand(defaultCommand);
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted");
        }
    }

    public long getCurrentTime() {
        long startTime = getStartTime();
        if (startTime == 0) {
            return 0;
        }
        return System.currentTimeMillis() / 1000 - startTime; // in seconds
    }
}
