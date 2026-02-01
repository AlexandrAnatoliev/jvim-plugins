import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Timer class
 *
 * Tests file operations, session time calculation, and edge cases
 * Uses temporary file that is cleaned up after each test
 * @version  0.8.7
 * @since    01.12.2026
 * @author   AlexandrAnatoliev 
 */
public class TimerTest {
    private static final String TEST_FILE_PATH = "test_timer.txt";

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
        timer.writeLong(startTime);

        Thread.sleep(1000);

        long sessionTime = timer.getSessionTime();

        assertTrue(sessionTime >= 1 && sessionTime <= 2,
                "Session time should be around 1 second");
    }

    /**
     * Tests getSessionTime() method when file does not exist
     * Verifies that method handles missing file gracefully
     */
    @Test
    void testGetSessionTimeWhenFileDoesNotExist() {
        timer.deleteFile();
        long sessionTime = timer.getSessionTime();
        assertTrue(sessionTime == 0, "Session time should be zero");
    }

    /**
     * Tests writeLong() and readLong() methods
     * Verifies that written value can be successfully read back
     */
    @Test
    void testWriteToFileAndReadFromFile() {
        long expectedValue = 123456789L;
        timer.writeLong(expectedValue);

        long actualValue = timer.readLong();
        assertEquals(expectedValue, actualValue);
    }

    /**
     * Tests readLong() method when file does not exist
     * Verifies that method returns 0 as default value
     */
    @Test
    void testReadFromFileWhenFileDoesNotExist() {
        timer.deleteFile();
        long actualValue = timer.readLong();
        assertEquals(0L, actualValue);
    }

    /**
     * Tests readLong() method with invalid data in file
     * Verifies that non-numeric data is handled gracefully
     *
     * @throws IOException if file writing fails
     */
    @Test
    void testReadFromFileWithInvalidData() throws IOException {
        Files.write(Paths.get(TEST_FILE_PATH), "Invalid_data".getBytes());

        long actualValue = timer.readLong();
        assertEquals(0L, actualValue);
    }

    /**
     * Tests that writeLong() method overwrites previous content
     * Verifies that only the last written value is preserved
     */
    @Test
    void testWriteToFileOverwritesPreviousContent() {
        timer.writeLong(100L);
        timer.writeLong(200L);

        long actualValue = timer.readLong();
        assertEquals(200L, actualValue);
    }

    /**
     * Tests writeLong() method with null value 
     * Verifies that null input is handled without exceptions
     */
    @Test
    void testWriteToFileWithNull() {
        timer.writeLong(null);

        long actualValue = timer.readLong();
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

        timer.deleteFile();

        assertFalse(Files.exists(Paths.get(TEST_FILE_PATH)),
                "File should be deleted");
    }

    /**
     * Tests deleteFile() method when file does not exist
     * Verifies that method handles missing file gracefully
     */
    @Test
    void testDeleteFileWhenFileDoesNotExist() {
        assertDoesNotThrow(() -> timer.deleteFile());
    }

    /**
     * Tests constructor with file path parameter
     * Verifies that path is correctly stored
     */
    @Test
    void testConstructorStoresFilePath() {
        String customPath = "custom_test_file.txt";
        Timer customTimer = new Timer(customPath);

        try {
            customTimer.writeLong(999L);
            long value = customTimer.readLong();
            assertEquals(999L, value);
        } finally {
            new File(customPath).delete();
        }
    }

    /**
     * Tests session time calculation accuracy
     * Verifies that calculated time matches expected duration
     *
     * @throws IOException if file writing fails
     * @throws InterruptedException if sleep is interrupted
     */
    @Test
    void testSessionTimeCalculationAccuracy() 
        throws IOException, InterruptedException {

        long startTime = System.currentTimeMillis() / 1000;
        timer.writeLong(startTime);

        int waitSecunds = 2;
        Thread.sleep(waitSecunds * 1000);

        long sessionTime = timer.getSessionTime();

        assertTrue(sessionTime >= waitSecunds && sessionTime <= waitSecunds + 1, 
                "Session time should be approximately " + waitSecunds + " seconds");
    }

    /**
     * Tests fileIsNotExist() method when file does not exist
     * Verifies that method returns true for non-existent file 
     */
    @Test
    void testFileIsNotExistWhenFileDoesNotExist() {
        assertTrue(timer.fileIsNotExist());
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
        assertFalse(timer.fileIsNotExist());
    }

    /**
     * Tests getFileDate() method when file exists
     * Verifies that creation date matches current date
     *
     * @throws IOException if file creation fails
     */
    @Test
    void testGetFileDateWhenFileExists() throws IOException {
        timer = new Timer(TEST_FILE_PATH);
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        Files.createFile(Paths.get(TEST_FILE_PATH));
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = timer.getFileDate(TEST_FILE_PATH);

        assertEquals(expectedDate, actualDate);
    }

    /**
     * Tests getFileDate() method when file does not exist 
     * Verifies fallback behavior returns current data
     */
    @Test
    void testGetFileDateWhenFileDoesNotExist() {
        LocalDate actualDate = timer.getFileDate(TEST_FILE_PATH);

        assertEquals(null, actualDate);
    }
}
