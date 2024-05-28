package co.edu.uptc.view.MainPanels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;

import co.edu.uptc.view.GlobalView;
import co.edu.uptc.view.MainView.MainView;
import co.edu.uptc.Utils.PropertiesService;
import co.edu.uptc.Utils.TextPrompt;
import co.edu.uptc.models.Appointment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        searchBarPanel.setBounds(300, 80, 300, 30);

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        new TextPrompt("Nombre del dueño o responsable", searchField);
        searchBarPanel.add(searchField, BorderLayout.CENTER);

        JButton searchButton = new JButton("Buscar");
        searchButton.setFocusPainted(false);
        searchButton.setBackground(GlobalView.ASIDE_BORDERCOLOR);
        searchButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterAppointmentsByOwner(searchField.getText());
            }
        });
        searchBarPanel.add(searchButton, BorderLayout.EAST);
        this.add(searchBarPanel);
    }

    private void createAppointmentHistoryTable() {
        String[] columnNames = { "ID", "Fecha de la cita", "Mascota", "Vacunas", "Dueño" };
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        appointmentHistoryTable = new JTable(model);

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
        JButton calendarButton = new JButton("Calendario");
        calendarButton.setBackground(GlobalView.BUTTONS_EDIT_BACKGROUND);
        calendarButton.setForeground(GlobalView.BUTTONS_FOREGROUND);
        calendarButton.setFocusPainted(false);
        calendarButton.setBorder(BorderFactory.createLineBorder(GlobalView.BUTTONS_BORDER_EDIT_COLOR, 2));
        calendarButton.setBounds(350, 550, 150, 30);
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
                                + appointment.getResponsible().getPersonLastName()
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
                        fullName
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
                            + appointment.getResponsible().getPersonLastName()
            });
        }
    }
}
