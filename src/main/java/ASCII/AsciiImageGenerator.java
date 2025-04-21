package ASCII;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class AsciiImageGenerator {
    public BufferedImage generateImage(BufferedImage image, char[][] asciiMatrix, char[][] asciiEdges, int scale, String fontName, int style, boolean color) {
        int cellWidth = scale;
        int cellHeight = scale;

        int width = cellWidth * asciiMatrix[0].length;
        int height = cellHeight * asciiMatrix.length;

        BufferedImage finalImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = finalImage.getGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        g.setFont(new Font(fontName, style, cellHeight));

        FontMetrics metrics = g.getFontMetrics();

        for (int y = 0; y < asciiMatrix.length; y++) {
            for (int x = 0; x < asciiMatrix[y].length; x++) {
                int newX = x * cellWidth;
                int newY = y * cellHeight;

                int offsetX = (cellWidth - metrics.charWidth(asciiMatrix[y][x])) / 2;
                int offsetY = ((cellHeight - metrics.getHeight()) / 2) + metrics.getAscent();

                int charX = newX + offsetX;
                int charY = newY + offsetY;

                char character = asciiEdges[y][x] == ' ' ? asciiMatrix[y][x] : asciiEdges[y][x];

                if (color) {
                    int pixelY = Math.min(newY, image.getHeight() - 1);
                    int pixelX = Math.min(newX, image.getWidth() - 1);
                    
                    g.setColor(new Color(image.getRGB(pixelX, pixelY)));
//                    g.setColor(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()).brighter());
                }

                g.drawString(String.valueOf(character), charX, charY);
            }
        }

        g.dispose();
        
        return finalImage;
    }
}