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
  private final PrintStream originalErr = System.err;
  private ByteArrayOutputStream outputStream;
  private ByteArrayOutputStream errorStream;

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
    errorStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    System.setErr(new PrintStream(errorStream));
  }
}



