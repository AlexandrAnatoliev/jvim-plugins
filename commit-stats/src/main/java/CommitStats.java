import java.util.Scanner;

/**
 * The class to get git stats 
 *
 * @version  0.7.5
 * @since    01.01.2026
 * @author   AlexandrAnatoliev
 */
public class CommitStats {
    protected String pathToLastCommitHash;

    /**
     * CommitStats class constructor
     *
     * @param  pathToLastCommitHash Path to temporary file 
     *                              for last commit hash storage
     */
    public CommitStats(
            String pathToLastCommitHash) {
        this.pathToLastCommitHash = pathToLastCommitHash;
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
        try (FileWriter writer = new FileWriter(pathToLastCommitHash)) {
            writer.write(hash);
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " writing: "
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
            BufferedReader reader = new BufferedReader(
                    new FileReader(this.pathToLastCommitHash));
            String hash = reader.readLine();
            reader.close();
            return hash;
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " reading: "
                    + e.getMessage());
        }
        return "";
    }
}
