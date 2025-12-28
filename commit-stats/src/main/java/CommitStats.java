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
    public String getLastCommitHash() throws Exception {
        try {
            Process p = Runtime.getRuntime().exec("git rev-parse HEAD");
            return new Scanner(p.getInputStream()).next();
        } catch (Exception e) {
            System.out.println("ERROR: get last commit hash + e");
            return "";
        }
    }
}
