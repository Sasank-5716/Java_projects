package AttendanceManagementSystem;

import javax.swing.*;
import java.awt.*;

public class AttendanceManagementSystem extends JFrame {
    public AttendanceManagementSystem() {
        setTitle("Attendance Management System");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AttendanceManagementSystem app = new AttendanceManagementSystem();
            app.setVisible(true);
        });
    }
}

