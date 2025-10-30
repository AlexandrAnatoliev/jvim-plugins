import java.io.*;

/**
* Timer - class for measuring Vim working time 
* 
* The class provides methods for reading start time from temporary file, 
* calculates running duration, and managing the temporary file. 
*
* @version  0.1.11 
* @since    30.10.2025
* @author   AlexandrAnatoliev
*/
public abstract class Timer {
  protected String pathToFile;

  /** 
  * Timer class constructor
  *
  * @param  pathToFile - path to temporary file for store of value
  */
  public Timer(String pathToFile) {
    this.pathToFile = pathToFile;
  }
  
  /**
  * Writes time value to temporary file 
  *
  * @param  value - tlme value in seconds to write to file
  * @throws Exception if unexpected error 
  */
  public void writeToFile(Long value) {
    try {
      FileWriter writer = new FileWriter(pathToFile);
      writer.write(value.toString());
      
      writer.close();

    } catch (Exception e) {
        System.out.println("Ошибка записи: " + e.getMessage());
    }
  }
}

