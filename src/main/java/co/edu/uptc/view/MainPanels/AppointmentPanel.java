package co.edu.uptc.view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.TextPrompt;
import co.edu.uptc.models.Person;
import co.edu.uptc.models.Pet;
import co.edu.uptc.models.Vaccine;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainView.MainView;

public class AppointmentPanel extends JPanel {

    private JTextField petSearchField;
    private JTextField vaccineSearchField;
    private JTextField userSearchField;
    private JTable petTable;
    private JTable vaccinesTable;
    private JTable usersTable;
    private MainView mainView;
    private DefaultTableModel modelPets;
    private DefaultTableModel modelVaccines;
    private DefaultTableModel modelUsers;
    private static int CONTADOR_ID = 1;

    public AppointmentPanel(MainView mainView) {
        this.mainView = mainView;
        this.setBackground(GlobalView.PANEL_BACKGROUND);
        this.setLayout(null);
        createTitle();
        createPetsSearchBar();
        createPetTable();
        createVaccinesSearchBar();
        createVaccinesTable();
        createUsersSearchBar();
        createUsersTable();
        createAddButton();
    }

    private void createTitle() {
        JLabel titleLabel = new JLabel("Citas");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 35);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(400, 20, 200, 30);
        this.add(titleLabel);
    }

    private void createPetsSearchBar() {
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        searchBarPanel.setLayout(new BorderLayout());
        searchBarPanel.setBounds(300, 70, 300, 30);

        petSearchField = new JTextField();
        petSearchField.setPreferredSize(new Dimension(200, 30));
        new TextPrompt("Nombre de la mascota", petSearchField);
        searchBarPanel.add(petSearchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Buscar");
        searchButton.setFocusPainted(false);
        searchButton.setBackground(GlobalView.ASIDE_BORDERCOLOR);
        searchButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para buscar mascotas si es necesario
            }
        });
        this.add(searchBarPanel);
    }

    private void createPetTable() {
        String[] columnNames = { "ID", "Nombre", "Especie", "Raza", "Dueño" };
        modelPets = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        petTable = new JTable(modelPets);

        JScrollPane scrollPane = new JScrollPane(petTable);
        scrollPane.setBounds(20, 120, 850, 100);
        this.add(scrollPane);
    }

    public void loadPetsData() {
        List<Pet> pets = mainView.getPresenter().getPets();

        modelPets.setRowCount(0);
        for (Pet pet : pets) {
            modelPets.addRow(new Object[] {
                    pet.getPetId(),
                    pet.getPetName(),
                    pet.getSpecie(),
                    pet.getBreed(),
                    pet.getOwner().getPersonName()
            });
        }
    }

    private void createVaccinesSearchBar() {
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        searchBarPanel.setLayout(new BorderLayout());
        searchBarPanel.setBounds(300, 240, 300, 30);

        vaccineSearchField = new JTextField();
        vaccineSearchField.setPreferredSize(new Dimension(200, 30));
        new TextPrompt("Nombre de la vacuna", vaccineSearchField);
        searchBarPanel.add(vaccineSearchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Buscar");
        searchButton.setFocusPainted(false);
        searchButton.setBackground(GlobalView.ASIDE_BORDERCOLOR);
        searchButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para buscar vacunas si es necesario
            }
        });
        this.add(searchBarPanel);
    }

    private void createVaccinesTable() {
        String[] columnNames = { "ID", "Nombre", "Vida Util" };
        modelVaccines = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vaccinesTable = new JTable(modelVaccines);

        JScrollPane scrollPane = new JScrollPane(vaccinesTable);
        scrollPane.setBounds(20, 290, 850, 100);
        this.add(scrollPane);
    }

    public void loadVaccinesData() {
        List<Vaccine> vaccines = mainView.getPresenter().getVaccines();

        modelVaccines.setRowCount(0);
        for (Vaccine vaccine : vaccines) {
            modelVaccines.addRow(new Object[] {
                    vaccine.getVaccineId(),
                    vaccine.getVaccineName(),
                    vaccine.getLifeSpan()
            });
        }
    }

    private void createUsersSearchBar() {
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        searchBarPanel.setLayout(new BorderLayout());
        searchBarPanel.setBounds(300, 410, 300, 30);

        userSearchField = new JTextField();
        userSearchField.setPreferredSize(new Dimension(200, 30));
        new TextPrompt("Documento del usuario", userSearchField);
        searchBarPanel.add(userSearchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Buscar");
        searchButton.setFocusPainted(false);
        searchButton.setBackground(GlobalView.ASIDE_BORDERCOLOR);
        searchButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para buscar usuarios si es necesario
            }
        });
        this.add(searchBarPanel);
    }

    private void createUsersTable() {
        String[] columnNames = { "ID", "Nombre", "Apellido", "Edad", "Tipo de documento", "Numero de documento" };
        modelUsers = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        usersTable = new JTable(modelUsers);

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBounds(20, 460, 850, 100);
        this.add(scrollPane);
    }

    public void loadPersonsData() {
        List<Person> users = mainView.getPresenter().getPersons();

        modelUsers.setRowCount(0);
        for (Person person : users) {
            modelUsers.addRow(new Object[] {
                    person.getPersonId(),
                    person.getPersonName(),
                    person.getPersonLastName(),
                    person.getPersonAge(),
                    person.getDocumentType(),
                    person.getDocumentNumber()
            });
        }
    }

    private void createAddButton() {
        JButton addButton = new JButton("Crear Cita");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBounds(380, 580, 150, 40);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // createAppointment();
            }
        });
        this.add(addButton);
    }

    // TO DO Crear y agregar appointment, solucionar usuario al agregar mascota,
    // Funcion boton edita

    // private void createAppointment() {
    // int[] selectedPetRows = petTable.getSelectedRows();
    // int[] selectedVaccineRows = vaccinesTable.getSelectedRows();
    // int[] selectedUserRows = usersTable.getSelectedRows();

    // if (selectedPetRows.length == 0 || selectedVaccineRows.length == 0 ||
    // selectedUserRows.length == 0) {
    // JOptionPane.showMessageDialog(null, "Error", "Porfavor seleccione
    // correctamente", 2);
    // return;
    // }

    // List<Pet> selectedPets = new ArrayList<>();
    // for (int row : selectedPetRows) {
    // int petId = (int) petTable.getValueAt(row, 0);
    // Pet pet = mainView.getPresenter().getPetById(petId);
    // selectedPets.add(pet);
    // }

    // // Obtener las vacunas seleccionadas
    // List<Vaccine> selectedVaccines = new ArrayList<>();
    // for (int row : selectedVaccineRows) {
    // int vaccineId = (int) vaccinesTable.getValueAt(row, 0); // Suponiendo que el
    // ID de la vacuna está en la
    // // columna 0
    // Vaccine vaccine = mainView.getPresenter().getVaccineById(vaccineId);
    // selectedVaccines.add(vaccine);
    // }

    // // Obtener los usuarios seleccionados
    // List<Person> selectedUsers = new ArrayList<>();
    // for (int row : selectedUserRows) {
    // int userId = (int) usersTable.getValueAt(row, 0); // Suponiendo que el ID del
    // usuario está en la columna 0
    // Person user = mainView.getPresenter().getPersonById(userId);
    // selectedUsers.add(user);
    // }

    // mainView.getPresenter().createAppointment(CONTADOR_ID, selectedPets.get(0),
    // selectedVaccines,
    // selectedUsers.get(0));
    // }
}
