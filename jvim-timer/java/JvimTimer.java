import java.io.*;
import java.time.*;

/**
* JvimTimer - Utility for measuring vim working time 
* 
* Class reads Vim start time from a temporary file, 
* calculates running duration, outputs the result 
* and deletes the temporary file 
*
* @version 0.1.2 31.08.2025
* @autor AlexandrAnatoliev
*/
public class JvimTimer {
  public static void main(String[] args) {
    System.out.printf("Run JavaTimer %s\n", 
      args.length > 0 ? args[0] : "no args");
    if (args.length > 0 && "start".equals(args[0])) {
      start();
    } else {
      stop();
    }
  }

  public static void start() {
    try {
      FileWriter writer = new FileWriter("/tmp/jvim_start_time.txt");
      Long startTime = System.currentTimeMillis();
      writer.write(startTime.toString());
      writer.close();

    } catch (Exception e) {
        System.out.println("Ошибка записи: " + e.getMessage());
    }
  }

  public static void stop() {
    try {
      BufferedReader reader = 
        new BufferedReader(new FileReader("/tmp/jvim_start_time.txt"));
      long startTime = Long.parseLong(reader.readLine());
      reader.close();
            
      long duration = System.currentTimeMillis() - startTime;
      long hours = duration / 3600000;
      long minutes = (duration % 3600000) / 60000;
      long seconds = (duration % 60000) / 1000;
            
      System.out.printf("Время работы Vim: %d ч %d мин %d сек",
                        hours, minutes, seconds);
            
      new File("/tmp/jvim_start_time.txt")
      .delete();
            
    } catch (Exception e) {
        System.out.println("Ошибка таймера: " + e.getMessage());
    }
  }
}
