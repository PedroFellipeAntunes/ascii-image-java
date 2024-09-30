package FileManager;

import ImageData.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PngReader {
    private final ArrayList<ArrayList<Pixel>> pixelGrid = new ArrayList<>();
    
    public ArrayList<ArrayList<Pixel>> readPNG(String fileLocation, boolean gray) {
        try {
            File file = new File(fileLocation);
            BufferedImage image = ImageIO.read(file);
            
            //Check file format
            String formatName = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);
            
            if (formatName.equalsIgnoreCase("jpg") || formatName.equalsIgnoreCase("jpeg")) {
                PngConverter converter = new PngConverter();
                image = converter.convertToPng(image);
            }
            
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int currentPixel = image.getRGB(x, y);
                    int[] RGBA = new int[4];
                    
                    RGBA[0] = (currentPixel >> 24) & 0xff; //Alpha
                    RGBA[1] = (currentPixel >> 16) & 0xff; //Red
                    RGBA[2] = (currentPixel >> 8) & 0xff; //Green
                    RGBA[3] = (currentPixel) & 0xff; //Blue
                    
                    if (x == 0) {
                        pixelGrid.add(new ArrayList<>());
                    }
                    
                    if (gray == true) {
                        Grayscale gs = new Grayscale();
                        RGBA = gs.bt709(RGBA);
                    }
                    
                    pixelGrid.get(y).add(new Pixel(RGBA[1], RGBA[2], RGBA[3], RGBA[0]));
                }
            }
            
            return pixelGrid;
        } catch (IOException e) {
            System.err.println("Error when reading image: " + fileLocation);
        }
        
        return null;
    }
}