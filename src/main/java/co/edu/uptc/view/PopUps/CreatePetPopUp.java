package co.edu.uptc.view.PopUps;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import co.edu.uptc.Pojos.Person;
import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainPanels.PetPanel;

public class CreatePetPopUp extends JDialog {

    private JTextField nameField;
    private JComboBox<String> animalDropdown;
    private JTextField breedField;
    private JTextField ageField;
    private JTable ownerTable;
    private PetPanel petPanel;
    private static int ID_COUNTER;
    private PropertiesService p = new PropertiesService();
    private JTextField searchOwnerField;

    public CreatePetPopUp(PetPanel petPanel) throws IOException {
        this.petPanel = petPanel;
        this.setTitle("Crear Mascota");
        this.setSize(400, 600);
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
        createOwnerTable(null);
        createAddButton();
        createCancelButton();
        setContadorId();
    }

    private void setContadorId() {
        CreatePetPopUp.ID_COUNTER = petPanel.getMainView().getPresenter().getPetLastId();
    }

    private void createNameField() {
        JLabel titleLabel = new JLabel("Agregar Mascota");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 25);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(75, 5, 230, 50);
        this.add(titleLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 70, 200, 30);
        new TextPrompt("Nombre de la mascota", nameField);
        this.add(nameField);
    }

    private void createAnimalDropdown() {
        String[] options = { "Gato", "Perro" };
        animalDropdown = new JComboBox<>(options);
        animalDropdown.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        animalDropdown.setForeground(GlobalView.SEARCHBAR_FOREGROUND);
        animalDropdown.setBounds(100, 120, 200, 30);
        this.add(animalDropdown);
    }

    private void createBreedField() {
        breedField = new JTextField();
        breedField.setBounds(100, 170, 200, 30);
        new TextPrompt("Raza", breedField);
        this.add(breedField);
    }

    private void createAgeField() {
        ageField = new JTextField();
        ageField.setBounds(100, 220, 200, 30);
        new TextPrompt("Edad de la mascota", ageField);
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

    private void createOwnerTable(String ownerName) {
        String[] columnNames = { "ID", "Nombre", "Apellido", "Documento" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ownerTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(ownerTable);
        scrollPane.setBounds(20, 320, 350, 150);
        this.add(scrollPane);

        loadOwnersData();

        searchOwnerField = new JTextField();
        searchOwnerField.setBounds(100, 270, 200, 30);
        new TextPrompt("Buscar Dueño por nombre", searchOwnerField);
        searchOwnerField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String searchText = searchOwnerField.getText().trim();
                filterOwnersTable(searchText, model);
            }
        });
        this.add(searchOwnerField);

        selectCurrentOwner(ownerName, model);
    }

    private void selectCurrentOwner(String ownerName, DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String fullName = model.getValueAt(i, 1) + " " + model.getValueAt(i, 2);
            if (fullName.equalsIgnoreCase(ownerName)) {
                ownerTable.setRowSelectionInterval(i, i);
                break;
            }
        }
    }

    private void filterOwnersTable(String searchText, DefaultTableModel model) {
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        ownerTable.setRowSorter(tableRowSorter);

        if (searchText.length() == 0) {
            tableRowSorter.setRowFilter(null);
        } else {
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1, 2));
        }
    }

    private void loadOwnersData() {
        List<Person> owners = petPanel.getMainView().getPresenter().getPersons();
        DefaultTableModel model = (DefaultTableModel) ownerTable.getModel();
        model.setRowCount(0);
        for (Person owner : owners) {
            model.addRow(new Object[] { owner.getPersonId(), owner.getPersonName(), owner.getPersonLastName(),
                    owner.getDocumentNumber() });
        }
    }

    private void createAddButton() {
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBounds(90, 480, 100, 40);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String petName = nameField.getText();
                String specie = (String) animalDropdown.getSelectedItem();
                String breed = breedField.getText();
                String ageText = ageField.getText();
                int selectedRow = ownerTable.getSelectedRow();

                if (petName.isEmpty() || breed.isEmpty() || ageText.isEmpty() || selectedRow == -1) {
                    JOptionPane.showMessageDialog(CreatePetPopUp.this, "Todos los campos son obligatorios.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int petAge = Integer.parseInt(ageText);
                int personId = Integer.parseInt(String.valueOf(ownerTable.getValueAt(selectedRow, 0)));

                petPanel.getMainView().getPresenter()
                        .registerPet(petPanel.getMainView().getPresenter().createPet(ID_COUNTER, petName, specie,
                                breed, petAge, petPanel.getMainView().getPresenter().getPersonById(personId)));
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
        cancelButton.setBounds(210, 480, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}
