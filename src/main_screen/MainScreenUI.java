//package main_screen;
//
//import java.awt.*;
//
//public class MainScreenUI {
//
//    public void drawTitle(Graphics g, int screenWidth) {
//        g.setColor(Color.WHITE);
//        g.setFont(new Font("Serif", Font.BOLD, 64));
//        FontMetrics fm = g.getFontMetrics();
//        String title = "MINI GAMES";
//        int x = (screenWidth - fm.stringWidth(title)) / 2;
//        g.drawString(title, x, 120);
//    }
//
//    public void drawInstructions(Graphics g, int screenWidth, int screenHeight) {
//        g.setColor(Color.LIGHT_GRAY);
//        g.setFont(new Font("Serif", Font.PLAIN, 18));
//        g.setFont(new Font("Arial", Font.BOLD, 64));
//        FontMetrics fm = g.getFontMetrics();
//        String title = "MINI GAMES";
//        int x = (screenWidth - fm.stringWidth(title)) / 2;
//        g.drawString(title, x, 120);
//    }
//
//    public void drawInstructions(Graphics g, int screenWidth, int screenHeight) {
//        g.setColor(Color.LIGHT_GRAY);
//        g.setFont(new Font("Arial", Font.PLAIN, 18));
//        String text = "Select a game to play";
//        FontMetrics fm = g.getFontMetrics();
//        int x = (screenWidth - fm.stringWidth(text)) / 2;
//        g.drawString(text, x, screenHeight - 30);
//    }
//}

package main_screen;

import java.awt.*;

public class MainScreenUI {

    public void drawTitle(Graphics g, int screenWidth) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 64));
        FontMetrics fm = g.getFontMetrics();
        String title = "MINI GAMES";
        int x = (screenWidth - fm.stringWidth(title)) / 2;
        g.drawString(title, x, 120);
    }

    public void drawInstructions(Graphics g, int screenWidth, int screenHeight) {
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Serif", Font.PLAIN, 18)); 
        String text = "Select a game to play";
        FontMetrics fm = g.getFontMetrics();
        int x = (screenWidth - fm.stringWidth(text)) / 2;
        g.drawString(text, x, screenHeight - 30);
    }
}
 