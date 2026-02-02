import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
 * TimeStats - class for measuring Vim working time 
 * 
 * The class provides methods for reading start time from temporary file, 
 * calculates running duration, and managing the temporary file. 
 *
 * @version  0.8.7 
 * @since    02.02.2026
 * @author   AlexandrAnatoliev
 */
public class TimeStats extends Stats {

    /** 
     * TimeStats class constructor
     *
     * @param  pathToLong - path to temporary file for store of value
     */
    public TimeStats(String pathToLong) {
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
}

