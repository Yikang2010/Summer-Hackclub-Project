package chess;

public class Bishop extends Piece {
    public Bishop(boolean isWhite, String imagePath) { super(isWhite, imagePath); }

    @Override
    public boolean isValidMove(int startR, int startC, int endR, int endC, Piece[][] grid) {
        if (Math.abs(startR - endR) != Math.abs(startC - endC)) return false; // Must be diagonal

        int rDir = (endR - startR) > 0 ? 1 : -1;
        int cDir = (endC - startC) > 0 ? 1 : -1;
        
        // Collision checking
        int r = startR + rDir;
        int c = startC + cDir;
        while (r != endR && c != endC) {
            if (grid[r][c] != null) return false;
            r += rDir;
            c += cDir;
        }
        return true;
    }
}