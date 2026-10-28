/**
 * Represents a player or CPU paddle.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Paddle {
    private int x, y;
    private int width = 15;
    private int height = 90;
    private int yVelocity = 0;
    private int speed = 4; // Adjusted for 120 FPS smoothness

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y += yVelocity;
        if (y < 0) y = 0;
        if (y > 470) y = 470;
    }

    public void updateCPU(int targetY) {
        int paddleCenter = y + (height / 2);
        int cpuSpeed = 3; // Adjusted for 120 FPS smoothness

        if (paddleCenter < targetY - 6) {
            y += cpuSpeed;
        } else if (paddleCenter > targetY + 6) {
            y -= cpuSpeed;
        }

        if (y < 0) y = 0;
        if (y > 470) y = 470;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    public void setYVelocity(int dir) {
        this.yVelocity = dir * speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}