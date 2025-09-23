import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DayTimerTest {
  private static final String TEST_FILE_PATH = "test_timer.txt";
  private DayTimer dayTimer;

  @BeforeEach
  void setUp() {
    dayTimer = new DayTimer(TEST_FILE_PATH);
  }

  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
  }

  @Test
  void testFileIsNotExistWhenFileDoesNotExist() {
    assertTrue(dayTimer.fileIsNotExist());
  }

  @Test
  void testFileIsNotExistWhenFileExists() throws IOException {
    Files.createFile(Paths.get(TEST_FILE_PATH));
    assertFalse(dayTimer.fileIsNotExist());
  }

  @Test
  void testGetFileDateWhenFileExists() throws IOException {
    Files.createFile(Paths.get(TEST_FILE_PATH));
    LocalDate expectedDate = LocalDate.now();
    LocalDate actualDate = dayTimer.getFileDate();

    assertEquals(expectedDate, actualDate);
  }

  @Test
  void testGetFileDateWhenFileDoesNotExist() {
    LocalDate expectedDate = LocalDate.now();
    LocalDate actualDate = dayTimer.getFileDate();

    assertEquals(expectedDate, actualDate);
  }
}

// jvim-timer$ java -cp "/usr/share/java/junit-jupiter-api-5.10.1.jar:/usr/share/java/junit-platform-co
// nsole-standalone-1.9.1.jar:bin/main" org.junit.platform.console.ConsoleLauncher --scan-classpath  --
// class-path bin/test/

  
// jvim-timer$ javac -d bin/test/ -cp "/usr/share/java/junit-jupiter-api-5.10.1.jar:/usr/share/java/jun
// it-platform-console-standalone-1.9.1.jar:bin/main" src/test/java/*
