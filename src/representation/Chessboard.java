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

    // Hold all the capture and quiet move bitmasks
    private static final Bitboard[][][] captureMoves = Piece.getCaptureMoves();
    private static final Bitboard[][][] quietMoves   = Piece.getQuietMoves();

    // Hold a bitmask for each piece type and for each color
    private Bitboard[] boards;
}
