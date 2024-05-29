package co.edu.uptc.view.PopUps;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import co.edu.uptc.Pojos.Vaccine;
import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainPanels.VaccinesPanel;

public class EditVaccinePopUp extends JDialog {
    private JTextField nameField;
    private JTextField lifeSpanField;
    private PropertiesService p = new PropertiesService();
    private VaccinesPanel vaccinesPanel;
    private int id;

    public EditVaccinePopUp(int id, String name, int lifeSpan, VaccinesPanel vaccinesPanel) throws IOException {
        this.id = id;
        this.vaccinesPanel = vaccinesPanel;
        this.setTitle("Editar Vacuna");
        this.setSize(350, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLayout(null);
        this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        createNameField(name);
        createLifeSpanField(lifeSpan);
        createEditButton();
        createCancelButton();
    }

    private void createNameField(String name) {
        JLabel titleLabel = new JLabel("Editar Vacuna");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 25);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(75, 5, 230, 50);
        this.add(titleLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 60, 150, 30);
        new TextPrompt(name, nameField);
        this.add(nameField);
    }

    private void createLifeSpanField(int lifeSpan) {
        lifeSpanField = new JTextField();
        lifeSpanField.setBounds(100, 105, 150, 30);
        new TextPrompt(String.valueOf(lifeSpan), lifeSpanField);
        this.add(lifeSpanField);
    }

    private void createEditButton() {
        JButton editButton = new JButton("Editar");
        editButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        editButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        editButton.setFocusPainted(false);
        editButton.setBounds(50, 160, 100, 40);
        editButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newName = nameField.getText().trim();

                if (newName.isEmpty() || lifeSpanField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese todos los campos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int newLifeSpan = Integer.parseInt(lifeSpanField.getText());
                Vaccine editedVaccine = new Vaccine(id, newName, newLifeSpan);

                vaccinesPanel.getMainView().getPresenter().editVaccine(id, editedVaccine);
                vaccinesPanel.loadVaccinesData();

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
        cancelButton.setBounds(190, 160, 100, 40);
        cancelButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancelButton);
    }
}
