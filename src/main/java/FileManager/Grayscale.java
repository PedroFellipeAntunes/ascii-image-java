package FileManager;

import java.awt.Color;

public class Grayscale {
    /**
    * Expects integer array of size 4, with values between 0 and 255, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on max RGB value
    * @param RGBA
    * @return int RGBA[4]
    */
    public int[] maxValue(int RGBA[]) {
        int gray = Math.max(RGBA[1], Math.max(RGBA[2], RGBA[3]));
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects integer array of size 4, with values between 0 and 255
    * with {alpha, red, green, blue}, 
    * returns grayscale based on min RGB value
    * @param RGBA
    * @return int RGBA[4]
    */
    public int[] minValue(int RGBA[]) {
        int gray = Math.min(RGBA[1], Math.min(RGBA[2], RGBA[3]));
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects integer array of size 4, with values between 0 and 255, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on ITU-R (BT.601) weights
    * red = 0.299, green = 0.587, blue = 0.114
    * @param RGBA
    * @return int RGBA[4]
    */
    public int[] bt601(int RGBA[]) {
        int gray = (int) (RGBA[1] * 0.299 + RGBA[2] * 0.587 + RGBA[3] * 0.114);
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects integer array of size 4, with values between 0 and 255, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on ITU-R (BT.709) weights
    * red = 0.2126, green = 0.7152, blue = 0.0722
    * @param RGBA
    * @return int RGBA[4]
    */
    public int[] bt709(int RGBA[]) {
        int gray = (int) (RGBA[1] * 0.2126 + RGBA[2] * 0.7152 + RGBA[3] * 0.0722);
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects integer array of size 4, with values between 0 and 255, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on basic weights for RGB
    * red = 0.3, green = 0.59, blue = 0.11
    * @param RGBA
    * @return int RGBA[4]
    */
    public int[] weightedAverage(int RGBA[]) {
        int gray = (int) (RGBA[1] * 0.3 + RGBA[2] * 0.59 + RGBA[3] * 0.11);
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects integer array of size 4, with values between 0 and 255, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on basic average for RGB
    * @param RGBA
    * @return int RGBA[4]
    */
    public int[] average(int RGBA[]) {
        int average = (RGBA[1] + RGBA[2] + RGBA[3]) / 3;
        
        RGBA[1] = average;
        RGBA[2] = average;
        RGBA[3] = average;
        
        return RGBA;
    }
    
    /**
    * Expects integer array of size 4, with values between 0 and 255, 
    * with {alpha, red, green, blue}, 
    * returns grayscale by setting Saturation to 0 in HSB color space
    * @param RGBA
    * @return int RGBA[4]
    */
    public int[] hsbSaturation(int RGBA[]) {
        float[] hsb = Color.RGBtoHSB(RGBA[1], RGBA[2], RGBA[3], null);
        int grayPixel = Color.HSBtoRGB(hsb[0], 0, hsb[2]);
        
        RGBA[1] = (grayPixel >> 16) & 0xff; //Red
        RGBA[2] = (grayPixel >> 8) & 0xff; //Green
        RGBA[3] = (grayPixel) & 0xff; //Blue
        
        return RGBA;
    }
    
    /**
    * Expects double array of size 4, with values between 0 and 1, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on max RGB value
    * @param RGBA
    * @return double RGBA[4]
    */
    public double[] maxValue(double RGBA[]) {
        double gray = Math.max(RGBA[1], Math.max(RGBA[2], RGBA[3]));
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects double array of size 4, with values between 0 and 1
    * with {alpha, red, green, blue}, 
    * returns grayscale based on min RGB value
    * @param RGBA
    * @return double RGBA[4]
    */
    public double[] minValue(double RGBA[]) {
        double gray = Math.min(RGBA[1], Math.min(RGBA[2], RGBA[3]));
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects double array of size 4, with values between 0 and 1, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on ITU-R (BT.601) weights
    * red = 0.299, green = 0.587, blue = 0.114
    * @param RGBA
    * @return double RGBA[4]
    */
    public double[] bt601(double RGBA[]) {
        double gray = RGBA[1] * 0.299 + RGBA[2] * 0.587 + RGBA[3] * 0.114;
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects double array of size 4, with values between 0 and 1, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on ITU-R (BT.709) weights
    * red = 0.2126, green = 0.7152, blue = 0.0722
    * @param RGBA
    * @return double RGBA[4]
    */
    public double[] bt709(double RGBA[]) {
        double gray = RGBA[1] * 0.2126 + RGBA[2] * 0.7152 + RGBA[3] * 0.0722;
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects double array of size 4, with values between 0 and 1, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on basic weights for RGB
    * red = 0.3, green = 0.59, blue = 0.11
    * @param RGBA
    * @return double RGBA[4]
    */
    public double[] weightedAverage(double RGBA[]) {
        double gray = RGBA[1] * 0.3 + RGBA[2] * 0.59 + RGBA[3] * 0.11;
        
        RGBA[1] = gray;
        RGBA[2] = gray;
        RGBA[3] = gray;
        
        return RGBA;
    }
    
    /**
    * Expects double array of size 4, with values between 0 and 1, 
    * with {alpha, red, green, blue}, 
    * returns grayscale based on basic average for RGB
    * @param RGBA
    * @return double RGBA[4]
    */
    public double[] average(double RGBA[]) {
        double average = (RGBA[1] + RGBA[2] + RGBA[3]) / 3;
        
        RGBA[1] = average;
        RGBA[2] = average;
        RGBA[3] = average;
        
        return RGBA;
    }
    
    /**
    * Expects double array of size 4, with values between 0 and 1, 
    * with {alpha, red, green, blue}, 
    * returns grayscale by setting Saturation to 0 in HSB color space
    * @param RGBA
    * @return double RGBA[4]
    */
    public double[] hsbSaturation(double[] RGBA) {
        int r = (int) (RGBA[1] * 255);
        int g = (int) (RGBA[2] * 255);
        int b = (int) (RGBA[3] * 255);
        
        float[] hsb = java.awt.Color.RGBtoHSB(r, g, b, null);
        int grayRGB = java.awt.Color.HSBtoRGB(hsb[0], 0f, hsb[2]);
        
        RGBA[1] = ((grayRGB >> 16) & 0xFF) / 255.0;
        RGBA[2] = ((grayRGB >> 8) & 0xFF) / 255.0;
        RGBA[3] = (grayRGB & 0xFF) / 255.0;
        
        return RGBA;
    }
}