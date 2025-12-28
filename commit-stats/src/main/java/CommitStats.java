import java.util.Scanner;

/**
 * The class to get git stats 
 *
 * @version  0.7.1
 * @since    28.12.2025
 * @author   AlexandrAnatoliev
 */
public class CommitStats {
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
}
