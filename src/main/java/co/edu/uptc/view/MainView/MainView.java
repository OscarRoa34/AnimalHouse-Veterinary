package co.edu.uptc.view.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;

import co.edu.uptc.Interfaces.VetInterface;
import co.edu.uptc.Interfaces.VetInterface.Presenter;
import co.edu.uptc.Utils.PropertiesService;
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
    private AppointmentPanel appointmentPanel;
    private AppointmentHistoryPanel appointmentHistoryPanel;
    private PetPanel petPanel;
    private UserPanel userPanel;
    private VaccinesPanel vaccinesPanel;

    private JButton petButton;
    private JButton appointmentButton;
    private JButton appointmentHistoryButton;
    private JButton vaccineButton;
    private JButton usersButton;

    private VetInterface.Presenter presenter;
    private PropertiesService p = new PropertiesService();

    public MainView() throws IOException {
        appointmentPanel = new AppointmentPanel(this);
        vaccinesPanel = new VaccinesPanel(this);
        appointmentHistoryPanel = new AppointmentHistoryPanel(this);
        petPanel = new PetPanel(this);
        userPanel = new UserPanel(this);
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
        presenter.loadAppointmentsFromJson(p.getProperties("appointmentsJson"));
        presenter.loadVaccinesFromJson(p.getProperties("vaccinesJson"));
        presenter.loadPersonsFromJson(p.getProperties("personsJson"));
        presenter.loadPetsFromJson(p.getProperties("petsJson"));

        appointmentPanel.loadPersonsData();
        appointmentPanel.loadPetsData();
        appointmentPanel.loadVaccinesData();
        appointmentHistoryPanel.loadAppointmentsData();
        petPanel.loadPetsData();
        vaccinesPanel.loadVaccinesData();
        userPanel.loadPersonsData();

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                presenter.saveAppointmentsToJson(p.getProperties("appointmentsJson"));
                presenter.saveVaccinesToJson(p.getProperties("vaccinesJson"));
                presenter.savePersonsToJson(p.getProperties("personsJson"));
                presenter.savePetsToJson(p.getProperties("petsJson"));
            }
        });
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
        panelChanger.add(petPanel, "pet");
        panelChanger.add(appointmentPanel, "appointment");
        panelChanger.add(appointmentHistoryPanel, "appointmentHistory");
        panelChanger.add(vaccinesPanel, "vaccine");
        panelChanger.add(userPanel, "user");
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
            appointmentHistoryPanel.loadAppointmentsData();
        } else if (e.getSource() == vaccineButton) {
            cardLayout.show(panelChanger, "vaccine");
        } else if (e.getSource() == usersButton) {
            cardLayout.show(panelChanger, "user");
        }
    }

    public AppointmentHistoryPanel getAppointmentHistoryPanel() {
        return appointmentHistoryPanel;
    }
}
