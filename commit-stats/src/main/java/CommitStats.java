import java.util.Scanner;

/**
 * The class to get git stats 
 *
 * @version  0.7.0
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
        Scanner scanner = null;
        try {
            Process p = Runtime.getRuntime().exec("git rev-parse HEAD");
            p.waitFor();
            scanner = new Scanner(p.getInputStream());
            return scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            System.out.println(Colors.RED.apply("[ERROR]") 
                    + " get last commit hash: " 
                    + e.getMessage());
            return "";
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
