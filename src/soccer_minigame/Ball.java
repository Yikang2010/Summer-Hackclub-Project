package soccer_minigame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Ball {
    public double x, y;
    public double vx, vy;
    public boolean isMoving = false;
    private Image ballImage;
    public int size = 150; 

    public Ball(int startX, int startY) {
        this.x = startX - (size / 2);
        this.y = startY - (size / 2);
        try {
            ballImage = ImageIO.read(getClass().getResource("/sprites/soccer_ball.png"));
        } catch (Exception e) {
            System.out.println("Could not load soccer_ball.png");
        }
    }

    public void kick(double velocityX, double velocityY) {
        this.vx = velocityX;
        this.vy = velocityY;
        this.isMoving = true;
    }
    
    public void update() {
        if (isMoving) {
            x += vx;
            y += vy ;
            vx *= 0.98; // Friction
            vy *= 0.98;

            if (Math.abs(vx) < 0.2 && Math.abs(vy) < 0.2) {
                isMoving = false;
                vx = 0; vy = 0;
            }
        }
    }

    public void draw(Graphics g) {
        if (ballImage != null) {
            g.drawImage(ballImage, (int)x, (int)y, size, size, null);
        }
    }

    // 🚨 MODIFIED: Shrunk to 50% of visual size for maximum leniency
    public Rectangle getBounds() {
        int hitboxSize = (int)(size * 0.5); 
        int offset = (size - hitboxSize) / 2; // Centers the tiny core hitbox
        return new Rectangle((int)x + offset, (int)y + offset, hitboxSize, hitboxSize);
    }
}