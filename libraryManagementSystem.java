import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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
        // Use system look and feel for better native appearance *** UPDATED ***
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
            setSize(400, 220);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
            contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

            // Input panel with GridBagLayout for better spacing *** UPDATED ***
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

            // Buttons panel *** UPDATED ***
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            buttonPanel.setBackground(new Color(245, 245, 245));
            JButton loginButton = new JButton("Login");
            JButton signupButton = new JButton("Signup");

            // Style buttons *** UPDATED ***
            Font btnFont = new Font("Segoe UI", Font.BOLD, 14);
            loginButton.setFont(btnFont);
            signupButton.setFont(btnFont);
            loginButton.setBackground(new Color(70, 130, 180));
            loginButton.setForeground(Color.WHITE);
            signupButton.setBackground(new Color(34, 139, 34));
            signupButton.setForeground(Color.WHITE);
            loginButton.setFocusPainted(false);
            signupButton.setFocusPainted(false);

            // Hover effect for buttons *** UPDATED ***
            loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    loginButton.setBackground(new Color(100, 149, 237));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    loginButton.setBackground(new Color(70, 130, 180));
                }
            });
            signupButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    signupButton.setBackground(new Color(50, 205, 50));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    signupButton.setBackground(new Color(34, 139, 34));
                }
            });

            buttonPanel.add(loginButton);
            buttonPanel.add(signupButton);

            contentPanel.add(inputPanel, BorderLayout.CENTER);
            contentPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(contentPanel);

            // Button actions
            loginButton.addActionListener(e -> {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                User user = users.get(username);
                if (user != null && user.getPassword().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + username);
                    new MainFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
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
                        JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else if (users.containsKey(uname)) {
                        JOptionPane.showMessageDialog(this, "Username already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        users.put(uname, new User(uname, pword));
                        JOptionPane.showMessageDialog(this, "Signup successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
        }
    }

    // Main Application Frame with Tabs
    static class MainFrame extends JFrame {
        public MainFrame() {
            setTitle("Library Management System");
            setSize(950, 700);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            JTabbedPane tabbedPane = new JTabbedPane();

            // Set font for tabs *** UPDATED ***
            tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 16));

            tabbedPane.addTab("Books", new BookPanel());
            tabbedPane.addTab("Members", new MemberPanel());
            tabbedPane.addTab("Transactions", new TransactionPanel());

            add(tabbedPane, BorderLayout.CENTER);
        }
    }

    // Book Management Panel with JTable *** UPDATED ***
    static class BookPanel extends JPanel {
        private JTextField idField, titleField, authorField;
        private JTable bookTable;
        private DefaultTableModel tableModel;

        public BookPanel() {
            setLayout(new BorderLayout(15, 15));
            setBorder(new EmptyBorder(15, 15, 15, 15));
            setBackground(Color.WHITE);

            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(Color.WHITE);
            inputPanel.setBorder(BorderFactory.createTitledBorder("Add / Remove Book"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;

            JLabel idLabel = new JLabel("Book ID:");
            idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            idField = new JTextField(15);
            idField.setToolTipText("Unique Book ID");

            JLabel titleLabel = new JLabel("Title:");
            titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            titleField = new JTextField(15);
            titleField.setToolTipText("Book Title");

            JLabel authorLabel = new JLabel("Author:");
            authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            authorField = new JTextField(15);
            authorField.setToolTipText("Author Name");

            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(idLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(idField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(titleLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(titleField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            inputPanel.add(authorLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(authorField, gbc);

            JButton addButton = new JButton("Add Book");
            JButton removeButton = new JButton("Remove Book");
            addButton.setBackground(new Color(0, 123, 255));
            addButton.setForeground(Color.WHITE);
            removeButton.setBackground(new Color(220, 53, 69));
            removeButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);
            removeButton.setFocusPainted(false);
            addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            removeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            addButton.setToolTipText("Add a new book");
            removeButton.setToolTipText("Remove selected book by ID");

            gbc.gridx = 0; gbc.gridy = 3;
            inputPanel.add(addButton, gbc);
            gbc.gridx = 1;
            inputPanel.add(removeButton, gbc);

            // Table setup *** UPDATED ***
            tableModel = new DefaultTableModel(new String[]{"Book ID", "Title", "Author", "Status"}, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false; // Make table cells non-editable
                }
            };
            bookTable = new JTable(tableModel);
            bookTable.setFillsViewportHeight(true);
            bookTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            bookTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
            JScrollPane scrollPane = new JScrollPane(bookTable);

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
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (books.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Book ID already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            books.put(id, new Book(id, title, author));
            displayBooks();
            clearFields();
        }

        private void removeBook() {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Book ID to remove!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!books.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Book not found!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            books.remove(id);
            displayBooks();
            clearFields();
        }

        private void displayBooks() {
            tableModel.setRowCount(0);
            for (Book book : books.values()) {
                tableModel.addRow(new Object[]{
                        book.getId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.isAvailable() ? "Available" : "Issued"
                });
            }
        }

        private void clearFields() {
            idField.setText("");
            titleField.setText("");
            authorField.setText("");
        }
    }

    // Member Management Panel with JTable *** UPDATED ***
    static class MemberPanel extends JPanel {
        private JTextField idField, nameField, contactField;
        private JTable memberTable;
        private DefaultTableModel tableModel;

        public MemberPanel() {
            setLayout(new BorderLayout(15, 15));
            setBorder(new EmptyBorder(15, 15, 15, 15));
            setBackground(Color.WHITE);

            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(Color.WHITE);
            inputPanel.setBorder(BorderFactory.createTitledBorder("Add / Remove Member"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;

            JLabel idLabel = new JLabel("Member ID:");
            idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            idField = new JTextField(15);
            idField.setToolTipText("Unique Member ID");

            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            nameField = new JTextField(15);
            nameField.setToolTipText("Member Name");

            JLabel contactLabel = new JLabel("Contact:");
            contactLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            contactField = new JTextField(15);
            contactField.setToolTipText("Contact Information");

            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(idLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(idField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(nameLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(nameField, gbc);

            gbc.gridx = 0; gbc.gridy = 2;
            inputPanel.add(contactLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(contactField, gbc);

            JButton addButton = new JButton("Add Member");
            JButton removeButton = new JButton("Remove Member");
            addButton.setBackground(new Color(0, 123, 255));
            addButton.setForeground(Color.WHITE);
            removeButton.setBackground(new Color(220, 53, 69));
            removeButton.setForeground(Color.WHITE);
            addButton.setFocusPainted(false);
            removeButton.setFocusPainted(false);
            addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            removeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            addButton.setToolTipText("Add a new member");
            removeButton.setToolTipText("Remove member by ID");

            gbc.gridx = 0; gbc.gridy = 3;
            inputPanel.add(addButton, gbc);
            gbc.gridx = 1;
            inputPanel.add(removeButton, gbc);

            // Table setup *** UPDATED ***
            tableModel = new DefaultTableModel(new String[]{"Member ID", "Name", "Contact"}, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            memberTable = new JTable(tableModel);
            memberTable.setFillsViewportHeight(true);
            memberTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            memberTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
            JScrollPane scrollPane = new JScrollPane(memberTable);

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
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (members.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Member ID already exists!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            members.put(id, new Member(id, name, contact));
            displayMembers();
            clearFields();
        }

        private void removeMember() {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the Member ID to remove!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!members.containsKey(id)) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            members.remove(id);
            displayMembers();
            clearFields();
        }

        private void displayMembers() {
            tableModel.setRowCount(0);
            for (Member member : members.values()) {
                tableModel.addRow(new Object[]{
                        member.getId(),
                        member.getName(),
                        member.getContact()
                });
            }
        }

        private void clearFields() {
            idField.setText("");
            nameField.setText("");
            contactField.setText("");
        }
    }

    // Transaction Management Panel with JTable *** UPDATED ***
    static class TransactionPanel extends JPanel {
        private JTextField bookIdField, memberIdField;
        private JTable transactionTable;
        private DefaultTableModel tableModel;
        private JButton issueButton, returnButton;

        public TransactionPanel() {
            setLayout(new BorderLayout(15, 15));
            setBorder(new EmptyBorder(15, 15, 15, 15));
            setBackground(Color.WHITE);

            JPanel inputPanel = new JPanel(new GridBagLayout());
            inputPanel.setBackground(Color.WHITE);
            inputPanel.setBorder(BorderFactory.createTitledBorder("Issue / Return Books"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.anchor = GridBagConstraints.WEST;

            JLabel bookIdLabel = new JLabel("Book ID:");
            bookIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            bookIdField = new JTextField(15);
            bookIdField.setToolTipText("Book ID to issue or return");

            JLabel memberIdLabel = new JLabel("Member ID:");
            memberIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            memberIdField = new JTextField(15);
            memberIdField.setToolTipText("Member ID for issuing");

            gbc.gridx = 0; gbc.gridy = 0;
            inputPanel.add(bookIdLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(bookIdField, gbc);

            gbc.gridx = 0; gbc.gridy = 1;
            inputPanel.add(memberIdLabel, gbc);
            gbc.gridx = 1;
            inputPanel.add(memberIdField, gbc);

            issueButton = new JButton("Issue Book");
            returnButton = new JButton("Return Book");
            issueButton.setBackground(new Color(0, 123, 255));
            issueButton.setForeground(Color.WHITE);
            returnButton.setBackground(new Color(40, 167, 69));
            returnButton.setForeground(Color.WHITE);
            issueButton.setFocusPainted(false);
            returnButton.setFocusPainted(false);
            issueButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            returnButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            issueButton.setToolTipText("Issue book to member");
            returnButton.setToolTipText("Return book from member");

            gbc.gridx = 0; gbc.gridy = 2;
            inputPanel.add(issueButton, gbc);
            gbc.gridx = 1;
            inputPanel.add(returnButton, gbc);

            // Table setup *** UPDATED ***
            tableModel = new DefaultTableModel(new String[]{"Book ID", "Member ID", "Issue Date", "Due Date", "Return Date", "Fine"}, 0) {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            transactionTable = new JTable(tableModel);
            transactionTable.setFillsViewportHeight(true);
            transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            transactionTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
            JScrollPane scrollPane = new JScrollPane(transactionTable);

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
                JOptionPane.showMessageDialog(this, "Both fields are required!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!books.containsKey(bookId)) {
                JOptionPane.showMessageDialog(this, "Book not found!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!members.containsKey(memberId)) {
                JOptionPane.showMessageDialog(this, "Member not found!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book book = books.get(bookId);
            if (!book.isAvailable()) {
                JOptionPane.showMessageDialog(this, "Book is already issued!", "Warning", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Book ID is required!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!books.containsKey(bookId)) {
                JOptionPane.showMessageDialog(this, "Book not found!", "Warning", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "No active transaction for this book!", "Warning", JOptionPane.WARNING_MESSAGE);
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
                        "Book returned " + daysLate + " days late. Fine: ₹" + fine, "Fine", JOptionPane.INFORMATION_MESSAGE);
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
            tableModel.setRowCount(0);
            for (Transaction t : transactions) {
                tableModel.addRow(new Object[]{
                        t.getBookId(),
                        t.getMemberId(),
                        sdf.format(t.getIssueDate()),
                        sdf.format(t.getDueDate()),
                        t.getReturnDate() != null ? sdf.format(t.getReturnDate()) : "Not Returned",
                        String.format("₹%.2f", t.getFine())
                });
            }
        }

        private void clearFields() {
            bookIdField.setText("");
            memberIdField.setText("");
        }
    }
}
