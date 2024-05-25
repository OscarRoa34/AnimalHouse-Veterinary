package co.edu.uptc.view.PopUps;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;

public class EditUserPopUp extends JDialog {
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField ageField;
    @SuppressWarnings("unused")
    private TextPrompt txtPrompt;
    private PropertiesService p = new PropertiesService();

    public EditUserPopUp() throws IOException {
        this.setTitle("Editar Usuario");
        this.setSize(350, 375);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createNameField();
        createAgeField();
        createEditButton();
        createCancelButton();
    }

    private void createNameField() {
        JLabel titleLabel = new JLabel("Editar Usuario");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 25);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(75, 5, 200, 50);
        this.add(titleLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 70, 150, 30);
        txtPrompt = new TextPrompt("Nombre del usuario", nameField);
        this.add(nameField);

        lastNameField = new JTextField();
        lastNameField.setBounds(100, 120, 150, 30);
        txtPrompt = new TextPrompt("Apellido del usuario", lastNameField);
        this.add(lastNameField);
    }

    private void createAgeField() {
        ageField = new JTextField();
        ageField.setBounds(100, 170, 150, 30);
        txtPrompt = new TextPrompt("Edad del usuario", ageField);
        this.add(ageField);
    }

    private void createEditButton() {
        JButton addButton = new JButton("Editar");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBounds(50, 250, 100, 40);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.add(addButton);
    }

    private void createCancelButton() {
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        cancelButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(190, 250, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}