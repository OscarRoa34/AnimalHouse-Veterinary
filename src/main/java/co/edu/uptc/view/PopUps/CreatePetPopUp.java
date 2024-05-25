package co.edu.uptc.view.PopUps;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import co.edu.uptc.PropertiesService;
import co.edu.uptc.TextPrompt;
import co.edu.uptc.models.Person;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainPanels.PetPanel;

public class CreatePetPopUp extends JDialog {

    private JTextField nameField;
    private JComboBox<String> animalDropdown;
    private JTextField breedField;
    private JTextField ageField;
    private JComboBox<String> personDropdown;
    @SuppressWarnings("unused")
    private TextPrompt txtPrompt;
    private PetPanel petPanel;
    private static int CONTADOR_ID = 1;
    private PropertiesService p = new PropertiesService();

    public CreatePetPopUp(PetPanel petPanel) throws IOException {
        this.petPanel = petPanel;
        this.setTitle("Crear Mascota");
        this.setSize(350, 500);
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
        createPersonDropdown();
        createAddButton();
        createCancelButton();
    }

    private void createNameField() {
        JLabel titleLabel = new JLabel("Agregar Mascota");
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

    private void createAnimalDropdown() {
        String[] options = { "Gato", "Perro" };
        animalDropdown = new JComboBox<>(options);
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

    private void createPersonDropdown() {
        String[] options = { "Persona 1", "Persona 2", "Persona 3" };
        personDropdown = new JComboBox<>(options);
        personDropdown.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        personDropdown.setForeground(GlobalView.SEARCHBAR_FOREGROUND);
        personDropdown.setBounds(100, 320, 150, 30);
        this.add(personDropdown);
    }

    private void createAddButton() {
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBounds(50, 370, 100, 40);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String petName = nameField.getText();
                String specie = (String) animalDropdown.getSelectedItem();
                String breed = breedField.getText();
                int petAge = Integer.parseInt(ageField.getText());

                petPanel.getMainView().getPresenter()
                        .registerPet(petPanel.getMainView().getPresenter().createPet(CONTADOR_ID++, petName, specie,
                                breed, petAge,
                                new Person()));
                petPanel.loadPetsData();
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
