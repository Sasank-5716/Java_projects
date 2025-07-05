import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;

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

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
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

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
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

    // Getters and setters
    public String getBookId() {
        return bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public double getFine() {
        return fine;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}

public class libraryManagementSystem {
    private static HashMap<String, Book> books = new HashMap<>();
    private static HashMap<String, Member> members = new HashMap<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        // Create and show the login frame
        new LoginFrame().setVisible(true);
    }

    // Login Frame
    static class LoginFrame extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;

        public LoginFrame() {
            setTitle("Library System - Login");
            setSize(350, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridLayout(3, 2, 10, 10));
            setLocationRelativeTo(null);

            JLabel userLabel = new JLabel("Username:");
            JLabel passLabel = new JLabel("Password:");
            usernameField = new JTextField();
            passwordField = new JPasswordField();
            JButton loginButton = new JButton("Login");

            add(userLabel);
            add(usernameField);
            add(passLabel);
            add(passwordField);
            add(new JLabel());
            add(loginButton);

            loginButton.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if ("admin".equals(username) && "admin".equals(password)) {
                    new MainFrame().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            });
        }
    }

    // Main Application Frame
    static class MainFrame extends JFrame {
        public MainFrame() {
            setTitle("Library Management System");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            setLocationRelativeTo(null);

            // Create tabbed pane
            JTabbedPane tabbedPane = new JTabbedPane();

            // Add tabs
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

            // Input panel
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

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(addButton);
            buttonPanel.add(removeButton);

            inputPanel.add(addButton);
            inputPanel.add(removeButton);

            // Output area
            outputArea = new JTextArea(15, 50);
            outputArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(outputArea);

            // Add components
            add(inputPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            // Button actions
            addButton.addActionListener(e -> addBook());
            removeButton.addActionListener(e -> removeBook());

            // Display initial books
            displayBooks();
        }

        private void addMember() {
            String id = idField.getText();
            String name = nameField.getText();
            String contact = contactField.getText();

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
            String id = idField.getText();

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
            sb.append("ID\tName\tContact\n");

            for (Member member : members.values()) {
                sb.append(member.getId()).append("\t")
                        .append(member.getName()).append("\t")
                        .append(member.getContact()).append("\n");
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

            // Input panel
            JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
            bookIdField = new JTextField();
            memberIdField = new JTextField();

            inputPanel.add(new JLabel("Book ID:"));
            inputPanel.add(bookIdField);
            inputPanel.add(new JLabel("Member ID:"));
            inputPanel.add(memberIdField);

            issueButton = new JButton("Issue Book");
            returnButton = new JButton("Return Book");

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(issueButton);
            buttonPanel.add(returnButton);

            inputPanel.add(issueButton);
            inputPanel.add(returnButton);

            // Output area
            outputArea = new JTextArea(15, 50);
            outputArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(outputArea);

            // Add components
            add(inputPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);

            // Button actions
            issueButton.addActionListener(e -> issueBook());
            returnButton.addActionListener(e -> returnBook());

            // Display initial transactions
            displayTransactions();
        }

        private void issueBook() {
            String bookId = bookIdField.getText();
            String memberId = memberIdField.getText();

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

            // Set issue and due dates
            Date issueDate = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(issueDate);
            cal.add(Calendar.DAY_OF_MONTH, 14);
            Date dueDate = cal.getTime();

            // Create transaction
            Transaction transaction = new Transaction(bookId, memberId, issueDate, dueDate);
            transactions.add(transaction);

            // Update book status
            book.setAvailable(false);

            displayTransactions();
            clearFields();

        }

        private void returnBook() {
            String bookId = bookIdField.getText();

            if (bookId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Book ID is required!");
                return;
            }

            if (!books.containsKey(bookId)) {
                JOptionPane.showMessageDialog(this, "Book not found!");
                return;

                // Find active transaction for this book
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

                // Set return date and calculate fine
                Date returnDate = new Date();
                transaction.setReturnDate(returnDate);

                // Calculate days late
                long daysLate = ChronoUnit.DAYS.between(
                        transaction.getDueDate().toInstant(),
                        returnDate.toInstant());

                if (daysLate > 0) {
                    double fine = calculateFine(daysLate);
                    transaction.setFine(fine);
                    JOptionPane.showMessageDialog(this,
                            "Book returned " + daysLate + " days late. Fine: ₹" + fine);
                }

                // Update book status
                books.get(bookId).setAvailable(true);

                displayTransactions();
                clearFields();
            }

            private double calculateFine(long daysLate) {
            if (daysLate <= 5) return daysLate * 0.5;
            if (daysLate <= 10) return 2.5 + (daysLate - 5) * 1;
            return 7.5 + (daysLate - 10) * 5;
        }
        }
    }
}