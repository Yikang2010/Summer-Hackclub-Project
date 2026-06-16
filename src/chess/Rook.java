package chess;

public class Rook extends Piece {
    public Rook(boolean isWhite, String imagePath) { super(isWhite, imagePath); }

    @Override
    public boolean isValidMove(int startR, int startC, int endR, int endC, Piece[][] grid) {
        if (startR != endR && startC != endC) return false; // Must be straight line

        // Collision checking
        if (startR == endR) {
            int min = Math.min(startC, endC);
            int max = Math.max(startC, endC);
            for (int i = min + 1; i < max; i++) if (grid[startR][i] != null) return false;
        } else {
            int min = Math.min(startR, endR);
            int max = Math.max(startR, endR);
            for (int i = min + 1; i < max; i++) if (grid[i][startC] != null) return false;
        }
        return true;
    }
}