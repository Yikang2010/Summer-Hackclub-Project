package chess;

public class Knight extends Piece {
    public Knight(boolean isWhite, String imagePath) { super(isWhite, imagePath); }

    @Override
    public boolean isValidMove(int startR, int startC, int endR, int endC, Piece[][] grid) {
        int rowDiff = Math.abs(startR - endR);
        int colDiff = Math.abs(startC - endC);
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
}