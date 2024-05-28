package co.edu.uptc.view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import co.edu.uptc.Pojos.Person;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainView.MainView;
import co.edu.uptc.view.PopUps.CreateUserPopUp;
import co.edu.uptc.view.PopUps.EditUserPopUp;

public class UserPanel extends JPanel {

    private JTextField searchField;
    private JTable usersTable;
    private DefaultTableModel model;
    private MainView mainView;
    private TableRowSorter<DefaultTableModel> tableRowSorter;

    public UserPanel(MainView mainView) {
        this.mainView = mainView;
        this.setBackground(GlobalView.PANEL_BACKGROUND);
        this.setLayout(null);
        createTitle();
        createSearchBar();
        createUsersTable();
        createButtons();
    }

    private void createTitle() {
        JLabel titleLabel = new JLabel("Usuarios");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 35);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(350, 20, 200, 50);
        this.add(titleLabel);
    }

    private void createSearchBar() {
        JPanel searchBarPanel = new JPanel();
        searchBarPanel.setBackground(GlobalView.SEARCHBAR_BACKGROUND);
        searchBarPanel.setLayout(new BorderLayout());
        searchBarPanel.setBounds(340, 80, 200, 30);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        searchBarPanel.add(searchField);
        new TextPrompt("Documento del usuario", searchField);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().trim();
                filterUsersTable(searchText);
            }
        });
        this.add(searchBarPanel);
    }

    private void createUsersTable() {
        String[] columnNames = { "ID", "Nombre", "Apellido", "Edad", "Tipo de documento", "Numero de documento" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        usersTable = new JTable(model);
        usersTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableRowSorter = new TableRowSorter<>(model);
        usersTable.setRowSorter(tableRowSorter);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < usersTable.getColumnCount(); i++) {
            usersTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(usersTable);
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

    private JButton createAddButton() {
        JButton addButton = new JButton("Agregar");
        addButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        addButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateUserPopUp createUserWindow;
                try {
                    createUserWindow = new CreateUserPopUp(getInstance());
                    createUserWindow.setVisible(true);
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
                int selectedRow = usersTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = usersTable.convertRowIndexToModel(selectedRow);

                    int id = Integer.parseInt(String.valueOf(usersTable.getModel().getValueAt(selectedRow, 0)));
                    String name = (String) usersTable.getModel().getValueAt(selectedRow, 1);
                    String lastName = (String) usersTable.getModel().getValueAt(selectedRow, 2);
                    String age = String.valueOf(usersTable.getModel().getValueAt(selectedRow, 3));
                    String docType = String.valueOf(usersTable.getModel().getValueAt(selectedRow, 4));
                    String docNumber = String.valueOf(usersTable.getModel().getValueAt(selectedRow, 5));

                    try {
                        EditUserPopUp editUserWindow = new EditUserPopUp(id, name, lastName, age, docType, docNumber,
                                getInstance());
                        editUserWindow.setVisible(true);
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
                int selectedRow = usersTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = usersTable.convertRowIndexToModel(selectedRow);
                    int id = Integer.parseInt(String.valueOf(usersTable.getModel().getValueAt(selectedRow, 0)));

                    int confirmation = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro de que quieres eliminar este usuario?", "Confirmación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        mainView.getPresenter().removePersonById(id);
                        model.removeRow(selectedRow);
                        loadPersonsData();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un usuario para eliminar.", "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return deleteButton;
    }

    public void loadPersonsData() {
        List<Person> users = mainView.getPresenter().getPersons();

        model.setRowCount(0);
        for (Person person : users) {
            model.addRow(new Object[] {
                    person.getPersonId(),
                    person.getPersonName(),
                    person.getPersonLastName(),
                    person.getPersonAge(),
                    person.getDocumentType(),
                    person.getDocumentNumber()
            });
        }
    }

    private void filterUsersTable(String searchText) {
        if (searchText.isEmpty()) {
            tableRowSorter.setRowFilter(null);
        } else {
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 5));
        }
    }

    private UserPanel getInstance() {
        return this;
    }

    public MainView getMainView() {
        return mainView;
    }
}