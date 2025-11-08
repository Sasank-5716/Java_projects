package CurrencyConverterApp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterApp extends JFrame {
    private static final Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("GBP", 0.78);
        exchangeRates.put("INR", 82.5);
        exchangeRates.put("JPY", 146.7);
    }

    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JLabel resultLabel;

    public CurrencyConverterApp() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("From Currency:"));
        fromCurrency = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        inputPanel.add(fromCurrency);

        inputPanel.add(new JLabel("To Currency:"));
        toCurrency = new JComboBox<>(exchangeRates.keySet().toArray(new String[0]));
        inputPanel.add(toCurrency);

        inputPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        inputPanel.add(amountField);

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
        inputPanel.add(convertButton);

        resultLabel = new JLabel("Enter amount and select currencies.");

        inputPanel.add(resultLabel);

        add(inputPanel, BorderLayout.CENTER);
    }

    private void performConversion() {
        try {
            String from = (String) fromCurrency.getSelectedItem();
            String to = (String) toCurrency.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());

            double fromRate = exchangeRates.get(from);
            double toRate = exchangeRates.get(to);

            double convertedAmount = amount / fromRate * toRate;

            resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, from, convertedAmount, to));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.", "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        CurrencyConverterApp app = new CurrencyConverterApp();
        app.setVisible(true);
    }

}