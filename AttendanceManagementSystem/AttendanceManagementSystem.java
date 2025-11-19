package AttendanceManagementSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class AttendanceManagementSystem extends JFrame {
    private JPanel topPanel;

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendanceManagementSystem app = new AttendanceManagementSystem();
            app.setVisible(true);
        });
    }
}
