import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for PomodoroTimer class
*
* Tests file operations, and edge cases
* Uses temporary file that is cleaned up after each test
* @version  0.6.2
* @since    24.11.2025
* @author   AlexandrAnatoliev 
*/

public class PomodoroTimerTest {
    private static final String TEST_FILE_PATH = "test_timer.txt";
    protected String testCommand = "test";
    protected long time = 1L;                
  
    private PomodoroTimer pomodoroTimer;

    /**
    * Set up test environment before each test method
    * Creates new PomodoroTimer instance with test file path
    */
    @BeforeEach
    void setUp() {
        pomodoroTimer = new PomodoroTimer(
                TEST_FILE_PATH, 
                testCommand, 
                time);
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
    * Test writing command to file 
    * Verifies that command is correctly written to the file
    *
    * @throws IOException if file reading fails
    */
    @Test
    void TestWriteCommand() throws IOException {
        pomodoroTimer.writeCommand(testCommand);
        String content = Files.readString(Paths.get(TEST_FILE_PATH));
        assertEquals(testCommand, content);
    }
}


