package _2D_Shooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.Timer;
import game_engine.MiniGames;
import game_engine.GameState;

public class Main extends JPanel implements MiniGames {
    
    private GameState engine;
    private Timer gameTimer;
    
    // Set these to your actual game dimensions!
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public Main(GameState engine) {
        this.engine = engine;
        
        // 1. Give the panel size
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        this.setFocusable(true);

        // 2. Initialize your Timer (replace '16' with your speed, 'this' with your logic)
        // If you have an actionPerformed method in this class:
        // gameTimer = new Timer(16, e -> { 
        //    updateGame(); 
        //    repaint(); 
        // });
    }

    @Override
    public JPanel getGamePanel() {
        return this;
    }

    @Override
    public void init() {
        System.out.println("Shooter Initializing...");
        // Reset your player health/zombies here
        if (gameTimer != null) {
            gameTimer.start();
        }
    }

    @Override
    public void stop() {
        System.out.println("Shooter Stopping...");
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // TEST DRAW: See if anything shows up
        g.setColor(Color.GREEN);
        g.fillRect(10, 10, 50, 50); 
        
        // YOUR GAME DRAWING LOGIC HERE
        // player.draw(g);
        // zombies.draw(g);
    }
}