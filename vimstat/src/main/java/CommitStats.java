import java.util.Scanner;
import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
 * The class to get git stats 
 *
 * @version  0.8.7
 * @since    01.02.2026
 * @author   AlexandrAnatoliev
 */
public class CommitStats extends Stats {
    protected String pathToString;

    /**
     * CommitStats class constructor
     *
     * @param  pathToString Path to temporary file 
     *                              for last commit hash storage
     */
    public CommitStats(
            String pathToString,
            String pathToLong) {
        super(pathToLong);
        this.pathToString = pathToString;
            }

    /**
     * Get last commit hash
     * 
     * @return Last commit hash, or "" if error
     */
    public String getLastCommitHash() {
        ProcessBuilder pb = new ProcessBuilder("git", "rev-parse", "HEAD");
        try {
            Process p = pb.start();
            p.waitFor();
            try (Scanner scanner = new Scanner(p.getInputStream())) {
                return scanner.hasNext() ? scanner.next() : "";
            }
        } catch (Exception e) {
            System.out.println(Colors.RED.apply("[ERROR]") 
                    + " get last commit hash: " 
                    + e.getMessage());
            return "";
        }
    }

    /**
     * Writes hash to a temporary file
     *
     * @param hash Hash to write to the file
     */
    public void writeHashToFile(String hash) {
        try {
            String content = (hash == null) ? "" : hash;
            Files.writeString(Paths.get(pathToString), content);
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " writing hash: "
                    + e.getMessage());
        }
    }

    /**
     * Reads hash value from temporary file 
     *
     * @return String Hash value from file 
     */
    public String readHashFromFile() {
        try {
            return Files.readString(Paths.get(this.pathToString));
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " reading hash: "
                    + e.getMessage());
            return "";
        }
    }

    /**
     * Writes daily commits value to temporary file 
     *
     * @param  value Daily commits value to write to file
     */
    public void writeDailyCommitsToFile(Long value) {
        try (FileWriter writer = new FileWriter(pathToLong)) {
            writer.write(value.toString());
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " writing daily commits: "
                    + e.getMessage());
        }
    }

    /**
     * Reads daily commits value from temporary file 
     *
     * @return  daily commits value from file, 
     *          or 0 if file does not exist or contains invalid data
     */
    public long readDailyCommitsFromFile() {
        try (BufferedReader reader = new BufferedReader(
                    new FileReader(this.pathToLong))) {
            long value = Long.parseLong(reader.readLine());
            return value;
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " reading daily commits: "
                    + e.getMessage());
        }
        return 0;
    }

    /**
     * Checks if temporary file is exist 
     * 
     * @param   pathToFile  Path to temporary file
     * @return  true        If file is exist
     *          false       If file is not exist
     */
    public boolean isFileExists(String pathToFile) {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + pathToFile);
        try {
            return file.exists();
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " file existing checking: "
                    + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the creation date of the file
     *
     * @param   pathToFile  Path to temporary file
     * @return  LocalDate   Representing file creation time
     */
    public LocalDate getFileDate(String pathToFile) {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + pathToFile);
        try {
            BasicFileAttributes attrs = Files.readAttributes(
                    file.toPath(), 
                    BasicFileAttributes.class);
            LocalDate fileDate = attrs.creationTime().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
            return fileDate;
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " get file date checking: "
                    + e.getMessage());
        }
        return null;
    }
}
