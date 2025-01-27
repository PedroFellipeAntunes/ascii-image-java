package Operation;

import ImageData.Pixel;
import java.awt.Color;
import java.util.ArrayList;

public class AsciiMapper {
    public char[][] map(ArrayList<ArrayList<Pixel>> image, char[] ascii, int scale, boolean invert) {
        if (scale <= 0) {
            return null;
        }
        
        char[][] asciiImage = new char[image.size() / scale][image.get(0).size() / scale];
        
        for (int y = 0; y < image.size() / scale; y++) {
            for (int x = 0; x < image.get(0).size() / scale; x++) {
                Pixel pixel = image.get(y * scale).get(x * scale);
                
                // Get luminance
                float[] hsb = Color.RGBtoHSB(pixel.getRed(), pixel.getGreen(), pixel.getBlue(), null);
                
                // Map to vector
                int index = (int) (hsb[2] * (ascii.length - 1));
                
                if (invert) {
                    index = ascii.length - 1 - index;
                }
                
                asciiImage[y][x] = ascii[index];
            }
        }
        
        return asciiImage;
    }
}