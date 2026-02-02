import java.time.*;
import java.time.temporal.ChronoUnit;

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
 * @version  0.8.7
 * @since    02.02.2026
 * @author   AlexandrAnatoliev
 */
public class Main {
    private static final String PATH_TO_LAST_COMMIT_HASH = 
        "/.vim/pack/my-plugins/start/vimstat/data/last_commit_hash.txt";
    private static final String PATH_TO_DAILY_COMMITS = 
        "/.vim/pack/my-plugins/start/vimstat/data/daily_commits.txt";

    private static final String SESSION_FILE_PATH = 
        "/.vim/pack/my-plugins/start/vimstat/data/jvim_session_time.txt";
    private static final String DAY_FILE_PATH = 
        "/.vim/pack/my-plugins/start/vimstat/data/jvim_day_time.txt";
    private static final String MONTH_FILE_PATH = 
        "/.vim/pack/my-plugins/start/vimstat/data/jvim_month_time.txt";
    private static final String YESTERDAY_FILE_PATH = 
        "/.vim/pack/my-plugins/start/vimstat/data/jvim_yesterday_time.txt";

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
     * Creates and configures a GitStats interface with standard settings.
     *
     * @return Configured GitStats instance ready for use
     */
    private static GitStats createGitStats() {
        String homeDir = System.getProperty("user.home");
        return new GitStats (
                homeDir + PATH_TO_LAST_COMMIT_HASH, 
                homeDir + PATH_TO_DAILY_COMMITS);
    }

    /**
     * Starts a new GitStats session.
     */
    public static void start() {
        String homeDir = System.getProperty("user.home");
        GitStats gitStats = createGitStats();
        LocalDate today = LocalDate.now();

        if (!gitStats.isFileExists(homeDir + PATH_TO_DAILY_COMMITS)
                || !today.equals(gitStats.getFileDate(homeDir + PATH_TO_DAILY_COMMITS))) {
            gitStats.writeLong(0L);
                }

        if (!gitStats.isFileExists(homeDir + PATH_TO_LAST_COMMIT_HASH)) {
            gitStats.writeString("");
        }

        String pathToDayTime = homeDir + DAY_FILE_PATH;
        String pathToMonthTime = homeDir + MONTH_FILE_PATH;
        String pathToYesterdayTime = homeDir + YESTERDAY_FILE_PATH;

        TimeStats sessionTimeStats = new TimeStats(homeDir + SESSION_FILE_PATH);
        TimeStats dayTimeStats = new TimeStats(pathToDayTime);
        TimeStats monthTimeStats = new TimeStats(pathToMonthTime);
        TimeStats yesterdayTimeStats = new TimeStats(pathToYesterdayTime);

        if(sessionTimeStats.isFileExists(homeDir + SESSION_FILE_PATH)) {
            long pastDuration = sessionTimeStats.getSessionTime();
            long dayTime = dayTimeStats.readLong();
            dayTimeStats.writeLong(dayTime + pastDuration);
        }
        sessionTimeStats.writeLong(System.currentTimeMillis() / 1000);

        if(!yesterdayTimeStats.isFileExists(pathToYesterdayTime)) {
            yesterdayTimeStats.writeLong(0L);
        }

        if(!monthTimeStats.isFileExists(pathToMonthTime)) {
            monthTimeStats.writeLong(0L);
        }

        if(!monthTimeStats.getFileDate(pathToMonthTime).equals(today)) {
            long yesterdayTime = monthTimeStats.readLong();
            yesterdayTimeStats.writeLong(yesterdayTime);

            long emptyDays = ChronoUnit.DAYS.between(
                    monthTimeStats.getFileDate(pathToMonthTime), today);
            long monthTime = monthTimeStats.readLong() * (30 - emptyDays);
            monthTimeStats.writeLong(
                    (monthTime + dayTimeStats.readLong()) / 30);     
        }

        if(!dayTimeStats.isFileExists(pathToDayTime) || 
                !dayTimeStats.getFileDate(pathToDayTime).equals(today)) {
            dayTimeStats.writeLong(0L);
                }

        return;
    }

    /*
     * Update commit stats
     */
    public static void update() {
        GitStats gitStats = createGitStats(); 
        String savedHash = gitStats.readString();
        String lastHash = gitStats.getLastCommitHash();

        if (!lastHash.equals(savedHash)) {
            long savedDailyCommits = gitStats.readLong();
            gitStats.writeLong(savedDailyCommits + 1L);
        }

        gitStats.writeString(lastHash);
    }

    /*
     * Print commit stats
     */
    public static void stop() {
        String homeDir = System.getProperty("user.home");
        String pathToDayTime = homeDir + DAY_FILE_PATH;
        String pathToMonthTime = homeDir + MONTH_FILE_PATH;
        String pathToYesterdayTime = homeDir + YESTERDAY_FILE_PATH;

        TimeStats sessionTimeStats = new TimeStats(homeDir + SESSION_FILE_PATH);
        TimeStats dayTimeStats = new TimeStats(pathToDayTime);
        TimeStats monthTimeStats = new TimeStats(pathToMonthTime);
        TimeStats yesterdayTimeStats = new TimeStats(pathToYesterdayTime);

        long duration = sessionTimeStats.getSessionTime(); 

        if(!dayTimeStats.isFileExists(pathToDayTime)) {
            dayTimeStats.writeLong(0L);
        }

        long dayTime = dayTimeStats.readLong() + duration;

        dayTimeStats.writeLong(dayTime);

        if(!monthTimeStats.isFileExists(pathToMonthTime)) {
            monthTimeStats.writeLong(0L);
        }
        long monthTime = monthTimeStats.readLong();

        long yesterdayTime = yesterdayTimeStats.readLong();

        long sessionHours = duration / 3600;
        long sessionMinutes = (duration % 3600) / 60;
        long sessionSeconds = duration % 60;

        long dayHours = dayTime / 3600;
        long dayMinutes = (dayTime % 3600) / 60;
        long daySeconds = dayTime % 60;

        long monthHours = monthTime / 3600;
        long monthMinutes = (monthTime % 3600) / 60;
        long monthSeconds = monthTime % 60;

        System.out.println("\n");
        System.out.print("""
                =========================================
                Vim uptime:                
                -----------------------------------------
                """);
        System.out.printf( "  - per session:        %2d h %2d min %2d sec\n",
                sessionHours, sessionMinutes, sessionSeconds);
        System.out.printf( "  - per day:            %2d h %2d min %2d sec\n",
                dayHours, dayMinutes, daySeconds);
        if(monthTime > yesterdayTime || dayTime > yesterdayTime) {
            System.out.printf(Colors.GREEN.apply(
                        "  - average per month:  %2d h %2d min %2d sec\n"),
                    monthHours, monthMinutes, monthSeconds);
        }
        else {
            System.out.printf(Colors.RED.apply(
                        "  - average per month:  %2d h %2d min %2d sec\n"),
                    monthHours, monthMinutes, monthSeconds);
        }

        System.out.println("  =========================================");

        sessionTimeStats.deleteFile();


        GitStats gitStats = createGitStats(); 
        long savedDailyCommits = gitStats.readLong();

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

