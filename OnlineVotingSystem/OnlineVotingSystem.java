package OnlineVotingSystem;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OnlineVotingSystem extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;

    UserManager userManager;
    ElectionManager electionManager;
    User currentUser;

    public OnlineVotingSystem() {
        setTitle("Online Voting System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userManager = new UserManager();
        electionManager = new ElectionManager();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public ElectionManager getElectionManager() {
        return electionManager;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OnlineVotingSystem app = new OnlineVotingSystem();
            app.setVisible(true);
        });
    }

}

// User roles
enum Role {
    ADMIN, VOTER
}

// User class
class User {
    private String username;
    private String password;
    private Role role;
    private boolean hasVoted = false;
    private String votedCandidate = null;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setVoted(boolean voted) {
        this.hasVoted = voted;
    }

    public String getVotedCandidate() {
        return votedCandidate;
    }

    public void setVotedCandidate(String candidate) {
        this.votedCandidate = candidate;
    }
}

// Manages users and authentication
class UserManager {
    private Map<String, User> users = new ConcurrentHashMap<>();

    public UserManager() {
        // Create default admin user
        users.put("admin", new User("admin", "admin", Role.ADMIN));
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username))
            return false;
        users.put(username, new User(username, password, Role.VOTER));
        return true;
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }
}

// Candidate class with vote count
class Candidate {
    private String name;
    private int votes;

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void incrementVotes() {
        votes++;
    }
}

class ElectionManager {
    private Map<String, Candidate> candidates = new LinkedHashMap<>();

    public ElectionManager() {
        addCandidate("Candidate A");
        addCandidate("Candidate B");
        addCandidate("Candidate C");
    }

    public void addCandidate(String name) {
        if (!candidates.containsKey(name)) {
            candidates.put(name, new Candidate(name));
        }
    }

    public Collection<Candidate> getCandidates() {
        return candidates.values();
    }

    public void vote(String candidateName) {
        Candidate candidate = candidates.get(candidateName);
        if (candidate != null) {
            candidate.incrementVotes();
        }
    }

    public Map<String, Integer> getResults() {
        Map<String, Integer> results = new LinkedHashMap<>();
        for (Candidate c : candidates.values()) {
            results.put(c.getName(), c.getVotes());
        }
        return results;
    }
}

// Login UI panel
class LoginPanel extends JPanel {
    private OnlineVotingSystem app;

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;
    private JLabel lblStatus;

    public LoginPanel(OnlineVotingSystem app) {
        this.app = app;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblTitle = new JLabel("ONLINE VOTING SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridy++;
        add(new JLabel("Username:"), gbc);
        txtUsername = new JTextField(15);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Password:"), gbc);
        txtPassword = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
        JPanel panelBtns = new JPanel();
        panelBtns.add(btnLogin);
        panelBtns.add(btnRegister);
        add(panelBtns, gbc);

        gbc.gridy++;
        lblStatus = new JLabel(" ");
        lblStatus.setForeground(Color.RED);
        add(lblStatus, gbc);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                lblStatus.setText("Please enter username and password.");
                return;
            }
            User user = app.getUserManager().authenticate(username, password);
            if (user != null) {
                app.setCurrentUser(user);
                lblStatus.setText("Login successful!");
                txtUsername.setText("");
                txtPassword.setText("");
                if (user.getRole() == Role.ADMIN) {
                    app.showPanel("Admin");
                } else {
                    app.showPanel("Voter");
                }
            } else {
                lblStatus.setText("Invalid username or password.");
            }
        });

        btnRegister.addActionListener(e -> app.showPanel("Register"));
    }

    public void clearStatus() {
        lblStatus.setText(" ");
    }
}