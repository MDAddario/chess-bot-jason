package representation;

class Move {

    // https://www.chessprogramming.org/Encoding_Moves

    // Attributes
    private Square       from;     // Departing piece
    private Square       to;       // Destination piece (captured piece, if any)
    private byte         moveCode; // Encode move status
    private SpecialFlags flags;    // Flags before move were done

    // Read the move code
    boolean readMoveCode(int index) {
        return (this.moveCode >> index & 1) > 0;
    }

    boolean isPromotion() {
        return this.readMoveCode(3);
    }

    boolean isCapture() {
        return this.readMoveCode(2);
    }

    boolean isCastle() {
        return this.moveCode == 2 || this.moveCode == 3;
    }

    int getPromotedPiece() {
        return this.moveCode % 4 + Piece.KNIGHT;
    }

    // Constructors
}
