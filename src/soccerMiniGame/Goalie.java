package soccerMiniGame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

public class Goalie {
    public int x, y;
    public int width = 60, height = 80;
    public int speed = 4; 
    private Image goalieImage;

    public Goalie(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        try {
            goalieImage = ImageIO.read(getClass().getResource("/sprites/rGoalie.png"));
        } catch (Exception e) {
            System.out.println("Could not load rGoalie.png");
        }
    }

    public void update(Ball ball) {
        if (ball.isMoving) {
            int centerOfGoalie = x + (width / 2);
            int centerOfBall = (int)ball.x + (ball.size / 2);

            if (centerOfGoalie < centerOfBall - speed) {
                x += speed;
            } else if (centerOfGoalie > centerOfBall + speed) {
                x -= speed;
            }
        }
    }

    public void draw(Graphics g) {
        if (goalieImage != null) {
            g.drawImage(goalieImage, x, y, width, height, null);
        }
    }

    // 🚨 MODIFIED: Goalie hitbox is now only 50% of his body size, ignoring limbs/edges
    public Rectangle getBounds() {
        int hitboxWidth = (int)(width * 0.5);
        int hitboxHeight = (int)(height * 0.5);
        int offsetX = (width - hitboxWidth) / 2;
        int offsetY = (height - hitboxHeight) / 2;
        return new Rectangle(x + offsetX, y + offsetY, hitboxWidth, hitboxHeight);
    }
}