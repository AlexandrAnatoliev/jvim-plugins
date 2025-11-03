import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for Timer class
*
* Tests file operations, session time calculation, and edge cases
* Uses temporary file that is cleaned up after each test
* @version 0.2.4
* @author AlexandrAnatoliev 
*/
public class TimerTest {
  private static final String TEST_FILE_PATH = 
    "test_session_timer.txt";
  
  private Timer timer;

  /**
  * Set up test environment before each test method
  * Creates new SessionTimer instance with test file path
  */
  @BeforeEach
  void setUp() {
    timer = new Timer(TEST_FILE_PATH);
  }

  /**
  * Clean up test environment after each test method
  * Deletes temporary test file if it exists
  *
  * @throws IOException if file deletion fails
  */
  @AfterEach
  void tearDown() throws IOException {
    Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
  }

  /**
  * Tests getSessionTime() method with valid start time
  * Verifies that session time calculation is correct
  *
  * @throws IOException if file creation fails
  * @throws InterruptedException if sleep is interrupted
  */
  @Test
  void testGetSessionTimeWithValidStartTime()
    throws IOException, InterruptedException {

    long startTime = System.currentTimeMillis() / 1000;
    timer.writeToFile(startTime);

    Thread.sleep(1000);

    long sessionTime = timer.getSessionTime();

    assertTrue(sessionTime >= 1 && sessionTime <= 2,
        "Session time should be around 1 second");
  }
}
