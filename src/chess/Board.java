package chess;

import javax.swing.*;
import game_engine.GameState;
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
    private GameState engine; 
    
    public Board(GameState engine) {
    	this.engine = engine; // Save the engine link
        
        // Tell the engine window exactly how big this chess panel needs to be:
        this.setPreferredSize(new Dimension(480, 480));
        
        try {
            //boardImage = ImageIO.read(new File("src/chess/chessBoard460.png"));
        	// This looks inside the JAR/classpath for the file
        	boardImage = ImageIO.read(getClass().getResource("/chess/chessBoard460.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // MOVED THE MOUSE LISTENER HERE! Now it actually listens.
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
    
    // NEW METHOD: Called by chess.Game when the panel is actually shown
    public void startPuzzle() {
        setupPuzzleStart();
        startBlackCaptureAnimation();
    }

//    private void setupPuzzleStart() {
//        String p = "src/chess/";
//        for (int r = 0; r < 8; r++) for (int c = 0; c < 8; c++) grid[r][c] = null;
//
//        grid[0][3] = new Rook(true, p + "w-rook.png");       // Target Rook on d8
//        grid[0][4] = new Rook(false, p + "b-Rook.png");      // Attacker on e8
//        grid[0][7] = new King(false, p + "b-king.png");      // h8
//        grid[2][2] = new Queen(false, p + "b-queen.png");    // c6
//        grid[1][6] = new Pawn(false, p + "b-Pawn.png");      // g6
//        grid[1][7] = new Pawn(false, p + "b-Pawn.png");      // h6
//        grid[5][4] = new Rook(false, p + "b-Rook.png");
//        
//        grid[6][5] = new Queen(true, p + "w-queen.png");     // f2
//        grid[7][2] = new King(true, p + "w-king.png");       // c1
//        grid[6][1] = new Pawn(true, p + "w-pawn.png");       // b2
//        grid[6][2] = new Pawn(true, p + "w-pawn.png");       // c2
//        grid[7][3] = new Rook(true, p + "w-rook.png");       // STARTING ROOK
//    }
    
    private void setupPuzzleStart() {
        for (int r = 0; r < 8; r++) for (int c = 0; c < 8; c++) grid[r][c] = null;

        // Just pass the raw file names now!
        grid[0][3] = new Rook(true, "w-rook.png");       
        grid[0][4] = new Rook(false, "b-Rook.png");      
        grid[0][7] = new King(false, "b-king.png");      
        grid[2][2] = new Queen(false, "b-queen.png");    
        grid[1][6] = new Pawn(false, "b-Pawn.png");      
        grid[1][7] = new Pawn(false, "b-Pawn.png");      
        grid[5][4] = new Rook(false, "b-Rook.png");
        
        grid[6][5] = new Queen(true, "w-queen.png");     
        grid[7][2] = new King(true, "w-king.png");       
        grid[6][1] = new Pawn(true, "w-pawn.png");       
        grid[6][2] = new Pawn(true, "w-pawn.png");       
        grid[7][3] = new Rook(true, "w-rook.png");       
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
                    
                    if (engine != null) {
                        engine.onGameFinished(); // Transition to Soccer!
                    }
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
}









