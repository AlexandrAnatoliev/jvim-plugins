import java.util.Scanner;
import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

/**
 * The class to get git stats 
 *
 * @version  0.8.7
 * @since    02.02.2026
 * @author   AlexandrAnatoliev
 */
public class GitStats extends Stats {
    protected String pathToString;

    /**
     * GitStats class constructor
     *
     * @param  pathToString Path to temporary file 
     *                              for last commit hash storage
     */
    public GitStats(
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
}
