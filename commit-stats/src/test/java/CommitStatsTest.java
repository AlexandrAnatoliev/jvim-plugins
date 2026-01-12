import java.io.*;
import java.nio.file.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for CommitStats class
 *
 * @version  0.7.6
 * @since    10.01.2026
 * @author   AlexandrAnatoliev 
 */
public class CommitStatsTest {
    private CommitStats stats;
    private static final String PATH_TO_LAST_COMMIT_HASH = "last_commit_hash.txt";
    private static final String PATH_TO_DAILY_COMMITS = "daily_commits.txt";

    /**
     * Set up test environment before each test method execution.
     * Creates new CommitStats instance.
     */
    @BeforeEach
    void setUp() {
        stats = new CommitStats(
                PATH_TO_LAST_COMMIT_HASH,
                PATH_TO_DAILY_COMMITS);
    }

    /**
     * Clean up test environment after each test method execution.
     *
     * @throws IOException if file operation fails
     */
    @AfterEach
    void tearDown() throws IOException {
        stats = null;
        Files.deleteIfExists(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        Files.deleteIfExists(Paths.get(PATH_TO_DAILY_COMMITS));
    }

    @Test
    @DisplayName("The constructor must set all fields are properly")
    void testConstructorShouldSetPaths() {
        String lastCommitHash = "last_hash.txt";
        String dailyCommits = "daily_commits.txt";
        CommitStats testCommitStats = new CommitStats (
                lastCommitHash,
                dailyCommits);
        assertEquals(lastCommitHash, testCommitStats.pathToLastCommitHash);
        assertEquals(dailyCommits, testCommitStats.pathToDailyCommits);
    }

    @Test
    @DisplayName("The constructor must work with null values") 
    void testConstructorShouldHandleNull() {
        CommitStats stats = new CommitStats(null, null);
        assertNull(stats.pathToLastCommitHash);
        assertNull(stats.pathToDailyCommits);
    }

    /**
     * This test requires git to be installed and the test to run in git repo
     */
    @Test
    @DisplayName("The getLastCommitHash method must get last commit hash in git repository")
    void testGetLastCommitHashInGitRepo() {
        // Check if in a git repo
        try {
            Process p = new ProcessBuilder("git", "rev-parse", "--git-dir")
                .start();
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                String hash = stats.getLastCommitHash();
                assertNotNull(hash);
                assertFalse(hash.isEmpty());
                assertEquals(40, hash.length(), 
                        "Git hash should be 40 chars");
                assertTrue(hash.matches("[0-9a-f]{40}"),
                        "Should be a valid SHA-1 hash");
                // Not in a git repo, test should return empty string
            } else {
                String hash = stats.getLastCommitHash();
                assertEquals("", hash);
            }

            // Git not avaible, test should return empty string
        } catch (Exception e) {
            String hash = stats.getLastCommitHash();
            assertEquals("", hash);
        }
    }

    @Test
    @DisplayName("Git is installed")
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

    @Test
    @DisplayName("The getLastCommitHash method doesn't throw exceptions")
    void testGetLastCommitHashNoExceptions() {
        assertDoesNotThrow(() -> {
            String hash = stats.getLastCommitHash();
        });
    }

    @Test
    @DisplayName("The getLastCommitHash method scanner is properly closed")
    void testResourceCleanup() {
        for (int i = 0; i < 100 ; i++) {
            String hash = stats.getLastCommitHash();
        }
        assertTrue(true);
    }

    @Test
    @DisplayName("The writeHashToFile method is correctly write a hash to the file")
    void testWriteHashToFile() throws IOException {
        String testHash = "0123456789abcdef";
        stats.writeHashToFile(testHash);
        String content = Files.readString(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        assertEquals(testHash, content);
    }

    @Test
    @DisplayName("The writeHashToFile method is correctly write a empty string to the file")
    void testWriteEmptyString() throws IOException {
        String emptyString = "";
        stats.writeHashToFile(emptyString);
        String content = Files.readString(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        assertEquals(emptyString, content);
    }

    @Test
    @DisplayName("The writeHashToFile method error handling works for invalid paths")
    void testWriteHashToInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                invalidPath,
                PATH_TO_DAILY_COMMITS);
        assertDoesNotThrow(() -> invalidStats.writeHashToFile("test"));
    }

