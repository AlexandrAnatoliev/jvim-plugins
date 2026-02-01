import java.io.*;
import java.nio.file.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

/**
 * Unit tests for CommitStats class
 *
 * @version  0.8.7
 * @since    01.02.2026
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
    @DisplayName("Constructor should set all fields are properly")
    void testConstructorShouldSetPaths() {
        String lastCommitHash = "last_hash.txt";
        String dailyCommits = "daily_commits.txt";
        CommitStats testCommitStats = new CommitStats (
                lastCommitHash,
                dailyCommits);
        assertEquals(lastCommitHash, testCommitStats.pathToString,
                "Path to last commit hash file should be set correctly");
        assertEquals(dailyCommits, testCommitStats.pathToLong,
                "Path to daily commits file should be set correctly");
    }

    @Test
    @DisplayName("Constructor should work with null values") 
    void testConstructorShouldHandleNull() {
        CommitStats testStats = new CommitStats(null, null);
        assertNull(testStats.pathToString);
        assertNull(testStats.pathToLong);
    }

    @Test
    @DisplayName("Constructor should work with empty strings")
    void testConstructorShouldHandleEmptyStrings() {
        CommitStats testStats = new CommitStats("", "");
        assertEquals("", testStats.pathToString);
        assertEquals("", testStats.pathToLong);
    }

    @Test
    @DisplayName("Constructor should handle strings with whitespace") 
    void testConstructorShouldHandleWhitespaceStrings() {
        CommitStats testStats = new CommitStats(" ", " \t");
        assertEquals(" ", testStats.pathToString,
                "pathToString should preserve whitespace");
        assertEquals(" \t", testStats.pathToLong,
                "pathToLong should preserve whitespace and tabs");
    }

    @Test
    @DisplayName("Constructor should handle relative paths")
    void testConstructorShouldHandleRelativePaths() {
        String relativePath1 = "./data/last_commit_hash.txt";
        String relativePath2 = "../commits/daily_commits.txt";
        CommitStats testStats = new CommitStats(relativePath1, relativePath2);
        assertEquals(relativePath1, testStats.pathToString);
        assertEquals(relativePath2, testStats.pathToLong);
    }

    @Test
    @DisplayName("Constructor should handle absolute paths")
    void testConstructorShouldHandleAbsolutePaths() {
        String absolutePath1 = "/tmp/last_commit_hash.txt";
        String absolutePath2 = "var/log/commits/daily_commits.txt";
        if (!System.getProperty("os.name").toLowerCase().contains("win")) {
            CommitStats testStats = new CommitStats(absolutePath1, absolutePath2);
            assertEquals(absolutePath1, testStats.pathToString);
            assertEquals(absolutePath2, testStats.pathToLong);
        }
    }

    @Test
    @DisplayName("Constructor should set different paths for different files")
    void testConstructorShouldSetDifferentPaths() {
        String path1 = "file1.txt";
        String path2 = "file2.txt";
        CommitStats testStats = new CommitStats(path1, path2);
        assertNotSame(testStats.pathToString, 
                testStats.pathToLong,
                "Paths should be different objects"); 
        assertNotEquals(testStats.pathToString,
                testStats.pathToLong,
                "Paths should be different values");
    }

    @Test
    @DisplayName("Constructor should preserve special characters in paths")
    void testConstructorShouldPreserveSpecialCharacters() {
        String pathWithSpecialChars1 = "last_commit_2025-01-15.txt";
        String pathWithSpecialChars2 = "daily_commits_v1.2.3.txt";
        CommitStats testStats = new CommitStats(
                pathWithSpecialChars1,
                pathWithSpecialChars2);
        assertEquals(pathWithSpecialChars1, testStats.pathToString,
                "Special characters in paths should be preserved");
        assertEquals(pathWithSpecialChars2, testStats.pathToLong,
                "Special characters in paths should be preserved");
    }

    @Test
    @DisplayName("Git should be installed and accessible")
    void testIsGitInstalled() {
        try {
            Process p = new ProcessBuilder("git", "--version")
                .redirectErrorStream(true)
                .start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
            String versionOutput = reader.readLine();
            int exitCode = p.waitFor();
            assertEquals(0, exitCode, "Git command should execute successfully");
            assertNotNull(versionOutput, "Git version output should not be null");
            assertTrue(versionOutput.toLowerCase().contains("git version"),
                    "Output should contain 'git version'");
        } catch (Exception e) {
            fail("Git is not installed or not accessible: " + e.getMessage());
        }
    }

    /**
     * This test requires git to be installed and may fail if not in a git repository
     */
    @Test
    @DisplayName("getLastCommitHash must get last commit hash in git repository")
    void testGetLastCommitHashInGitRepo() {
        try {
            // Check if we are in a Git repo
            Process p = new ProcessBuilder("git", "rev-parse", "--git-dir")
                .redirectErrorStream(true)
                .start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                        p.getInputStream()));
            int exitCode = p.waitFor();
            if (exitCode == 0) {
                // We are in a Git repo
                String hash = stats.getLastCommitHash();
                assertNotNull(hash, "Hash should not be null");
                assertFalse(hash.isEmpty());
                assertEquals(40, hash.length(), 
                        "Git hash should be 40 chars");
                assertTrue(hash.matches("[0-9a-f]{40}"),
                        "Should be a valid SHA-1 hash");
            } else {
                // Not in a Git repo, test should return empty string
                String hash = stats.getLastCommitHash();
                assertEquals("", hash,
                        "Should return empty string when Git is not available");
            }
        } catch (IOException e) {
            String hash = stats.getLastCommitHash();
            assertEquals("", hash,
                    "Should return empty string when Git is not available or IO error occurs");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test was interrupted: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("getLastCommitHash doesn't throw exceptions and returns a value")
    void testGetLastCommitHashNoExceptions() {
        String hash = assertDoesNotThrow(() -> stats.getLastCommitHash(),
                "Method should not throw any exceptions");
        assertNotNull(hash, "Returned value should not be null");
    }

    @Test
    @DisplayName("writeHashToFile correctly writes a hash to the file")
    void testWriteHashToFile() throws IOException {
        String testHash = "0123456789abcdef";
        stats.writeHashToFile(testHash);
        String content = Files.readString(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        assertEquals(testHash, content);
    }

    @Test
    @DisplayName("writeHashToFile correctly writes an empty string to the file")
    void testWriteEmptyString() throws IOException {
        String emptyString = "";
        stats.writeHashToFile(emptyString);
        String content = Files.readString(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        assertEquals(emptyString, content);
    }

    @Test
    @DisplayName("writeHashToFile error handling works for invalid paths")
    void testWriteHashToInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                invalidPath,
                PATH_TO_DAILY_COMMITS);
        assertDoesNotThrow(() -> invalidStats.writeHashToFile("test"));
    }

    @Test
    @DisplayName("writeHashToFile handles null values gracefully")
    void testWriteNullToHashFile() {
        assertDoesNotThrow(() -> stats.writeHashToFile(null));
        assertTrue(Files.exists(Paths.get(PATH_TO_LAST_COMMIT_HASH)));
    }

    @Test
    @DisplayName("writeHashToFile new hash overwrites previous content")
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
    @DisplayName("writeHashToFile and readHashFromFile "
    + "write and read hash correctly from the file")
    void testWriteAndReadHash() throws IOException {
        String testHash = "0123456789abcdef";
        stats.writeHashToFile(testHash);
        String content = stats.readHashFromFile();
        assertEquals(testHash, content);
    }

    @Test
    @DisplayName("writeHashToFile and readHashFromFile "
    + " write and read empty string correctly from the file")
    void testWriteAndReadEmptyString() throws IOException {
        String emptyString = "";
        stats.writeHashToFile(emptyString);
        String content = stats.readHashFromFile();
        assertEquals(emptyString, content);
    }

    @Test
    @DisplayName("readHashFromFile error handling works for invalid paths")
    void testReadHashFromInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                invalidPath,
                PATH_TO_DAILY_COMMITS);
        assertDoesNotThrow(() -> invalidStats.readHashFromFile());
    }

    @Test
    @DisplayName("readHashFromFile empty file reading are handled gracefully")
    void testReadEmptyHashFile() {
        assertDoesNotThrow(() -> {
            new File(PATH_TO_LAST_COMMIT_HASH).createNewFile();
            String result = stats.readHashFromFile();
            assertEquals("", result);
        });
    }

    @Test
    @DisplayName("Tests readHashFromFile() returns empty string "
    + "when file does not exist")
    void testReadHashFromFileWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(Paths.get(PATH_TO_LAST_COMMIT_HASH));
        String actualValue = stats.readHashFromFile();
        assertEquals("", actualValue);
    }

    @Test
    @DisplayName("writeLong writes value to the file correctly")
    void testWriteDailyCommitsToFile() throws IOException {
        Long testValue = 123L;
        stats.writeLong(testValue);
        Long content = Long.parseLong(
                Files.readString(Paths.get(PATH_TO_DAILY_COMMITS)));
        assertEquals(testValue, content);
    }

    @Test
    @DisplayName("writeLong error handling works for invalid paths")
    void testWriteDailyCommitsToInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                PATH_TO_LAST_COMMIT_HASH,
                invalidPath);
        assertDoesNotThrow(() -> invalidStats.writeLong(123L));
    }

    @Test
    @DisplayName("writeLong handles null values gracefully")
    void testWriteNullToDailyCommitsFile() {
        assertDoesNotThrow(() -> stats.writeLong(null));
        assertTrue(Files.exists(Paths.get(PATH_TO_DAILY_COMMITS)));
    }

    @Test
    @DisplayName("writeLong overwrites previous content with new value")
    void testDailyCommitsFileOverwrite() throws IOException {
        Long firstValue = 123L;
        Long secondValue = 1234L;
        stats.writeLong(firstValue);
        stats.writeLong(secondValue);
        Long content = Long.parseLong(
                Files.readString(Paths.get(PATH_TO_DAILY_COMMITS)));
        assertEquals(secondValue, content);
        assertNotEquals(firstValue, content);
    }

    @Test
    @DisplayName("writeLong and readLong "
    + "write and read from the file correctly")
    void testWriteAndReadDailyCommits() throws IOException {
        Long testValue = 123L;
        stats.writeLong(testValue);
        Long content = stats.readLong();
        assertEquals(testValue, content);
    }

    @Test
    @DisplayName("readLong error handling works for invalid paths")
    void testReadDailyCommitsFromInvalidPath() {
        String invalidPath = "non_existent_directory/test.txt";
        CommitStats invalidStats = new CommitStats(
                PATH_TO_LAST_COMMIT_HASH,
                invalidPath);
        assertDoesNotThrow(() -> invalidStats.readLong());
    }

    @Test
    @DisplayName("readLong empty file reading are handled gracefully")
    void testReadEmptyDailyCommitsFile() {
        assertDoesNotThrow(() -> {
            new File(PATH_TO_DAILY_COMMITS).createNewFile();
            Long result = stats.readLong();
            assertEquals(0L, (long)result);
        });
    }

    @Test
    @DisplayName("readLong returns 0 as default value")
    void testReadDailyCommitsFromFileWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(Paths.get(PATH_TO_DAILY_COMMITS));
        Long actualValue = stats.readLong();
        assertEquals(0L, (long)actualValue);
    }

    @Test
    @DisplayName("readLong non-numeric data is handled gracefully")
    void testReadDailyCommitsFromFileWithInvalidData() throws IOException {
        Files.write(Paths.get(PATH_TO_DAILY_COMMITS), "Invalid_data".getBytes());
        assertDoesNotThrow(() -> stats.readLong());
    }

    @Test
    @DisplayName("isFileExists returns false for non-existent file")
    void testIsFileExistsWhenFileDoesNotExist() {
        assertFalse(stats.isFileExists(PATH_TO_LAST_COMMIT_HASH));
    }

    @Test
    @DisplayName("isFileExists returns true for existent file")
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
    @DisplayName("getFileDate return null when file does not exist")
    void testGetFileDateWhenFileDoesNotExist() throws IOException {
        LocalDate actualDate = stats.getFileDate(PATH_TO_LAST_COMMIT_HASH);
        assertEquals(null, actualDate);
    }

    @Test
    @DisplayName("getFileDate return file creation data")
    void testGetFileDateWhenFileExists() throws IOException {
        stats.writeHashToFile("hash");
        LocalDate expectedDate = LocalDate.now();
        LocalDate actualDate = stats.getFileDate(PATH_TO_LAST_COMMIT_HASH);
        assertEquals(expectedDate, actualDate);
    }
}
