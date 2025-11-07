package CurrencyConverterApp;
import javax.swing.JFrame;
import java.awt.BorderLayout;

public class CurrencyConverterApp {
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