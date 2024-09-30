# ASCII Art Image Converter

This project is a **Java Maven** application built using **NetBeans** IDE and **Swing** that converts images into **ASCII art** representations. Users can select image files, adjust parameters like scaling, and choose various ASCII character sets for the conversion.
This code is HEAVILY inspired by this video made by **Acerola:** https://www.youtube.com/watch?v=gg40RWiaHRY
(Though my code does not include the part about edge detection)

<p align="center">
  <img src="baboon_ASCII%5B8%5D.png" width="350" alt="Example ASCII Art">
</p>

<p align="center">
  <img src="tulips_ASCII%5B8%5D.png" width="350" alt="Example ASCII Art with color">
</p>

## Features

- **Image Selection**: Choose images in `.png`, `.jpg`, or `.jpeg` formats for conversion.
- **Character Set Customization**: Use custom ASCII character sets to define the appearance of the ASCII art.
- **Scaling**: Adjust the scaling factor to control the size of the output ASCII art.
- **Color Option**: Choose to display the ASCII art in color based on the original image's colors or in monochrome.
- **Preview & Save Option**: Preview the generated ASCII art and choose to save it after processing.

## Output Format & Naming

- The output ASCII art is saved in **PNG** format.
- The naming convention for the output file is:  
  **`original_name_ASCII[scale].png`**  
  Where:
  - `original_name` is the original image's filename.
  - `scale` is the size of the character in the scaled grid of the image.

## Implementation Details

- The core image processing is managed through a data structure of `ArrayList<ArrayList<Pixel>>` to represent and manipulate image pixel data.
- `Pixel` is an object containing separate `int` values for **RGBA**.
- Each image is processed based on user input and settings, and a preview is displayed after every operation.
- The final window allows users to save the modified ASCII art image.

## How to Use

1. **Start the Application**: Run the Java application in NetBeans or execute the `.jar` file in the **target** folder.
2. **Select Images**: Use the image selection dialog to load images (`.png`, `.jpg`, or `.jpeg`).
3. **Adjust Parameters**:
   - Set the scaling factor to control the size of the ASCII art.
   - Choose a custom ASCII character set to define the appearance of the art.
   - Toggle the color option to display the ASCII art in color or monochrome.
4. **Preview & Save**: After processing the image, preview the generated ASCII art. You will be prompted to save the modified image as a PNG file with the new name.
