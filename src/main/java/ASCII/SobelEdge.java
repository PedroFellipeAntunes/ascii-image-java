package ASCII;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdge {
    private final int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}},
            sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    
    // Generate a char matrix with empty spaces and characters, using Sobel to find the edges
    public char[][] generateEdges(BufferedImage image, int scale, int threshold) {
        int width = image.getWidth();
        int height = image.getHeight();

        int newWidth = (width + scale - 1) / scale;
        int newHeight = (height + scale - 1) / scale;

        char[][] result = new char[newHeight][newWidth];

        double maxMagnitude = findHeighestLuminosity(image, scale);

        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                int gx = 0, gy = 0;

                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int ny = (y * scale) + j;
                        int nx = (x * scale) + i;

                        // Clamp edges
                        ny = Math.max(0, Math.min(ny, height - 1));
                        nx = Math.max(0, Math.min(nx, width - 1));

                        Color c = new Color(image.getRGB(nx, ny)); // image.get(ny).get(nx);
                        int gray = c.getRed();

                        gx += gray * sobelX[j + 1][i + 1];
                        gy += gray * sobelY[j + 1][i + 1];
                    }
                }

                double magnitude = Math.sqrt(gx * gx + gy * gy);
                magnitude = (magnitude / maxMagnitude) * 100;

                if (magnitude < threshold) {
                    result[y][x] = ' ';
                } else {
                    double angle = Math.atan2(gy, gx);
                    result[y][x] = mapAngleToChar(angle);
                }
            }
        }

        return result;
    }
    
    // Find the heighest value for threshold
    private double findHeighestLuminosity(BufferedImage image, int scale) {
        double heighest = 0;
        
        for (int y = 0; y < image.getHeight(); y += scale) {
            for (int x = 0; x < image.getWidth(); x += scale) {
                Color c = new Color(image.getRGB(x, y));
                
                double temp = c.getRed();
                
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