import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class TodoListApp extends JFrame {
    private DefaultListModel<Task> pendingModel;
    private DefaultListModel<Task> completedModel;
    private JList<Task> pendingList;
    private JList<Task> completedList;
    private JTextField taskField;
    private JButton addButton, removePendingButton, removeCompletedButton;

    public TodoListApp() {
        setTitle("TodoListApp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center window

        // Colors and fonts
        Font font = new Font("Segoe UI", Font.PLAIN, 16);
        Color bgColor = new Color(245, 245, 245);
        Color btnAddColor = new Color(100, 149, 237);
        Color btnRemoveColor = new Color(220, 20, 60);
        Color textColor = Color.BLACK;

        getContentPane().setBackground(bgColor);
        setLayout(new BorderLayout(10, 10));

        // Input panel at top
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBackground(bgColor);

        taskField = new JTextField();
        taskField.setFont(font);
        taskField.setForeground(textColor);
        inputPanel.add(taskField, BorderLayout.CENTER);

        addButton = new JButton("Add Task");
        addButton.setFont(font);
        addButton.setBackground(btnAddColor);
        addButton.setForeground(Color.BLACK);  // Button text color set to black
        addButton.setFocusPainted(false);
        inputPanel.add(addButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);

        // Models for pending and completed tasks
        pendingModel = new DefaultListModel<>();
        completedModel = new DefaultListModel<>();

        // Lists
        pendingList = new JList<>(pendingModel);
        completedList = new JList<>(completedModel);

        // Set custom cell renderer to show checkbox + text
        pendingList.setCellRenderer(new TaskCellRenderer());
        completedList.setCellRenderer(new TaskCellRenderer());

        // Allow selection of single task at a time
        pendingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        completedList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

         // Set font and text color
        pendingList.setFont(font);
        completedList.setFont(font);
        pendingList.setForeground(textColor);
        completedList.setForeground(textColor);




    }
}
