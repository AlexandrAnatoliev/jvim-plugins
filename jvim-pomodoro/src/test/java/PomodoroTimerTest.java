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
    protected String defaultCommand = "test";
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
                defaultCommand, 
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
    void testWriteCommand() throws IOException {
        pomodoroTimer.writeCommand(defaultCommand);
        String content = Files.readString(Paths.get(TEST_FILE_PATH));
        assertEquals(defaultCommand, content);
    }

    /** 
    * Test writing empty command to file
    * Verifies that empty string is handled correctly
    *
    * @throws IOException if file reading fails
    */
    @Test
    void testWtriteEmptyCommand() throws IOException {
        String emptyCommand = "";
        pomodoroTimer.writeCommand(emptyCommand);
        String content = Files.readString(Paths.get(TEST_FILE_PATH));
        assertEquals(emptyCommand, content);
    }

    /**
    * Test writing command with special characters
    * Verifies that special characters are handled correctly
    *
    * @throws IOException if file reading fails
    */
    @Test
    void testWriteCommandWithSpecialCharacters() throws IOException {
        String specialCommand = "command with spaces & symbols @#$%";
        pomodoroTimer.writeCommand(specialCommand);
        String content = Files.readString(Paths.get(TEST_FILE_PATH));
        assertEquals(specialCommand, content);
    }

    /**
    * Test timer functionality with short timeout
    * Verifies that command is written after timer expires
    * Used short timeout for test execution speed
    *
    * @throws IOException if file reading fails
    * @throws InterruptedException if thread sleep is interrupted
    */
    @Test
    void testStartTimer() throws IOException, InterruptedException {
        PomodoroTimer shortTimer = new PomodoroTimer(
                TEST_FILE_PATH,
                defaultCommand,
                0L);
        Thread timerThread = new Thread(shortTimer::startTimer);
        timerThread.start();
        timerThread.join(2000);
        String content = Files.readString(Paths.get(TEST_FILE_PATH));
        assertEquals(defaultCommand, content);
    }

    /**
    * Test file overwrite behavior
    * Verifies that new command overwrites previous content
    *
    * @throws IOException if file reading fails
    */
    @Test
    void testFileOverwrite() throws IOException {
        String firstCommand = "first command";
        String secondCommand = "second command";
        pomodoroTimer.writeCommand(firstCommand);
        pomodoroTimer.writeCommand(secondCommand);
        String content = Files.readString(Paths.get(TEST_FILE_PATH));
        assertEquals(secondCommand, content);
        assertNotEquals(firstCommand, content);
    }

    /**
    * Test constructor initialization
    * Verifies that all fields are properly set
    */
    @Test
    void testConstructor() {
        String testPath = "test_path.txt";
        String testCommand = "test_cmd";
        long testTime = 5L;
        PomodoroTimer timer = new PomodoroTimer(
                testPath, 
                testCommand, 
                testTime);
        assertEquals(testPath, timer.pathToMonitor);
        assertEquals(testCommand, timer.defaultCommand);
        assertEquals(testTime, timer.time);
    }

    /**
    * Test with non-existent directory path
    * Verifies that error handling works for invalid paths
    */
    @Test
    void testWriteToInvalidPath() {
    String invalidPath = "non_existent_directory/test.txt";
    PomodoroTimer invalidTimer = new PomodoroTimer(
            invalidPath,
            defaultCommand,
            time);
    assertDoesNotThrow(() -> invalidTimer.writeCommand("test"));
    }

    /**
    * Test timer interruption handling
    * Verifies that interrupted execution is caught and handled
    *
    * @throws InterruptedException if thread operations fail
    */
    @Test
    void testTimerInterruption() throws InterruptedException {
        PomodoroTimer longTimer = new PomodoroTimer(
                TEST_FILE_PATH,
                defaultCommand,
                10L);
        Thread timerThread = new Thread(longTimer::startTimer);
        timerThread.start();
        timerThread.interrupt();
        Thread.sleep(100);
        assertFalse(timerThread.isAlive());
    }
      

}


