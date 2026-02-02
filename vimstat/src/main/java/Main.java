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
        String homeDir = System.getProperty("user.home");
        CommitStats commitStats = createCommitStats();
        LocalDate today = LocalDate.now();

        if (!commitStats.isFileExists(homeDir + PATH_TO_DAILY_COMMITS)
                || !today.equals(commitStats.getFileDate(homeDir + PATH_TO_DAILY_COMMITS))) {
            commitStats.writeLong(0L);
                }

        if (!commitStats.isFileExists(homeDir + PATH_TO_LAST_COMMIT_HASH)) {
            commitStats.writeHashToFile("");
        }

        String pathToDayTime = homeDir + DAY_FILE_PATH;
        String pathToMonthTime = homeDir + MONTH_FILE_PATH;
        String pathToYesterdayTime = homeDir + YESTERDAY_FILE_PATH;

        Timer sessionTimer = new Timer(homeDir + SESSION_FILE_PATH);
        Timer dayTimer = new Timer(pathToDayTime);
        Timer monthTimer = new Timer(pathToMonthTime);
        Timer yesterdayTimer = new Timer(pathToYesterdayTime);

        if(sessionTimer.isFileExists(homeDir + SESSION_FILE_PATH)) {
            long pastDuration = sessionTimer.getSessionTime();
            long dayTime = dayTimer.readLong();
            dayTimer.writeLong(dayTime + pastDuration);
        }
        sessionTimer.writeLong(System.currentTimeMillis() / 1000);

        if(!yesterdayTimer.isFileExists(pathToYesterdayTime)) {
            yesterdayTimer.writeLong(0L);
        }

        if(!monthTimer.isFileExists(pathToMonthTime)) {
            monthTimer.writeLong(0L);
        }

        if(!monthTimer.getFileDate(pathToMonthTime).equals(today)) {
            long yesterdayTime = monthTimer.readLong();
            yesterdayTimer.writeLong(yesterdayTime);

            long emptyDays = ChronoUnit.DAYS.between(
                    monthTimer.getFileDate(pathToMonthTime), today);
            long monthTime = monthTimer.readLong() * (30 - emptyDays);
            monthTimer.writeLong(
                    (monthTime + dayTimer.readLong()) / 30);     
        }

        if(!dayTimer.isFileExists(pathToDayTime) || 
                !dayTimer.getFileDate(pathToDayTime).equals(today)) {
            dayTimer.writeLong(0L);
                }

        return;
    }

    /*
     * Update commit stats
     */
    public static void update() {
        CommitStats commitStats = createCommitStats(); 
        String savedHash = commitStats.readHashFromFile();
        String lastHash = commitStats.getLastCommitHash();

        if (!lastHash.equals(savedHash)) {
            long savedDailyCommits = commitStats.readLong();
            commitStats.writeLong(savedDailyCommits + 1L);
        }

        commitStats.writeHashToFile(lastHash);
    }

    /*
     * Print commit stats
     */
    public static void stop() {
        String homeDir = System.getProperty("user.home");
        String pathToDayTime = homeDir + DAY_FILE_PATH;
        String pathToMonthTime = homeDir + MONTH_FILE_PATH;
        String pathToYesterdayTime = homeDir + YESTERDAY_FILE_PATH;

        Timer sessionTimer = new Timer(homeDir + SESSION_FILE_PATH);
        Timer dayTimer = new Timer(pathToDayTime);
        Timer monthTimer = new Timer(pathToMonthTime);
        Timer yesterdayTimer = new Timer(pathToYesterdayTime);

        long duration = sessionTimer.getSessionTime(); 

        if(!dayTimer.isFileExists(pathToDayTime)) {
            dayTimer.writeLong(0L);
        }

        long dayTime = dayTimer.readLong() + duration;

        dayTimer.writeLong(dayTime);

        if(!monthTimer.isFileExists(pathToMonthTime)) {
            monthTimer.writeLong(0L);
        }
        long monthTime = monthTimer.readLong();

        long yesterdayTime = yesterdayTimer.readLong();

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

        sessionTimer.deleteFile();


        CommitStats commitStats = createCommitStats(); 
        long savedDailyCommits = commitStats.readLong();

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

