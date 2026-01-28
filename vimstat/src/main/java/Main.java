import java.time.*;

/**
 * Vim utility to get commit stats
 * 
 * When ended work with Vim, print value commit per day
 *
 * Usage:
 *   java Main start    -   erases information from temporary files, 
 *                          and starts to calculate commit stats  
 *   java Main update   -   update commit stats 
 *   java Main stop     -   print commit stats
 *
 * @version  0.7.5
 * @since    06.01.2026
 * @author   AlexandrAnatoliev
 */
public class Main {
    private static final String PATH_TO_LAST_COMMIT_HASH = 
        "/.vim/pack/my-plugins/start/commit-stats/data/last_commit_hash.txt";
    private static final String PATH_TO_DAILY_COMMITS = 
        "/.vim/pack/my-plugins/start/commit-stats/data/daily_commits.txt";

    /** Main entry point for the Commit Stats application
     *
     * @param args command line arguments - first argument determines operation mode 
     *   "start"    To begin new work session     
     *   "update"   To update commit stats     
     *   "stop"     Or any other argument to print commit stats     
     */
    public static void main(String[] args) {
        if (args.length > 0 && "start".equals(args[0])) {
            start();
        } else if (args.length > 0 && "update".equals(args[0])) {
            update();
        } else {
            stop();
        }
    }

    /**
     * Creates and configures a CommitStats interface with standard settings.
     *
     * @return Configured CommitStats instance ready for use
     */
    private static CommitStats createCommitStats() {
        String homeDir = System.getProperty("user.home");
        return new CommitStats (
                homeDir + PATH_TO_LAST_COMMIT_HASH, 
                homeDir + PATH_TO_DAILY_COMMITS);
    }

    /**
     * Starts a new CommitStats session.
     */
    public static void start() {
        CommitStats commitStats = createCommitStats();
        LocalDate today = LocalDate.now();

        if (!commitStats.isFileExists(PATH_TO_DAILY_COMMITS)
                || !today.equals(commitStats.getFileDate(PATH_TO_DAILY_COMMITS))) {
            commitStats.writeDailyCommitsToFile(0L);
                }

        if (!commitStats.isFileExists(PATH_TO_LAST_COMMIT_HASH)) {
            commitStats.writeHashToFile("");
        }
    }

    /*
     * Update commit stats
     */
    public static void update() {
        CommitStats commitStats = createCommitStats(); 
        String savedHash = commitStats.readHashFromFile();
        String lastHash = commitStats.getLastCommitHash();

        if (!lastHash.equals(savedHash)) {
            long savedDailyCommits = commitStats.readDailyCommitsFromFile();
            commitStats.writeDailyCommitsToFile(savedDailyCommits + 1L);
        }

        commitStats.writeHashToFile(lastHash);
    }

    /*
     * Print commit stats
     */
    public static void stop() {
        CommitStats commitStats = createCommitStats(); 
        long savedDailyCommits = commitStats.readDailyCommitsFromFile();

        new Thread(() -> {
            try {
                Thread.sleep(200);
                System.out.print("""
                          =========================================
                                        Commit stats:                
                          -----------------------------------------
                        """);
                System.out.println("  - Commits per day: " + savedDailyCommits);
                System.out.println("  =========================================");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
