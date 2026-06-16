package chess;

public class Move {
    public int startRow, startCol, endRow, endCol;

    public Move(int sr, int sc, int er, int ec) {
        this.startRow = sr;
        this.startCol = sc;
        this.endRow = er;
        this.endCol = ec;
    }

    // Helper to check if two moves are the same
    public boolean equals(Move other) {
        return startRow == other.startRow && startCol == other.startCol &&
               endRow == other.endRow && endCol == other.endCol;
    }
}