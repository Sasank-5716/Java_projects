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
    }
}
