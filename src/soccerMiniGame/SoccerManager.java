package soccerMiniGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import game_engine.GameState;
import game_engine.MiniGames; // Missing import added
import java.io.File;
import javax.imageio.ImageIO;

public class SoccerManager extends JPanel implements ActionListener, MouseListener, MouseMotionListener, MiniGames {
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

    private final int WIDTH = 1470;
    
    private final int HEIGHT = 980;

    private GameState engine;

    // Merged into a single, clean constructor
    public SoccerManager(GameState engine) {
        this.engine = engine;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addMouseListener(this);
        addMouseMotionListener(this);
        
        uiManager = new UIManager();
        trajectory = new BallTrajectory();
        goal = new Goal(WIDTH); 
        
        try {
            //background = ImageIO.read(new File("src/soccerMiniGame/goal.jpg"));
        	background = ImageIO.read(getClass().getResource("/soccerMiniGame/goal.jpg"));
        } catch (Exception e) {
            System.out.println("Could not load goal.jpg. Check the file path!");
        }
        resetRound();
    }
    
    @Override public JPanel getGamePanel() { return this; }
    
    @Override public void init() { 
        gameLoop = new Timer(16, this);
        gameLoop.start();
        this.requestFocusInWindow();
    }
    
    @Override public void stop() { 
        if (gameLoop != null) gameLoop.stop();
    }

    private void resetRound() {
        ball = new Ball(WIDTH / 2, HEIGHT - 200); 
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
            misses++;
            checkWinLoss();
            resetRound();
        }
    }
 
    private void checkWinLoss() {
        if (goals >= 5) { 
            gameOver = true; 
            endMessage = "WORLD CLASS FINISHER!"; 
            stop(); // Stop game loop
            engine.onGameFinished(); // THE FIX: Move to the next game!
        }
        else if (misses >= 2) { 
            gameOver = true; 
            endMessage = "MATCH OVER - YOU LOST"; 
            stop();
        }
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (background != null) {
//            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
//        }
//        goalie.draw(g);
//        ball.draw(g);
//        trajectory.draw(g); 
//        uiManager.drawScores(g, goals, misses, endMessage);
//        if (gameOver) uiManager.drawEndScreen(g, endMessage, WIDTH, HEIGHT);
//    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 1. Draw the background FIRST so it stays in the back
        if (background != null) {
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, this);
        }
        
        // 2. Draw your game objects on top of the background
        goalie.draw(g);
        ball.draw(g);
        trajectory.draw(g);
        
        // 3. Draw your text/UI on the absolute top layer
        uiManager.drawScores(g, goals, misses, endMessage);
        if (gameOver) uiManager.drawEndScreen(g, endMessage, WIDTH, HEIGHT);
    }
    
    
    
    

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
            
            double distance = Math.sqrt(dx * dx + dy * dy);
            double maxPower = 200.0; 
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
 }



