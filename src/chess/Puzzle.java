package chess;

import java.util.ArrayList;
import java.util.List;

public class Puzzle {
    private String[][] layout; // 8x8 grid of piece names (e.g., "w-rook")
    private List<Move> solutionMoves;
    private String description;
    private boolean whiteToMove;

    public Puzzle(String[][] layout, String description, boolean whiteToMove) {
        this.layout = layout;
        this.description = description;
        this.whiteToMove = whiteToMove;
        this.solutionMoves = new ArrayList<>();
    }

    public void addSolutionMove(int sr, int sc, int er, int ec) {
        solutionMoves.add(new Move(sr, sc, er, ec));
    }
    public boolean isValidSolutionMove(int step, int startR, int startC, int endR, int endC) {
        if (step >= solutionMoves.size()) return false;
        Move m = solutionMoves.get(step);
        return m.startRow == startR && m.startCol == startC && 
               m.endRow == endR && m.endCol == endC;
    }
    
    // Getters
    public String[][] getLayout() { return layout; }
    public String getDescription() { return description; }
    public boolean isWhiteToMove() { return whiteToMove; }
    public List<Move> getSolutionMoves() { return solutionMoves; }
}