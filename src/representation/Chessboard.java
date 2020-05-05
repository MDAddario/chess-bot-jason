package representation;

class Chessboard {

    // Distinguish the piece colors and piece types
    private static final int NONE   = -1;
    private static final int BLACK  = Piece.BLACK;
    private static final int WHITE  = Piece.WHITE;
    private static final int KING   = Piece.KING;
    private static final int KNIGHT = Piece.KNIGHT;
    private static final int ROOK   = Piece.ROOK;
    private static final int BISHOP = Piece.BISHOP;
    private static final int QUEEN  = Piece.QUEEN;
    private static final int PAWN   = Piece.PAWN;

    // Hold all the capture and quiet move bitmasks
    private static final Bitboard[][][] captureMoves = Piece.getCaptureMoves();
    private static final Bitboard[][][] quietMoves   = Piece.getQuietMoves();

    // Hold a bitmask for each piece type and for each color
    private Bitboard[] boards;

    // Keep track of en passant rights and castling rights
    private SpecialFlags flags;

    // Default constructor
    Chessboard() {

        // Allocate boards
        this.boards = new Bitboard[8];
        for (int i = 0; i < 8; i++)
            this.boards[i] = new Bitboard();

        // Load the defaults
        this.loadDefaults();
    }

    // Protection methods
    private boolean isTypeLegal(int type)   { return 2 <= type && type < 8; }
    private boolean isColorLegal(int color) { return 0 <= color && color < 2; }

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

    // More helper functions
    private int getColor(char file, int rank) {

        if (this.boards[WHITE].getBit(file, rank))
            return WHITE;
        else if (this.boards[BLACK].getBit(file, rank))
            return BLACK;
        return NONE;
    }

    private int getType(char file, int rank) {

        for (int type = 2; type < 8; type++)
            if (this.boards[type].getBit(file, rank))
                return type;
        return NONE;
    }

    // Load starting chess position
    private void loadDefaults() {

        // White frontend
        this.setBit('A', 2, PAWN, WHITE, true);
        this.setBit('B', 2, PAWN, WHITE, true);
        this.setBit('C', 2, PAWN, WHITE, true);
        this.setBit('D', 2, PAWN, WHITE, true);
        this.setBit('E', 2, PAWN, WHITE, true);
        this.setBit('F', 2, PAWN, WHITE, true);
        this.setBit('G', 2, PAWN, WHITE, true);
        this.setBit('H', 2, PAWN, WHITE, true);

        // White backend
        this.setBit('A', 1, ROOK,   WHITE, true);
        this.setBit('B', 1, KNIGHT, WHITE, true);
        this.setBit('C', 1, BISHOP, WHITE, true);
        this.setBit('D', 1, QUEEN,  WHITE, true);
        this.setBit('E', 1, KING,   WHITE, true);
        this.setBit('F', 1, BISHOP, WHITE, true);
        this.setBit('G', 1, KNIGHT, WHITE, true);
        this.setBit('H', 1, ROOK,   WHITE, true);

        // Black frontend
        this.setBit('A', 7, PAWN, BLACK, true);
        this.setBit('B', 7, PAWN, BLACK, true);
        this.setBit('C', 7, PAWN, BLACK, true);
        this.setBit('D', 7, PAWN, BLACK, true);
        this.setBit('E', 7, PAWN, BLACK, true);
        this.setBit('F', 7, PAWN, BLACK, true);
        this.setBit('G', 7, PAWN, BLACK, true);
        this.setBit('H', 7, PAWN, BLACK, true);

        // Black backend
        this.setBit('A', 8, ROOK,   BLACK, true);
        this.setBit('B', 8, KNIGHT, BLACK, true);
        this.setBit('C', 8, BISHOP, BLACK, true);
        this.setBit('D', 8, QUEEN,  BLACK, true);
        this.setBit('E', 8, KING,   BLACK, true);
        this.setBit('F', 8, BISHOP, BLACK, true);
        this.setBit('G', 8, KNIGHT, BLACK, true);
        this.setBit('H', 8, ROOK,   BLACK, true);

        // Load the starting flags
        this.flags = new SpecialFlags();
    }

    @Override
    public String toString() {

        // Create correspondence arrays
        char[] names = new char[8];
        names[KING]   = 'K';
        names[QUEEN]  = 'Q';
        names[BISHOP] = 'B';
        names[ROOK]   = 'R';
        names[KNIGHT] = 'N';
        names[PAWN]   = 'P';

        char[] colors = new char[2];
        colors[WHITE] = 'w';
        colors[BLACK] = 'b';

        StringBuilder output = new StringBuilder();
        output.append("-----".repeat(8));
        output.append("\n");
        for (int rank = 8; rank >= 1; rank--) {
            for (char file = 'A'; file <= 'H'; file++) {

                int color = this.getColor(file, rank);
                int type  = this.getType(file, rank);

                if (color != NONE && type != NONE) {
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
