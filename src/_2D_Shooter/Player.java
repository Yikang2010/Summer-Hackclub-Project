package _2D_Shooter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Player extends KeyAdapter implements MouseListener, MouseMotionListener {
    public double x, y, angle = 0;
    private double speed = 4;
    private boolean up, down, left, right;
    public int health = 100;
    
    // --- SIZE VARIABLES ---
    public int size = 64; // Change this value to scale your player up or down!
    private int halfSize = size / 2;
    
    private int mouseX, mouseY;
    private BufferedImage sprite;
    public ArrayList<Bullet> bullets = new ArrayList<>();

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        loadSprite();
    }

    private void loadSprite() {
        try {
            URL imgUrl = getClass().getResource("/player.png");
            if (imgUrl != null) sprite = ImageIO.read(imgUrl);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void tick() {
        // 1. WASD Movement Logic
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        // 2. Aiming Logic: Centered using halfSize
        double diffX = mouseX - (x + halfSize);
        double diffY = mouseY - (y + halfSize);
        angle = Math.atan2(diffY, diffX);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
            if (bullets.get(i).lifetime <= 0) bullets.remove(i);
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        AffineTransform old = g2d.getTransform();

        // Translate to the middle of the player for rotation
        g2d.translate(x + halfSize, y + halfSize);
        g2d.rotate(angle);
        
        // Draw centered around the rotation point
        if (sprite != null) {
            g2d.drawImage(sprite, -halfSize, -halfSize, size, size, null);
        } else {
            g2d.setColor(Color.BLUE);
            g2d.fillRect(-halfSize, -halfSize, size, size);
            g2d.setColor(Color.WHITE); 
            g2d.drawLine(0, 0, halfSize + 4, 0); // Dynamic direction line
        }

        g2d.setTransform(old); 
        for (Bullet b : bullets) b.render(g);
    }

    // Hitbox scales automatically now
    public Rectangle getBounds() { return new Rectangle((int)x, (int)y, size, size); }

    // Keyboard Input
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) up = true;
        if (key == KeyEvent.VK_S) down = true;
        if (key == KeyEvent.VK_A) left = true;
        if (key == KeyEvent.VK_D) right = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) up = false;
        if (key == KeyEvent.VK_S) down = false;
        if (key == KeyEvent.VK_A) left = false;
        if (key == KeyEvent.VK_D) right = false;
    }

    // Mouse Input
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Spawn bullets close to the new centered position
        bullets.add(new Bullet(x + halfSize - 4, y + halfSize - 4, angle));
    }

    @Override public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
