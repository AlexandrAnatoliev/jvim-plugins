import java.io.*;

/**
* Session - class for measuring Vim session working time 
* 
* The class provides methods for reading start time from temporary file, 
* calculates running duration, and managing the temporary file. 
*
* @version  0.1.4 
* @since    06.09.2025
* @author   AlexandrAnatoliev
*/
public class Session {
  private String pathToFile;

  /** 
  * Session class constructor
  *
  * @param  pathToFile - path to temporary file for store of value
  */
  Session(String pathToFile) {
    this.pathToFile = pathToFile;
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
  * Writes time value to temporary file 
  *
  * @param  varue - tlme value to write
  */
  void writeToFile(Long value) {
    try {
      FileWriter writer = new FileWriter(pathToFile);
      writer.write(value.toString());
      
      writer.close();

    } catch (Exception e) {
        System.out.println("Ошибка записи: " + e.getMessage());
    }
  }

  /**
  * Reads time value from temporary file 
  *
  * @return tlme value from file, or 0 in case of error 
  */
  long readFromFile() {
    try {
      BufferedReader reader = new BufferedReader(
                          new FileReader(this.pathToFile));
      long value = Long.parseLong(reader.readLine());

      reader.close();

      return value;

    } catch (Exception e) {
        System.out.println("Ошибка чтения: " + e.getMessage());
    }
    return 0;
  }

  /**
  * Deletes temporary file 
  */
  void deleteFile() {
    new File(this.pathToFile).delete();
  }
}
