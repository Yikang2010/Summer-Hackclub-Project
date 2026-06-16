package chess;

public class King extends Piece {
    public King(boolean isWhite, String imagePath) { super(isWhite, imagePath); }

    @Override
    public boolean isValidMove(int startR, int startC, int endR, int endC, Piece[][] grid) {
        int rowDiff = Math.abs(startR - endR);
        int colDiff = Math.abs(startC - endC);
        // King moves exactly 1 square in any direction
        return rowDiff <= 1 && colDiff <= 1;
    }
}