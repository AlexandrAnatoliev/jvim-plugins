import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

/**
* Unit tests for PomodoroTimer class
*
* Tests file operations, and edge cases
* Uses temporary file that is cleaned up after each test
* @version  0.6.5
* @since    29.11.2025
* @author   AlexandrAnatoliev 
*/

public class PomodoroTimerTest {
    private static final String TEST_MONITOR_PATH = "test_monitor.txt";
    private static final String TEST_START_TIME_PATH = "test_start_time.txt";
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
                TEST_MONITOR_PATH, 
                TEST_START_TIME_PATH,
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
        Files.deleteIfExists(Paths.get(TEST_MONITOR_PATH));
        Files.deleteIfExists(Paths.get(TEST_START_TIME_PATH));
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
        String content = Files.readString(Paths.get(TEST_MONITOR_PATH));
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
        String content = Files.readString(Paths.get(TEST_MONITOR_PATH));
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
        String content = Files.readString(Paths.get(TEST_MONITOR_PATH));
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
                TEST_MONITOR_PATH,
                TEST_START_TIME_PATH,
                defaultCommand,
                0L);
        Thread timerThread = new Thread(shortTimer::startTimer);
        timerThread.start();
        timerThread.join(2000);
        String content = Files.readString(Paths.get(TEST_MONITOR_PATH));
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
        String content = Files.readString(Paths.get(TEST_MONITOR_PATH));
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
        String testStartTimePath = "test_start_time_path.txt";
        String testCommand = "test_cmd";
        long testTime = 5L;
        PomodoroTimer timer = new PomodoroTimer(
                testPath, 
                testStartTimePath,
                testCommand, 
                testTime);
        assertEquals(testPath, timer.pathToMonitor);
        assertEquals(testStartTimePath, timer.pathToStartTime);
        assertEquals(testCommand, timer.defaultCommand);
        assertEquals(testTime, timer.time);
    }

    /**
    * Test with non-existent directory path
    * Verifies that error handling works for invalid paths
    */
    @Test
    void testWriteCommandToInvalidPath() {
    String invalidPath = "non_existent_directory/test.txt";
    PomodoroTimer invalidTimer = new PomodoroTimer(
            invalidPath,
            TEST_START_TIME_PATH,
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
                TEST_MONITOR_PATH,
                TEST_START_TIME_PATH,
                defaultCommand,
                10L);
        Thread timerThread = new Thread(longTimer::startTimer);
        timerThread.start();
        timerThread.interrupt();
        Thread.sleep(100);
        assertFalse(timerThread.isAlive());
    }
      
    /**
    * Test with very long command string
    * Verifies handling of long input strings
    *
    * @throws IOException if file reading fails
    */
    @Test
    void testWriteVeryLongCommand() throws IOException {
        String longCommand = "A".repeat(10000);
        pomodoroTimer.writeCommand(longCommand);
        String content = Files.readString(Paths.get(TEST_MONITOR_PATH));
        assertEquals(longCommand, content);
    }

    /**
    * Test null command handling
    * Verifies that null commands are handled gracefully
    */
    @Test
    void testWriteNullCommand() {
        assertDoesNotThrow(() -> pomodoroTimer.writeCommand(null));
        assertTrue(Files.exists(Paths.get(TEST_MONITOR_PATH)));
    }

    /**
    * Test multiple timer instance
    * Verifies that multiple timers don't interfere with each other
    *
    * @throws IOException if file reading fails
    */
    @Test
    void testMultipleTimers() throws IOException {
        String file1 = "test1.txt";
        String file2 = "test2.txt";
        PomodoroTimer timer1 = new PomodoroTimer(
                file1, 
                TEST_START_TIME_PATH,
                "command1", 
                0L);
        PomodoroTimer timer2 = new PomodoroTimer(
                file2, 
                TEST_START_TIME_PATH,
                "command2", 
                0L);
        try {
            timer1.writeCommand("command1");
            timer2.writeCommand("command2");
            assertEquals("command1", Files.readString(Paths.get(file1)));
            assertEquals("command2", Files.readString(Paths.get(file2)));
        } finally {
            Files.deleteIfExists(Paths.get(file1));
            Files.deleteIfExists(Paths.get(file2));
        }
    }

    /**
    * Test writing time to file 
    * Verifies that time is correctly written to the file
    *
    * @throws IOException if file reading fails
    */
    @Test
    void testWriteTime() throws IOException {
        long testTime = 5L;
        pomodoroTimer.writeTime(testTime);
        long content = Long.parseLong(
                Files.readString(Paths.get(TEST_START_TIME_PATH)));
        assertEquals(testTime, content);
    }

    /**
    * Test write time overwrite behavior
    * Verifies that new command overwrites previous content
    *
    * @throws IOException if file reading fails
    */
    @Test
    void testWriteTimeOverwrite() throws IOException {
        long firstTime = 1;
        long secondTime = 2;
        pomodoroTimer.writeTime(firstTime);
        pomodoroTimer.writeTime(secondTime);
        long content = Long.parseLong(
                Files.readString(Paths.get(TEST_START_TIME_PATH)));
        assertEquals(secondTime, content);
        assertNotEquals(firstTime, content);
    }

    /**
    * Test with non-existent directory path
    * Verifies that error handling works for invalid paths
    */
    @Test
    void testWriteTimeToInvalidPath() {
    String invalidPath = "non_existent_directory/test.txt";
    PomodoroTimer invalidTimer = new PomodoroTimer(
            invalidPath,
            TEST_START_TIME_PATH,
            defaultCommand,
            time);
    assertDoesNotThrow(() -> invalidTimer.writeTime(123L));
    }

    /**
    * Test write null time handling
    * Verifies that null commands are handled gracefully
    */
    @Test
    void testWriteNullTime() {
        assertDoesNotThrow(() -> pomodoroTimer.writeTime(null));
        assertTrue(Files.exists(Paths.get(TEST_START_TIME_PATH)));
    }

    /**
    * Tests getElapcedTime method with valid start time
    * Verifies that session time calculation is correct
    *
    * @throws IOException if file creation fails
    * @throws InterruptedException if sleep is interrupted
    */
    @Test
    void testGetElapsedTimeWithValidStartTime()
        throws IOException, InterruptedException {

        long startTime = System.currentTimeMillis() / 1000;
        pomodoroTimer.writeTime(startTime);

        Thread.sleep(1000);

        long elapsedTime = pomodoroTimer.getElapcedTime();

        assertTrue(elapsedTime >= 1 && elapsedTime <= 2,
                Colors.RED.apply("Session time should be around 1 second"));
    }

    /**
    * Tests getStartTime method when file does not exist
    * Verifies that method handles missing file gracefully
    */
    @Test
    void testGetStartTimeWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_START_TIME_PATH));
        assertDoesNotThrow(() -> pomodoroTimer.getStartTime());
    }

    /**
    * Tests writeTime and getStartTime methods
    * Verifies that written value can be successfully read back
    */
    @Test
    void testWriteTimeAndGetStartTime() {
        long expectedValue = 123456789L;
        pomodoroTimer.writeTime(expectedValue);

        long actualValue = pomodoroTimer.getStartTime();
        assertEquals(expectedValue, actualValue);
    }

    /**
    * Tests getStartTime method with invalid data in file
    * Verifies that non-numeric data is handled gracefully
    *
    * @throws IOException if file writing fails
    */
    @Test
    void testGetStartTimeWithInvalidData() throws IOException {
        Files.write(Paths.get(TEST_START_TIME_PATH), "Invalid_data".getBytes());

        assertDoesNotThrow(() -> pomodoroTimer.getStartTime());
    }

    /**
    * Tests elapsed time calculation accuracy
    * Verifies that calculated time matches expected duration
    *
    * @throws IOException if file writing fails
    * @throws InterruptedException if sleep is interrupted
    */
    @Test
    void testGetElapsedTimeCalculationAccuracy() 
        throws IOException, InterruptedException {
    
        long startTime = System.currentTimeMillis() / 1000;
        pomodoroTimer.writeTime(startTime);

        int waitSecunds = 1;
        Thread.sleep(waitSecunds * 1000);

        long elapsedTime = pomodoroTimer.getElapcedTime();

        assertTrue(elapsedTime >= waitSecunds && elapsedTime <= waitSecunds + 1, 
                Colors.RED.apply(
                    "Session time should be approximately " 
                    + waitSecunds + " seconds"));
    }
}


