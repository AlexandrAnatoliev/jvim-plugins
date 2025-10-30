import java.io.*;

/**
* SessionTimer - class for measuring Vim session working time 
* 
* The class provides methods for reading start time from temporary file, 
* calculates running duration, and managing the temporary file. 
*
* @version  0.1.11 
* @since    30.10.2025
* @author   AlexandrAnatoliev
*/
public class SessionTimer extends Timer {
  /** 
  * Constructs a SessionTimer instance with specified file path
  *
  * @param  pathToFile - path to temporary file for storing time values
  */
  public SessionTimer(String pathToFile) {
    super(pathToFile);
  }

  /**
  * Reads start time from file  and calculates session duration
  * in seconds
  *
  * @return - Vim session duration in seconds
  */
  long getSessionTime() {
    long startTime = readFromFile();
    long duration = (System.currentTimeMillis() / 1000) - startTime;

    return duration;
  }

  /**
  * Deletes temporary file 
  */
  void deleteFile() {
    new File(this.pathToFile).delete();
  }
}
