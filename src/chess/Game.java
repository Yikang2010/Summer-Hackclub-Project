package chess;

import javax.swing.JPanel;
import game_engine.MiniGames;
import game_engine.GameState;
import java.awt.BorderLayout;

public class Game extends JPanel implements MiniGames {
    private GameState engine;
    private Board board;

    public Game(GameState engine) {
        this.engine = engine;
        this.setLayout(new BorderLayout());
        
        // Pass the engine down into the Board!
        this.board = new Board(engine); 
        this.add(board, BorderLayout.CENTER);
    }

    @Override 
    public JPanel getGamePanel() { 
        return this; 
    }

    @Override 
    public void init() { 
        // 1. Pass the mouse/keyboard focus DOWN to the actual board
        board.setFocusable(true);
        board.requestFocusInWindow();
        
        // 2. WAKE UP THE BOARD (This triggers the animations now!)
        board.startPuzzle(); 
    }

    @Override 
    public void stop() { 
        // Background processes stop here if needed
    }
}
