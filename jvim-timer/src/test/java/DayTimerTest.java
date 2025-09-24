import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/** 
* Unit test for DayTimer class
* 
* Tests file operations, date handling, and edge cases
* Uses temporary file that is cleaned up after each test
* @version 0.1.9 
* @author AlexandrAnatoliev
*/
public class DayTimerTest {
  /** Path to temporary test file */ 
  private static final String TEST_FILE_PATH = "test_timer.txt";

  /** Instance of DayTimer for testing */
  private DayTimer dayTimer;

  /**
  * Set up test environment before each test method
  * Creates new DayTimer instance with test file path
  */
  @BeforeEach
  void setUp() {
    dayTimer = new DayTimer(TEST_FILE_PATH);
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
  * Tests fileIsNotExist() method when file does not exist
  * Verifies that method returns true for non-existent file 
  */
  @Test
  void testFileIsNotExistWhenFileDoesNotExist() {
    assertTrue(dayTimer.fileIsNotExist());
  }

  @Test
  /**
  * Tests fileIsNotExist() method when file exists
  * Verifies that method returns false for existing file 
  *
  * @throws IOException if file creation fails
  */
  void testFileIsNotExistWhenFileExists() throws IOException {
    Files.createFile(Paths.get(TEST_FILE_PATH));
    assertFalse(dayTimer.fileIsNotExist());
  }

  /**
  * Tests getFileDate() method when file exists
  * Verifies that creation date matches current date
  *
  * @throws IOException if file creation fails
  */
  @Test
  void testGetFileDateWhenFileExists() throws IOException {
    Files.createFile(Paths.get(TEST_FILE_PATH));
    LocalDate expectedDate = LocalDate.now();
    LocalDate actualDate = dayTimer.getFileDate();

    assertEquals(expectedDate, actualDate);
  }

  /**
  * Tests getFileDate() method when file does not exist 
  * Verifies fallback behavior returns current data
  */
  @Test
  void testGetFileDateWhenFileDoesNotExist() {
    LocalDate expectedDate = LocalDate.now();
    LocalDate actualDate = dayTimer.getFileDate();

    assertEquals(expectedDate, actualDate);
  }

  /**
  * Integration test for writeToFile() and readFromFile() methods
  * Verifies that written value can be successfully read back
  */
  @Test
  void testWriteToFileAndReadFromFile() {
    Long expectedValue = 12345L;
    dayTimer.writeToFile(expectedValue);

    Long actualValue = dayTimer.readFromFile();
    assertEquals(expectedValue, actualValue);
  }

  /**
  * Tests readFromFile() method when file does not exist
  * Verifies that method returns 0 as default value
  */
  @Test
  void testReadFromFileWhenFileDoesNotExist() {
    Long actualValue = dayTimer.readFromFile();
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

    Long actualValue = dayTimer.readFromFile();
    assertEquals(0L, actualValue);
  }

  /**
  * Tests that that writeToFile() method overwrite previous content
  * Verifies that only the last written value is preserved
  */
  @Test
  void testWriteToFileOverwritesPreviousContent() {
    dayTimer.writeToFile(100L);
    dayTimer.writeToFile(200L);

    Long actualValue = dayTimer.readFromFile();
    assertEquals(200L, actualValue);
  }

  /**
  * Tests writeToFile() method with null value 
  * Verifies that null input is handled without exceptions
  */
  @Test
  void testWriteToFileWithNull() {
    dayTimer.writeToFile(null);

    Long actualValue = dayTimer.readFromFile();
    assertEquals(0L, actualValue);
  }
}
