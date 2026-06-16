package chess;

public class Queen extends Piece {
    public Queen(boolean isWhite, String imagePath) { super(isWhite, imagePath); }

    @Override
    public boolean isValidMove(int startR, int startC, int endR, int endC, Piece[][] grid) {
        // Queen is just a Rook and a Bishop combined!
        Rook tempRook = new Rook(isWhite, "");
        Bishop tempBishop = new Bishop(isWhite, "");
        return tempRook.isValidMove(startR, startC, endR, endC, grid) || 
               tempBishop.isValidMove(startR, startC, endR, endC, grid);
    }
}