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

        // Layout setup
        add(messageLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        guessField.setPreferredSize(new Dimension(150, 25));
        inputPanel.add(guessField);
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(guessButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center window
        setVisible(true);

    }

    private void processGuess() {
        if (animationTimer != null && animationTimer.isRunning()) {
            // Ignore input during animation
            return;
        }

        String userInput = guessField.getText().trim();
        if (userInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int guess = Integer.parseInt(userInput);
            attempts++;

            if (guess < 1 || guess > 100) {
                JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 100.", "Input Error",
                        JOptionPane.WARNING_MESSAGE);
            } else if (guess == numberToGuess) {
                startWinnerAnimation();
            } else if (guess < numberToGuess) {
                if (numberToGuess - guess <= 5) {
                    messageLabel.setText("Little low! Try again.");
                } else {
                    messageLabel.setText("Too low! Try again.");
                }
            } else { // guess > numberToGuess
                if (guess - numberToGuess <= 5) {
                    messageLabel.setText("Little high! Try again.");
                } else {
                    messageLabel.setText("Too high! Try again.");
                }
            }
            guessField.setText("");
            guessField.requestFocusInWindow();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please enter a valid whole number.", "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            guessField.setText("");
            guessField.requestFocusInWindow();
        }
    }

    private void startWinnerAnimation() {
        // Disable inputs during animation
        guessField.setEnabled(false);
        guessButton.setEnabled(false);

    }

}