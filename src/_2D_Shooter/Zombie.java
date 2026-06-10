package _2D_Shooter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform; // Required for rotation
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Zombie {
    // Positioning and movement
    public double x, y;
    private double speed;
    private double angle = 0; // The direction the zombie is facing
    
    // Stats
    public int health;
    public int maxHealth; // Added this so the health bar works for all waves
    private final int SIZE = 72; // Standard size for hitbox and sprite
    
    private BufferedImage sprite;

    // Added 'wave' parameter to the constructor
    public Zombie(int x, int y, int wave) {
        this.x = x;
        this.y = y;
        
        String spriteName = "/zombie.png"; // Default

        // Set stats based on the wave
        if (wave == 1) {
            this.speed = 1.5;
            this.maxHealth = 3;
            spriteName = "/zombie.png";
        } else if (wave == 2) {
            this.speed = 4.5; // More speed
            this.maxHealth = 3;
            spriteName = "/zombie2.png";
        } else if (wave >= 3) {
            this.speed = 2.8; // A bit more speed than wave 2
            this.maxHealth = 5; // A lot more health
            spriteName = "/zombie3.png";
        }

        this.health = this.maxHealth; // Start at full health
        loadSprite(spriteName);
    }

    // Now takes the filename as an argument
    private void loadSprite(String filename) {
        try {
            URL imgUrl = getClass().getResource(filename);
            if (imgUrl != null) {
                sprite = ImageIO.read(imgUrl);
//<<<<<<< HEAD
//<<<<<<< HEAD
                System.out.println("Zombie sprite");
            } else {
                System.err.println("zombie.png NOT");
////=======
////>>>>>>> branch 'master' of https://github.com/Yikang2010/Chou-Yikang-Juigistopia_Master_of_Mischief
//=======
            //} else {
                System.out.println("Could not find sprite: " + filename);
//>>>>>>> branch 'master' of https://github.com/Yikang2010/Chou-Yikang-Juigistopia_Master_of_Mischief
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(double playerX, double playerY) {
        // 1. Calculate the angle to the player
        double diffX = playerX - this.x;
        double diffY = playerY - this.y;
        
        // atan2 gives us the angle in radians between the zombie and player
        this.angle = Math.atan2(diffY, diffX);

        // 2. Move toward the player using trigonometry
        // This ensures the zombie moves directly toward the player at its set speed
        x += Math.cos(angle) * speed;
        y += Math.sin(angle) * speed;
    }
    
 // The evidence is saying how because of open sources transparenty anyone developers around the world can find the vurnibiltys and fix it as the many eyes 
    //that can see it can fix it. That is different to private code as the vurnibitlys stay hidden. Which causes the 
    //
    
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        // Save the current state of the graphics settings
        AffineTransform old = g2d.getTransform();

        // Move the drawing "pen" to the center of the zombie and rotate it
        g2d.translate(x + SIZE / 2, y + SIZE / 2);
        g2d.rotate(angle);

        if (sprite != null) {
            // Draw the sprite centered at the new rotated origin
            g2d.drawImage(sprite, -SIZE / 2, -SIZE / 2, SIZE, SIZE, null);
        } else {
            // Fallback red square if the image is missing
            g2d.setColor(Color.RED);
            g2d.fillRect(-SIZE / 2, -SIZE / 2, SIZE, SIZE);
        }

        // Restore the graphics settings so we don't rotate everything else on screen
        g2d.setTransform(old);

        // Draw a small health bar above the zombie's head
        // drawHealthBar(g);
    }

//    private void drawHealthBar(Graphics g) {
//        // Background (Red)
//        g.setColor(Color.RED);
//        g.fillRect((int)x, (int)y - 10, SIZE, 4);
//        
//        // Foreground (Green) based on current health
//        g.setColor(Color.GREEN);
//        // Updated this to use maxHealth instead of hardcoding 3.0!
//        int barWidth = (int)((health / (double)maxHealth) * SIZE);
//        g.fillRect((int)x, (int)y - 10, barWidth, 4);
//    }

    // Called when the zombie is hit by a bullet
    public void hit() {
        health--;
    }
    // A risk of open source is that there might be vurniblities to the code that might be easy to 
    // This is the "Hitbox" used by the Frame class for collisions
    
    /*
     * Introduce evidence:
	Summarize the evidence: 
	Argue how the opposition fails to weaken your argument:

     * 
     * 
     * **/
    
    // The evidence is saying how because of open sources transparenty anyone developers around the world can find the vurnibiltys and 
    
    public Rectangle getBounds() { 
        return new Rectangle((int)x, (int)y, SIZE, SIZE); 
    }
}   
    
    
    
    
