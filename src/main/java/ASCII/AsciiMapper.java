package ASCII;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class AsciiMapper {

    public char[][] map(BufferedImage image, char[] ascii, int scale, boolean invert) {
        if (scale <= 0) {
            return null;
        }

        int height = (image.getHeight() + scale - 1) / scale;
        int width = (image.getWidth() + scale - 1) / scale;
        
        char[][] asciiImage = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelY = y * scale;
                int pixelX = x * scale;

                if (pixelY >= image.getHeight() || pixelX >= image.getWidth()) {
                    asciiImage[y][x] = ' '; // fora da imagem
                    continue;
                }

                Color c = new Color(image.getRGB(pixelX, pixelY));
                
                float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);

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