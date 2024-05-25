package co.edu.uptc.view.PopUps;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;

public class EditPetPopUp extends JDialog {

    private JTextField nameField;
    @SuppressWarnings("rawtypes")
    private JComboBox animalDropdown;
    private JTextField breedField;
    private JTextField ageField;
    @SuppressWarnings("rawtypes")
    private JComboBox personDropdown;
    @SuppressWarnings("unused")
    private TextPrompt txtPrompt;
    private PropertiesService p = new PropertiesService();

    public EditPetPopUp() throws IOException {
        this.setTitle("Editar Mascota");
        this.setSize(350, 450);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createNameField();
        createAnimalDropdown();
        createBreedField();
        createAgeField();
        createPersonTypePanel(createRadioButtonOwner(), createRadioButtonResponsible());
        createPersonDropdown();
        createEditButton();
        createCancelButton();
    }

    private void createNameField() {
        JLabel titleLabel = new JLabel("Editar Mascota");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 25);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(75, 5, 230, 50);
        this.add(titleLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 70, 150, 30);
        txtPrompt = new TextPrompt("Nombre de la mascota", nameField);
        this.add(nameField);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void createAnimalDropdown() {
        String[] options = { "Gato", "Perro" };
        animalDropdown = new JComboBox(options);
        animalDropdown.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        animalDropdown.setForeground(GlobalView.SEARCHBAR_FOREGROUND);
        animalDropdown.setBounds(100, 120, 150, 30);
        this.add(animalDropdown);
    }

    private void createBreedField() {
        breedField = new JTextField();
        breedField.setBounds(100, 170, 150, 30);
        txtPrompt = new TextPrompt("Raza", breedField);
        this.add(breedField);
    }

    private void createAgeField() {
        ageField = new JTextField();
        ageField.setBounds(100, 220, 150, 30);
        txtPrompt = new TextPrompt("Edad de la mascota", ageField);
        this.add(ageField);
    }

    private void createPersonTypePanel(JRadioButton owner, JRadioButton responsible) {
        owner.setBounds(100, 270, 80, 30);
        responsible.setBounds(185, 270, 100, 30);

        ButtonGroup personGroup = new ButtonGroup();
        personGroup.add(owner);
        personGroup.add(responsible);

        this.add(owner);
        this.add(responsible);
    }

    private JRadioButton createRadioButtonOwner() {
        return new JRadioButton("Dueño");
    }

    private JRadioButton createRadioButtonResponsible() {
        return new JRadioButton("Responsable");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void createPersonDropdown() {
        String[] options = { "Persona 1", "Persona 2", "Persona 3" };
        personDropdown = new JComboBox(options);
        personDropdown.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        personDropdown.setForeground(GlobalView.SEARCHBAR_FOREGROUND);
        personDropdown.setBounds(100, 320, 150, 30);
        this.add(personDropdown);
    }

    private void createEditButton() {
        JButton editButton = new JButton("Editar");
        editButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        editButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        editButton.setFocusPainted(false);
        editButton.setBounds(50, 370, 100, 40);
        editButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.add(editButton);
    }

    private void createCancelButton() {
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        cancelButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        cancelButton.setFocusPainted(false);
        cancelButton.setBounds(190, 370, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}
