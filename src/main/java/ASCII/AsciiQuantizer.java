package ASCII;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * AsciiQuantizer reads visible ASCII characters from a text file,
 * generates grayscale brightness values for each character,
 * normalizes them based on global min/max brightness,
 * creates quantized buckets, and selects representative characters per bucket.
 */
public class AsciiQuantizer {
    private static double minBrightness = Double.MAX_VALUE;
    private static double maxBrightness = Double.MIN_VALUE;

    /**
     * Reads all visible characters from a text file, removing duplicates.
     *
     * @param filePath path to the ASCII table file
     * @return list of unique visible ASCII characters
     */
    public static List<Character> readCharactersFromFile(String filePath) {
        Set<Character> uniqueChars = new LinkedHashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;
                
                if (!Character.isISOControl(c)) {
                    uniqueChars.add(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(uniqueChars);
    }

    /**
     * Renders a black-background image with a white character centered.
     * @param c character to render
     * @param width image width in pixels
     * @param height image height in pixels
     * @return BufferedImage containing the rendered character
     */
    public static BufferedImage renderCharacterImage(char c, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int fontSize = (int) (height * 0.75);
        
        g.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
        g.setColor(Color.WHITE);

        FontMetrics metrics = g.getFontMetrics();
        
        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getAscent();
        int x = (width - charWidth) / 2;
        int y = (height - metrics.getHeight()) / 2 + charHeight;

        g.drawString(String.valueOf(c), x, y);
        g.dispose();
        
        return img;
    }

    /**
     * Calculates the average brightness (HSB) of an image, normalized [0,1].
     * @param image grayscale or colored image
     * @return normalized brightness value
     */
    public static double calculateNormalizedBrightness(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double sum = 0;
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                
                float[] hsb = Color.RGBtoHSB(r, g, b, null);
                
                sum += hsb[2];
            }
        }
        
        return sum / (width * height);
    }

    /**
     * Computes the average brightness across three image sizes.
     * @param c character to measure
     * @return average normalized brightness
     */
    public static double calculateAverageBrightness(char c) {
        int[][] sizes = {{8, 8}, {16, 16}, {32, 32}};
        double total = 0;
        
        for (int[] size : sizes) {
            BufferedImage img = renderCharacterImage(c, size[0], size[1]);
            total += calculateNormalizedBrightness(img);
        }
        
        return total / sizes.length;
    }

    /**
     * Builds a map of character to its average brightness and updates global min/max.
     * @param chars list of visible ASCII characters
     * @return map from character to raw brightness
     */
    public static Map<Character, Double> computeBrightnessMap(List<Character> chars) {
        Map<Character, Double> brightnessMap = new HashMap<>();
        
        for (char c : chars) {
            double brightness = calculateAverageBrightness(c);
            
            brightnessMap.put(c, brightness);
            
            minBrightness = Math.min(minBrightness, brightness);
            maxBrightness = Math.max(maxBrightness, brightness);
        }
        
        return brightnessMap;
    }

    /**
     * Normalizes a brightness value to [0,1] using global min/max.
     * @param value raw brightness
     * @return normalized brightness
     */
    public static double normalize(double value) {
        if (maxBrightness == minBrightness) {
            return 0;
        }
        
        return (value - minBrightness) / (maxBrightness - minBrightness);
    }

    /**
     * Creates quantized buckets of characters based on brightness.
     * @param chars list of visible ASCII characters
     * @param bits number of bits for quantization
     * @return map from bucket index to sorted list of characters
     */
    public static Map<Integer, List<Character>> buildQuantBuckets(List<Character> chars, int bits) {
        int levels = 1 << bits;
        
        Map<Character, Double> brightnessMap = computeBrightnessMap(chars);
        Map<Integer, List<Character>> buckets = new HashMap<>();
        
        for (int i = 0; i < levels; i++) {
            buckets.put(i, new ArrayList<>());
        }
        
        for (char c : chars) {
            double raw = brightnessMap.get(c);
            double norm = normalize(raw);
            
            int index = (int) Math.floor(norm * levels);
            
            if (index >= levels) {
                index = levels - 1;
            }
            
            buckets.get(index).add(c);
        }
        
        for (List<Character> list : buckets.values()) {
            list.sort(Comparator.comparingDouble(brightnessMap::get));
        }
        
        return buckets;
    }

    /**
     * Extracts the first character (darkest) from each non-empty quantized
     * bucket.
     *
     * @param buckets quantized character buckets
     * @return array of representative characters per used bucket
     */
    public static char[] extractFirstChars(Map<Integer, List<Character>> buckets) {
        List<Character> resultList = new ArrayList<>();

        for (int i = 0; i < buckets.size(); i++) {
            List<Character> bucket = buckets.get(i);
            if (bucket != null && !bucket.isEmpty()) {
                resultList.add(bucket.get(0));
            }
        }

        char[] result = new char[resultList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }
    
    /**
     * Extracts the last character (brightest) from each non-empty quantized
     * bucket.
     *
     * @param buckets quantized character buckets
     * @return array of representative characters per used bucket
     */
    public static char[] extractLastChars(Map<Integer, List<Character>> buckets) {
        List<Character> resultList = new ArrayList<>();

        for (int i = 0; i < buckets.size(); i++) {
            List<Character> bucket = buckets.get(i);
            if (bucket != null && !bucket.isEmpty()) {
                resultList.add(bucket.get(bucket.size() - 1));
            }
        }

        char[] result = new char[resultList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = resultList.get(i);
        }

        return result;
    }

    public static void main(String[] args) {
        List<Character> chars = readCharactersFromFile("visible_ascii_table.txt");
        int bits = 8;
        Map<Integer, List<Character>> buckets = buildQuantBuckets(chars, bits);

        System.out.printf("Min bright = %.4f, Max bright = %.4f%n", minBrightness, maxBrightness);
        
        for (int i = 0; i < (1 << bits); i++) {
            double start = i / (double) (1 << bits);
            double end = (i + 1) / (double) (1 << bits);
            System.out.printf("Bucket %3d [%.2f–%.2f): %s%n", i, start, end, buckets.get(i));
        }

        char[] representatives = extractFirstChars(buckets);
        
        System.out.print("Floor brightnesss characters:\n");
        System.out.println(representatives);
        
        representatives = extractLastChars(buckets);
        
        System.out.print("Ceil brightness characters:\n");
        System.out.println(representatives);
    }
}