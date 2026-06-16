package chess;

import javax.swing.*;

import game_engine.GameState;

//import game_engine.GameState;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
  

	
	
	








	
	
	
	
	
	 



	
	
	










	
public class Board extends JPanel {
    private BufferedImage boardImage;
    private Piece[][] grid = new Piece[8][8];
    private final int TILE_SIZE = 60;
    
    // STATES: 
    // -1 = Animating (Ignore clicks)
    //  0 = Initial Black Capture
    //  1 = White Turn 1 (Rook)
    //  2 = White Turn 2 (Queen)
    //  3 = White Turn 3 (Rook Capture)
    //  4 = Solved!
    // 99 = Locked / Incorrect
    private int puzzleState = 0; 
    private Piece selectedPiece = null;
    private int selectedRow = -1, selectedCol = -1;
 // Add GameState engine to the constructor
   // private GameState engine;

//    public Board(GameState engine) {
//        this.engine = engine; 
//        // ... existing try/catch code ...
//    }
 // 1. Add this field at the top with your other variables
    private GameState engine; 
    
    // ... existing variables (boardImage, grid, etc.) ...

    // 2. Update the constructor to accept GameState
    public Board(GameState engine) {
        this.engine = engine; // Save the engine link
        
        try {
            boardImage = ImageIO.read(new File("src/chess/chessBoard460.png"));
            setupPuzzleStart();
            startBlackCaptureAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // ... rest of your mouse listener code ...
    }
    
    
    
    
    public Board() {
        try {
            boardImage = ImageIO.read(new File("src/chess/chessBoard460.png"));
            setupPuzzleStart();
            startBlackCaptureAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Ignore clicks if animating, locked, or solved
                if (puzzleState == 0 || puzzleState == -1 || puzzleState == 99 || puzzleState == 4) return;

                int col = e.getX() / TILE_SIZE;
                int row = e.getY() / TILE_SIZE;

                if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                    handlePuzzleInteraction(row, col);
                }
            }
        });
    }

    private void setupPuzzleStart() {
        String p = "src/chess/";
        for (int r = 0; r < 8; r++) for (int c = 0; c < 8; c++) grid[r][c] = null;

        // --- YOUR EXACT SETUP ---
        grid[0][3] = new Rook(true, p + "w-rook.png");       // Target Rook on d8
        grid[0][4] = new Rook(false, p + "b-Rook.png");      // Attacker on e8
        grid[0][7] = new King(false, p + "b-king.png");      // h8
        grid[2][2] = new Queen(false, p + "b-queen.png");    // c6
        grid[1][6] = new Pawn(false, p + "b-Pawn.png");      // g6
        grid[1][7] = new Pawn(false, p + "b-Pawn.png");      // h6
        grid[5][4] = new Rook(false, p + "b-Rook.png");
        
        grid[6][5] = new Queen(true, p + "w-queen.png");     // f2
        grid[7][2] = new King(true, p + "w-king.png");       // c1
        grid[6][1] = new Pawn(true, p + "w-pawn.png");       // b2
        grid[6][2] = new Pawn(true, p + "w-pawn.png");       // c2
        grid[7][3] = new Rook(true, p + "w-rook.png");       // STARTING ROOK
    }

    private void handlePuzzleInteraction(int row, int col) {
        if (selectedPiece == null) {
            if (grid[row][col] != null && grid[row][col].isWhite()) {
                selectedPiece = grid[row][col];
                selectedRow = row; selectedCol = col;
            }
        } else {
            // Deselect if clicking the same piece
            if (row == selectedRow && col == selectedCol) {
                selectedPiece = null;
                repaint();
                return;
            }

            // STEP 1: White Rook (7,3) -> (0,3)
            if (puzzleState == 1) {
                if (selectedRow == 7 && selectedCol == 3 && row == 0 && col == 3) {
                    executeMove(row, col);
                    puzzleState = -1; // Lock for animation
                    triggerBlackQueenBlock(); 
                } else {
                    puzzleState = 99; // Incorrect move
                }
            } 
            // STEP 2: White Queen (6,5) -> (0,5)
            else if (puzzleState == 2) {
                if (selectedRow == 6 && selectedCol == 5 && row == 0 && col == 5) {
                    executeMove(row, col);
                    puzzleState = -1; // Lock for animation
                    triggerBlackQueenCapture();
                } else {
                    puzzleState = 99; // Incorrect move
                }
            } 
            // STEP 3: White Rook (0,3) -> (0,5)
            else if (puzzleState == 3) {
                if (selectedRow == 0 && selectedCol == 3 && row == 0 && col == 5) {
                    executeMove(row, col);
                    puzzleState = 4; // PUZZLE SOLVED!
                    
                    
                    
                 // --- ADD TRANSITION HERE ---
                    // This is the exact moment the final move is made.
                    // We tell the GameManager to swap to the next game (Soccer).
                    if (engine != null) {
                        engine.onGameFinished();
                    }
                    // ---------------------------
                } else {
                    puzzleState = 99; // Incorrect move
                }
            }

            selectedPiece = null;
            repaint();
        }
    }

    private void executeMove(int row, int col) {
        grid[row][col] = selectedPiece;
        grid[selectedRow][selectedCol] = null;
    }

    // --- AI ANIMATIONS ---

    private void startBlackCaptureAnimation() {
        Timer timer = new Timer(1000, e -> {
            grid[0][3] = grid[0][4]; // Move Black Rook from e8 to d8
            grid[0][4] = null;
            puzzleState = 1;         // UNLOCKS WHITE TURN 1
            repaint();
            ((Timer)e.getSource()).stop();
        });
        timer.start();
    }

    private void triggerBlackQueenBlock() {
        Timer timer = new Timer(800, e -> {
            grid[0][4] = grid[2][2]; // Black Queen blocks at e8
            grid[2][2] = null;
            puzzleState = 2;         // UNLOCKS WHITE TURN 2
            repaint();
            ((Timer)e.getSource()).stop();
        });
        timer.start();
    }

    private void triggerBlackQueenCapture() {
        Timer timer = new Timer(800, e -> {
            grid[0][5] = grid[0][4]; // Black Queen takes White Queen at f8
            grid[0][4] = null;
            puzzleState = 3;         // UNLOCKS WHITE TURN 3
            repaint();
            ((Timer)e.getSource()).stop();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (boardImage != null) g.drawImage(boardImage, 0, 0, 480, 480, this);

        if (selectedPiece != null) {
            g.setColor(new Color(255, 255, 0, 150));
            g.fillRect(selectedCol * TILE_SIZE, selectedRow * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (grid[r][c] != null) {
                    grid[r][c].draw(g, c * TILE_SIZE, r * TILE_SIZE, 60);
                }
            }
        }

        // Draw Game Over Overlays
        if (puzzleState == 99) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, 480, 480);
            g.setColor(Color.WHITE);
            g.drawString("BOARD LOCKED: INCORRECT SEQUENCE", 120, 240);
        } else if (puzzleState == 4) {
            g.setColor(new Color(0, 255, 0, 100)); // Success green tint
            g.fillRect(0, 0, 480, 480);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("PUZZLE SOLVED!", 150, 240);
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Chess Puzzle");
        f.add(new Board());
        f.setSize(496, 519);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}