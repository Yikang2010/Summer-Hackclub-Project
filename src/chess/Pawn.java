package chess;

public class Pawn extends Piece {
    public Pawn(boolean isWhite, String imagePath) { super(isWhite, imagePath); }

    @Override
    public boolean isValidMove(int startR, int startC, int endR, int endC, Piece[][] grid) {
        int dir = isWhite ? -1 : 1;
        int startRow = isWhite ? 6 : 1;

        // Moving straight forward
        if (startC == endC) {
            if (endR == startR + dir && grid[endR][endC] == null) return true;
            if (startR == startRow && endR == startR + (2 * dir) && grid[endR][endC] == null && grid[startR + dir][endC] == null) return true;
        }
        // Capturing diagonally
        if (Math.abs(startC - endC) == 1 && endR == startR + dir) {
            if (grid[endR][endC] != null && grid[endR][endC].isWhite() != this.isWhite) return true;
        }
        return false;
    }
}