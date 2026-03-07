package com.vimstat;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Vim utility to get Vim stats
 *
 * <p>When ended work with Vim, print stats
 *
 * <p>Usage: java Main start - erases information from temporary files, and starts to calculate
 * stats java Main update - update stats java Main stop - print stats
 *
 * @version 0.8.43
 * @since 07.03.2026
 * @author AlexandrAnatoliev
 */
public class Main {
  private static final String HOME_DIR = System.getProperty("user.home");

  private static final String TIME_SESSION_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_session.txt";
  private static final String TIME_DAY_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_day.txt";
  private static final String TIME_MONTH_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_month.txt";
  private static final String TIME_YESTERDAY_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_yesterday.txt";

  private static final String GIT_HASH_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_hash.txt";
  private static final String GIT_DAY_COMMIT_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_day_commit.txt";
  private static final String GIT_DAY_ADDED_LINES_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_day_added_lines.txt";
  private static final String GIT_DAY_DELETED_LINES_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_day_deleted_lines.txt";

  private static final String GIT_AVERAGE_COMMIT_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_average_commit.txt";
  private static final String GIT_AVERAGE_ADDED_LINES_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_average_added_lines.txt";
  private static final String GIT_AVERAGE_DELETED_LINES_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_average_deleted_lines.txt";

  private static TimeStats sessionTimeStats;
  private static TimeStats dayTimeStats;
  private static TimeStats monthTimeStats;
  private static TimeStats yesterdayMonthValueTimeStats;

  private static GitStats dayGitStats;
  private static GitStats dayAddedLinesGitStats;
  private static GitStats dayDeletedLinesGitStats;

  private static GitStats averageCommitGitStats;
  private static GitStats averageAddedLinesGitStats;
  private static GitStats averageDeletedLinesGitStats;

  private Main() {}

  /**
   * Main entry point for the vimstats application
   *
   * @param args command line arguments - first argument determines operation mode "start" To begin
   *     new work session "update" To update stats "stop" Or any other argument to print stats
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

  /** Creates and configures GitStats instances. */
  private static void initGitStatsInstances() {
    dayGitStats = new GitStats(GIT_HASH_PATH, GIT_DAY_COMMIT_PATH);
    dayAddedLinesGitStats = new GitStats(GIT_HASH_PATH, GIT_DAY_ADDED_LINES_PATH);
    dayDeletedLinesGitStats = new GitStats(GIT_HASH_PATH, GIT_DAY_DELETED_LINES_PATH);

    averageCommitGitStats = new GitStats(GIT_HASH_PATH, GIT_AVERAGE_COMMIT_PATH);
    averageAddedLinesGitStats = new GitStats(GIT_HASH_PATH, GIT_AVERAGE_ADDED_LINES_PATH);
    averageDeletedLinesGitStats = new GitStats(GIT_HASH_PATH, GIT_AVERAGE_DELETED_LINES_PATH);
  }

  /** Creates and configures TimeStats instances. */
  private static void initTimeStatsInstances() {
    sessionTimeStats = new TimeStats(TIME_SESSION_PATH);
    dayTimeStats = new TimeStats(TIME_DAY_PATH);
    monthTimeStats = new TimeStats(TIME_MONTH_PATH);
    yesterdayMonthValueTimeStats = new TimeStats(TIME_YESTERDAY_PATH);
  }

  /**
   * Update average value and write in file
   *
   * @param Stats instance
   */
  private static void updateAverageValue(Stats noTodayInstance, Stats averageInstance) {
    LocalDate today = LocalDate.now();
    LocalDate noToday = noTodayInstance.getFileDate(noTodayInstance.pathToCounter);

    long averageValue = averageInstance.readCount();
    long noTodayValue = noTodayInstance.readCount();
    long emptyDays = ChronoUnit.DAYS.between(noToday, today);

    averageInstance.write(averageValue - (averageValue * emptyDays) / 30 + noTodayValue);
  }

