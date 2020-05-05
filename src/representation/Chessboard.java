package representation;

class Chessboard {

    // Distinguish the piece colors and piece types
    private static final int BLACK  = Piece.BLACK;
    private static final int WHITE  = Piece.WHITE;
    private static final int KING   = Piece.KING;
    private static final int KNIGHT = Piece.KNIGHT;
    private static final int ROOK   = Piece.ROOK;
    private static final int BISHOP = Piece.BISHOP;
    private static final int QUEEN  = Piece.QUEEN;
    private static final int PAWN   = Piece.PAWN;

    // Hold a bitmask for each piece type and for each color
    private Bitboard[] boards;

    // Hold all the capture and quiet move bitmasks
    private static final Bitboard[][][] captureMoves = Piece.getCaptureMoves();
    private static final Bitboard[][][] quietMoves   = Piece.getQuietMoves();

    // Default constructor
    Chessboard() {

        // Allocate boards
        this.boards = new Bitboard[8];
        for (int i = 0; i < 8; i++)
            this.boards[i] = new Bitboard();

        // Set default boards
        this.setDefaultBoards();
    }

    // Load starting chess position
    private void setDefaultBoards() {

        for (char file = 'A'; file <= 'H'; file++) {

            // White color board
            this.boards[WHITE].setBit(file, 1, true);
            this.boards[WHITE].setBit(file, 2, true);

            // Black color board
            this.boards[BLACK].setBit(file, 7, true);
            this.boards[BLACK].setBit(file, 8, true);

            // Pawns
            this.boards[PAWN].setBit(file, 2, true);
            this.boards[PAWN].setBit(file, 7, true);
        }

        // White backend
        this.boards[ROOK]  .setBit('A', 1, true);
        this.boards[KNIGHT].setBit('B', 1, true);
        this.boards[BISHOP].setBit('C', 1, true);
        this.boards[QUEEN] .setBit('D', 1, true);
        this.boards[KING]  .setBit('E', 1, true);
        this.boards[BISHOP].setBit('F', 1, true);
        this.boards[KNIGHT].setBit('G', 1, true);
        this.boards[ROOK]  .setBit('H', 1, true);

        // Black backend
        this.boards[ROOK]  .setBit('A', 8, true);
        this.boards[KNIGHT].setBit('B', 8, true);
        this.boards[BISHOP].setBit('C', 8, true);
        this.boards[QUEEN] .setBit('D', 8, true);
        this.boards[KING]  .setBit('E', 8, true);
        this.boards[BISHOP].setBit('F', 8, true);
        this.boards[KNIGHT].setBit('G', 8, true);
        this.boards[ROOK]  .setBit('H', 8, true);
    }

    @Override
    public String toString() {

        // Create correspondence array
        char[] names = new char[8];
        names[KING]   = 'K';
        names[QUEEN]  = 'Q';
        names[BISHOP] = 'B';
        names[ROOK]   = 'R';
        names[KNIGHT] = 'N';
        names[PAWN]   = 'P';

        StringBuilder output = new StringBuilder();
        output.append("-----".repeat(8));
        output.append("\n");
        for (int rank = 8; rank >= 1; rank--) {
            for (char file = 'A'; file <= 'H'; file++) {

                char color = '\0';

                if (this.boards[WHITE].getBit(file, rank))
                    color = 'w';
                else if (this.boards[BLACK].getBit(file, rank))
                    color = 'b';

                if (color != '\0') {
                    for (int type = 2; type < 8; type++)
                        if (this.boards[type].getBit(file, rank)) {
                            output.append(" ");
                            output.append(color);
                            output.append(names[type]);
                            output.append(color);
                            output.append(" ");
                        }
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
