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
 * @version 0.8.26
 * @since 15.02.2026
 * @author AlexandrAnatoliev
 */
public class Main {
  private static final String HOME_DIR = System.getProperty("user.home");
  private static final String GIT_HASH_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_hash.txt";
  private static final String GIT_DAY_COMMIT_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/git_day_commit.txt";
  private static final String TIME_SESSION_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_session.txt";
  private static final String TIME_DAY_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_day.txt";
  private static final String TIME_MONTH_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_month.txt";
  private static final String TIME_YESTERDAY_PATH =
      HOME_DIR + "/.vim/pack/my-plugins/start/vimstat/data/time_yesterday.txt";

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

  /**
   * Creates and configures a GitStats interface with standard settings.
   *
   * @return Configured GitStats instance ready for use
   */
  private static GitStats createGitStats() {
    return new GitStats(GIT_HASH_PATH, GIT_DAY_COMMIT_PATH);
  }

  /** Starts a new stats session. */
  public static void start() {
    LocalDate today = LocalDate.now();
    TimeStats sessionTimeStats = new TimeStats(TIME_SESSION_PATH);
    TimeStats dayTimeStats = new TimeStats(TIME_DAY_PATH);
    TimeStats monthTimeStats = new TimeStats(TIME_MONTH_PATH);
    TimeStats yesterdayTimeStats = new TimeStats(TIME_YESTERDAY_PATH);
    GitStats gitStats = createGitStats();

    if (!gitStats.isFileExists(GIT_DAY_COMMIT_PATH)
        || !today.equals(gitStats.getFileDate(GIT_DAY_COMMIT_PATH))) {
      gitStats.writeLong(0L);
    }

    if (!gitStats.isFileExists(GIT_HASH_PATH)) {
      gitStats.writeString("");
    }

    if (sessionTimeStats.isFileExists(TIME_SESSION_PATH)) {
      long pastDuration = sessionTimeStats.getSessionTime();
      long dayTime = dayTimeStats.readLong();
      dayTimeStats.writeLong(dayTime + pastDuration);
    }
    sessionTimeStats.writeLong(System.currentTimeMillis() / 1000);

    if (!yesterdayTimeStats.isFileExists(TIME_YESTERDAY_PATH)) {
      yesterdayTimeStats.writeLong(0L);
    }

    if (!monthTimeStats.isFileExists(TIME_MONTH_PATH)) {
      monthTimeStats.writeLong(0L);
    }

    if (!monthTimeStats.getFileDate(TIME_MONTH_PATH).equals(today)) {
      long yesterdayTime = monthTimeStats.readLong();
      yesterdayTimeStats.writeLong(yesterdayTime);

      long emptyDays = ChronoUnit.DAYS.between(monthTimeStats.getFileDate(TIME_MONTH_PATH), today);
      long monthTime = monthTimeStats.readLong() * (30 - emptyDays);
      monthTimeStats.writeLong((monthTime + dayTimeStats.readLong()) / 30);
    }

    if (!dayTimeStats.isFileExists(TIME_DAY_PATH)
        || !dayTimeStats.getFileDate(TIME_DAY_PATH).equals(today)) {
      dayTimeStats.writeLong(0L);
    }
  }

  /*
   * Update stats
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
   * Print stats
   */
  public static void stop() {
    TimeStats sessionTimeStats = new TimeStats(TIME_SESSION_PATH);
    TimeStats dayTimeStats = new TimeStats(TIME_DAY_PATH);
    TimeStats monthTimeStats = new TimeStats(TIME_MONTH_PATH);
    TimeStats yesterdayTimeStats = new TimeStats(TIME_YESTERDAY_PATH);
    GitStats gitStats = createGitStats();

    long duration = sessionTimeStats.getSessionTime();

    if (!dayTimeStats.isFileExists(TIME_DAY_PATH)) {
      dayTimeStats.writeLong(0L);
    }

    long dayTime = dayTimeStats.readLong() + duration;

    dayTimeStats.writeLong(dayTime);

    if (!monthTimeStats.isFileExists(TIME_MONTH_PATH)) {
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

    long savedDailyCommits = gitStats.readLong();

    System.out.printf(
        """
                    -----------------------------------------
                                Commit stats:
                    -----------------------------------------
                    - Commits per day: %d
                """,
        savedDailyCommits);
    System.out.println("    =========================================");
  }
}
