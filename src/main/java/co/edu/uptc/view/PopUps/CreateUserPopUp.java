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

public class CreateUserPopUp extends JDialog {
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField documentNumberField;
    @SuppressWarnings("unused")
    private TextPrompt txtPrompt;
    private PropertiesService p = new PropertiesService();
    private UserPanel userPanel;
    private static int CONTADOR_ID = 1;
    private JComboBox<String> documentComboBox;

    public CreateUserPopUp(UserPanel userPanel) throws IOException {
        this.userPanel = userPanel;
        this.setTitle("Crear Usuario");
        this.setSize(350, 450);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createNameField();
        createAgeField();
        documentComboBox = createDocumentComboBox();
        createDocumentPanel(documentComboBox);
        createDocumentNumberField();
        createAddButton();
        createCancelButton();
    }

    private void createNameField() {
        JLabel titleLabel = new JLabel("Agregar Usuario");
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
        documentComboBox.setBounds(120, 220, 110, 30);
        documentComboBox.addItem("Cedula de ciudadania");
        documentComboBox.addItem("Tarjeta de identidad");
        documentComboBox.addItem("Pasaporte");
        documentComboBox.addItem("Cedula Extranjera");
        this.add(documentComboBox);
    }

    private JComboBox<String> createDocumentComboBox() {
        return new JComboBox<String>();
    }

    private void createDocumentNumberField() {
        documentNumberField = new JTextField();
        documentNumberField.setBounds(100, 270, 150, 30);
        txtPrompt = new TextPrompt("Numero del documento", documentNumberField);
        documentNumberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        this.add(documentNumberField);
    }

    private void createAddButton() {
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBounds(50, 320, 100, 40);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String personName = nameField.getText();
                String personLastName = lastNameField.getText();
                String ageText = ageField.getText();
                String docType = (String) documentComboBox.getSelectedItem();
                String docNumber = documentNumberField.getText();

                if (personName.isEmpty() || personLastName.isEmpty() || ageText.isEmpty() || docNumber.isEmpty()
                        || docType == null) {
                    JOptionPane.showMessageDialog(CreateUserPopUp.this, "Todos los campos son obligatorios.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int personAge = Integer.parseInt(ageText);

                userPanel.getMainView().getPresenter().registerPerson(userPanel.getMainView().getPresenter()
                        .createPerson(CONTADOR_ID++, personName, personLastName, personAge, docType, docNumber));
                userPanel.loadPersonsData();

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
        cancelButton.setBounds(190, 320, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}
