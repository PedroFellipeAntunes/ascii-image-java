# ASCII Art Image Converter

This project is a **Java Maven** application built using **NetBeans** IDE and **Swing** that converts images into **ASCII art** representations. Users can select image files, adjust parameters like scaling, and choose various ASCII character sets for the conversion.
This code is HEAVILY inspired by this video made by **Acerola:** https://www.youtube.com/watch?v=gg40RWiaHRY

<p align="center">
  <img src="tests/baboon_ASCII%5B8%5D.png" width="450" alt="Example ASCII Art">
</p>

<p align="center">
  <img src="tests/tulips_ASCII%5B8%5D.png" width="550" alt="Example ASCII Art with color">
</p>

<p align="center">
  <img src="tests/warframe_ASCII%5B8%5D.png" width="750" alt="Example ASCII Art inverted">
</p>

## Features

- **Image Selection**: Choose images in `.png`, `.jpg`, or `.jpeg` formats for conversion.
- **Character Set Customization**: Use custom ASCII character sets to define the appearance of the ASCII art, which can be ordered automatically to create the gradient.
- **Scaling**: Adjust the scaling factor to control the size of the output ASCII art.
- **Color Option**: Choose to display the ASCII art in color based on the original image's colors or in monochrome.
- **Invert**: Choose to the brightness of the image, useful for images with very high brightness.
- **Preview & Save Option**: Preview the generated ASCII art and choose to save it after processing.

## Output Format & Naming

- The output ASCII art is saved in **PNG** format.
- The naming convention for the output file is:  
  **`original_name_ASCII[scale].png`**  
  Where:
  - `original_name` is the original image's filename.
  - `scale` is the size of the character in the scaled grid of the image.

## How to Use

1. **Start the Application**: Run the Java application in NetBeans or execute the `.jar` file in the **target** folder.
2. **Adjust Parameters**:
   - Set the scaling factor to control the size of the ASCII art.
   - Choose a custom ASCII character set to define the appearance of the art.
   - Toggle the color option to display the ASCII art in color or monochrome.
3. **Select Images**: Drop down the image files to load them (`.png`, `.jpg`, or `.jpeg`).
5. **Preview & Save**: After processing the image, preview the generated ASCII art. You will be prompted to save the modified image as a PNG file with the new name.

## Extra
- There is a separate file which contains code which should read any text file and generate a string of certain size, ordered by luminance of each character.
