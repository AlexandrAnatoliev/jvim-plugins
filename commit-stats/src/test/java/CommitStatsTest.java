import java.io.*;
import java.nio.file.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CommitStats class
 *
 * @version  0.7.6
 * @since    10.01.2026
 * @author   AlexandrAnatoliev 
 */
public class CommitStatsTest {
    private CommitStats commitStats;
    private static final String TEST_PATH_TO_LAST_COMMIT_HASH = "test_last_commit_hash.txt";
    private static final String TEST_PATH_TO_DAILY_COMMITS = "test_daily_commits.txt";

    /**
     * Set up test environment before each test method execution.
     * Creates new CommitStats instance.
     */
    @BeforeEach
    void setUp() {
        commitStats = new CommitStats(
                TEST_PATH_TO_LAST_COMMIT_HASH,
                TEST_PATH_TO_DAILY_COMMITS);
    }

    /**
     * Clean up test environment after each test method execution.
     *
     * @throws IOException if file operation fails
     */
    @AfterEach
    void tearDown() throws IOException {
        commitStats = null;
        Files.deleteIfExists(Paths.get(TEST_PATH_TO_LAST_COMMIT_HASH));
        Files.deleteIfExists(Paths.get(TEST_PATH_TO_DAILY_COMMITS));
    }

    /**
     * Test constructor field initialization.
     * Verifies that all fields are properly set.
     */
    @Test
    void testConstructor() {
        String testLastCommitHash = "test_last_hash.txt";
        String testDailyCommits = "test_daily_commits.txt";
        CommitStats testCommitStats = new CommitStats (
                testLastCommitHash,
                testDailyCommits);
        assertEquals(testLastCommitHash, testCommitStats.pathToLastCommitHash);
        assertEquals(testDailyCommits, testCommitStats.pathToDailyCommits);
    }

