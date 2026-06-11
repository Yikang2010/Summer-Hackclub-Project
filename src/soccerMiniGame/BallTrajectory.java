package soccerMiniGame;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class BallTrajectory {
    public boolean isAiming = false;
    public int startX, startY;
    public int currentX, currentY;
    private Image arrowImage;
    private final int maxPower = 200; // Increased to match the larger field
    
    public BallTrajectory() {
        try {
            arrowImage = ImageIO.read(getClass().getResource("/sprites/arrow (1).png"));
        } catch (Exception e) {
            System.out.println("Could not load arrow.png");
        }
    }

    public void draw(Graphics g) {
        if (isAiming) {
            Graphics2D g2d = (Graphics2D) g.create(); // Create a copy to avoid messing up other draws

            int dx = startX - currentX;
            int dy = startY - currentY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            // 1. Draw the Dotted Line (The "Path")
            float[] dash = {10.0f};
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            g2d.setColor(new Color(255, 255, 255, 150)); // Semi-transparent white
            
            // Clamp distance for the line calculation
            double drawDist = Math.min(distance, maxPower + 100); 
            double angle = Math.atan2(dy, dx);
            
            int endLineX = (int) (startX + Math.cos(angle) * drawDist);
            int endLineY = (int) (startY + Math.sin(angle) * drawDist);
            g2d.drawLine(startX, startY, endLineX, endLineY);

            // 2. Draw the Rotated Arrow Image
            if (arrowImage != null) {
                // Calculate rotation (atan2 takes y, x)
                // We add Math.PI/2 if your arrow points "Up" by default. 
                // If your arrow points "Right" by default, just use 'angle'.
                double rotation = angle + Math.PI / 2; 

                AffineTransform at = new AffineTransform();
                at.translate(startX, startY); // Move to ball center
                at.rotate(rotation);
                
                // Scale arrow based on power (optional, makes it look dynamic)
                double scale = Math.min(distance / maxPower, 1.0);
                at.scale(0.5 + (scale * 0.5), 0.5 + (scale * 0.5)); 
                
                // Center the image on the anchor point
                at.translate(-arrowImage.getWidth(null) / 2.0, -arrowImage.getHeight(null) / 2.0);

                g2d.drawImage(arrowImage, at, null);
            }

            g2d.dispose(); // Clean up the graphics copy
        }
    }
}