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
 * @version 0.8.34
 * @since 24.02.2026
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

  private static TimeStats sessionTimeStats;
  private static TimeStats dayTimeStats;
  private static TimeStats monthTimeStats;
  private static TimeStats yesterdayTimeStats;

  private static GitStats gitStats;
  private static GitStats todayAddedLinesGitStats;
  private static GitStats todayDeletedLinesGitStats;

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
    gitStats = new GitStats(GIT_HASH_PATH, GIT_DAY_COMMIT_PATH);
    todayAddedLinesGitStats = new GitStats(GIT_HASH_PATH, GIT_DAY_ADDED_LINES_PATH);
    todayDeletedLinesGitStats = new GitStats(GIT_HASH_PATH, GIT_DAY_DELETED_LINES_PATH);
  }

  /** Creates and configures TimeStats instances. */
  private static void initTimeStatsInstances() {
    sessionTimeStats = new TimeStats(TIME_SESSION_PATH);
    dayTimeStats = new TimeStats(TIME_DAY_PATH);
    monthTimeStats = new TimeStats(TIME_MONTH_PATH);
    yesterdayTimeStats = new TimeStats(TIME_YESTERDAY_PATH);
  }

  /** Starts a new stats session. */
  public static void start() {
    initTimeStatsInstances();
    initGitStatsInstances();

    LocalDate today = LocalDate.now();

    if (!gitStats.isFileExists(GIT_HASH_PATH)) {
      gitStats.writeStringValue("");
    }

    if (!gitStats.isFileExists(GIT_DAY_COMMIT_PATH)
        || !today.equals(gitStats.getFileDate(GIT_DAY_COMMIT_PATH))) {
      gitStats.writeLongValue(0L);
    }

    if (!todayAddedLinesGitStats.isFileExists(GIT_DAY_ADDED_LINES_PATH)
        || !today.equals(todayAddedLinesGitStats.getFileDate(GIT_DAY_ADDED_LINES_PATH))) {
      todayAddedLinesGitStats.writeLongValue(0L);
    }

    if (!todayDeletedLinesGitStats.isFileExists(GIT_DAY_DELETED_LINES_PATH)
        || !today.equals(todayDeletedLinesGitStats.getFileDate(GIT_DAY_DELETED_LINES_PATH))) {
      todayDeletedLinesGitStats.writeLongValue(0L);
    }

    if (sessionTimeStats.isFileExists(TIME_SESSION_PATH)) {
      long pastDuration = sessionTimeStats.getSessionTime();
      long dayTime = dayTimeStats.readLongValue();
      dayTimeStats.writeLongValue(dayTime + pastDuration);
    }
    sessionTimeStats.writeLongValue(System.currentTimeMillis() / 1000);

    if (!yesterdayTimeStats.isFileExists(TIME_YESTERDAY_PATH)) {
      yesterdayTimeStats.writeLongValue(0L);
    }

    if (!monthTimeStats.isFileExists(TIME_MONTH_PATH)) {
      monthTimeStats.writeLongValue(0L);
    }

    if (!monthTimeStats.getFileDate(TIME_MONTH_PATH).equals(today)) {
      long yesterdayTime = monthTimeStats.readLongValue();
      yesterdayTimeStats.writeLongValue(yesterdayTime);

      long emptyDays = ChronoUnit.DAYS.between(monthTimeStats.getFileDate(TIME_MONTH_PATH), today);
      long monthTime = monthTimeStats.readLongValue() * (30 - emptyDays);
      monthTimeStats.writeLongValue((monthTime + dayTimeStats.readLongValue()) / 30);
    }

    if (!dayTimeStats.isFileExists(TIME_DAY_PATH)
        || !dayTimeStats.getFileDate(TIME_DAY_PATH).equals(today)) {
      dayTimeStats.writeLongValue(0L);
    }
  }

  /*
   * Update stats
   */
  public static void update() {
    initGitStatsInstances();

    String savedHash = gitStats.readStringValue();
    String lastHash = gitStats.getLastCommitHash();
    long lastCommitAddedLines = todayAddedLinesGitStats.getLastCommitLines("added");
    long lastCommitDeletedLines = todayDeletedLinesGitStats.getLastCommitLines("deleted");
    System.out.println("lastCommitDeletedLines = " + lastCommitDeletedLines);

    if (!lastHash.equals(savedHash)) {
      long savedDailyCommits = gitStats.readLongValue();
      gitStats.writeLongValue(savedDailyCommits + 1L);

      long savedDailyCommitAddedLines = todayAddedLinesGitStats.readLongValue();
      todayAddedLinesGitStats.writeLongValue(savedDailyCommitAddedLines + lastCommitAddedLines);

      long savedDailyCommitDeletedLines = todayDeletedLinesGitStats.readLongValue();
      todayDeletedLinesGitStats.writeLongValue(
          savedDailyCommitDeletedLines + lastCommitDeletedLines);
    }

    gitStats.writeStringValue(lastHash);
  }

  /*
   * Print stats
   */
  public static void stop() {
    initGitStatsInstances();
    initTimeStatsInstances();

    long duration = sessionTimeStats.getSessionTime();

    if (!dayTimeStats.isFileExists(TIME_DAY_PATH)) {
      dayTimeStats.writeLongValue(0L);
    }

    long dayTime = dayTimeStats.readLongValue() + duration;

    dayTimeStats.writeLongValue(dayTime);

    if (!monthTimeStats.isFileExists(TIME_MONTH_PATH)) {
      monthTimeStats.writeLongValue(0L);
    }

    long monthTime = monthTimeStats.readLongValue();
    long yesterdayTime = yesterdayTimeStats.readLongValue();

    long sessionHours = duration / 3600;
    long sessionMinutes = (duration % 3600) / 60;
    long sessionSeconds = duration % 60;

    long dayHours = dayTime / 3600;
    long dayMinutes = (dayTime % 3600) / 60;
    long daySeconds = dayTime % 60;

    long monthHours = monthTime / 3600;
    long monthMinutes = (monthTime % 3600) / 60;
    long monthSeconds = monthTime % 60;

    System.out.printf(
        """

                    =========================================
                                Vim uptime:
                    -----------------------------------------
                    - per session:        %2d h %2d min %2d sec
                    - per day:            %2d h %2d min %2d sec
                """,
        sessionHours, sessionMinutes, sessionSeconds, dayHours, dayMinutes, daySeconds);

    if (monthTime > yesterdayTime || dayTime > yesterdayTime) {
      System.out.printf(
          Colors.GREEN.apply("    - average per month:  %2d h %2d min %2d sec%n"),
          monthHours,
          monthMinutes,
          monthSeconds);
    } else {
      System.out.printf(
          Colors.RED.apply("    - average per month:  %2d h %2d min %2d sec%n"),
          monthHours,
          monthMinutes,
          monthSeconds);
    }

    sessionTimeStats.deleteFile();

    long savedDailyCommits = gitStats.readLongValue();
    long savedDailyCommitAddedLines = todayAddedLinesGitStats.readLongValue();
    long savedDailyCommitDeletedLines = todayDeletedLinesGitStats.readLongValue();

    System.out.printf(
        "    - today commits: %d lines: "
            + Colors.GREEN.apply("%+4d ")
            + Colors.RED.apply(" %+4d ")
            + "%n",
        savedDailyCommits,
        savedDailyCommitAddedLines,
        0 - savedDailyCommitDeletedLines);
    System.out.println("    =========================================");
  }
}
