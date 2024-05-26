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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainPanels.UserPanel;

public class EditUserPopUp extends JDialog {
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField docNumberField;
    private JComboBox<String> documentComboBox;
    @SuppressWarnings("unused")
    private TextPrompt txtPrompt;
    private PropertiesService p = new PropertiesService();
    private UserPanel userPanel;
    private int id;

    public EditUserPopUp(int id, String name, String lastName, String age, String docType, String docNumber,
            UserPanel userPanel) throws IOException {
        this.id = id;
        this.userPanel = userPanel;
        this.setTitle("Editar Usuario");
        this.setSize(350, 475);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createNameField(name, lastName);
        createAgeField(age);
        documentComboBox = createDocumentComboBox(docType);
        createDocumentPanel(documentComboBox);
        createDocumentNumberField(docNumber);
        createEditButton();
        createCancelButton();
    }

    private void createNameField(String name, String lastName) {
        JLabel titleLabel = new JLabel("Editar Usuario");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 25);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(75, 5, 200, 50);
        this.add(titleLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 70, 150, 30);
        txtPrompt = new TextPrompt(name, nameField);
        this.add(nameField);

        lastNameField = new JTextField();
        lastNameField.setBounds(100, 120, 150, 30);
        txtPrompt = new TextPrompt(lastName, lastNameField);
        this.add(lastNameField);
    }

    private void createAgeField(String age) {
        ageField = new JTextField();
        ageField.setBounds(100, 170, 150, 30);
        txtPrompt = new TextPrompt(age, ageField);
        ageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        this.add(ageField);
    }

    private void createDocumentPanel(JComboBox<String> documentComboBox) {
        documentComboBox.setBounds(100, 220, 150, 30);
        documentComboBox.setSelectedItem(documentComboBox.getSelectedItem());
        this.add(documentComboBox);
    }

    private JComboBox<String> createDocumentComboBox(String docType) {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("Cedula de ciudadania");
        comboBox.addItem("Tarjeta de identidad");
        comboBox.addItem("Pasaporte");
        comboBox.addItem("Cedula Extranjera");
        comboBox.setSelectedItem(docType);
        return comboBox;
    }

    private void createDocumentNumberField(String docNumber) {
        docNumberField = new JTextField();
        docNumberField.setBounds(100, 270, 150, 30);
        txtPrompt = new TextPrompt(docNumber, docNumberField);
        docNumberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        this.add(docNumberField);
    }

    private void createEditButton() {
        JButton editButton = new JButton("Editar");
        editButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        editButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        editButton.setFocusPainted(false);
        editButton.setBounds(50, 350, 100, 40);
        editButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String personName = nameField.getText();
                String personLastName = lastNameField.getText();
                String ageText = ageField.getText();
                String docType = (String) documentComboBox.getSelectedItem();
                String docNumber = docNumberField.getText();

                if (personName.isEmpty() || personLastName.isEmpty() || ageText.isEmpty() || docType.isEmpty()
                        || docNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(EditUserPopUp.this, "Todos los campos son obligatorios.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int personAge = Integer.parseInt(ageText);

                userPanel.getMainView().getPresenter().editPerson(id, userPanel.getMainView().getPresenter()
                        .createPerson(id, personName, personLastName, personAge, docType, docNumber));

                userPanel.loadPersonsData();
                dispose();
            }
        });
        this.add(editButton);

    }

    private void createCancelButton() {
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        cancelButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(190, 350, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}
