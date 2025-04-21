/*
 * TODO: Allow user to choose font and style
*/

package ASCII;

import FileManager.PngReader;
import FileManager.PngSaver;
import Windows.ImageViewer;
import java.awt.Font;
import java.awt.image.BufferedImage;

public class Operations {
    private static int scale;
    private static String font = Font.MONOSPACED;
    private static int style = Font.PLAIN;

    public static void processFile(String filePath, boolean color, int scale, char[] ascii, boolean invert) {
        Operations.scale = scale;
        long startTime = System.currentTimeMillis();

        System.out.println(">>> Starting ASCII image generation...");
        
        // Step 1: Read the image
        long stepStart = System.currentTimeMillis();
        PngReader reader = new PngReader();
        BufferedImage inputImage = reader.readPNG(filePath, false);
        System.out.printf("✔ Image loaded in %d ms%n", System.currentTimeMillis() - stepStart);

        // Step 2: Scale the image
        stepStart = System.currentTimeMillis();
        Scaler scaler = new Scaler();
        BufferedImage scaledImage = scaler.scaleImage(inputImage, scale);
        System.out.printf("✔ Image scaled in %d ms%n", System.currentTimeMillis() - stepStart);

        // Step 3: Quantize brightness
        stepStart = System.currentTimeMillis();
        Quantization quantizer = new Quantization();
        scaledImage = quantizer.quantize(scaledImage, ascii.length);
        System.out.printf("✔ Luminance quantized in %d ms%n", System.currentTimeMillis() - stepStart);

        // Step 4: Map brightness to ASCII characters
        stepStart = System.currentTimeMillis();
        AsciiMapper asciiMapper = new AsciiMapper();
        char[][] asciiImage = asciiMapper.map(scaledImage, ascii, scale, invert);
        System.out.printf("✔ Brightness mapped to ASCII in %d ms%n", System.currentTimeMillis() - stepStart);

        // Step 5: Detect edges
        stepStart = System.currentTimeMillis();
        SobelEdge sobel = new SobelEdge();
        char[][] asciiEdges = sobel.generateEdges(scaledImage, scale, 75);
        System.out.printf("✔ Edges detected in %d ms%n", System.currentTimeMillis() - stepStart);

        // Step 6: Generate final ASCII image
        if (asciiImage != null && asciiEdges != null) {
            stepStart = System.currentTimeMillis();
            AsciiImageGenerator generator = new AsciiImageGenerator();
            BufferedImage outputImage = generator.generateImage(inputImage, asciiImage, asciiEdges, scale, font, style, color);
            System.out.printf("✔ Final image generated in %d ms%n", System.currentTimeMillis() - stepStart);

            // Step 7: Display image to user
            new ImageViewer(outputImage, filePath);
        } else {
            throw new RuntimeException("Error: Failed to generate ASCII image. ASCII data or edge data is null.");
        }

        long totalTime = System.currentTimeMillis() - startTime;
        System.out.printf("🎉Processing completed in %d ms%n", totalTime);
    }

    public static void saveImage(BufferedImage image, String filePath) {
        PngSaver saver = new PngSaver();
        saver.saveToFile("ASCII[" + scale + "]", filePath, image);
        System.out.println("✔ Image saved successfully.");
    }
}