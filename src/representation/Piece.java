package representation;

abstract class Piece {



    // General class information
    abstract String getName();
    abstract int    getValue();

    // Bitboard generating methods
    abstract Bitboard[][] generateQuietBitboards();
    abstract Bitboard[][] generateCaptureBitboards();

    // Bitboard helper methods
    static Bitboard[][] allocateBitboards() {
        return new Bitboard[2][64];
    }
    static void applyColorNeutral(Bitboard[][] boards) {
        boards[1] = boards[0].clone();
    }
}
