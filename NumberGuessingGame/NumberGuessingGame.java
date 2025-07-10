import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {
    private int numberToGuess;
    private int attempts;
    private JTextField guessField;
    private JLabel messageLabel;
    private JButton guessButton;

       // Animation fields
    private Timer animationTimer;
    private int animationCount;

    public NumberGuessingGame() {
        super("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 150);
        setLayout(new BorderLayout(10, 10)); // Padding

        // Initialize game state
        numberToGuess = new Random().nextInt(100) + 1;
        attempts = 0;

        // Components
        messageLabel = new JLabel("Guess a number between 1 and 100:", SwingConstants.CENTER);
        guessField = new JTextField();
        guessButton = new JButton("Guess");

        // Guess button action
        guessButton.addActionListener(e -> processGuess());

        // Enter key triggers guess
        guessField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processGuess();
                }
            }
        });

    }

}