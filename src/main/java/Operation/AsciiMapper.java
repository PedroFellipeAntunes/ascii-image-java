package Operation;

import ImageData.Pixel;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

public class AsciiMapper {
    public char[][] map(ArrayList<ArrayList<Pixel>> image, char[] ascii, int scale) {
        if (scale <= 0) {
            return null;
        }
        
        char[][] asciiImage = new char[image.size() / scale][image.get(0).size() / scale];
        
        //StringBuilder copy = new StringBuilder();
        
        for (int y = 0; y < image.size() / scale; y++) {
            for (int x = 0; x < image.get(0).size() / scale; x++) {
                Pixel pixel = image.get(y * scale).get(x * scale);
                
                // Get luminance
                float[] hsb = Color.RGBtoHSB(pixel.getRed(), pixel.getGreen(), pixel.getBlue(), null);
                
                // Map to vector
                int index = (int) (hsb[2] * ascii.length);
                
                if (index >= ascii.length) {
                    index = ascii.length - 1;
                }
                
                asciiImage[y][x] = ascii[index];
                
                //System.out.print(ascii[index] + " ");
                //copy.append(ascii[index]);
                //copy.append(" ");
            }
            
            //System.out.println();
            //copy.append("\n");
        }
        
        //StringSelection stringSelection = new StringSelection(copy.toString());
        //Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        //System.out.println("COPIED TO CTRL+V");
        
        return asciiImage;
    }
}