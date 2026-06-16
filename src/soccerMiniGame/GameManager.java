package soccerMiniGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import javax.imageio.ImageIO;

public class GameManager extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    private Ball ball;
    private Goalie goalie;
    private Goal goal;
    private BallTrajectory trajectory;
    private UIManager uiManager;
    private Image background;
    private int goals = 0;
    private int misses = 0;
    private boolean gameOver = false;
    private String endMessage = "";
    private Timer gameLoop;

    // Updated to match your new goal.jpg dimensions
    private final int WIDTH = 1470;
    private final int HEIGHT = 980;

    public GameManager() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);
        addMouseMotionListener(this);
        
        uiManager = new UIManager();
        trajectory = new BallTrajectory();
        goal = new Goal(WIDTH); // Goal hit-box will scale to the new width
        
        try {
            // Updated filename
            background = ImageIO.read(new File("src/soccerMiniGame/goal.jpg"));
        } catch (Exception e) {
            System.out.println("Could not load goal.jpg. Check the file path!");
        }

        resetRound();
        gameLoop = new Timer(16, this);
        gameLoop.start();
    }

    private void resetRound() {
        // Positioned the ball at the bottom center of the new 1470x980 area
        ball = new Ball(WIDTH / 2, HEIGHT - 200); 
        
        // Positioned the goalie relative to the new height
        goalie = new Goalie(WIDTH / 2 - 75, 250);
        goalie.width = 150; 
        goalie.height = 200;
        
        trajectory.isAiming = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            ball.update();
            goalie.update(ball);
            checkCollisions();
        }
        repaint();
    }

    private void checkCollisions() {
        if (!ball.isMoving) return;

        if (ball.getBounds().intersects(goalie.getBounds())) {
            misses++;
            checkWinLoss();
            resetRound();
        } else if (goal.isGoal(ball)) {
            goals++;
            checkWinLoss();
            resetRound();
        } else if (ball.y < -200 || ball.x < -200 || ball.x > WIDTH + 200) {
            // Ball went out of bounds
            misses++;
            checkWinLoss();
            resetRound();
        }
    }

    private void checkWinLoss() {
        if (goals >= 5) { gameOver = true; endMessage = "WORLD CLASS FINISHER!"; }
        else if (misses >= 2) { gameOver = true; endMessage = "MATCH OVER - YOU LOST"; }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
        }
        
        goalie.draw(g);
        ball.draw(g);
        trajectory.draw(g); 
        
        uiManager.drawScores(g, goals, misses, endMessage);
        if (gameOver) uiManager.drawEndScreen(g, endMessage, WIDTH, HEIGHT);
    }

    // --- MOUSE METHODS (No changes needed here) ---
    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver && !ball.isMoving) {
            trajectory.isAiming = true;
            trajectory.startX = (int)ball.x + (ball.size / 2);
            trajectory.startY = (int)ball.y + (ball.size / 2);
            trajectory.currentX = e.getX();
            trajectory.currentY = e.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (trajectory.isAiming) {
            trajectory.currentX = e.getX();
            trajectory.currentY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (trajectory.isAiming) {
            trajectory.isAiming = false;
            double dx = trajectory.startX - e.getX();
            double dy = trajectory.startY - e.getY();
            
            // Power clamping
            double distance = Math.sqrt(dx * dx + dy * dy);
            double maxPower = 200.0; // Increased max power slightly for the larger screen
            if (distance > maxPower) {
                double ratio = maxPower / distance;
                dx *= ratio;
                dy *= ratio;
            }
            ball.kick(dx * 0.18, dy * 0.18); 
        }
    }

    public void mouseMoved(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Soccer Shootout");
        frame.add(new GameManager());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}