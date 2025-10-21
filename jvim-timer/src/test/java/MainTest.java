import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;

/**
* Unit test for Main class
*
* Tests command line argument handling and main workflow
* Uses temporary directories and real file operations
* @version 0.1.11
* @author AlexandrAnatoliev
*/
public class MainTest {

  @TempDir
  Path tempDir;

  private String originalHome;
  private final PrintStream originalOut = System.out;
  private ByteArrayOutputStream outputStream;

  /**
  * Set up test environment before each test method
  * Sets temporary home directory and captures System.out
  */
  @BeforeEach
  void setUp() throws Exception {

    Path vimDir = tempDir.resolve(".vim/pack/my-plugins/start/jvim-timer/data");
    Files.createDirectories(vimDir);

    originalHome = System.getProperty("user.home");
    System.setProperty("user.home", tempDir.toString());

    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  /**
   * Clean up test environment after each test method
   * Restores original home directory and System.out
   */
  @AfterEach
  void tearDown() {
    if (originalHome != null) {
      System.setProperty("user.home", originalHome);
    }

    System.setOut(originalOut);
  }

  /**
   * Tests main method with "start" argument
   * Verifies that session file is created when start command is used
   *
   * @throws Exception if file operations fails
   */
  @Test
  void testMainWithStartArgument() throws Exception {
    String[] args = {"start"};

    Main.main(args);
    File sessionFile = getSessionFile();
    assertTrue(sessionFile.exists(), 
        "Session file should be created after start command");
  }

  /**
   * Helper method to get the session file path
   * Constructs the full path to session file in temporary directory
   * 
   * @return File object representing the session file 
   */
  private File getSessionFile() {
    String path = tempDir + ".vim/pack/my-plagins/start/jvim-timer/data/jvim_session_time.txt";
    return new File(path);
  }

  /**
   * Helper method to get the day file path
   * Constructs the full path to day file in temporary directory
   * 
   * @return File object representing the day file 
   */
  private File getDayFile() {
    String path = tempDir + ".vim/pack/my-plagins/start/jvim-timer/data/jvim_day_time.txt";
    return new File(path);
  }
}



