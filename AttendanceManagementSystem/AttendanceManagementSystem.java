package AttendanceManagementSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class AttendanceManagementSystem extends JFrame {
    private JPanel topPanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public AttendanceManagementSystem() {
        setTitle("Attendance Management System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void createTopPanel() {
        topPanel = new JPanel();
        topPanel.setBackground(new Color(34, 49, 63));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

        JButton btnAddAttendance = new JButton("Add Attendance");
        btnAddAttendance.setFocusPainted(false);
        btnAddAttendance.setBackground(new Color(41, 128, 185));
        btnAddAttendance.setForeground(Color.white);
        btnAddAttendance.setFont(new Font("Arial", Font.BOLD, 14));
        btnAddAttendance.setPreferredSize(new Dimension(140, 35));
        btnAddAttendance.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnAddAttendance.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddAttendance.addActionListener(e -> cardLayout.show(mainPanel, "addAttendance"));
        topPanel.add(btnAddAttendance);

        JButton btnViewRecords = new JButton("View Records");
        btnViewRecords.setFocusPainted(false);
        btnViewRecords.setBackground(new Color(41, 128, 185));
        btnViewRecords.setForeground(Color.white);
        btnViewRecords.setFont(new Font("Arial", Font.BOLD, 14));
        btnViewRecords.setPreferredSize(new Dimension(140, 35));
        btnViewRecords.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btnViewRecords.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewRecords.addActionListener(e -> {
            cardLayout.show(mainPanel, "viewRecords");
            refreshTable();
        });
        topPanel.add(btnViewRecords);

    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        JPanel addPanel = new JPanel(new GridBagLayout());
        addPanel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblName = new JLabel("Student Name:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        addPanel.add(lblName, gbc);

        tfName = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        addPanel.add(tfName, gbc);

        JLabel lblTotalClasses = new JLabel("Total Classes Held:");
        lblTotalClasses.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        addPanel.add(lblTotalClasses, gbc);

        tfTotalClasses = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        addPanel.add(tfTotalClasses, gbc);

        JLabel lblClassesAttended = new JLabel("Classes Attended:");
        lblClassesAttended.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        addPanel.add(lblClassesAttended, gbc);

        tfClassesAttended = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addPanel.add(tfClassesAttended, gbc);

        JButton btnAdd = new JButton("Add Record");
        btnAdd.setBackground(new Color(39, 174, 96));
        btnAdd.setForeground(Color.white);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        addPanel.add(btnAdd, gbc);

        btnAdd.addActionListener(e -> addAttendance());

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendanceManagementSystem app = new AttendanceManagementSystem();
            app.setVisible(true);
        });
    }
}
