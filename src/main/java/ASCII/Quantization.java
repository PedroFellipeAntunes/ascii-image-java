package ASCII;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Quantization {
    private Color averageColor;
    
    public BufferedImage quantize(BufferedImage image, int amount) {
        BufferedImage luminanceImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        
        int totalRed = 0, totalGreen = 0, totalBlue = 0;
        
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color c = new Color(image.getRGB(x, y));
                
                //Acumulate values for background color
                totalRed += c.getRed();
                totalGreen += c.getGreen();
                totalBlue += c.getBlue();
                
                //Get HSB
                float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
                
                //Desaturate
                hsb[1] = 0.0f; //Useless step, just here to follow through with the video
                
                //Quantize
                hsb[2] = (float) (Math.floor(hsb[2] * amount)) / amount;
                
                //Back to RGB
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                
                luminanceImage.setRGB(x, y, rgb);
            }
        }
        
        int pixelCount = image.getHeight() * image.getWidth();
        averageColor = new Color(totalRed / pixelCount, totalGreen / pixelCount, totalBlue / pixelCount);
        
        return luminanceImage;
    }
    
    public Color getAverageColor() {
        return averageColor;
    }
}