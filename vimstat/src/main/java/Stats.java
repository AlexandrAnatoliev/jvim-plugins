import java.io.*;
import java.time.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public abstract class Stats {
    protected String pathToLong;

    public Stats (String pathToLong) {
        this.pathToLong = pathToLong;
    }

    /**
     * Writes long value to temporary file 
     *
     * @param  value Long value to write to file
     */
    public void writeLong(Long value) {
        try (FileWriter writer = new FileWriter(pathToLong)) {
            writer.write(value.toString());
        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " Writing long: "
                    + e.getMessage());
        }
    }

    /**
     * Reads long value from temporary file 
     *
     * @return  long value from file, 
     *          or 0 if file does not exist or contains invalid data
     */
    public long readLong() {
        try (BufferedReader reader = new BufferedReader(
                    new FileReader(this.pathToLong))) {

            return Long.parseLong(reader.readLine());

        } catch (Exception e) {
            System.out.println(
                    Colors.RED.apply("[ERROR]")
                    + " Reading long: "
                    + e.getMessage());
            return 0;
        }
    }

    /**
     * Retrieves the creation date of the file
     *
     * @param   pathToFile  Path to temporary file
     * @return  LocalDate   Representing file creation time
     */
    public LocalDate getFileDate(String pathToFile) {
        File file = new File(pathToFile);
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
                    + " Get file date checking: "
                    + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if temporary file is exist 
     * 
     * @param   pathToFile  Path to temporary file
     * @return  true        If file is exist
     *          false       If file is not exist
     */
    public boolean isFileExists(String pathToFile) {
        File file = new File(pathToFile);
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
}
