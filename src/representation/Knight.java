package representation;

class Knight {

    private static final String NAME  = "Knight";
    private static final int    VALUE = 3;

    // General class information
    static String getName()  { return NAME; }
    static int    getValue() { return VALUE; }

    // Bitboard generating methods
    static Bitboard[][] generateQuietBitboards() {

        // Allocate the board arrays
        Bitboard[][] boards = Piece.allocateBitboards();

        // Construct all knight moves
        int[] dFile = {+1, -1, -2, -2, -1, +1, +2, +2};
        int[] dRank = {+2, +2, +1, -1, -2, -2, -1, +1};

        // Span all starting squares
        for (char file = 'A'; file <= 'H'; file++)
            for (int rank = 1; rank <= 8; rank++) {

                // Create new bitboard
                Bitboard board = new Bitboard();

                // Span all knight moves
                for (int i = 0; i < 8; i++)
                    if (Bitboard.isFileRankInBounds((char)(file + dFile[i]), rank + dRank[i]))
                        board.setBit((char)(file + dFile[i]), rank + dRank[i], true);

                // Store the board in the right spot
                boards[0][Bitboard.indexFromFileRank(file, rank)] = board;
            }

        // Copy the color-neutral board
        Piece.applyColorNeutral(boards);
        return boards;
    }

    // Identical quiets and captures
    static Bitboard[][] generateCaptureBitboards() {
        return generateQuietBitboards();
    }

    // Unit testing
    public static void main(String[] args) {

        // Create night moves
        Bitboard[][] knightMoves = generateCaptureBitboards();

        // Let's focus on E4
        Bitboard knightBoard = knightMoves[0][Bitboard.indexFromFileRank('E', 4)];
        Bitboard board = new Bitboard();
        board.setBit('C', 3, true);
        board.setBit('C', 5, true);
        board.setBit('D', 6, true);
        board.setBit('D', 2, true);
        board.setBit('F', 6, true);
        board.setBit('F', 2, true);
        board.setBit('G', 3, true);
        board.setBit('G', 5, true);

        if (!board.equals(knightBoard))
            throw new UnitTestException("Knight move generation is bad.");

        System.out.println(knightBoard);
        System.out.println(board);

        // Let's focus on A1
        knightBoard = knightMoves[0][Bitboard.indexFromFileRank('B', 1)];
        board = new Bitboard();
        board.setBit('A', 3, true);
        board.setBit('C', 3, true);
        board.setBit('D', 2, true);

        if (!board.equals(knightBoard))
            throw new UnitTestException("Knight move generation is bad.");

        System.out.println(knightBoard);
        System.out.println(board);
    }
}
