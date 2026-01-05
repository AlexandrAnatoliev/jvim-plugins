import java.time.*;

public class Main {
    private static final String PATH_TO_LAST_COMMIT_HASH = 
        "/.vim/pack/my-plugins/start/commit-stats/data/last_commit_hash.txt";
    private static final String PATH_TO_DAILY_COMMITS = 
        "/.vim/pack/my-plugins/start/commit-stats/data/daily_commits.txt";

    public static void main(String[] args) {
        if (args.length > 0 && "start".equals(args[0])) {
            // start();
        }
    }

    private static CommitStats createCommitStats() {
        String homeDir = System.getProperty("user.home");
        return new CommitStats (
                homeDir + PATH_TO_LAST_COMMIT_HASH, 
                homeDir + PATH_TO_DAILY_COMMITS);
    }

    public static void start() {
        CommitStats commitStats = createCommitStats();
        LocalDate today = LocalDate.now();

        if (!commitStats.fileIsExists(PATH_TO_DAILY_COMMITS)
                || commitStats.getFileDate(PATH_TO_DAILY_COMMITS)
                .equals(today)) {
            commitStats.writeDailyCommitsToFile(0L);
                }

        if (!commitStats.fileIsExists(PATH_TO_LAST_COMMIT_HASH)) {
            commitStats.writeHashToFile("");
        }
    }
}
