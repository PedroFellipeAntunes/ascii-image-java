//Shit quick code just to have the option to choose your own characters

package Windows;

import Operation.StringSortedAscii;
import javax.swing.*;
import java.awt.*;

public class CharacterWindow extends JDialog {
    private final String font = Font.MONOSPACED;
    private final int style = Font.PLAIN;
    
    private JLabel charCountLabel;
    private JTextField textField;
    private final JButton resetButton;
    private final JButton orderButton;
    private final JButton saveButton;
    
    private String originalText = " .;coPO?@■";
    public char[] asciiNew;
    public int characterCount;
    public boolean saved = false;
    
    public CharacterWindow(Frame owner, char[] ascii) {
        super(owner, "ASCII Characters", true);
        
        asciiNew = ascii;
        characterCount = asciiNew.length;
        
        charCountLabel = new JLabel("" + characterCount);
        charCountLabel.setOpaque(true);
        charCountLabel.setBackground(Color.BLACK);
        charCountLabel.setForeground(Color.WHITE);
        charCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        textField = new JTextField(String.valueOf(asciiNew));
        textField.setSize(200, 40);
        resetButton = new JButton("Reset");
        setButtonsVisuals(resetButton);
        orderButton = new JButton("Order");
        setButtonsVisuals(orderButton);
        saveButton = new JButton("Save");
        setButtonsVisuals(saveButton);
        
        textField.addActionListener(e -> {
            asciiNew = textField.getText().toCharArray();
            characterCount = textField.getText().length();
            charCountLabel.setText("" + characterCount);
        });
        
        resetButton.addActionListener(e -> {
            textField.setText(originalText);
            asciiNew = originalText.toCharArray();
            characterCount = originalText.length();
            charCountLabel.setText("" + characterCount);
        });
        
        orderButton.addActionListener(e -> {
            StringSortedAscii ssa = new StringSortedAscii();
            asciiNew = ssa.stringToSortedCharArray(textField.getText(), font, style);
            
            textField.setText(String.valueOf(asciiNew));
            characterCount = asciiNew.length;
            charCountLabel.setText("" + characterCount);
        });
        
        saveButton.addActionListener(e -> {
            saved = true;
            dispose();
        });
        
        setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel(new GridLayout(5, 1));
        contentPanel.add(charCountLabel);
        contentPanel.add(textField);
        contentPanel.add(resetButton);
        contentPanel.add(orderButton);
        contentPanel.add(saveButton);
        add(contentPanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    private void setButtonsVisuals(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
    }
}