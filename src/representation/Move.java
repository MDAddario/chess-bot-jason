package representation;

class Move {

    // Distinguish the piece colors and piece types
    private static final int NONE   = Piece.NONE;
    private static final int BLACK  = Piece.BLACK;
    private static final int WHITE  = Piece.WHITE;
    private static final int KING   = Piece.KING;
    private static final int KNIGHT = Piece.KNIGHT;
    private static final int ROOK   = Piece.ROOK;
    private static final int BISHOP = Piece.BISHOP;
    private static final int QUEEN  = Piece.QUEEN;
    private static final int PAWN   = Piece.PAWN;

    class Square {

        // Attributes
        private char file;
        private int  rank;
        private int  type;
        private int  color;

        // Getters
        char getFile()  { return this.file; }
        int  getRank()  { return this.rank; }
        int  getType()  { return this.type; }
        int  getColor() { return this.color; }

        Square(char file, int rank, int type, int color) {

            this.file  = file;
            this.rank  = rank;
            this.type  = type;
            this.color = color;
        }
    }

    // Attributes
    private Square from;     // Departing piece
    private Square to;       // Destination piece (captured piece, if any)
    private Square promo;    // Promoted piece
    private byte   moveCode; // Encode move status



}
