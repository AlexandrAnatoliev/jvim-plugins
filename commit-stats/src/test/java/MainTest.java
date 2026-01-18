import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CommitStats class
 *
 * @version  0.7.6
 * @since    18.01.2026
 * @author   AlexandrAnatoliev 
 */
class MainTest {
    @TempDir
    Path tempDir;

    private String originalHome;
    private Path testLastCommitHashPath;
    private Path testDailyCommitsPath;

    @BeforeEach
    void setUp() throws Exception {
        originalHome = System.getProperty("user.home");
        System.setProperty("user.home", tempDir.toString());

        Path dataDir = tempDir.resolve(".vim/pack/my-plugins/start/commit-stats/data");
        Files.createDirectories(dataDir);

        testLastCommitHashPath = dataDir.resolve("last_commit_hash.txt");
        testDailyCommitsPath = dataDir.resolve("daily_commits.txt");
    }

    @AfterEach
    void tearDown() {
        if (originalHome != null) {
            System.setProperty("user.home", originalHome);
        }
    }

    @Test
    @DisplayName("Running with 'start' argument creates files")
    void testStartCreatesFiles() {
        assertFalse(Files.exists(testLastCommitHashPath));
        assertFalse(Files.exists(testDailyCommitsPath));

        Main.main(new String[]{"start"});

        assertTrue(Files.exists(testLastCommitHashPath),
                "last_commit_hash.txt file should be created");
        assertTrue(Files.exists(testDailyCommitsPath),
                "daily_commits.txt file should be created");

        try {
            String hashContent = Files.readString(testLastCommitHashPath).trim();
            String commitsContent = Files.readString(testDailyCommitsPath).trim();

            assertEquals("", hashContent, "last_commit_hash.txt should be empty");
            assertEquals("0", commitsContent, "daily_commits.txt should contain '0'");
        } catch (IOException e) {
            fail("Error reading files: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Running with 'start' argument doesn't overwrite existing files with current date")
    void testStartDoesNotOverwriteExistingFiles() throws IOException {
        Files.writeString(testLastCommitHashPath, "existing_hash");
        Files.writeString(testDailyCommitsPath, "5");

        Main.main(new String[]{"start"});

        String hashContent = Files.readString(testLastCommitHashPath).trim();
        String commitsContent = Files.readString(testDailyCommitsPath).trim();

        assertEquals("existing_hash", hashContent,
                "last_commit_hash.txt should not be overwritten");
        assertEquals("5", commitsContent,
                "daily_commits.txt should not be overwritten");
    }

    @Test
    @DisplayName("Running with 'update' argument updates hash and commit counter")
    void testUpdate() throws IOException {
        Main.main(new String[]{"start"});

        Files.writeString(testLastCommitHashPath, "old_hash");
        Files.writeString(testDailyCommitsPath, "3");

        Main.main(new String[]{"update"});

        String newHash = Files.readString(testLastCommitHashPath).trim();
        assertFalse(newHash.isEmpty(), "Hash should be updated");
        assertNotEquals("old_hash", newHash, "Hash should change");

        String commits = Files.readString(testDailyCommitsPath).trim();
        assertEquals("4", commits, "Commit counter should increase by 1");
    }

    @Test
    @DisplayName("Running with 'stop' argument displays statics")
    void testOutput() throws IOException, InterruptedException {
        Files.writeString(testDailyCommitsPath, "7");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOutput =System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Main.main(new String[]{"stop"});
            Thread.sleep(500);
            String output = outputStream.toString();
            assertTrue(output.contains("Commit stats:"), 
                    "Output should contain 'Commit stats:'");
            assertTrue(output.contains("Commits per day: 7"), 
                    "Output should contain commit count");
        } finally {
            System.setOut(originalOutput);
        }
    }

    @Test
    @DisplayName("Running without arguments calls 'stop'")
    void testNoArgumentsCallsStop() throws IOException, InterruptedException {
        Files.writeString(testDailyCommitsPath, "2");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOutput =System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Main.main(new String[]{});
            Thread.sleep(500);
            String output = outputStream.toString();
            assertTrue(output.contains("Commit stats:"), 
                    "Running without arguments should call 'stop'");
        } finally {
            System.setOut(originalOutput);
        }
    }

