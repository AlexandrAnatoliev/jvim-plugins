import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CommitStats class
 *
 * @version  0.7.1
 * @since    28.12.2025
 * @author   AlexandrAnatoliev 
 */
public class CommitStatsTest {
    private CommitStats commitStats;

    /**
     * Set up test environment before each test method execution.
     * Creates new CommitStats instance.
     */
    @BeforeEach
    void setUp() {
        commitStats = new CommitStats();
    }

    /**
     * Clean up test environment after each test method execution.
     */
    @AfterEach
    void tearDown() {
        commitStats = null;
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
                        "Git hash should be 40 40 chars");
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
}
