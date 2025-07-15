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

        // Scroll panes
        JScrollPane pendingScroll = new JScrollPane(pendingList);
        JScrollPane completedScroll = new JScrollPane(completedList);

        // Labels for sections
        JLabel pendingLabel = new JLabel("Pending Tasks");
        pendingLabel.setFont(font);
        pendingLabel.setForeground(textColor);
        JLabel completedLabel = new JLabel("Completed Tasks");
        completedLabel.setFont(font);
        completedLabel.setForeground(textColor);

        // Panel for lists side by side
        JPanel listsPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        listsPanel.setBackground(bgColor);

        // Panel for pending tasks with label and remove button
        JPanel pendingPanel = new JPanel(new BorderLayout(5, 5));
        pendingPanel.setBackground(bgColor);
        pendingPanel.add(pendingLabel, BorderLayout.NORTH);
        pendingPanel.add(pendingScroll, BorderLayout.CENTER);

        removePendingButton = new JButton("Remove Selected Pending");
        removePendingButton.setFont(font);
        removePendingButton.setBackground(btnRemoveColor);
        removePendingButton.setForeground(Color.BLACK);  // Button text color set to black
        removePendingButton.setFocusPainted(false);
        pendingPanel.add(removePendingButton, BorderLayout.SOUTH);

         // Panel for completed tasks with label and remove button
        JPanel completedPanel = new JPanel(new BorderLayout(5, 5));
        completedPanel.setBackground(bgColor);
        completedPanel.add(completedLabel, BorderLayout.NORTH);
        completedPanel.add(completedScroll, BorderLayout.CENTER);

        removeCompletedButton = new JButton("Remove Selected Completed");
        removeCompletedButton.setFont(font);
        removeCompletedButton.setBackground(btnRemoveColor);
        removeCompletedButton.setForeground(Color.BLACK);  // Button text color set to black
        removeCompletedButton.setFocusPainted(false);
        completedPanel.add(removeCompletedButton, BorderLayout.SOUTH);

        listsPanel.add(pendingPanel);
        listsPanel.add(completedPanel);

        add(listsPanel, BorderLayout.CENTER);

        // Add task action
        addButton.addActionListener(e -> addTask());
        taskField.addActionListener(e -> addTask());

        // Remove buttons actions
        removePendingButton.addActionListener(e -> removeSelectedTask(pendingList, pendingModel));
        removeCompletedButton.addActionListener(e -> removeSelectedTask(completedList, completedModel));

    }
}
