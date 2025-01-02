package Operation;

import ImageData.Pixel;
import java.util.ArrayList;

public class SobelEdge {

    private final int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}},
            sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    
    //Generate a char matrix with empty spaces and characters, using Sobel to find the edges
    public char[][] generateEdges(ArrayList<ArrayList<Pixel>> image, int scale, int threshold) {
        int width = image.get(0).size();
        int height = image.size();
        
        int newWidth = width / scale;
        int newHeight = height / scale;
        
        char[][] result = new char[newHeight][newWidth];
        
        double maxMagnitude = findHeighestLuminosity(image, scale);
        
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int gx = 0, gy = 0;
                
                // Sobel filter
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int ny = (y * scale) + j;
                        int nx = (x * scale) + i;
                        
                        //Check if border
                        ny = Math.max(0, Math.min(ny, height));
                        nx = Math.max(0, Math.min(nx, width));
                        
                        Pixel pixel = image.get(ny).get(nx);
                        
                        int gray = pixel.getRed();
                        
                        gx += gray * sobelX[j + 1][i + 1];
                        gy += gray * sobelY[j + 1][i + 1];
                    }
                }
                
                // Calculate magnitude of gradient
                double magnitude = Math.sqrt(gx * gx + gy * gy);
                
                magnitude = (magnitude / maxMagnitude) * 100;
                
                // Remove lower values
                if (magnitude < threshold) {
                    result[y][x] = ' ';
                } else {
                    // Calculate gradient direction
                    double angle = Math.atan2(gy, gx);
                    
                    char direction = mapAngleToChar(angle);
                    result[y][x] = direction;
                }
            }
        }
        
        return result;
    }
    
    // Find the heighest value for threshold
    private double findHeighestLuminosity(ArrayList<ArrayList<Pixel>> image, int scale) {
        double heighest = 0;
        
        for (int y = 0; y < image.size(); y += scale) {
            for (int x = 0; x < image.get(0).size(); x += scale) {
                double temp = image.get(y).get(x).getRed();
                
                if (temp > heighest) {
                    heighest = temp;
                }
            }
        }
        
        return heighest;
    }
    
    // Map the angle to a character
    private char mapAngleToChar(double angle) {
        // Convert to degrees
        double degree = Math.toDegrees(angle);
        degree = (degree + 360) % 360; // Make it positive
        
        if (degree >= 337.5 || degree < 22.5) {
            return '|'; // Right
        } else if (degree >= 22.5 && degree < 67.5) {
            return '/'; // Lower right
        } else if (degree >= 67.5 && degree < 112.5) {
            return '―'; // Bottom
        } else if (degree >= 112.5 && degree < 157.5) {
            return '\\'; // Lower left
        } else if (degree >= 157.5 && degree < 202.5) {
            return '|'; // Left
        } else if (degree >= 202.5 && degree < 247.5) {
            return '/'; // Upper left
        } else if (degree >= 247.5 && degree < 292.5) {
            return '―'; // Up
        } else if (degree >= 292.5 && degree < 337.5) {
            return '\\'; // Upper right
        }
        
        return 'x'; // Error
    }
}