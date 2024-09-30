package Util;
/*
This code is HEAVILY inspired by this video made by Acerola: https://www.youtube.com/watch?v=gg40RWiaHRY
Though my code does not include the part about edge detection.
*/

/*
Pedro Fellipe Cruz Antunes
Code that creates a window which receives an n amount of PNG files scales the
image down and then back up, quantizes the luminance, creates a matrix of
char based on the image and saves it as a new image.

The output will be n PNG files.

User inputs:
    Drop files;
    Choose scale of characters;
    Define characters;
    Choose if it will be black&white or colored;
    Save png file to the same folder as the original file;
*/

import Windows.DropDownWindow;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DropDownWindow dropDownWindow = new DropDownWindow();
        });
    }
}