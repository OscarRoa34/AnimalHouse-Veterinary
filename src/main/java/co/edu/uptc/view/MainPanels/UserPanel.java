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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.models.Person;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainView.MainView;
import co.edu.uptc.view.PopUps.CreateUserPopUp;
import co.edu.uptc.view.PopUps.EditUserPopUp;

public class UserPanel extends JPanel {

    private JTextField searchField;
    private JTable usersTable;
    @SuppressWarnings("unused")
    private TextPrompt txtPrompt;
    private DefaultTableModel model;
    private MainView mainView;

    public UserPanel(MainView mainView) {
        this.mainView = mainView;
        this.setBackground(GlobalView.PANEL_BACKGROUND);
        this.setLayout(null);
        createTitle();
        createSearchBar();
        createUsersTable();
        createButtons();
        loadPersonsData();
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
        searchBarPanel.setBounds(300, 80, 300, 30);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        txtPrompt = new TextPrompt("Documento del usuario", searchField);
        searchBarPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Buscar");
        searchButton.setFocusPainted(false);
        searchButton.setBackground(GlobalView.ASIDE_BORDERCOLOR);
        searchButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        searchBarPanel.add(searchButton, BorderLayout.EAST);
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

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBounds(20, 130, 850, 400);
        this.add(scrollPane);
    }

    private void createButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(GlobalView.PANEL_BACKGROUND);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        buttonPanel.setBounds(20, 550, 880, 50);

        JButton addButton = createAddButon();
        addButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(addButton);

        JButton editButton = createEditButon();
        editButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(editButton);

        JButton deleteButton = createRemoveButon();
        deleteButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(deleteButton);

        this.add(buttonPanel);
    }

    private UserPanel getInstance() {
        return this;
    }

    public MainView getMainView() {
        return mainView;
    }

    private JButton createAddButon() {
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

    private JButton createEditButon() {
        JButton editButton = new JButton("Editar");
        editButton.setBackground(GlobalView.BUTTONS_EDIT_BACKGROUND);
        editButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_EDIT_COLOR, 2));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditUserPopUp().setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        return editButton;
    }

    private JButton createRemoveButon() {
        JButton deleteButton = new JButton("Eliminar");
        deleteButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        deleteButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
}
