package co.edu.uptc.view.MainPanels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;

import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainView.MainView;
import co.edu.uptc.view.PopUps.CreateFilterByWeightPopUp;
import co.edu.uptc.Pojos.Appointment;
import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AppointmentHistoryPanel extends JPanel {

    private JTextField searchField;
    private JTable appointmentHistoryTable;
    private MainView mainView;
    private DefaultTableModel model;
    private PropertiesService p = new PropertiesService();

    public AppointmentHistoryPanel(MainView mainView) {
        this.mainView = mainView;
        this.setBackground(GlobalView.PANEL_BACKGROUND);
        this.setLayout(null);
        createTitle();
        createSearchBar();
        createAppointmentHistoryTable();
        addCalendarButton();
        createVaccineFilterButton();
        createResetFiltersButton();
        createFilterByWeightButton();
    }

    public MainView getMainView() {
        return this.mainView;
    }

    private void createTitle() {
        JLabel titleLabel = new JLabel("Historial de citas");
        Font titleFont = titleLabel.getFont();
        Font biggerFont = titleFont.deriveFont(Font.BOLD, 35);
        titleLabel.setFont(biggerFont);
        titleLabel.setBounds(310, 20, 300, 50);
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
        new TextPrompt("Buscar cita por nombre del dueño", searchField);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = searchField.getText().trim();
                filterAppointmentsByOwner(searchText);
            }
        });
        this.add(searchBarPanel);
    }

    private void createAppointmentHistoryTable() {
        String[] columnNames = { "ID", "Fecha de la cita", "Mascota", "Vacunas", "Dueño",
                "Fecha de expiración de vacuna", "Peso de la mascota", };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        appointmentHistoryTable = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < appointmentHistoryTable.getColumnCount(); i++) {
            appointmentHistoryTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(appointmentHistoryTable);
        scrollPane.setBounds(20, 130, 850, 400);
        this.add(scrollPane);
    }

    private void addCalendarButton() {
        JButton calendarButton = createCalendarButton();
        calendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = createCalendarDialog();
                dialog.setVisible(true);
            }
        });
        this.add(calendarButton);
    }

    private JButton createCalendarButton() {
        JButton calendarButton = new JButton("Consultar Cita Por Fecha");
        calendarButton.setBackground(GlobalView.BUTTONS_EDIT_BACKGROUND);
        calendarButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        calendarButton.setFocusPainted(false);
        calendarButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_EDIT_COLOR, 2));
        calendarButton.setBounds(240, 550, 200, 30);
        return calendarButton;
    }

    private JDialog createCalendarDialog() {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Calendario");
        dialog.setSize(400, 300);
        try {
            dialog.setIconImage(ImageIO.read(new File(p.getProperties("principalFrameLogo"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setLocationRelativeTo(null);

        JCalendar calendar = createCalendar();
        dialog.add(calendar, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel(dialog, calendar);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        return dialog;
    }

    private JCalendar createCalendar() {
        JCalendar calendar = new JCalendar();
        calendar.setBackground(GlobalView.ASIDE_FOREGROUND);
        calendar.setForeground(GlobalView.PANEL_FOREGROUND);
        return calendar;
    }

    private JPanel createButtonPanel(JDialog dialog, JCalendar calendar) {
        JPanel buttonPanel = new JPanel();
        JButton selectButton = createSelectButton(dialog, calendar);
        JButton cancelButton = createCancelButton(dialog);
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    private JButton createSelectButton(JDialog dialog, JCalendar calendar) {
        JButton selectButton = new JButton("Seleccionar");
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar selectedCalendar = calendar.getCalendar();
                Date selectedDate = selectedCalendar.getTime();
                LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                filterAppointmentsByDate(localDate);
                dialog.dispose();
            }
        });
        return selectButton;
    }

    private JButton createCancelButton(JDialog dialog) {
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        return cancelButton;
    }

    private void createVaccineFilterButton() {
        JButton vaccineFilterButton = new JButton("Filtrar por vacunas próximas a vencer (1 Semana)");
        vaccineFilterButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        vaccineFilterButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        vaccineFilterButton.setFocusPainted(false);
        vaccineFilterButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        vaccineFilterButton.setBounds(450, 550, 300, 30);
        vaccineFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterAppointmentsByVaccineExpiry();
            }
        });
        this.add(vaccineFilterButton);
    }

    private void createResetFiltersButton() {
        JButton resetFiltersButton = new JButton("Resetear Filtros");
        resetFiltersButton.setBackground(GlobalView.BUTTONS_REMOVE_BACKGROUND);
        resetFiltersButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        resetFiltersButton.setFocusPainted(false);
        resetFiltersButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_REMOVE_COLOR, 2));
        resetFiltersButton.setBounds(240, 600, 200, 30);
        resetFiltersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFilters();
            }
        });
        this.add(resetFiltersButton);
    }

    public void resetFilters() {
        searchField.setText("");
        loadAppointmentsData();
    }

    private AppointmentHistoryPanel getInstance() {
        return this;
    }

    private void createFilterByWeightButton() {
        JButton vaccineFilterButton = new JButton("Filtrar por peso");
        vaccineFilterButton.setBackground(GlobalView.BUTTONS_ADD_BACKGROUND);
        vaccineFilterButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        vaccineFilterButton.setFocusPainted(false);
        vaccineFilterButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_ADD_COLOR, 2));
        vaccineFilterButton.setBounds(450, 600, 300, 30);
        vaccineFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CreateFilterByWeightPopUp(getInstance()).setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        this.add(vaccineFilterButton);
    }

    public void filterAppointmentsByDate(LocalDate selectedDate) {
        List<Appointment> appointments = mainView.getPresenter().getAppointments();
        model.setRowCount(0);
        for (Appointment appointment : appointments) {
            LocalDate appointmentDate = appointment.getAppointmentDate();
            if (appointmentDate.equals(selectedDate)) {
                model.addRow(new Object[] {
                        appointment.getAppointmentId(),
                        appointment.getAppointmentDate(),
                        appointment.getPet().getPetName(),
                        appointment.getVaccineNames(),
                        appointment.getResponsible().getPersonName() + " "
                                + appointment.getResponsible().getPersonLastName(),
                        appointment.getEarliestVaccineExpiryDate(), appointment.getPet().getWeight()
                });
            }
        }
    }

    public void filterAppointmentsByOwner(String ownerName) {
        List<Appointment> appointments = mainView.getPresenter().getAppointments();
        model.setRowCount(0);
        for (Appointment appointment : appointments) {
            String fullName = appointment.getResponsible().getPersonName() + " "
                    + appointment.getResponsible().getPersonLastName();
            if (fullName.toLowerCase().contains(ownerName.toLowerCase())) {
                model.addRow(new Object[] {
                        appointment.getAppointmentId(),
                        appointment.getAppointmentDate(),
                        appointment.getPet().getPetName(),
                        appointment.getVaccineNames(),
                        fullName,
                        appointment.getEarliestVaccineExpiryDate(), appointment.getPet().getWeight()
                });
            }
        }
    }

    // Profe, Honestamente este metodo de filtrado fue hecho con chatGpt porque me
    // quedó grande
    public void filterAppointmentsByVaccineExpiry() {
        List<Appointment> appointments = mainView.getPresenter().getAppointments();
        model.setRowCount(0);
        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromNow = today.plusWeeks(1);

        for (Appointment appointment : appointments) {
            List<LocalDate> expireDates = appointment.calculateVaccineExpireDates();
            boolean vaccineExpiringSoon = expireDates.stream().anyMatch(date -> !date.isAfter(oneWeekFromNow));
            if (vaccineExpiringSoon) {
                model.addRow(new Object[] {
                        appointment.getAppointmentId(),
                        appointment.getAppointmentDate(),
                        appointment.getPet().getPetName(),
                        appointment.getVaccineNames(),
                        appointment.getResponsible().getPersonName() + " "
                                + appointment.getResponsible().getPersonLastName(),
                        appointment.getEarliestVaccineExpiryDate(), appointment.getPet().getWeight()
                });
            }
        }
    }

    public void loadAppointmentsData() {
        List<Appointment> appointments = mainView.getPresenter().getAppointments();
        model.setRowCount(0);
        for (Appointment appointment : appointments) {
            model.addRow(new Object[] {
                    appointment.getAppointmentId(),
                    appointment.getAppointmentDate(),
                    appointment.getPet().getPetName(),
                    appointment.getVaccineNames(),
                    appointment.getResponsible().getPersonName() + " "
                            + appointment.getResponsible().getPersonLastName(),
                    appointment.getEarliestVaccineExpiryDate(),
                    appointment.getPet().getWeight()
            });
        }
    }
}
