import java.io.*;

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
}
