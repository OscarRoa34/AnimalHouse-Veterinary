package co.edu.uptc.view.PopUps;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

import co.edu.uptc.Pojos.Pet;
import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainPanels.AppointmentHistoryPanel;

public class CreateFilterByWeightPopUp extends JDialog {

    private JTextField weightField;
    private JTable petTable;
    private DefaultTableModel petModel;
    private AppointmentHistoryPanel appointmentHistoryPanel;
    private PropertiesService p = new PropertiesService();

    public CreateFilterByWeightPopUp(AppointmentHistoryPanel appointmentHistoryPanel) throws IOException {
        this.appointmentHistoryPanel = appointmentHistoryPanel;
        this.setTitle("Consultar Por Peso");
        this.setSize(600, 650);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createWeightField();
        createPetTable();
        createLighterButton();
        createHeavierButton();
    }

    private void createWeightField() {
        weightField = new JTextField();
        weightField.setBounds(200, 20, 200, 30);
        new TextPrompt("Peso de la mascota en kilos", weightField);
        weightField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume();
                }
            }
        });
        this.add(weightField);
    }

    private void createPetTable() {
        String[] petColumnNames = { "Nombre de la mascota", "Peso", "Responsable" };
        petModel = new DefaultTableModel(petColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        petTable = new JTable(petModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < petTable.getColumnCount(); i++) {
            petTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(petTable);
        scrollPane.setBounds(50, 70, 500, 400);
        this.add(scrollPane);
    }

    private void createHeavierButton() {
        JButton heavierThanButton = new JButton("Pesos mayores >");
        heavierThanButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        heavierThanButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        heavierThanButton.setFocusPainted(false);
        heavierThanButton.setBounds(140, 500, 150, 40);
        heavierThanButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        heavierThanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String weightText = weightField.getText().trim();
                if (!weightText.isEmpty()) {
                    double weight = Double.parseDouble(weightText);
                    filterPetsByWeight(weight, true);
                }
            }
        });
        this.add(heavierThanButton);
    }

    private void createLighterButton() {
        JButton lighterThanButton = new JButton("Pesos menores <");
        lighterThanButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        lighterThanButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        lighterThanButton.setFocusPainted(false);
        lighterThanButton.setBounds(310, 500, 150, 40);
        lighterThanButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        lighterThanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String weightText = weightField.getText().trim();
                if (!weightText.isEmpty()) {
                    double weight = Double.parseDouble(weightText);
                    filterPetsByWeight(weight, false);
                }
            }
        });
        this.add(lighterThanButton);
    }

    private void filterPetsByWeight(double weight, boolean heavier) {
        petModel.setRowCount(0);
        List<Pet> pets = appointmentHistoryPanel.getMainView().getPresenter().getPets();
        for (Pet pet : pets) {
            if ((heavier && pet.getWeight() >= weight) || (!heavier && pet.getWeight() <= weight)) {
                petModel.addRow(new Object[] { pet.getPetName(), pet.getWeight(),
                        pet.getOwner().getPersonName() + " " + pet.getOwner().getPersonLastName() });
            }
        }
    }
}
