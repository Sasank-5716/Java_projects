import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;

// User class for login/signup
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

// Book class
class Book {
    private String id;
    private String title;
    private String author;
    private boolean available;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}

// Member class
class Member {
    private String id;
    private String name;
    private String contact;

    public Member(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
}

// Transaction class
class Transaction {
    private String bookId;
    private String memberId;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;
    private double fine;

    public Transaction(String bookId, String memberId, Date issueDate, Date dueDate) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }

    public String getBookId() { return bookId; }
    public String getMemberId() { return memberId; }
    public Date getIssueDate() { return issueDate; }
    public Date getDueDate() { return dueDate; }
    public Date getReturnDate() { return returnDate; }
    public double getFine() { return fine; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    public void setFine(double fine) { this.fine = fine; }
}

public class libraryManagementSystem {
    // User storage for login/signup
    private static HashMap<String, User> users = new HashMap<>();
    // Data storage for books, members, transactions
    private static HashMap<String, Book> books = new HashMap<>();
    private static HashMap<String, Member> members = new HashMap<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    static {
        // Default admin user
        users.put("admin", new User("admin", "admin"));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }

    // Login Frame with Signup option
    static class LoginFrame extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;

        public LoginFrame() {
            setTitle("Library System - Login");
            setSize(350, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(4, 2, 10, 10));
            setLocationRelativeTo(null);
            
             JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(new Color(245, 245, 245));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;

            JLabel userLabel = new JLabel("Username:");
            userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            usernameField = new JTextField(20);
            usernameField.setToolTipText("Enter your username");

            JLabel passLabel = new JLabel("Password:");
            passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            passwordField = new JPasswordField(20);
            passwordField.setToolTipText("Enter your password");

            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(userLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(usernameField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(passLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(passwordField, gbc);

           JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonPanel.setBackground(new Color(245, 245, 245));
            JButton loginButton = new JButton("Login");
            JButton signupButton = new JButton("Signup");

            add(userLabel);
            add(usernameField);
            add(passLabel);
            add(passwordField);
            add(loginButton);
            add(signupButton);

            loginButton.addActionListener(e -> {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                User user = users.get(username);
                if (user != null && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
                    new MainFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            });

            signupButton.addActionListener(e -> {
                JTextField newUser = new JTextField();
                JPasswordField newPass = new JPasswordField();
                Object[] message = {
                    "New Username:", newUser,
                    "New Password:", newPass
                };
                int option = JOptionPane.showConfirmDialog(this, message, "Signup", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String uname = newUser.getText().trim();
                    String pword = new String(newPass.getPassword());
                    if (uname.isEmpty() || pword.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
                    } else if (users.containsKey(uname)) {
                        JOptionPane.showMessageDialog(this, "Username already exists!");
                    } else {
                        users.put(uname, new User(uname, pword));
                        JOptionPane.showMessageDialog(this, "Signup successful! You can now log in.");
                    }
                }
            });
        }
    }

    // Main Application Frame with Tabs
    static class MainFrame extends JFrame {
        public MainFrame() {
            setTitle("Library Management System");
            setSize(900, 650);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Books", new BookPanel());
            tabbedPane.addTab("Members", new MemberPanel());
            tabbedPane.addTab("Transactions", new TransactionPanel());

            add(tabbedPane, BorderLayout.CENTER);
        }
    }

    // Book Management Panel
    static class BookPanel extends JPanel {
        private JTextField idField, titleField, authorField;
        private JTextArea outputArea;

        public BookPanel() {
            setLayout(new BorderLayout(10, 10));

            JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
            idField = new JTextField();
            titleField = new JTextField();
            authorField = new JTextField();

            inputPanel.add(new JLabel("Book ID:"));
            inputPanel.add(idField);
            inputPanel.add(new JLabel("Title:"));
            inputPanel.add(titleField);
            inputPanel.add(new JLabel("Author:"));
            inputPanel.add(authorField);

            JButton addButton = new JButton("Add Book");
            JButton removeButton = new JButton("Remove Book");

            inputPanel.add(addButton);
            inputPanel.add(removeButton);

            outputArea = new JTextArea(15, 60);
            outputArea.setEditable(false);
            outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(outputArea);

            add(inputPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            addButton.addActionListener(e -> addBook());
            removeButton.addActionListener(e -> removeBook());

            displayBooks();
        }

        private void addBook() {
            String id = idField.getText().trim();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();

            if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            if (books.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Book ID already exists!");
                return;
            }

            books.put(id, new Book(id, title, author));
            displayBooks();
            clearFields();
        }

        private void removeBook() {
            String id = idField.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Book ID is required!");
                return;
            }

            if (!books.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Book not found!");
                return;
            }

            books.remove(id);
            displayBooks();
            clearFields();
        }

        private void displayBooks() {
            StringBuilder sb = new StringBuilder("Available Books:\n");
            sb.append(String.format("%-10s %-30s %-20s %-10s\n", "ID", "Title", "Author", "Status"));
            sb.append("-----------------------------------------------------------------\n");
            for (Book book : books.values()) {
                sb.append(String.format("%-10s %-30s %-20s %-10s\n",
                        book.getId(), book.getTitle(), book.getAuthor(),
                        book.isAvailable() ? "Available" : "Issued"));
            }
            outputArea.setText(sb.toString());
        }

        private void clearFields() {
            idField.setText("");
            titleField.setText("");
            authorField.setText("");
        }
    }

    // Member Management Panel
    static class MemberPanel extends JPanel {
        private JTextField idField, nameField, contactField;
        private JTextArea outputArea;

        public MemberPanel() {
            setLayout(new BorderLayout(10, 10));

            JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
            idField = new JTextField();
            nameField = new JTextField();
            contactField = new JTextField();

            inputPanel.add(new JLabel("Member ID:"));
            inputPanel.add(idField);
            inputPanel.add(new JLabel("Name:"));
            inputPanel.add(nameField);
            inputPanel.add(new JLabel("Contact:"));
            inputPanel.add(contactField);

            JButton addButton = new JButton("Add Member");
            JButton removeButton = new JButton("Remove Member");

            inputPanel.add(addButton);
            inputPanel.add(removeButton);

            outputArea = new JTextArea(15, 60);
            outputArea.setEditable(false);
            outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(outputArea);

            add(inputPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            addButton.addActionListener(e -> addMember());
            removeButton.addActionListener(e -> removeMember());

            displayMembers();
        }

        private void addMember() {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            if (members.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Member ID already exists!");
                return;
            }

            members.put(id, new Member(id, name, contact));
            displayMembers();
            clearFields();
        }

        private void removeMember() {
            String id = idField.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Member ID is required!");
                return;
            }

            if (!members.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Member not found!");
                return;
            }

            members.remove(id);
            displayMembers();
            clearFields();
        }

        private void displayMembers() {
            StringBuilder sb = new StringBuilder("Registered Members:\n");
            sb.append(String.format("%-10s %-25s %-20s\n", "ID", "Name", "Contact"));
            sb.append("--------------------------------------------------------\n");
            for (Member member : members.values()) {
                sb.append(String.format("%-10s %-25s %-20s\n",
                        member.getId(), member.getName(), member.getContact()));
            }
            outputArea.setText(sb.toString());
        }

        private void clearFields() {
            idField.setText("");
            nameField.setText("");
            contactField.setText("");
        }
    }

    // Transaction Management Panel
    static class TransactionPanel extends JPanel {
        private JTextField bookIdField, memberIdField;
        private JTextArea outputArea;
        private JButton issueButton, returnButton;

        public TransactionPanel() {
            setLayout(new BorderLayout(10, 10));

            JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            bookIdField = new JTextField();
            memberIdField = new JTextField();

            inputPanel.add(new JLabel("Book ID:"));
            inputPanel.add(bookIdField);
            inputPanel.add(new JLabel("Member ID:"));
            inputPanel.add(memberIdField);

            issueButton = new JButton("Issue Book");
            returnButton = new JButton("Return Book");

            inputPanel.add(issueButton);
            inputPanel.add(returnButton);

            outputArea = new JTextArea(15, 60);
            outputArea.setEditable(false);
            outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(outputArea);

            add(inputPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            issueButton.addActionListener(e -> issueBook());
            returnButton.addActionListener(e -> returnBook());

            displayTransactions();
        }

        private void issueBook() {
            String bookId = bookIdField.getText().trim();
            String memberId = memberIdField.getText().trim();

            if (bookId.isEmpty() || memberId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Both fields are required!");
                return;
            }

            if (!books.containsKey(bookId)) {
                JOptionPane.showMessageDialog(this, "Book not found!");
                return;
            }

            if (!members.containsKey(memberId)) {
                JOptionPane.showMessageDialog(this, "Member not found!");
                return;
            }

            Book book = books.get(bookId);
            if (!book.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Book is already issued!");
                return;
            }

            Date issueDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(issueDate);
            cal.add(Calendar.DAY_OF_MONTH, 14);
            Date dueDate = cal.getTime();

            Transaction transaction = new Transaction(bookId, memberId, issueDate, dueDate);
            transactions.add(transaction);

            book.setAvailable(false);

            displayTransactions();
            clearFields();
        }

        private void returnBook() {
            String bookId = bookIdField.getText().trim();

            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Book ID is required!");
                return;
            }

            if (!books.containsKey(bookId)) {
                JOptionPane.showMessageDialog(this, "Book not found!");
                return;
            }

            Transaction transaction = null;
            for (Transaction t : transactions) {
                if (t.getBookId().equals(bookId) && t.getReturnDate() == null) {
                    transaction = t;
                    break;
                }
            }

            if (transaction == null) {
                JOptionPane.showMessageDialog(this, "No active transaction for this book!");
                return;
            }

            Date returnDate = new Date();
            transaction.setReturnDate(returnDate);

            long daysLate = ChronoUnit.DAYS.between(
                    transaction.getDueDate().toInstant(),
                    returnDate.toInstant());

            if (daysLate > 0) {
                double fine = calculateFine(daysLate);
                transaction.setFine(fine);
                JOptionPane.showMessageDialog(this,
                        "Book returned " + daysLate + " days late. Fine: ₹" + fine);
            }

            books.get(bookId).setAvailable(true);

            displayTransactions();
            clearFields();
        }

        private double calculateFine(long daysLate) {
            if (daysLate <= 5) return daysLate * 0.5;
            if (daysLate <= 10) return 2.5 + (daysLate - 5) * 1;
            return 7.5 + (daysLate - 10) * 5;
        }

        private void displayTransactions() {
            StringBuilder sb = new StringBuilder("Transaction History:\n");
            sb.append(String.format("%-10s %-10s %-12s %-12s %-12s %-6s\n",
                    "Book", "Member", "Issue Date", "Due Date", "Return Date", "Fine"));
            sb.append("---------------------------------------------------------------------\n");
            for (Transaction t : transactions) {
                sb.append(String.format("%-10s %-10s %-12s %-12s %-12s %-6.2f\n",
                        t.getBookId(),
                        t.getMemberId(),
                        sdf.format(t.getIssueDate()),
                        sdf.format(t.getDueDate()),
                        t.getReturnDate() != null ? sdf.format(t.getReturnDate()) : "Not Returned",
                        t.getFine()));
            }
            outputArea.setText(sb.toString());
        }

        private void clearFields() {
            bookIdField.setText("");
            memberIdField.setText("");
        }
    }
}
