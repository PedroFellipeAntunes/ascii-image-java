package ASCII;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VisibleAsciiGenerator {
    public static void main(String[] args) {
        String fileName = "visible_ascii_table.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 32; i <= 126; i++) {
                char c = (char) i;
                writer.write(String.format("%3d: %s%n", i, c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}