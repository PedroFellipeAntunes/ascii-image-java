package Operation;

import ImageData.Pixel;
import java.awt.Color;
import java.util.ArrayList;

public class Quantization {
    private Color averageColor;
    
    public ArrayList<ArrayList<Pixel>> quantize(ArrayList<ArrayList<Pixel>> image, int amount) {
        ArrayList<ArrayList<Pixel>> luminanceImage = new ArrayList<>(image.size());
        
        int totalRed = 0, totalGreen = 0, totalBlue = 0;
        
        for (int i = 0; i < image.size(); i++) {
            ArrayList<Pixel> row = new ArrayList<>(image.get(0).size());
            
            for (int j = 0; j < image.get(0).size(); j++) {
                Pixel pixel = image.get(i).get(j);
                
                //Acumulate values for background color
                totalRed += pixel.getRed();
                totalGreen += pixel.getGreen();
                totalBlue += pixel.getBlue();
                
                //Get HSB
                float[] hsb = Color.RGBtoHSB(pixel.getRed(), pixel.getGreen(), pixel.getBlue(), null);
                
                //Desaturate
                hsb[1] = 0.0f; //Useless step, just here to follow through with the video
                
                //Quantize
                hsb[2] = (float) (Math.floor(hsb[2] * amount)) / amount;
                
                //Back to RGB
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                
                pixel.setRed((rgb >> 16) & 0xff);
                pixel.setGreen((rgb >> 8) & 0xff);
                pixel.setBlue(rgb & 0xff);
                
                row.add(pixel);
            }
            
            luminanceImage.add(row);
        }
        
        int pixelCount = image.size() * image.get(0).size();
        averageColor = new Color(totalRed / pixelCount, totalGreen / pixelCount, totalBlue / pixelCount);
        
        return luminanceImage;
    }
    
    public Color getAverageColor() {
        return averageColor;
    }
}