package _2D_Shooter;

import javax.swing.*;

//import _2D_Shooter.Background;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import game_engine.MiniGames;
import game_engine.GameState;

public class Frame extends JPanel implements ActionListener, MiniGames {

    Player player = new Player(400, 300);
    ArrayList<Zombie> zombies = new ArrayList<>();
    Health healthUI = new Health();
    Background bg = new Background();
    Timer timer = new Timer(16, this);
    GameState engine; // Added engine reference

    int currentWave = 1;
    boolean gameWon = false;

    public Frame(GameState engine) {
        this.engine = engine;
        this.setPreferredSize(new Dimension(800, 600));
        this.setFocusable(true);
        this.addKeyListener(player);
        this.addMouseListener(player);
        this.addMouseMotionListener(player);
    }

    @Override
    public JPanel getGamePanel() { return this; }

    @Override
    public void init() {
        spawnWave();
        timer.start();
        this.requestFocusInWindow();
    }

    @Override
    public void stop() {
        timer.stop();
    }

    private void spawnWave() {
        zombies.clear();
        for(int i = 0; i < 5; i++) {
            zombies.add(new Zombie(i * 150 + 50, 50, currentWave));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bg.render(g); 
        player.render(g);
        for (Zombie z : zombies) z.render(g);
        healthUI.render(g, player.health);
        
        if (player.health <= 0 || gameWon) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(player.health <= 0 ? Color.RED : Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            String msg = player.health <= 0 ? "GAME OVER" : "WAVE CLEARED!";
            g.drawString(msg, 250, 300);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (player.health <= 0 || gameWon) return;
        player.tick();
        for (int i = zombies.size() - 1; i >= 0; i--) {
            Zombie z = zombies.get(i);
            z.tick(player.x, player.y);
            if (z.getBounds().intersects(player.getBounds())) player.health -= 1;
            for (int j = player.bullets.size() - 1; j >= 0; j--) {
                Bullet b = player.bullets.get(j);
                if (z.getBounds().intersects(b.getBounds())) {
                    player.bullets.remove(j);
                    z.hit();
                    if (z.health <= 0) zombies.remove(i);
                    break; 
                }
            }
        }
        if (zombies.isEmpty() && !gameWon) {
            currentWave++;
            if (currentWave > 3) {
                gameWon = true;
                timer.stop();
                // THE CRITICAL FIX: Tell the engine to move to the next game!
                engine.onGameFinished(); 
            }
            else spawnWave();
        }
        repaint();
    }
}


