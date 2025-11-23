package OnlineVotingSystem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineVotingSystem extends JFrame {
    public OnlineVotingSystem() {
        setTitle("Online Voting System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OnlineVotingSystem app = new OnlineVotingSystem();
            app.setVisible(true);
        });
    }

}
