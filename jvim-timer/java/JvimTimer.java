import java.io.*;
import java.time.*;

/**
* JvimTimer - Utility for measuring vim working time 
* 
* Class reads Vim start time from a temporary file, 
* calculates running duration, outputs the result 
* and deletes the temporary file 
*
* @version 0.1.0
* @autor AlexandrAnatoliev
*/
public class JvimTimer {
  public static void main(String[] args) {
    try {
      BufferedReader reader = 
        new BufferedReader(new FileReader("/tmp/vim_start_time.txt"));
      long startTime = Long.parseLong(reader.readLine());
      reader.close();
            
      long duration = System.currentTimeMillis() - startTime;
      double seconds = duration / 1000.0;
            
      System.out.println("Время работы Vim: " 
                          + String.format("%.1f", seconds) + " сек");
            
      new File("/tmp/vim_start_time.txt").delete();
            
    } catch (Exception e) {
        System.out.println("Ошибка таймера: " + e.getMessage());
    }
  }
}
