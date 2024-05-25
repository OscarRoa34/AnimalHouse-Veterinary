package co.edu.uptc.view.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;

import co.edu.uptc.PropertiesService;
import co.edu.uptc.Interfaces.VetInterface;
import co.edu.uptc.Interfaces.VetInterface.Presenter;
import co.edu.uptc.view.Aside.Aside;
import co.edu.uptc.view.Aside.AsideButton;
import co.edu.uptc.view.MainPanels.AppointmentHistoryPanel;
import co.edu.uptc.view.MainPanels.AppointmentPanel;
import co.edu.uptc.view.MainPanels.PetPanel;
import co.edu.uptc.view.MainPanels.UserPanel;
import co.edu.uptc.view.MainPanels.VaccinesPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainView extends JFrame implements ActionListener, VetInterface.View {

    private JPanel asidePanel;
    private JPanel panelChanger;
    private JButton petButton;
    private JButton appointmentButton;
    private JButton appointmentHistoryButton;
    private JButton vaccineButton;
    private JButton usersButton;
    private VetInterface.Presenter presenter;
    private PropertiesService p = new PropertiesService();

    private AppointmentPanel appointmentPanel;

    public MainView() throws IOException {
        appointmentPanel = new AppointmentPanel(this);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public VetInterface.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void begin() {
        this.setVisible(true);
        this.setTitle("Animal House");
        this.setSize(1200, 700);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            this.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        initGridBagLayout();
        createAsideButtons();
        createPanels();
    }

    private void initGridBagLayout() {
        GridBagConstraints gbc = new GridBagConstraints();
        asidePanel = new Aside();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(asidePanel, gbc);
        panelChanger = new JPanel(new CardLayout());
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.75;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(panelChanger, gbc);
    }

    private void createPanels() {
        panelChanger.add(new PetPanel(this), "pet");
        panelChanger.add(appointmentPanel, "appointment");
        panelChanger.add(new AppointmentHistoryPanel(this), "appointmentHistory");
        panelChanger.add(new VaccinesPanel(this), "vaccine");
        panelChanger.add(new UserPanel(this), "user");
    }

    private void createAsideButtons() {
        petButton = new AsideButton("Mascotas", new ImageIcon(p.getProperties("petsImage")));
        petButton.setBounds(50, 173, 200, 60);
        petButton.addActionListener(this);

        appointmentButton = new AsideButton("Citas", new ImageIcon(p.getProperties("appointmentsImage")));
        appointmentButton.setBounds(50, 258, 200, 60);
        appointmentButton.addActionListener(this);

        vaccineButton = new AsideButton("Vacunas", new ImageIcon(p.getProperties("vaccinesImage")));
        vaccineButton.setBounds(50, 343, 200, 60);
        vaccineButton.addActionListener(this);

        appointmentHistoryButton = new AsideButton("Historial de citas",
                new ImageIcon(p.getProperties("appointmentHistoryImage")));
        appointmentHistoryButton.setBounds(50, 428, 200, 60);
        appointmentHistoryButton.addActionListener(this);

        usersButton = new AsideButton("Usuarios", new ImageIcon(p.getProperties("usersImage")));
        usersButton.setBounds(50, 513, 200, 60);
        usersButton.addActionListener(this);

        asidePanel.add(petButton);
        asidePanel.add(appointmentButton);
        asidePanel.add(appointmentHistoryButton);
        asidePanel.add(vaccineButton);
        asidePanel.add(usersButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) panelChanger.getLayout();
        if (e.getSource() == petButton) {
            cardLayout.show(panelChanger, "pet");
        } else if (e.getSource() == appointmentButton) {
            cardLayout.show(panelChanger, "appointment");
            appointmentPanel.loadPersonsData();
            appointmentPanel.loadPetsData();
            appointmentPanel.loadVaccinesData();
        } else if (e.getSource() == appointmentHistoryButton) {
            cardLayout.show(panelChanger, "appointmentHistory");
        } else if (e.getSource() == vaccineButton) {
            cardLayout.show(panelChanger, "vaccine");
        } else if (e.getSource() == usersButton) {
            cardLayout.show(panelChanger, "user");
        }
    }

}
