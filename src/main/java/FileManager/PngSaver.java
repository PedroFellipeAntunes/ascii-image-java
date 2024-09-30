package FileManager;

import ImageData.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PngSaver {
    public void saveToFile(String fileName, String originalImagePath, ArrayList<ArrayList<Pixel>> pixelGrid) {
        int height = pixelGrid.size();
        int width = pixelGrid.get(0).size();
        
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        for (int y = 0; y < height; y++) {
            ArrayList<Pixel> row = pixelGrid.get(y);
            
            for (int x = 0; x < width; x++) {
                Pixel pixel = row.get(x);
                
                int rgba = (pixel.getAlpha() << 24) | (pixel.getRed() << 16) | (pixel.getGreen() << 8) | pixel.getBlue();
                bufferedImage.setRGB(x, y, rgba);
            }
        }
        
        String imagePathWithoutExtension = originalImagePath.substring(0, originalImagePath.lastIndexOf('.'));
        String newFilePath = generateNewFileName(fileName, imagePathWithoutExtension);
        
        saveImageToFile(bufferedImage, newFilePath);
    }
    
    private String generateNewFileName(String fileName, String imagePathWithoutExtension) {
        String newFileName = imagePathWithoutExtension + "_" + fileName;
        String newFilePath = newFileName + ".png";
        
        File newFile = new File(newFilePath);
        int counter = 1;
        while (newFile.exists()) {
            newFilePath = imagePathWithoutExtension + "_" + fileName + "_" + counter + ".png";
            newFile = new File(newFilePath);
            counter++;
        }
        
        return newFilePath;
    }
    
    private void saveImageToFile(BufferedImage image, String filePath) {
        try {
            File output = new File(filePath);
            ImageIO.write(image, "png", output);
            
            System.out.println("Image saved to: " + output.toString());
        } catch (IOException e) {
            System.err.println("Error when saving image: " + e.getMessage());
        }
    }
}