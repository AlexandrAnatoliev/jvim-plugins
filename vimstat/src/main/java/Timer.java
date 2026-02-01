import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
 * Timer - class for measuring Vim working time 
 * 
 * The class provides methods for reading start time from temporary file, 
 * calculates running duration, and managing the temporary file. 
 *
 * @version  0.8.7 
 * @since    01.02.2026
 * @author   AlexandrAnatoliev
 */
public class Timer extends Stats {

    /** 
     * Timer class constructor
     *
     * @param  pathToLong - path to temporary file for store of value
     */
    public Timer(String pathToLong) {
        super(pathToLong);
    }

    /**
     * Reads start time from file  and calculates session duration
     * in seconds
     *
     * @return - Vim session duration in seconds
     */
    long getSessionTime() {
        long startTime = readLong();

        if(startTime == 0) {
            return 0;
        }

        long duration = (System.currentTimeMillis() / 1000) - startTime;

        return duration;
    }

    /**
     * Deletes temporary file 
     */
    void deleteFile() {
        new File(this.pathToLong).delete();
    }

    /**
     * Checks if temporary file does not exist
     *
     * @return true if file does not exists
     *         false if file exists
     * @throws Exception if file does not exists
     */
    public boolean fileIsNotExist() {
        File file = new File(pathToLong);

        try {
            return !file.exists();
        } catch (Exception e) {
            System.out.println(Colors.RED.apply(
                        "ERROR file existing checking: " + e.getMessage()));
        }

        return false;
    }

    /**
     * Retrieves the creation date of the file
     *
     * @return LocalDate representing file creation time
     * @throws Exception if unexpected error 
     */
    public LocalDate getFileDate() {
        File file = new File(pathToLong);

        try {
            BasicFileAttributes attrs = Files.readAttributes(
                    file.toPath(), BasicFileAttributes.class);

            LocalDate fileDate = attrs.creationTime().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();

            return fileDate;

        } catch (Exception e) {
            System.out.println(Colors.RED.apply(
                        "ERROR get file date checking: " + e.getMessage()));
        }

        return LocalDate.now();
    }
}

