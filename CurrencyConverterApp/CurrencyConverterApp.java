package CurrencyConverterApp;

import javax.swing.JFrame;
import java.awt.BorderLayout;
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

    public CurrencyConverterApp() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        CurrencyConverterApp app = new CurrencyConverterApp();
        app.setVisible(true);
    }

}