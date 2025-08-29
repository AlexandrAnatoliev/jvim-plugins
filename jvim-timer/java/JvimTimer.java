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
      long hours = duration / 3600000;
      long minutes = (duration % 3600000) / 60000;
      long seconds = (duration % 60000) / 1000;
            
      System.out.printf("Время работы Vim: %d ч %d мин %d сек",
                        hours, minutes, seconds);
            
      new File("/tmp/vim_start_time.txt").delete();
            
    } catch (Exception e) {
        System.out.println("Ошибка таймера: " + e.getMessage());
    }
  }
}
