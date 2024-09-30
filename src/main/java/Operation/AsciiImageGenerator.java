package Operation;

import ImageData.Pixel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AsciiImageGenerator {
    public ArrayList<ArrayList<Pixel>> generateImage(ArrayList<ArrayList<Pixel>> image, char[][] asciiMatrix, int scale, String fontName, int style, boolean color) {
        int cellWidth = image.get(0).size() / asciiMatrix[0].length;
        int cellHeight = image.size() / asciiMatrix.length;
        
        BufferedImage bufferedImage = new BufferedImage(image.get(0).size(), image.size(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        
        //Fill black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, image.get(0).size(), image.size());
        
        //Define font and color
        g.setColor(Color.WHITE);
        g.setFont(new Font(fontName, style, cellHeight));
        
        FontMetrics metrics = g.getFontMetrics();
        
        for (int y = 0; y < asciiMatrix.length; y++) {
            for (int x = 0; x < asciiMatrix[y].length; x++) {
                //Calculate the position of character to centralize on cell
                int newX = x * cellWidth;
                int newY = y * cellHeight;
                
                int offsetX = (cellWidth - metrics.charWidth(asciiMatrix[y][x])) / 2;
                int offsetY = ((cellHeight - metrics.getHeight()) / 2) + metrics.getAscent();
                
                int charX = newX + offsetX;
                int charY = newY + offsetY;
                
                if (color) {
                    Pixel pixel = image.get(newY).get(newX);
                    g.setColor(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()).brighter());
                }
                
                g.drawString(String.valueOf(asciiMatrix[y][x]), charX, charY);
            }
        }
        
        g.dispose();
        
        return convertToArrayList(bufferedImage);
    }
    
    //Convert BufferedImage to ArrayList<ArrayList<Pixel>>
    private ArrayList<ArrayList<Pixel>> convertToArrayList(BufferedImage bufferedImage) {
        ArrayList<ArrayList<Pixel>> pixelImage = new ArrayList<>();
        
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            ArrayList<Pixel> row = new ArrayList<>();
            
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                
                Color color = new Color(rgb);
                
                row.add(new Pixel(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
            }
            
            pixelImage.add(row);
        }
        
        return pixelImage;
    }
}