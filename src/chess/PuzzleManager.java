package chess;

public class PuzzleManager {
    public static Puzzle getBackRankPuzzle() {
        String[][] layout = new String[8][8];
        
        // --- BLACK ---
        layout[0][7] = "b-king";   
        layout[0][4] = "b-Rook";   // The Rook waiting to capture
        layout[1][6] = "b-Pawn";   
        layout[1][7] = "b-Pawn";   
        layout[2][2] = "b-queen";

        // --- WHITE ---
        layout[0][3] = "w-rook";   // Placeholder piece on target square
        layout[7][3] = "w-rook";   // STARTING ROOK (d1)
        layout[6][5] = "w-queen";  // QUEEN (f2)
        layout[7][2] = "w-king";   

        Puzzle p = new Puzzle(layout, "Sacrifice the Rook!", true);
        
        // The 3-Step Interactive Solution:
        p.addSolutionMove(7, 3, 0, 3); // Step 1: Rook takes Rook
        p.addSolutionMove(6, 5, 0, 5); // Step 2: Queen sacrifices
        p.addSolutionMove(0, 3, 0, 5); // Step 3: Rook takes Queen
        
        return p;
    }
}