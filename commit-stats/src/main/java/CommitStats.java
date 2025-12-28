import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    * @throws Exception If is error 
    * @return Last commit hash, or "" if error
    */
    public String getLastCommitHash() throws Exception {
        Process p = Runtime.getRuntime().exec("git rev-parse HEAD");
        Scanner s = new Scanner(p.getInputStream());
        return s.hasNext() ? s.next() : "";
    }
}
