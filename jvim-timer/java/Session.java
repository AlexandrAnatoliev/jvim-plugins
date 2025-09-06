import java.io.*;
import java.nio.file.*;

public class Session {
  private String pathToFile;

  Session(String pathToFile) {
    this.pathToFile = pathToFile;
  }
  
  long getSessionTime() {
    long startTime = readFromFile();
    long duration = (System.currentTimeMillis() / 1000) - startTime;

    return duration;
  }

  void writeToFile(Long value) {
    try {
      FileWriter writer = new FileWriter(pathToFile);
      writer.write(value.toString());
      
      writer.close();

    } catch (Exception e) {
        System.out.println("Ошибка записи: " + e.getMessage());
    }
  }

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

  void deleteFile() {
    new File(this.pathToFile).delete();
  }
}