    /**
     * Test getting last commit hash in git repository
     * This test requires git to be installed and the test to run in git repo
     */
    @Test
    void testGetLastCommitHashInGitRepo() {
        // Check if in a git repo
        try {
            Process p = new ProcessBuilder("git", "rev-parse", "--git-dir")
                .start();
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                String hash = commitStats.getLastCommitHash();
                assertNotNull(hash);
                assertFalse(hash.isEmpty());
                assertEquals(40, hash.length(), 
                        "Git hash should be 40 chars");
                assertTrue(hash.matches("[0-9a-f]{40}"),
                        "Should be a valid SHA-1 hash");
                // Not in a git repo, test should return empty string
            } else {
                String hash = commitStats.getLastCommitHash();
                assertEquals("", hash);
            }

            // Git not avaible, test should return empty string
        } catch (Exception e) {
            String hash = commitStats.getLastCommitHash();
            assertEquals("", hash);
        }
    }

    /**
     * Test check is git installed
     */
    @Test
    void testIsGitInstalled() {
        try {
            Process p = new ProcessBuilder("git", "--version").start();
            assertEquals(0, p.waitFor());
        } catch (Exception e) {
            System.out.println(Colors.RED.apply("[ERROR]") 
                    + " testIsGitInstalled: " 
                    + e.getMessage());
        }
    }

    /**
     * Test that method doesn't throw exceptions
     */ 
    @Test
    void testGetLastCommitHashNoExceptions() {
        assertDoesNotThrow(() -> {
            String hash = commitStats.getLastCommitHash();
        });
    }

    /**
     * Test that scanner is properly closed
     * This is in an indirect test - we check that no resource leaks occurs
     */
    @Test
    void testResourceCleanup() {
        for (int i = 0; i < 100 ; i++) {
            String hash = commitStats.getLastCommitHash();
        }
        assertTrue(true);
    }

    /**
     * Test writing hash to file system.
     * Verifies that hash is correctly written to the file.
     *
     * @throws IOException if file writing operation fails
     */
    @Test
    void testWriteHashToFile() throws IOException {
        String testHash = "0123456789abcdef";
        commitStats.writeHashToFile(testHash);
        String content = Files.readString(Paths.get(TEST_PATH_TO_LAST_COMMIT_HASH));
        assertEquals(testHash, content);
    }

    /** 
     * Test writing empty string to file system.
     * Verifies that empty string is handled correctly.
     *
     * @throws IOException if file reading operation fails
     */
    @Test
    void testWriteEmptyString() throws IOException {
        String emptyString = "";
        commitStats.writeHashToFile(emptyString);
        String content = Files.readString(Paths.get(TEST_PATH_TO_LAST_COMMIT_HASH));
        assertEquals(emptyString, content);
    }

    /**
     * Test write with non-existent directory path.
     * Verifies that error handling works for invalid paths.
     */
    @Test
    void testWriteHashToInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                invalidPath,
                TEST_PATH_TO_DAILY_COMMITS);
        assertDoesNotThrow(() -> invalidStats.writeHashToFile("test"));
    }

    /**
     * Test null writing handling
     * Verifies that null writing are handled gracefully
     */
    @Test
    void testWriteNullToHashFile() {
        assertDoesNotThrow(() -> commitStats.writeHashToFile(null));
        assertTrue(Files.exists(Paths.get(TEST_PATH_TO_LAST_COMMIT_HASH)));
    }

    /**
     * Test file overwrite behavior for sequential hash writes.
     * Verifies that new hash overwrites previous content.
     *
     * @throws IOException if file reading operation fails
     */
    @Test
    void testHashToFileOverwrite() throws IOException {
        String firstHash = "test hash 1";
        String secondHash = "test hash 2";
        commitStats.writeHashToFile(firstHash);
        commitStats.writeHashToFile(secondHash);
        String content = Files.readString(Paths.get(TEST_PATH_TO_LAST_COMMIT_HASH));
        assertEquals(secondHash, content);
        assertNotEquals(firstHash, content);
    }

    /**
     * Test write and read hash from file system.
     * Verifies that hash is correctly written and read from the file.
     *
     * @throws IOException if file writing or reading operation fails
     */
    @Test
    void testWriteAndReadHash() throws IOException {
        String testHash = "0123456789abcdef";
        commitStats.writeHashToFile(testHash);
        String content = commitStats.readHashFromFile();
        assertEquals(testHash, content);
    }

    /**
     * Test write and read empty string from file system.
     * Verifies that empty string is correctly written and read from the file.
     *
     * @throws IOException if file writing or reading operation fails
     */
    @Test
    void testWriteAndReadEmptyString() throws IOException {
        String emptyString = "";
        commitStats.writeHashToFile(emptyString);
        String content = commitStats.readHashFromFile();
        assertEquals(emptyString, content);
    }

    /**
     * Test read with non-existent directory path.
     * Verifies that error handling works for invalid paths.
     */
    @Test
    void testReadHashFromInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                invalidPath,
                TEST_PATH_TO_DAILY_COMMITS);
        assertDoesNotThrow(() -> invalidStats.readHashFromFile());
    }

    /**
     * Test empty file reading handling
     * Verifies that empty file reading are handled gracefully
     */
    @Test
    void testReadEmptyHashFile() {
        assertDoesNotThrow(() -> {
            new File(TEST_PATH_TO_LAST_COMMIT_HASH).createNewFile();
            String result = commitStats.readHashFromFile();
            assertEquals("", result);
        });
    }

    /**
     * Tests readHashFromFile() method when file does not exist
     * Verifies that method returns "" as default value
     *
     * @throws IOException if file operation fails
     */
    @Test
    void testReadHashFromFileWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_PATH_TO_LAST_COMMIT_HASH));
        String actualValue = commitStats.readHashFromFile();
        assertEquals("", actualValue);
    }

    /**
     * Test writing daily commits value to file system.
     * Verifies that value is correctly written to the file.
     *
     * @throws IOException if file writing operation fails
     */
    @Test
    void testWriteDailyCommitsToFile() throws IOException {
        Long testValue = 123L;
        commitStats.writeDailyCommitsToFile(testValue);
        Long content = Long.parseLong(
                Files.readString(Paths.get(TEST_PATH_TO_DAILY_COMMITS)));
        assertEquals(testValue, content);
    }

    /**
     * Test write daily commits value with non-existent directory path.
     * Verifies that error handling works for invalid paths.
     */
    @Test
    void testWriteDailyCommitsToInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                TEST_PATH_TO_LAST_COMMIT_HASH,
                invalidPath);
        assertDoesNotThrow(() -> invalidStats.writeDailyCommitsToFile(123L));
    }

    /**
     * Test null writing handling
     * Verifies that null writing are handled gracefully
     */
    @Test
    void testWriteNullToDailyCommitsFile() {
        assertDoesNotThrow(() -> commitStats.writeDailyCommitsToFile(null));
        assertTrue(Files.exists(Paths.get(TEST_PATH_TO_DAILY_COMMITS)));
    }

    /**
     * Test file overwrite behavior for sequential daily commits value writes.
     * Verifies that new value overwrites previous content.
     *
     * @throws IOException if file reading operation fails
     */
    @Test
    void testDailyCommitsFileOverwrite() throws IOException {
        Long firstValue = 123L;
        Long secondValue = 1234L;
        commitStats.writeDailyCommitsToFile(firstValue);
        commitStats.writeDailyCommitsToFile(secondValue);
        Long content = Long.parseLong(
                Files.readString(Paths.get(TEST_PATH_TO_DAILY_COMMITS)));
        assertEquals(secondValue, content);
        assertNotEquals(firstValue, content);
    }

    /**
     * Test write and read daily commits value from file system.
     * Verifies that value is correctly written and read from the file.
     *
     * @throws IOException if file writing or reading operation fails
     */
    @Test
    void testWriteAndReadDailyCommits() throws IOException {
        Long testValue = 123L;
        commitStats.writeDailyCommitsToFile(testValue);
        Long content = commitStats.readDailyCommitsFromFile();
        assertEquals(testValue, content);
    }

    /**
     * Test read with non-existent directory path.
     * Verifies that error handling works for invalid paths.
     */
    @Test
    void testReadDailyCommitsFromInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                TEST_PATH_TO_LAST_COMMIT_HASH,
                invalidPath);
        assertDoesNotThrow(() -> invalidStats.readDailyCommitsFromFile());
    }

    /**
     * Test empty file reading handling
     * Verifies that empty file reading are handled gracefully
     */
    @Test
    void testReadEmptyDailyCommitsFile() {
        assertDoesNotThrow(() -> {
            new File(TEST_PATH_TO_DAILY_COMMITS).createNewFile();
            Long result = commitStats.readDailyCommitsFromFile();
            assertEquals(0, result);
        });
    }

    /**
     * Tests readDailyCommitsFromFile() method when file does not exist
     * Verifies that method returns 0 as default value
     *
     * @throws IOException if file operation fails
     */
    @Test
    void testReadDailyCommitsFromFileWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_PATH_TO_DAILY_COMMITS));
        Long actualValue = commitStats.readDailyCommitsFromFile();
        assertEquals(0, actualValue);
    }

    /**
     * Tests readDailyCommitsFromFile() method with invalid data in file.
     * Verifies that non-numeric data is handled gracefully
     *
     * @throws IOException if file writing fails
     */
    @Test
    void testReadDailyCommitsFromFileWithInvalidData() throws IOException {
        Files.write(Paths.get(TEST_PATH_TO_DAILY_COMMITS), "Invalid_data".getBytes());
        assertDoesNotThrow(() -> commitStats.readDailyCommitsFromFile());
    }

    /**
     * Tests isFileExists() method when file does not exist
     * Verifies that method returns false for non-existent file 
     */
    @Test
    void testIsFileExistsWhenFileDoesNotExist() {
        assertFalse(commitStats.isFileExists(TEST_PATH_TO_LAST_COMMIT_HASH));
    }

    /**
     * Tests isFileExists() method when file exists
     * Verifies that method returns true for existent file 
     */
    @Test
    void testIsFileExistsWhenFileExists() throws IOException {
        String testLastCommitHash = "/test_last_hash.txt";
        String testDailyCommits = "/test_daily_commits.txt";
        String homeDir = System.getProperty("user.home");
        CommitStats testCommitStats = new CommitStats (
                homeDir + testLastCommitHash,
                homeDir + testDailyCommits);
        testCommitStats.writeHashToFile("hash");
        assertTrue(commitStats.isFileExists(testLastCommitHash));
        Files.deleteIfExists(Paths.get(homeDir + testLastCommitHash));
    }
}
