import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for SessionTimer class
*
* Tests file operations, session time calculation, and edge cases
* Uses temporary file that is cleaned up after each test
* @version 0.1.10
* @author AlexandrAnatoliev 
*/
public class SessionTimerTest {
  private static final String TEST_FILE_PATH = 
    "test_session_timer.txt";
  
  private SessionTimer sessionTimer;

  /**
  * Set up test environment before each test method
  * Creates new SessionTimer instance with test file path
  */
  @BeforeEach
  void setUp() {
    sessionTimer = new SessionTimer(TEST_FILE_PATH);
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
    sessionTimer.writeToFile(startTime);

    Thread.sleep(1000);

    long sessionTime = sessionTimer.getSessionTime();

    assertTrue(sessionTime >= 1 && sessionTime <= 2,
        "Session time should be around 1 second");
  }

  /**
  * Tests getSessionTime() method when file does not exist
  * Verifies that method handles missing file gracefully
  */
  @Test
  void testGetSessionTimeWhenFileDoesNotExist() {
    long sessionTime = sessionTimer.getSessionTime();
    assertTrue(sessionTime >= 0, "Session time should be non-negative");
  }

  /**
  * Tests writeToFile() and readFromFile() methods
  * Verifies that written value can be successfully read back
  */
  @Test
  void testWriteToFileAndReadFromFile() {
    long expectedValue = 123456789L;
    sessionTimer.writeToFile(expectedValue);

    long actualValue = sessionTimer.readFromFile();
    assertEquals(expectedValue, actualValue);
  }

  /**
  * Tests readFromFile() method when file does not exist
  * Verifies that method returns 0 as default value
  */
  @Test
  void testReadFromFileWhenFileDoesNotExist() {
    long actualValue = sessionTimer.readFromFile();
    assertEquals(0L, actualValue);
  }

  /**
  * Tests readFromFile() method with invalid data in file
  * Verifies that non-numeric data is handled gracefully
  *
  * @throws IOException if file writing fails
  */
  @Test
  void testReadFromFileWithInvalidData() throws IOException {
    Files.write(Paths.get(TEST_FILE_PATH), "Invalid_data".getBytes());

    long actualValue = sessionTimer.readFromFile();
    assertEquals(0L, actualValue);
  }

  /**
  * Tests that writeToFile() method overwrites previous content
  * Verifies that only the last written value is preserved
  */
  @Test
  void testWriteToFileOverwritesPreviousContent() {
    sessionTimer.writeToFile(100L);
    sessionTimer.writeToFile(200L);

    long actualValue = sessionTimer.readFromFile();
    assertEquals(200L, actualValue);
  }

  /**
  * Tests writeToFile() method with null value 
  * Verifies that null input is handled without exceptions
  */
  @Test
  void testWriteToFileWithNull() {
    sessionTimer.writeToFile(null);

    long actualValue = sessionTimer.readFromFile();
    assertNotNull(actualValue);
  }

  /**
  * Tests deleteFile() method when file exists 
  * Verifies that file is successfully deleted
  *
  * @throws IOException if file creation fails
  */
  @Test
  void testDeleteFileWhenFileExists() throws IOException {
    Files.createFile(Paths.get(TEST_FILE_PATH));

    sessionTimer.deleteFile();

    assertFalse(Files.exists(Paths.get(TEST_FILE_PATH)),
      "File should be deleted");
  }

  /**
  * Tests deleteFile() method when file does not exist
  * Verifies that method handles missing file gracefully
  */
  @Test
  void testDeleteFileWhenFileDoesNotExist() {
    assertDoesNotThrow(() -> sessionTimer.deleteFile());
  }
}