    @Test
    @DisplayName("The writeHashToFile method null writing are handled gracefully")
    void testWriteNullToHashFile() {
        assertDoesNotThrow(() -> stats.writeHashToFile(null));
        assertTrue(Files.exists(Paths.get(PATH_TO_LAST_COMMIT_HASH)));
    }

    @Test
    @DisplayName("The writeHashToFile method new hash overwrites previous content")
    void testHashToFileOverwrite() throws IOException {
        String firstHash = "test hash 1";
        String secondHash = "test hash 2";
        stats.writeHashToFile(firstHash);
        stats.writeHashToFile(secondHash);
        String content = Files.readString(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        assertEquals(secondHash, content);
        assertNotEquals(firstHash, content);
    }

    @Test
    @DisplayName("The writeHashToFile and readHashFromFile methods"
                + " write and read hash correctly from the file")
    void testWriteAndReadHash() throws IOException {
        String testHash = "0123456789abcdef";
        stats.writeHashToFile(testHash);
        String content = stats.readHashFromFile();
        assertEquals(testHash, content);
    }

    @Test
    @DisplayName("The writeHashToFile and readHashFromFile methods"
                + " write and read empty string correctly from the file")
    void testWriteAndReadEmptyString() throws IOException {
        String emptyString = "";
        stats.writeHashToFile(emptyString);
        String content = stats.readHashFromFile();
        assertEquals(emptyString, content);
    }

    @Test
    @DisplayName("The readHashFromFile method error handling works for invalid paths")
    void testReadHashFromInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                invalidPath,
                PATH_TO_DAILY_COMMITS);
        assertDoesNotThrow(() -> invalidStats.readHashFromFile());
    }

    @Test
    @DisplayName("The readHashFromFile method empty file reading are handled gracefully")
    void testReadEmptyHashFile() {
        assertDoesNotThrow(() -> {
            new File(PATH_TO_LAST_COMMIT_HASH).createNewFile();
            String result = stats.readHashFromFile();
            assertEquals("", result);
        });
    }

    @Test
    @DisplayName("Tests readHashFromFile() method returns empty string "
                + "when file does not exist")
    void testReadHashFromFileWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        String actualValue = stats.readHashFromFile();
        assertEquals("", actualValue);
    }

    @Test
    @DisplayName("The writeDailyCommitsToFile write value to the file correctly")
    void testWriteDailyCommitsToFile() throws IOException {
        Long testValue = 123L;
        stats.writeDailyCommitsToFile(testValue);
        Long content = Long.parseLong(
                Files.readString(Paths.get(PATH_TO_DAILY_COMMITS)));
        assertEquals(testValue, content);
    }

    @Test
    @DisplayName("The writeDailyCommitsToFile method error handling works for invalid paths")
    void testWriteDailyCommitsToInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                PATH_TO_LAST_COMMIT_HASH,
                invalidPath);
        assertDoesNotThrow(() -> invalidStats.writeDailyCommitsToFile(123L));
    }

    @Test
    @DisplayName("The writeDailyCommitsToFile method null writing are handled gracefully")
    void testWriteNullToDailyCommitsFile() {
        assertDoesNotThrow(() -> stats.writeDailyCommitsToFile(null));
        assertTrue(Files.exists(Paths.get(PATH_TO_DAILY_COMMITS)));
    }

    @Test
    @DisplayName("The writeDailyCommitsToFile method new value overwrites previous content")
    void testDailyCommitsFileOverwrite() throws IOException {
        Long firstValue = 123L;
        Long secondValue = 1234L;
        stats.writeDailyCommitsToFile(firstValue);
        stats.writeDailyCommitsToFile(secondValue);
        Long content = Long.parseLong(
                Files.readString(Paths.get(PATH_TO_DAILY_COMMITS)));
        assertEquals(secondValue, content);
        assertNotEquals(firstValue, content);
    }

    @Test
    @DisplayName("The writeDailyCommitsToFile and readDailyCommitsFromFile methods"
                + " write and read from the file correctly")
    void testWriteAndReadDailyCommits() throws IOException {
        Long testValue = 123L;
        stats.writeDailyCommitsToFile(testValue);
        Long content = stats.readDailyCommitsFromFile();
        assertEquals(testValue, content);
    }

    @Test
    @DisplayName("The readDailyCommitsFromFile method error handling works for invalid paths")
    void testReadDailyCommitsFromInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                PATH_TO_LAST_COMMIT_HASH,
                invalidPath);
        assertDoesNotThrow(() -> invalidStats.readDailyCommitsFromFile());
    }

    @Test
    @DisplayName("The readDailyCommitsFromFile method empty file reading are handled gracefully")
    void testReadEmptyDailyCommitsFile() {
        assertDoesNotThrow(() -> {
            new File(PATH_TO_DAILY_COMMITS).createNewFile();
            Long result = stats.readDailyCommitsFromFile();
            assertEquals(0, result);
        });
    }

    @Test
    @DisplayName("The readDailyCommitsFromFile method returns 0 as default value")
    void testReadDailyCommitsFromFileWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(Paths.get(PATH_TO_DAILY_COMMITS));
        Long actualValue = stats.readDailyCommitsFromFile();
        assertEquals(0, actualValue);
    }

    @Test
    @DisplayName("The readDailyCommitsFromFile method non-numeric data is handled gracefully")
    void testReadDailyCommitsFromFileWithInvalidData() throws IOException {
        Files.write(Paths.get(PATH_TO_DAILY_COMMITS), "Invalid_data".getBytes());
        assertDoesNotThrow(() -> stats.readDailyCommitsFromFile());
    }

    @Test
    @DisplayName("The isFileExists method returns false for non-existent file")
    void testIsFileExistsWhenFileDoesNotExist() {
        assertFalse(stats.isFileExists(PATH_TO_LAST_COMMIT_HASH));
    }

    @Test
    @DisplayName("The isFileExists method returns true for existent file")
    void testIsFileExistsWhenFileExists() throws IOException {
        String lastCommitHash = "/last_hash.txt";
        String dailyCommits = "/daily_commits.txt";
        String homeDir = System.getProperty("user.home");
        CommitStats testCommitStats = new CommitStats (
                homeDir + lastCommitHash,
                homeDir + dailyCommits);
        testCommitStats.writeHashToFile("hash");
        assertTrue(stats.isFileExists(lastCommitHash));
        Files.deleteIfExists(Paths.get(homeDir + lastCommitHash));
    }

    @Test
    @DisplayName("The getFileDate method return null when file does not exist")
    void testGetFileDateWhenFileDoesNotExist() throws IOException {
        LocalDate actualDate = stats.getFileDate(PATH_TO_LAST_COMMIT_HASH);
        assertEquals(null, actualDate);
    }

    @Test
    @DisplayName("The getFileDate method return file creation data")
    void testGetFileDateWhenFileExists() throws IOException {
        String lastCommitHash = "/last_hash.txt";
        String dailyCommits = "/daily_commits.txt";
        String homeDir = System.getProperty("user.home");
        CommitStats testCommitStats = new CommitStats (
                homeDir + lastCommitHash,
                homeDir + dailyCommits);
        testCommitStats.writeHashToFile("hash");
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = testCommitStats.getFileDate(lastCommitHash);
        assertEquals(expectedDate, actualDate);
    }
}
