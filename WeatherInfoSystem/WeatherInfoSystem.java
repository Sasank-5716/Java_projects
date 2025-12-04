package WeatherInfoSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherInfoSystem extends JFrame {
    private JComboBox<String> cityComboBox;
    private JTextArea weatherDisplay;
    private JButton fetchButton;
    private final Color PURPLE_PRIMARY = new Color(138, 43, 226);
    private final Color PURPLE_LIGHT = new Color(218, 177, 218);
    private final Color PURPLE_DARK = new Color(75, 0, 130);

    private final String[] CITIES = { "Kathmandu", "Pokhara", "Biratnagar", "Lalitpur", "Bhaktapur",
            "London", "New York", "Tokyo", "Delhi", "Paris", "Dubai" };

    private final String API_KEY = "API_KEY";

    public WeatherInfoSystem() {
        setTitle("Weather Information System");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(PURPLE_DARK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel header = new JLabel(" Weather Dashboard", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(Color.WHITE);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(PURPLE_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel cityLabel = new JLabel(" Select City:");
        cityLabel.setForeground(Color.WHITE);
        cityLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(cityLabel, gbc);

        cityComboBox = new JComboBox<>(CITIES);
        cityComboBox.setPreferredSize(new Dimension(250, 40));
        cityComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cityComboBox.setBackground(PURPLE_LIGHT);
        cityComboBox.setForeground(PURPLE_DARK);
        cityComboBox.setBorder(BorderFactory.createLineBorder(PURPLE_PRIMARY, 3));
        gbc.gridx = 1;
        inputPanel.add(cityComboBox, gbc);

        fetchButton = new JButton(" Get Weather");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        fetchButton.setOpaque(true);
        fetchButton.setBorderPainted(false);
        fetchButton.setFocusPainted(false);
        fetchButton.setBorder(BorderFactory.createEmptyBorder(15, 35, 15, 35));
        fetchButton.addActionListener(new FetchWeatherListener());
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(fetchButton, gbc);

        cityComboBox.addActionListener(e -> {
            if (cityComboBox.getSelectedIndex() >= 0) {
                String city = (String) cityComboBox.getSelectedItem();
                fetchWeather(city, API_KEY);
            }
        });
    }
}