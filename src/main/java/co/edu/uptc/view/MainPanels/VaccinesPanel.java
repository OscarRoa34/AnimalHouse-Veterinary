package co.edu.uptc.view.MainPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import co.edu.uptc.Pojos.Vaccine;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainView.MainView;
import co.edu.uptc.view.PopUps.CreateVaccinePopUp;
import co.edu.uptc.view.PopUps.EditVaccinePopUp;

public class VaccinesPanel extends JPanel {

    private JTextField searchField;
    private JTable vaccinesTable;
    private MainView mainView;
    private DefaultTableModel model;

    public VaccinesPanel(MainView mainView) {
        this.mainView = mainView;
        this.setBackground(GlobalView.PANEL_BACKGROUND);
        this.setLayout(null);
        createTitle();
        createSearchBar();
        createVaccinesTable();
        createButtons();
    }

    private void createTitle() {
        JLabel titleLabel = new JLabel("Vacunas");
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
        searchBarPanel.setBounds(340, 80, 200, 30);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        new TextPrompt("Buscar Vacuna por el nombre", searchField);
        searchBarPanel.add(searchField, BorderLayout.CENTER);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().trim();
                filterVaccinesTable(searchText);
            }
        });
        this.add(searchBarPanel);
    }

    private void createVaccinesTable() {
        String[] columnNames = { "ID", "Nombre", "Vida Util (Días)" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        vaccinesTable = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        vaccinesTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(vaccinesTable);
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

    private VaccinesPanel getInstance() {
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
                    new CreateVaccinePopUp(getInstance()).setVisible(true);
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
                int selectedRow = vaccinesTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = vaccinesTable.convertRowIndexToModel(selectedRow);

                    int id = Integer.parseInt(String.valueOf(vaccinesTable.getModel().getValueAt(selectedRow, 0)));
                    String name = (String) vaccinesTable.getModel().getValueAt(selectedRow, 1);
                    int lifeSpan = Integer
                            .parseInt(String.valueOf(vaccinesTable.getModel().getValueAt(selectedRow, 2)));

                    try {
                        EditVaccinePopUp editVaccineWindow = new EditVaccinePopUp(id, name, lifeSpan, getInstance());
                        editVaccineWindow.setVisible(true);
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
                int selectedRow = vaccinesTable.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedRow = vaccinesTable.convertRowIndexToModel(selectedRow);
                    int id = Integer.parseInt(String.valueOf(vaccinesTable.getModel().getValueAt(selectedRow, 0)));

                    int confirmation = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro de que quieres eliminar esta vacuna?", "Confirmación",
                            JOptionPane.YES_NO_OPTION);
                    if (confirmation == JOptionPane.YES_OPTION) {
                        mainView.getPresenter().removeVaccineById(id);
                        model.removeRow(selectedRow);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una vacuna para eliminar.", "Advertencia",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return deleteButton;
    }

    public void loadVaccinesData() {
        List<Vaccine> vaccines = mainView.getPresenter().getVaccines();

        model.setRowCount(0);
        for (Vaccine vaccine : vaccines) {
            model.addRow(new Object[] {
                    vaccine.getVaccineId(),
                    vaccine.getVaccineName(),
                    vaccine.getLifeSpan()
            });
        }
    }

    private void filterVaccinesTable(String searchText) {
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(model);
        vaccinesTable.setRowSorter(tableRowSorter);

        if (searchText.length() == 0) {
            tableRowSorter.setRowFilter(null);
        } else {
            tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1));
        }
    }
}
