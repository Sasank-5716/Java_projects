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
     // Getters
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
}

public class libraryManagementSystem {
    
}
