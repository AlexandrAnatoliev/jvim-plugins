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
                    + " writing daily commits: "
                    + e.getMessage());
        }
    }
}
