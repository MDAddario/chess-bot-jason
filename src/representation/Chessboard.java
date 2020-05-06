package representation;

import java.util.ArrayList;

class Chessboard {

    // Hold all the capture and quiet move bitmasks
    private static final Bitboard[][][][] captureMoves = Piece.getCaptureMoves();
    private static final Bitboard[][][][] quietMoves   = Piece.getQuietMoves();

    // Hold a bitmask for each piece type and for each color
    private Bitboard[] boards;

    // Keep track of en passant rights and castling rights
    private SpecialFlags flags;

    // Keep track of the turn number
    private int ply;

    // Protection methods
    private boolean isTypeLegal(int type)   { return 2 <= type && type < 8; }
    private boolean isColorLegal(int color) { return 0 <= color && color < 2; }

    // More helper functions
    private int getColor(char file, int rank) {

        for (int color = 0; color < 2; color++)
            if (this.boards[color].getBit(file, rank))
                return color;
        return Piece.NONE;
    }

    private int getType(char file, int rank) {

        for (int type = 2; type < 8; type++)
            if (this.boards[type].getBit(file, rank))
                return type;
        return Piece.NONE;
    }

    // Default constructor
    Chessboard() {

        // Allocate boards
        this.boards = new Bitboard[8];
        for (int i = 0; i < 8; i++)
            this.boards[i] = new Bitboard();

        // Load the defaults
        this.loadDefaults();
    }

    // Helper function for managing boards
    private void setBit(char file, int rank, int type, int color, boolean isActive) {

        // Validate boards input
        if (!isTypeLegal(type))
            throw new IllegalArgumentException("Piece type specified is illegal.");
        if (!isColorLegal(color))
            throw new IllegalArgumentException("Color specified is illegal.");

        // Set the boards
        this.boards[color].setBit(file, rank, isActive);
        this.boards[type] .setBit(file, rank, isActive);
    }

    // Generate all quiet moves
    private ArrayList<Move> generateQuietMoves() {

        // Create the return array
        ArrayList<Move> moves = new ArrayList<>();

        // Determine whose turn it is
        int curColor = (this.ply + Piece.BLACK) % 2;
        int oppColor = (this.ply + Piece.BLACK + 1) % 2;

        // Run through all pieces of current color
        for (Square from : this.boards[curColor]) {

            continue;



        }

        return moves;
    }

    // Load starting chess position
    private void loadDefaults() {

        // White frontend
        this.setBit('A', 2, Piece.PAWN, Piece.WHITE, true);
        this.setBit('B', 2, Piece.PAWN, Piece.WHITE, true);
        this.setBit('C', 2, Piece.PAWN, Piece.WHITE, true);
        this.setBit('D', 2, Piece.PAWN, Piece.WHITE, true);
        this.setBit('E', 2, Piece.PAWN, Piece.WHITE, true);
        this.setBit('F', 2, Piece.PAWN, Piece.WHITE, true);
        this.setBit('G', 2, Piece.PAWN, Piece.WHITE, true);
        this.setBit('H', 2, Piece.PAWN, Piece.WHITE, true);

        // White backend
        this.setBit('A', 1, Piece.ROOK,   Piece.WHITE, true);
        this.setBit('B', 1, Piece.KNIGHT, Piece.WHITE, true);
        this.setBit('C', 1, Piece.BISHOP, Piece.WHITE, true);
        this.setBit('D', 1, Piece.QUEEN,  Piece.WHITE, true);
        this.setBit('E', 1, Piece.KING,   Piece.WHITE, true);
        this.setBit('F', 1, Piece.BISHOP, Piece.WHITE, true);
        this.setBit('G', 1, Piece.KNIGHT, Piece.WHITE, true);
        this.setBit('H', 1, Piece.ROOK,   Piece.WHITE, true);

        // Black frontend
        this.setBit('A', 7, Piece.PAWN, Piece.BLACK, true);
        this.setBit('B', 7, Piece.PAWN, Piece.BLACK, true);
        this.setBit('C', 7, Piece.PAWN, Piece.BLACK, true);
        this.setBit('D', 7, Piece.PAWN, Piece.BLACK, true);
        this.setBit('E', 7, Piece.PAWN, Piece.BLACK, true);
        this.setBit('F', 7, Piece.PAWN, Piece.BLACK, true);
        this.setBit('G', 7, Piece.PAWN, Piece.BLACK, true);
        this.setBit('H', 7, Piece.PAWN, Piece.BLACK, true);

        // Black backend
        this.setBit('A', 8, Piece.ROOK,   Piece.BLACK, true);
        this.setBit('B', 8, Piece.KNIGHT, Piece.BLACK, true);
        this.setBit('C', 8, Piece.BISHOP, Piece.BLACK, true);
        this.setBit('D', 8, Piece.QUEEN,  Piece.BLACK, true);
        this.setBit('E', 8, Piece.KING,   Piece.BLACK, true);
        this.setBit('F', 8, Piece.BISHOP, Piece.BLACK, true);
        this.setBit('G', 8, Piece.KNIGHT, Piece.BLACK, true);
        this.setBit('H', 8, Piece.ROOK,   Piece.BLACK, true);

        // Load the starting flags
        this.flags = new SpecialFlags();
    }

    @Override
    public String toString() {

        // Create correspondence arrays
        char[] names = new char[8];
        names[Piece.KING]   = 'K';
        names[Piece.QUEEN]  = 'Q';
        names[Piece.BISHOP] = 'B';
        names[Piece.ROOK]   = 'R';
        names[Piece.KNIGHT] = 'N';
        names[Piece.PAWN]   = 'P';

        char[] colors = new char[2];
        colors[Piece.WHITE] = 'w';
        colors[Piece.BLACK] = 'b';

        StringBuilder output = new StringBuilder();
        output.append("-----".repeat(8));
        output.append("\n");
        for (int rank = 8; rank >= 1; rank--) {
            for (char file = 'A'; file <= 'H'; file++) {

                int color = this.getColor(file, rank);
                int type  = this.getType(file, rank);

                if (color != Piece.NONE && type != Piece.NONE) {
                    output.append(" ");
                    output.append(colors[color]);
                    output.append(names[type]);
                    output.append(colors[color]);
                    output.append(" ");
                } else
                    output.append("  .  ");
            }
            output.append("  |  ");
            output.append(rank);
            output.append("\n");
        }
        output.append("-----".repeat(8));
        output.append("\n");
        for (char file = 'A'; file <= 'H'; file++) {
            output.append("  ");
            output.append(file);
            output.append("  ");
        }
        return output.toString();
    }

    // Unit testing
    public static void main(String[] args) {

        // Create instance
        Chessboard cBoard = new Chessboard();
        System.out.println(cBoard);
    }
}
