package co.edu.uptc.view.Aside;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.view.GlobalView;

import java.awt.Color;
import java.io.File;

public class Aside extends JPanel {
    private PropertiesService p = new PropertiesService();

    MatteBorder rightBorder = BorderFactory.createMatteBorder(0, 0, 0, 3, GlobalView.ASIDE_BORDERCOLOR);

    public Aside() {
        this.setBackground(GlobalView.ASIDE_BACKGROUND);
        this.setLayout(null);
        this.setBorder(rightBorder);
        createTitle();
    }

    private void createTitle() {
        JLabel titleLabel = new JLabel("AnimalHouse");
        Font titleFont = loadCustomFont(p.getProperties("KittenKlub"), 45);
        if (titleFont != null) {
            titleLabel.setFont(titleFont);
        } else {
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        }
        titleLabel.setBounds(10, 40, 300, 60);
        titleLabel.setForeground(Color.white);
        this.add(titleLabel);
    }

    private Font loadCustomFont(String path, float size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
