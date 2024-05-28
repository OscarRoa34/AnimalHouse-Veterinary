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

public class EditPetPopUp extends JDialog {

    private JTextField nameField;
    private JTextField specieField;
    private JTextField breedField;
    private JTextField ageField;
    private JTable ownerTable;
    private JTextField searchOwnerField;
    private PropertiesService p = new PropertiesService();
    private PetPanel petPanel;
    private int id;
    private int ownerId;

    public EditPetPopUp(int id, String name, String specie, String breed, int age, String ownerName, PetPanel petPanel)
            throws IOException {
        this.id = id;
        this.petPanel = petPanel;
        this.setTitle("Editar Mascota");
        this.setSize(450, 550);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createNameField(name);
        createSpecieField(specie);
        createBreedField(breed);
        createAgeField(age);
        createOwnerTable(ownerName);
        createEditButton();
        createCancelButton();
    }

    private void createNameField(String name) {
        JLabel titleLabel = new JLabel("Editar Mascota");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 25);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(150, 5, 230, 50);
        this.add(titleLabel);

        nameField = new JTextField();
        nameField.setBounds(150, 60, 150, 30);
        new TextPrompt(name, nameField);
        this.add(nameField);
    }

    private void createSpecieField(String specie) {
        specieField = new JTextField();
        specieField.setBounds(150, 105, 150, 30);
        new TextPrompt(specie, specieField);
        this.add(specieField);
    }

    private void createBreedField(String breed) {
        breedField = new JTextField();
        breedField.setBounds(150, 150, 150, 30);
        new TextPrompt(breed, breedField);
        this.add(breedField);
    }

    private void createAgeField(int age) {
        ageField = new JTextField();
        ageField.setBounds(150, 195, 150, 30);
        new TextPrompt(String.valueOf(age), ageField);
        ageField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '\b') {
                    e.consume();
                }
            }
        });
        this.add(ageField);
    }

    private void createOwnerTable(String ownerName) {
        String[] columnNames = { "ID", "Nombre", "Apellido" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ownerTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(ownerTable);
        scrollPane.setBounds(20, 290, 400, 150);
        this.add(scrollPane);

        loadOwnersData();

        searchOwnerField = new JTextField();
        searchOwnerField.setBounds(150, 250, 150, 30);
        new TextPrompt("Buscar Dueño", searchOwnerField);
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
            model.addRow(new Object[] { owner.getPersonId(), owner.getPersonName(), owner.getPersonLastName() });
        }
    }

    private void createEditButton() {
        JButton editButton = new JButton("Editar");
        editButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        editButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        editButton.setFocusPainted(false);
        editButton.setBounds(100, 470, 100, 40);
        editButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText().trim();
                String newSpecie = specieField.getText().trim();
                String newBreed = breedField.getText().trim();
                int newAge;

                if (newName.isEmpty() || newSpecie.isEmpty() || newBreed.isEmpty() || ageField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                newAge = Integer.parseInt(ageField.getText());

                int selectedRow = ownerTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = ownerTable.convertRowIndexToModel(selectedRow);
                    ownerId = Integer.parseInt(String.valueOf(ownerTable.getModel().getValueAt(selectedRow, 0)));
                }

                petPanel.getMainView().getPresenter().editPet(id,
                        petPanel.getMainView().getPresenter().createPet(id, newName, newSpecie, newBreed, newAge,
                                petPanel.getMainView().getPresenter().getPersonById(ownerId)));
                petPanel.loadPetsData();

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
        cancelButton.setBounds(250, 470, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}
