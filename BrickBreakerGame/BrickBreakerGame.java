package BrickBreakerGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class BrickBreakerGame extends JPanel implements KeyListener {
    private boolean play = false;
    private Timer timer;
    private int delay = 8;

    public BrickBreakerGame() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer();
        timer.start()
    }

    public void paint(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        // ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 20, 20);

        g.dispose();
    }

    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            ballposX += ballXdir;
            ballposY += ballYdir;
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX < 600)
                playerX += 20;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX > 10)
                playerX -= 20;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            play = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        JFrame obj = new JFrame();
        BrickBreakerGame gamePlay = new BrickBreakerGame();
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Brick Breaker Game");
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
        obj.setVisible(true);
    }
}

public class MapGenerator {
    public int[][] map;
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int[] rowArr : map) {
            java.util.Arrays.fill(rowArr, 1);
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(new Color(255, 105, 180));
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(new Color(255, 20, 147));
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }
    
}