  /** Starts a new stats session. */
  public static void start() {
    initTimeStatsInstances();
    initGitStatsInstances();

    /* Create files if does not exist */
    dayGitStats.createFiles();
    dayAddedLinesGitStats.createFiles();
    dayDeletedLinesGitStats.createFiles();
    averageCommitGitStats.createFiles();
    averageAddedLinesGitStats.createFiles();
    averageDeletedLinesGitStats.createFiles();
    yesterdayMonthValueTimeStats.createFiles();
    monthTimeStats.createFiles();
    dayTimeStats.createFiles();

    /* Create session file */
    if (sessionTimeStats.isFileExists(TIME_SESSION_PATH)) {
      long pastDuration = sessionTimeStats.getSessionTime();
      long dayTime = dayTimeStats.readCount();
      dayTimeStats.write(dayTime + pastDuration);
    }
    sessionTimeStats.write(System.currentTimeMillis() / 1000);

    LocalDate today = LocalDate.now();
    /* if new day */
    if (!today.equals(dayGitStats.getFileDate(GIT_DAY_COMMIT_PATH))) {
      /* Save yesterday month value */
      yesterdayMonthValueTimeStats.write(monthTimeStats.readCount());
      System.out.println("yesterdayMonthValueTimeStats.write(" + monthTimeStats.readCount() + ")");

      /* Update average counters */
      updateAverageValue(dayGitStats, averageCommitGitStats);
      updateAverageValue(dayAddedLinesGitStats, averageAddedLinesGitStats);
      updateAverageValue(dayDeletedLinesGitStats, averageDeletedLinesGitStats);
      updateAverageValue(dayTimeStats, monthTimeStats);

      /* Reset counts */
      dayGitStats.write(0L);
      dayAddedLinesGitStats.write(0L);
      dayDeletedLinesGitStats.write(0L);
      dayTimeStats.write(0L);
    }
  }

  /*
   * Update stats
   */
  public static void update() {
    initGitStatsInstances();

    String savedHash = dayGitStats.readHash();
    String lastHash = dayGitStats.getLastCommitHash();
    long lastCommitAddedLines = dayAddedLinesGitStats.getLastCommitLines("added");
    long lastCommitDeletedLines = dayDeletedLinesGitStats.getLastCommitLines("deleted");

    if (!lastHash.equals(savedHash)) {
      long savedDailyCommits = dayGitStats.readCount();
      dayGitStats.write(savedDailyCommits + 1L);

      long savedDailyCommitAddedLines = dayAddedLinesGitStats.readCount();
      dayAddedLinesGitStats.write(savedDailyCommitAddedLines + lastCommitAddedLines);

      long savedDailyCommitDeletedLines = dayDeletedLinesGitStats.readCount();
      dayDeletedLinesGitStats.write(savedDailyCommitDeletedLines + lastCommitDeletedLines);
    }

    dayGitStats.write(lastHash);
  }

  /*
   * Print stats
   */
  public static void stop() {
    initGitStatsInstances();
    initTimeStatsInstances();

    long duration = sessionTimeStats.getSessionTime();
    long dayTime = dayTimeStats.readCount() + duration;

    dayTimeStats.write(dayTime);

    long monthTime = monthTimeStats.readCount();
    long yesterdayMonthValueTime = yesterdayMonthValueTimeStats.readCount();

    long dayHours = dayTime / 3600;
    long dayMinutes = (dayTime % 3600) / 60;
    long daySeconds = dayTime % 60;

    long monthHours = monthTime / 3600;
    long monthMinutes = (monthTime % 3600) / 60;
    long monthSeconds = monthTime % 60;

    System.out.printf(
        """

                    =======================================
                                    Vim stats:
                    ---------------------------------------
                    - today:            %2d h %2d min %2d sec
                """,
        dayHours, dayMinutes, daySeconds);

    if (monthTime > yesterdayMonthValueTime || dayTime > yesterdayMonthValueTime) {
      System.out.printf(
          Colors.GREEN.apply("    - average:          %2d h %2d min %2d sec%n"),
          monthHours,
          monthMinutes,
          monthSeconds);
    } else {
      System.out.printf(
          Colors.RED.apply("    - average:          %2d h %2d min %2d sec%n"),
          monthHours,
          monthMinutes,
          monthSeconds);
    }

    sessionTimeStats.deleteFile();

    long savedDailyCommits = dayGitStats.readCount();
    long savedDailyCommitAddedLines = dayAddedLinesGitStats.readCount();
    long savedDailyCommitDeletedLines = dayDeletedLinesGitStats.readCount();

    long averageCommits = averageCommitGitStats.readCount();
    long averageAddedLines = averageAddedLinesGitStats.readCount();
    long averageDeletedLines = averageDeletedLinesGitStats.readCount();

    String dailyFormat =
        "    - today:   %2d commits "
            + Colors.GREEN.apply("%5d++ ")
            + Colors.RED.apply(" %5d-- ")
            + "%n";
    String averageFormat =
        "    - average: %2d commits "
            + Colors.GREEN.apply("%5d++ ")
            + Colors.RED.apply(" %5d-- ")
            + "%n";
    System.out.printf(
        dailyFormat, savedDailyCommits, savedDailyCommitAddedLines, savedDailyCommitDeletedLines);
    System.out.printf(
        averageFormat, averageCommits / 30, averageAddedLines / 30, averageDeletedLines / 30);
    System.out.println("    =======================================");
  }
}
