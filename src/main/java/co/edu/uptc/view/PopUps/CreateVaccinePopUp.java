package co.edu.uptc.view.PopUps;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainPanels.VaccinesPanel;

public class CreateVaccinePopUp extends JDialog {
    private JTextField nameField;
    private JTextField lifeSpanField;
    private PropertiesService p = new PropertiesService();
    private static int CONTADOR_ID;
    private VaccinesPanel vaccinesPanel;

    public CreateVaccinePopUp(VaccinesPanel vaccinesPanel) throws IOException {
        this.vaccinesPanel = vaccinesPanel;
        this.setTitle("Crear Vacuna");
        this.setSize(350, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createNameField();
        createLifeSpanField();
        createAddButton();
        createCancelButton();
        setContadorId();
    }

    private void setContadorId() {
        CreateVaccinePopUp.CONTADOR_ID = vaccinesPanel.getMainView().getPresenter().getVaccineLastId();
    }

    private void createNameField() {
        JLabel titleLabel = new JLabel("Agregar Vacuna");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 25);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(75, 5, 230, 50);
        this.add(titleLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 60, 150, 30);
        new TextPrompt("Nombre de la vacuna", nameField);
        this.add(nameField);
    }

    private void createLifeSpanField() {
        lifeSpanField = new JTextField();
        lifeSpanField.setBounds(100, 105, 150, 30);
        new TextPrompt("Vida útil de la vacuna", lifeSpanField);
        lifeSpanField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        this.add(lifeSpanField);
    }

    private void createAddButton() {
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBounds(50, 160, 100, 40);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String vaccineName = nameField.getText();
                String lifeSpanText = lifeSpanField.getText();

                if (vaccineName.isEmpty() || lifeSpanText.isEmpty()) {
                    JOptionPane.showMessageDialog(CreateVaccinePopUp.this, "Todos los campos son obligatorios.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int vaccineLifeSpan = Integer.parseInt(lifeSpanText);

                vaccinesPanel.getMainView().getPresenter().registerVaccine(
                        vaccinesPanel.getMainView().getPresenter().createVaccine(CONTADOR_ID++, vaccineName,
                                vaccineLifeSpan));
                vaccinesPanel.loadVaccinesData();
                dispose();
            }
        });
        this.add(addButton);
    }

    private void createCancelButton() {
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        cancelButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(190, 160, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}
