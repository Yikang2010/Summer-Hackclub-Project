package soccer_minigame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UIManager {
    public void drawScores(Graphics g, int goals, int misses, String feedback) {
        // 1. Sleek top menu banner for high contrast readability
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, 1470, 100);
        
        // 2. Score text
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.drawString("GOALS: " + goals + " / 5", 40, 42);
        
        g.setColor(Color.RED);
        g.drawString("MISSES: " + misses + " / 2", 40, 80);

        // 3. Gameplay Instructions
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("HOW TO PLAY: Click & drag backwards from the ball to aim. Release to shoot!", 320, 58);

        // 4. Round Feedback Message (Flashes GOAL or WIDE OF POST)
        if (!feedback.isEmpty()) {
            if (feedback.equals("GOAL!!")) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.ORANGE);
            }
            g.setFont(new Font("Arial", Font.BOLD, 36));
            int textWidth = g.getFontMetrics().stringWidth(feedback);
            g.drawString(feedback, (1470 - textWidth) / 2, 160);
        }
    }

    public void drawEndScreen(Graphics g, String message, int screenWidth, int screenHeight) {
        g.setColor(new Color(0, 0, 0, 200)); 
        g.fillRect(0, 0, screenWidth, screenHeight);
        
        if (message.contains("OVER")) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.YELLOW);
        }
        
        g.setFont(new Font("Arial", Font.BOLD, 64));
        int textWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, (screenWidth - textWidth) / 2, screenHeight / 2);
    }
}