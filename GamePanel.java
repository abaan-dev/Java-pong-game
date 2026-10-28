/**
 * Core game engine panel running at ~120 FPS with clickable Restart/Quit buttons on Game Over.
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Ball ball;
    private Paddle player1, player2;
    
    private int score1 = 0;
    private int score2 = 0;
    
    private enum GameState { PLAYING, GAMEOVER }
    private GameState gameState = GameState.PLAYING;
    private String winnerMessage = "";

    // UI Buttons for Game Over screen
    private JButton restartButton;
    private JButton quitButton;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 560));
        setBackground(new Color(20, 60, 160));
        setLayout(null); // Allows absolute positioning for buttons
        setFocusable(true);
        addKeyListener(this);

        // Initialize Restart Button
        restartButton = new JButton("Restart");
        restartButton.setBounds(300, 270, 200, 45);
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setFocusable(false); // Prevents button from stealing arrow key focus
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> resetGame());
        add(restartButton);

        // Initialize Quit Button
        quitButton = new JButton("Quit");
        quitButton.setBounds(300, 330, 200, 45);
        quitButton.setFont(new Font("Arial", Font.BOLD, 18));
        quitButton.setFocusable(false);
        quitButton.setVisible(false);
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);

        ball = new Ball(400, 280);
        player1 = new Paddle(30, 235);
        player2 = new Paddle(755, 235);

        // 8ms delay = ~120 FPS
        timer = new Timer(8, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    public void resetGame() {
        score1 = 0;
        score2 = 0;
        ball.reset();
        gameState = GameState.PLAYING;
        
        // Hide buttons and resume gameplay
        restartButton.setVisible(false);
        quitButton.setVisible(false);
        
        requestFocusInWindow();
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Center net line
        g.setColor(new Color(255, 255, 255, 150));
        Graphics2D g2d = (Graphics2D) g;
        float[] dashPattern = {10f, 10f};
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
        g2d.drawLine(400, 0, 400, 560);

        // Scoreboard display
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(score1), 250, 70);
        g.drawString(String.valueOf(score2), 525, 70);

        // Game objects
        ball.draw(g);
        player1.draw(g);
        player2.draw(g);

        // Game Over Overlay Screen
        if (gameState == GameState.GAMEOVER) {
            g.setColor(new Color(0, 0, 0, 210));
            g.fillRect(0, 0, 800, 560);

            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.RED);
            int msgWidth = g.getFontMetrics().stringWidth(winnerMessage);
            g.drawString(winnerMessage, (800 - msgWidth) / 2, 190);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameState == GameState.PLAYING) {
            ball.update();
            player1.update();
            player2.updateCPU(ball.getY());

            // Bulletproof Paddle Collisions
            if (ball.getBounds().intersects(player1.getBounds()) && ball.getXVelocity() < 0) {
                ball.setX(player1.getBounds().x + player1.getBounds().width);
                ball.reverseXVelocity();
                ball.accelerate();
            }

            if (ball.getBounds().intersects(player2.getBounds()) && ball.getXVelocity() > 0) {
                ball.setX(player2.getBounds().x - ball.getDiameter());
                ball.reverseXVelocity();
                ball.accelerate();
            }

            // Score check
            if (ball.getX() < 0) {
                score2++;
                checkWinCondition();
                ball.reset();
            } else if (ball.getX() > 800) {
                score1++;
                checkWinCondition();
                ball.reset();
            }
        }
        repaint();
    }

    private void checkWinCondition() {
        if (score1 >= 10) {
            gameState = GameState.GAMEOVER;
            winnerMessage = "YOU WON!";
            timer.stop();
            restartButton.setVisible(true);
            quitButton.setVisible(true);
        } else if (score2 >= 10) {
            gameState = GameState.GAMEOVER;
            winnerMessage = "CPU WON!";
            timer.stop();
            restartButton.setVisible(true);
            quitButton.setVisible(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (gameState == GameState.PLAYING) {
            if (key == KeyEvent.VK_UP) {
                player1.setYVelocity(-1);
            }
            if (key == KeyEvent.VK_DOWN) {
                player1.setYVelocity(1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (gameState == GameState.PLAYING) {
            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                player1.setYVelocity(0);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}