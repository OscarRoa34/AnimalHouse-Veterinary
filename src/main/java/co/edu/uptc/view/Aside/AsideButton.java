package co.edu.uptc.view.Aside;

import javax.swing.*;
import javax.swing.border.Border;

import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.view.GlobalView;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AsideButton extends JButton {

    private PropertiesService p = new PropertiesService();
    Font buttonFont;

    public AsideButton(String text, ImageIcon icon) {
        buttonFont = loadCustomFont(p.getProperties("AstonPoliz"), Font.BOLD, 15);
        if (buttonFont == null) {
            buttonFont = new Font("Arial", Font.BOLD, 13);
        }

        this.setText(text);
        this.setIcon(icon);
        this.setBackground(GlobalView.ASIDE_BUTTONS_BACKGROUND);
        this.setForeground(GlobalView.ASIDE_BUTTONS_FOREGROUND);
        this.setFont(buttonFont);
        this.setFocusPainted(false);
        this.setHorizontalTextPosition(JButton.RIGHT);
        this.setVerticalTextPosition(JButton.CENTER);
        Border border = BorderFactory.createLineBorder(GlobalView.ASIDE_BORDERCOLOR, 2);
        this.setBorder(border);
    }

    private Font loadCustomFont(String path, int style, float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(style, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
