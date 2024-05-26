package co.edu.uptc.view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.models.Pet;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainView.MainView;
import co.edu.uptc.view.PopUps.CreatePetPopUp;
import co.edu.uptc.view.PopUps.EditPetPopUp;

public class PetPanel extends JPanel {

    private JTextField searchField;
    private JTable petsTable;
    @SuppressWarnings("unused")
    private TextPrompt txtPrompt;
    private MainView mainView;
    private DefaultTableModel model;

    public PetPanel(MainView mainView) {
        this.mainView = mainView;
        this.setBackground(GlobalView.PANEL_BACKGROUND);
        this.setLayout(null);
        createTitle();
        createSearchBar();
        createPetsTable();
        createButtons();
    }

    private void createTitle() {
        JLabel titleLabel = new JLabel("Mascotas");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 35);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(369, 20, 200, 50);
        this.add(titleLabel);
    }

    private void createSearchBar() {
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        searchBarPanel.setLayout(new BorderLayout());
        searchBarPanel.setBounds(300, 80, 300, 30);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        txtPrompt = new TextPrompt("Nombre de la mascota", searchField);
        searchBarPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Buscar");
        searchButton.setFocusPainted(false);
        searchButton.setBackground(GlobalView.ASIDE_BORDERCOLOR);
        searchButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                filterPetsTable(searchText);
            }
        });
        this.add(searchBarPanel);
    }

    private void createPetsTable() {
        String[] columnNames = { "ID", "Nombre", "Especie", "Raza", "Edad", "Dueño" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        petsTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(petsTable);
        scrollPane.setBounds(20, 130, 850, 400);
        this.add(scrollPane);
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(GlobalView.PANEL_BACKGROUND);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        buttonPanel.setBounds(20, 550, 880, 50);

        JButton addButton = createAddButton();
        addButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(addButton);

        JButton editButton = createEditButton();
        editButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(editButton);

        JButton deleteButton = createRemoveButton();
        deleteButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(deleteButton);

        this.add(buttonPanel);
    }

    private PetPanel getInstance() {
        return this;
    }

    public MainView getMainView() {
        return mainView;
    }

    private JButton createAddButton() {
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreatePetPopUp(getInstance()).setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        return addButton;
    }

    private JButton createEditButton() {
        JButton editButton = new JButton("Editar");
        editButton.setBackground(GlobalView.BUTTONS_EDIT_BACKGROUND);
        editButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_EDIT_COLOR, 2));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = petsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = petsTable.convertRowIndexToModel(selectedRow);

                    int id = Integer.parseInt(String.valueOf(petsTable.getModel().getValueAt(selectedRow, 0)));
                    String name = (String) petsTable.getModel().getValueAt(selectedRow, 1);
                    String specie = (String) petsTable.getModel().getValueAt(selectedRow, 2);
                    String breed = (String) petsTable.getModel().getValueAt(selectedRow, 3);
                    int age = Integer.parseInt(String.valueOf(petsTable.getModel().getValueAt(selectedRow, 4)));
                    int ownerId = Integer.parseInt(String.valueOf(petsTable.getModel().getValueAt(selectedRow, 5)));

                    try {
                        EditPetPopUp editPetWindow = new EditPetPopUp(id, name, specie, breed, age, ownerId,
                                getInstance());
                        editPetWindow.setVisible(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        return editButton;
    }

    private JButton createRemoveButton() {
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        deleteButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = petsTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = petsTable.convertRowIndexToModel(selectedRow);
                    int id = Integer.parseInt(String.valueOf(petsTable.getModel().getValueAt(selectedRow, 0)));

                    int confirmation = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro de que quieres eliminar esta mascota?", "Confirmación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        mainView.getPresenter().removePetById(id);
                        model.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una mascota para eliminar.", "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return deleteButton;
    }

    public void loadPetsData() {
        List<Pet> pets = mainView.getPresenter().getPets();

        model.setRowCount(0);
        for (Pet pet : pets) {
            model.addRow(new Object[] {
                    pet.getPetId(),
                    pet.getPetName(),
                    pet.getSpecie(),
                    pet.getBreed(),
                    pet.getPetAge(),
                    pet.getOwner().getPersonId()
            });
        }
    }

    private void filterPetsTable(String searchText) {
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        petsTable.setRowSorter(tableRowSorter);

        if (searchText.length() == 0) {
            tableRowSorter.setRowFilter(null);
        } else {
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1));
        }
    }
}
