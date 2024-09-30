package Operation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StringSortedAscii {
    private final int size = 100;
    private String font;
    private int style;
    
    public char[] stringToSortedCharArray(String input, String font, int style) {
        this.font = font;
        this.style = style;
        
        //String to stream of int
        //Converts int value to distinct char Object
        //Sorts it
        //Collects into the List of type Character
        List<Character> charsSorted = input.chars()
                                           .mapToObj(c -> (char) c)
                                           .distinct()
                                           .sorted(Comparator.comparingDouble(this::getBrightSum))
                                           .collect(Collectors.toList());
        
        //Convert to array
        char[] sortedArray = new char[charsSorted.size()];
        
        for (int i = 0; i < charsSorted.size(); i++) {
            sortedArray[i] = charsSorted.get(i);
        }
        
        return sortedArray;
    }
    
    private double getBrightSum(char c) {
        //Generate small grayscale image
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = img.createGraphics();
        
        //Draw the character in this image
        g.setFont(new Font(font, style, size));
        g.drawString(String.valueOf(c), 0, size);
        
        g.dispose();
        
        //Go through the image and get the sum of the brightness
        float[] hsbValues = new float[3];
        double brightnessSum = 0;
        
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int rgb = img.getRGB(x, y);
                Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, hsbValues);
                
                brightnessSum += hsbValues[2];
            }
        }
        
        return brightnessSum;
    }
}