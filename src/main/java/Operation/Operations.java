/*
TODO: Allow user to choose font and style
*/

package Operation;

import FileManager.PngReader;
import FileManager.PngSaver;
import ImageData.Pixel;
import Windows.ImageViewer;
import java.awt.Font;
import java.util.ArrayList;

public class Operations {
    //static char[] ascii = {' ','.',';','c','o','P','O','?','@','■'};
    static int scale;
    static String font = Font.MONOSPACED;
    static int style = Font.PLAIN;
    
    public static void processFile(String filePath, boolean color, int scale, char[] ascii) {
        Operations.scale = scale;
        
        PngReader imageToPixelList = new PngReader();
        
        //Get 2D matrix of pixels
        ArrayList<ArrayList<Pixel>> originalImage = imageToPixelList.readPNG(filePath, false);
        
        //Loosy image scaling for character size match
        Scaler sc = new Scaler();
        ArrayList<ArrayList<Pixel>> image = sc.scaleImage(originalImage, scale);
        
        //Quantize luminance (in this case brightness)
        Quantization qt = new Quantization();
        image = qt.quantize(image, ascii.length);
        
        //Map luminance (brigthness) to ascii
        AsciiMapper am = new AsciiMapper();
        char[][] asciiImage = am.map(image, ascii, scale);
        
        if (asciiImage != null) {
            //Generate image based on ascii matrix
            AsciiImageGenerator aig = new AsciiImageGenerator();
            image = aig.generateImage(originalImage, asciiImage, scale, font, style, color);
        }
        
        //Show image to user
        ImageViewer viewer = new ImageViewer(image, filePath);
    }
    
    //Save files
    public static void saveImage(ArrayList<ArrayList<Pixel>> image, String filePath) {
        PngSaver listToImage = new PngSaver();
        listToImage.saveToFile("ASCII["+scale+"]", filePath, image);
    }
}