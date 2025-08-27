import java.io.*;
import java.time.*;

public class JvimTimer {
  public static void main(String[] args) {
    try {
      // Читаем время старта из файла
      BufferedReader reader = new BufferedReader(new FileReader("/tmp/vim_start_time.txt"));
      long startTime = Long.parseLong(reader.readLine());
      reader.close();
            
      // Вычисляем время работы
      long duration = System.currentTimeMillis() - startTime;
      double seconds = duration / 1000.0;
            
      // Выводим результат
      System.out.println("Время работы Vim: " + String.format("%.1f", seconds) + " секунд");
            
      // Удаляем временный файл
      new File("/tmp/vim_start_time.txt").delete();
            
    } catch (Exception e) {
        System.out.println("Ошибка таймера: " + e.getMessage());
    }
  }
}
