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

}