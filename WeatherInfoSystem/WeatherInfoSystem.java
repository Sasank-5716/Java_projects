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

    private String getApiKey() {
        String envKey = System.getenv("API_KEY");
        if (envKey != null && !envKey.equals("API_KEY")) {
            return envKey;
        }
        return "demo_key";
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

        weatherDisplay = new JTextArea();
        weatherDisplay.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        weatherDisplay.setEditable(false);
        weatherDisplay.setBackground(PURPLE_LIGHT);
        weatherDisplay.setForeground(PURPLE_DARK);
        weatherDisplay.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PURPLE_PRIMARY, 3),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        JScrollPane scrollPane = new JScrollPane(weatherDisplay);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // Initial instructions
        weatherDisplay.setText(
                " Just select a city from dropdown or click Get Weather!\n🌐 API key handled automatically.\n👆 Try Kathmandu first (Nepal capital).");
    }

    private class FetchWeatherListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String city = (String) cityComboBox.getSelectedItem();
            fetchWeather(city, API_KEY);
        }
    }

    private void fetchWeather(String city, String apiKey) {
        SwingUtilities.invokeLater(() -> weatherDisplay.setText("🔄 Fetching weather for " + city + "..."));

        new Thread(() -> {
            try {
                String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey
                        + "&units=metric";
                URL url = new URL(urlString);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    br.close();

                    String data = response.toString();
                    String name = extractValue(data, "\"name\"");
                    String temp = extractValueFromNested(data, "main", "temp");
                    String feels_like = extractValueFromNested(data, "main", "feels_like");
                    String humidity = extractValueFromNested(data, "main", "humidity");
                    String wind_speed = extractValueFromNested(data, "wind", "speed");
                    String description = extractValueFromArray(data, "weather", "description");

                    String weatherInfo = String.format(
                            "🌍 Location: %s\n━━━━━━━━━━━━━━━━━━━\n🌡️ Temperature: %s °C\n😓 Feels Like: %s °C\n💧 Humidity: %s%%\n💨 Wind Speed: %s m/s\n☁️ Condition: %s",
                            name, temp, feels_like, humidity, wind_speed, capitalize(description));

                    SwingUtilities.invokeLater(() -> weatherDisplay.setText(weatherInfo));
                } else {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            weatherDisplay.setText("❌ Error for " + city + " (Code: " + conn.getResponseCode()
                                    + ")\n\n✅ Make sure API key is valid in source code\n🔗 Replace 'YOUR_API_KEY_HERE' with real key");
                        } catch (IOException ioEx) {
                            weatherDisplay.setText(
                                    "❌ Error for " + city + "\n\n✅ Check your internet connection and API key");
                        }
                    });
                }
                conn.disconnect();
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> weatherDisplay.setText(
                        "🌐 Network error: " + ex.getMessage() + "\n\n✅ Check internet & API key in source code"));
            }
        }).start();
    }

    private String extractValue(String json, String key) {
        int keyIndex = json.indexOf(key);
        if (keyIndex == -1)
            return "N/A";
        int colonIndex = json.indexOf(":", keyIndex);
        int commaIndex = json.indexOf(",", colonIndex);
        int endIndex = commaIndex == -1 ? json.indexOf("}", colonIndex) : commaIndex;
        String raw = json.substring(colonIndex + 1, endIndex).trim();
        return cleanString(raw);
    }

    private String extractValueFromNested(String json, String parentKey, String childKey) {
        int parentIndex = json.indexOf(parentKey);
        if (parentIndex == -1)
            return "N/A";
        int childIndex = json.indexOf(childKey, parentIndex);
        if (childIndex == -1)
            return "N/A";
        return extractValue(json.substring(childIndex - 20), "\"" + childKey + "\"");
    }

    private String extractValueFromArray(String json, String arrayKey, String targetKey) {
        int arrayIndex = json.indexOf(arrayKey);
        if (arrayIndex == -1)
            return "N/A";
        int objStart = json.indexOf("{", arrayIndex);
        int keyIndex = json.indexOf(targetKey, objStart);
        if (keyIndex == -1)
            return "N/A";
        return extractValue(json.substring(keyIndex - 10), "\"" + targetKey + "\"");
    }

    private String cleanString(String raw) {
        raw = raw.replaceAll("\"", "");
        raw = raw.replaceAll("}", "");
        raw = raw.replaceAll("\\]", "");
        raw = raw.replaceAll("\\[", "");
        return raw.trim();
    }

    private String capitalize(String text) {
        if (text == null || text.length() == 0)
            return text;
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new WeatherInfoSystem().setVisible(true);
        });
    }
}
