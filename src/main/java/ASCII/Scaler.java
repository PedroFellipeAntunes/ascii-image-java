package ASCII;

import java.awt.image.BufferedImage;

public class Scaler {
//    public ArrayList<ArrayList<Pixel>> scaleImage(ArrayList<ArrayList<Pixel>> image, int scale) {
//        if (scale <= 0) {
//            return image;
//        }
//
//        ArrayList<ArrayList<Pixel>> scaledImage = new ArrayList<>(image.size());
//
//        for (int y = 0; y < image.size(); y++) {
//            ArrayList<Pixel> row = new ArrayList<>(image.get(0).size());
//
//            for (int x = 0; x < image.get(0).size(); x++) {
//                //Find correspondent pixel in scaled image
//                int origX = (x / scale) * scale;
//                int origY = (y / scale) * scale;
//
//                //Create a new pixel to separate both images
//                row.add(new Pixel(image.get(origY).get(origX)));
//            }
//
//            scaledImage.add(row);
//        }
//
//        return scaledImage;
//    }
    
    public BufferedImage scaleImage(BufferedImage image, int scale) {
        if (scale <= 0) {
            return image;
        }

        BufferedImage scaledImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                //Find correspondent pixel in scaled image
                int origX = (x / scale) * scale;
                int origY = (y / scale) * scale;

                //Create a new pixel to separate both images
                scaledImage.setRGB(x, y, image.getRGB(origX, origY));
            }
        }

        return scaledImage;
    }
}