    @Test
    @DisplayName("Running with invalid arguments calls 'stop'")
    void testInvalidArgumentsCallsStop() throws IOException, InterruptedException {
        Files.writeString(testDailyCommitsPath, "1");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOutput =System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Main.main(new String[]{"invalid"});
            Thread.sleep(500);
            String output = outputStream.toString();
            assertTrue(output.contains("Commit stats:"), 
                    "Running with invalid arguments should call 'stop'");
        } finally {
            System.setOut(originalOutput);
        }
    }

    @Test
    @DisplayName("start method creates files when they don't exist")
    void testStartMethodDirectly() {
        try {
            Files.deleteIfExists(testLastCommitHashPath);
            Files.deleteIfExists(testDailyCommitsPath);
        } catch (IOException e) {
            // ignore if files don't exist
        }

        Main.start();

        assertTrue(Files.exists(testLastCommitHashPath));
        assertTrue(Files.exists(testDailyCommitsPath));
    }

    @Test
    @DisplayName("update method handles missing files")
    void testUpdateWithMissingFiles() {
        try {
            Files.deleteIfExists(testLastCommitHashPath);
            Files.deleteIfExists(testDailyCommitsPath);
        } catch (IOException e) {
            // ignore if files don't exist
        }

        assertDoesNotThrow(() -> Main.update());
    }

    @Test
    @DisplayName("stop method handles missing daily_commits.txt file")
    void testStopWithMissingFile() throws InterruptedException {
        try {
            Files.deleteIfExists(testDailyCommitsPath);
        } catch (IOException e) {
            // ignore if files don't exist
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            assertDoesNotThrow(() -> Main.stop());
            Thread.sleep(500);
            String output = outputStream.toString();
            assertNotNull(output);
        } finally {
            System.setOut(originalOutput);
        }
    }

    @Test
    @DisplayName("Check CommitStats creation with correct paths")
    void testPathsConfiguration() throws IOException {
        Main.main(new String[]{"start"});

        assertTrue(testLastCommitHashPath.toAbsolutePath().toString()
                .contains("last_commit_hash.txt"));
        assertTrue(testDailyCommitsPath.toAbsolutePath().toString()
                .contains("daily_commits.txt"));
        assertTrue(testLastCommitHashPath.toAbsolutePath().toString()
                .contains(".vim/pack/my-plugins/start/commit-stats/data"));
        assertTrue(testDailyCommitsPath.toAbsolutePath().toString()
                .contains(".vim/pack/my-plugins/start/commit-stats/data"));
    }

    @Test
    @DisplayName("Multiple start calls don't corrupt existing data")
    void testMultipleStartCalls() throws IOException {
        Main.main(new String[]{"start"});

        Files.writeString(testLastCommitHashPath, "custom_hash");
        Files.writeString(testDailyCommitsPath, "10");

        Main.main(new String[]{"start"});

        String hash = Files.readString(testLastCommitHashPath).trim();
        String commits = Files.readString(testDailyCommitsPath).trim();

        assertEquals("custom_hash", hash, 
                "Hash should not reset on repeated start");
        assertEquals("10", commits, 
                "Commit counter should not reset on repeated start");
    }

    @Test
    @DisplayName("Full start-update-stop workflow works correctly")
    void testFullWorkflow() throws IOException, InterruptedException {
        Main.main(new String[]{"start"});
        assertTrue(Files.exists(testLastCommitHashPath));
        assertTrue(Files.exists(testDailyCommitsPath));

        String initialCommits = Files.readString(testDailyCommitsPath).trim();
        assertEquals("0", initialCommits, "Initial value should be '0'");

        String initialHash = Files.readString(testLastCommitHashPath).trim();
        Main.main(new String[]{"update"});

        String afterUpdateCommits = Files.readString(testDailyCommitsPath).trim();
        String afterUpdateHash = Files.readString(testLastCommitHashPath).trim();

        assertNotEquals(initialHash, afterUpdateHash, "Hash should be update");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Main.main(new String[]{"stop"});
            Thread.sleep(500);

            String output = outputStream.toString();
            assertTrue(output.contains("Commit stats:"), "Should display statistics");
        } finally {
            System.setOut(originalOutput);
        }
    }

    @Test
    @DisplayName("Edge case: very large number of commits")
    void testLargeNumbersOfCommits() throws IOException, InterruptedException {
        Files.writeString(testDailyCommitsPath, "999999");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            Main.main(new String[]{"stop"});
            Thread.sleep(500);

            String output = outputStream.toString();
            assertTrue(output.contains("999999"), 
                    "Should display large number of commits");
        } finally {
            System.setOut(originalOutput);
        }
    }

    @Test
    @DisplayName("Concurrent access handling in stop method")
    void testConcurrentAccess() throws IOException, InterruptedException {
        Files.writeString(testDailyCommitsPath, "3");

        Thread thread1 = new Thread(() -> Main.stop());
        Thread thread2 = new Thread(() -> Main.stop());

        thread1.start();
        thread2.start();

        thread1.join(1000);
        thread2.join(1000);

        assertFalse(thread1.isAlive());
        assertFalse(thread2.isAlive());
    }
}
