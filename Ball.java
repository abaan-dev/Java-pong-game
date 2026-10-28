/**
 * Handles ball physics, velocity vectors, bouncing, and screen resetting.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
    private int x, y;
    private int diameter = 20;
    private int xVelocity, yVelocity;
    private Random random = new Random();

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        reset();
    }

    public void reset() {
        x = 400 - diameter / 2;
        y = 280 - diameter / 2;
        
        int randomXDir = random.nextBoolean() ? 1 : -1;
        int randomYDir = random.nextBoolean() ? 1 : -1;
        
        xVelocity = randomXDir * 3;
        yVelocity = randomYDir * 3;
    }

    public void update() {
        x += xVelocity;
        y += yVelocity;

        // Bounce off top and bottom walls
        if (y <= 0 || y >= 540) {
            yVelocity = -yVelocity;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, diameter, diameter);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    public void accelerate() {
        if (xVelocity > 0) xVelocity++;
        else xVelocity--;
        
        if (yVelocity > 0) yVelocity++;
        else yVelocity--;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // --- Helper methods required by GamePanel collision checks ---
    public int getXVelocity() {
        return xVelocity;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getDiameter() {
        return diameter;
    }
}