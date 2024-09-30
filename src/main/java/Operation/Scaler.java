package Operation;

import ImageData.Pixel;
import java.util.ArrayList;

public class Scaler {
    public ArrayList<ArrayList<Pixel>> scaleImage(ArrayList<ArrayList<Pixel>> image, int scale) {
        if (scale <= 0) {
            return image;
        }
        
        ArrayList<ArrayList<Pixel>> scaledImage = new ArrayList<>(image.size());
        
        for (int i = 0; i < image.size(); i++) {
            ArrayList<Pixel> row = new ArrayList<>(image.get(0).size());
            
            for (int j = 0; j < image.get(0).size(); j++) {
                //Find correspondent pixel in scaled image
                int origX = (j / scale) * scale;
                int origY = (i / scale) * scale;
                
                //Create a new pixel to separate both images
                row.add(new Pixel(image.get(origY).get(origX)));
            }
            
            scaledImage.add(row);
        }
        
        return scaledImage;
    }
}