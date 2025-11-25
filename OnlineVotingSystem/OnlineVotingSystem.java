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
