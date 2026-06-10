package _2D_Shooter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Health {
    private BufferedImage icon;

    public Health() {
        try {
            icon = ImageIO.read(new File("health.png"));
        } catch (Exception e) {
            System.out.println("Console: health.png missing.");
        }
    }

    public void render(Graphics g, int healthValue) {
        // Draw the icon
        if (icon != null) {
            g.drawImage(icon, 10, 10, 30, 30, null);
        }
        
        // Draw a simple health bar
        g.setColor(Color.GRAY);
        g.fillRect(50, 15, 200, 20);
        g.setColor(Color.GREEN);
        if (healthValue < 30) g.setColor(Color.RED);
        g.fillRect(50, 15, healthValue * 2, 20);
        
        g.setColor(Color.WHITE);
        g.drawString("HP: " + healthValue, 55, 30);
    }
}