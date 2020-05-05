package representation;

class Piece {

    // Distinguish the colors
    private static final int BLACK = 0;
    private static final int WHITE = 1;

    // Bitboard helper methods
    private static Bitboard[][] allocateBitboards() {
        return new Bitboard[2][64];
    }

    private static void applyColorNeutral(Bitboard[][] boards) {
        boards[BLACK] = boards[WHITE].clone();
    }

    private static Bitboard[][] combinePawnBitboards(Bitboard[][] whiteBoard, Bitboard[][] blackBoard) {
        whiteBoard[BLACK] = blackBoard[BLACK];
        return whiteBoard;
    }

    // Bitboard generating methods
    private static Bitboard[][] generateJumpingBoards(int[] dFile, int[] dRank) {

        // Allocate the board arrays
        Bitboard[][] boards = Piece.allocateBitboards();

        // Span all starting squares
        for (char file = 'A'; file <= 'H'; file++)
            for (int rank = 1; rank <= 8; rank++) {

                // Create new bitboard
                Bitboard board = new Bitboard();

                // Span all jump moves
                for (int index = 0; index < dFile.length; index++)
                    if (Bitboard.isFileRankInBounds((char)(file + dFile[index]), rank + dRank[index]))
                        board.setBit((char)(file + dFile[index]), rank + dRank[index], true);

                // Store the board in the right spot
                boards[WHITE][Bitboard.indexFromFileRank(file, rank)] = board;
            }

        // Copy the color-neutral board
        Piece.applyColorNeutral(boards);
        return boards;
    }

    private static Bitboard[][] generateSlidingBoards(int[] dFile, int[] dRank) {

        // Allocate the board arrays
        Bitboard[][] boards = Piece.allocateBitboards();

        // Span all starting squares
        for (char file = 'A'; file <= 'H'; file++)
            for (int rank = 1; rank <= 8; rank++) {

                // Create new bitboard
                Bitboard board = new Bitboard();

                // Span all slide moves
                for (int index = 0; index < dFile.length; index++)

                    // Span all depths of sliding
                    for (int depth = 1; true; depth++) {

                        if (!Bitboard.isFileRankInBounds((char)(file + dFile[index] * depth),
                                                                rank + dRank[index] * depth))
                            break;

                        board.setBit((char)(file + dFile[index] * depth), rank + dRank[index] * depth, true);
                    }
                // Store the board in the right spot
                boards[WHITE][Bitboard.indexFromFileRank(file, rank)] = board;
            }

        // Copy the color-neutral board
        Piece.applyColorNeutral(boards);
        return boards;
    }

    // Pawn generating methods
    private static Bitboard[][] generateWhitePawnCaptures() {

        // Allocate the board arrays
        Bitboard[][] boards = Piece.allocateBitboards();

        // Span all starting squares
        for (char file = 'A'; file <= 'H'; file++)
            for (int rank = 2; rank <= 7; rank++) {

                // Create new bitboard
                Bitboard board = new Bitboard();

                // Check capture left
                if (Bitboard.isFileRankInBounds((char)(file - 1), rank + 1))
                    board.setBit((char)(file - 1), rank + 1, true);

                // Check capture right
                if (Bitboard.isFileRankInBounds((char)(file + 1), rank + 1))
                    board.setBit((char)(file + 1), rank + 1, true);

                // Store the board in the right spot
                boards[WHITE][Bitboard.indexFromFileRank(file, rank)] = board;
            }
        return boards;
    }

    private static Bitboard[][] generateBlackPawnCaptures() {

        // Allocate the board arrays
        Bitboard[][] boards = Piece.allocateBitboards();

        // Span all starting squares
        for (char file = 'A'; file <= 'H'; file++)
            for (int rank = 2; rank <= 7; rank++) {

                // Create new bitboard
                Bitboard board = new Bitboard();

                // Check capture left
                if (Bitboard.isFileRankInBounds((char)(file - 1), rank - 1))
                    board.setBit((char)(file - 1), rank - 1, true);

                // Check capture right
                if (Bitboard.isFileRankInBounds((char)(file + 1), rank - 1))
                    board.setBit((char)(file + 1), rank - 1, true);

                // Store the board in the right spot
                boards[BLACK][Bitboard.indexFromFileRank(file, rank)] = board;
            }
        return boards;
    }

    private static Bitboard[][] generateWhitePawnQuiets() {

        // Allocate the board arrays
        Bitboard[][] boards = Piece.allocateBitboards();

        // Span all starting squares
        for (char file = 'A'; file <= 'H'; file++) {

            // Starting rank
            int rank = 2;

            // Create new bitboard
            Bitboard board = new Bitboard();

            // The elusive double thrust
            board.setBit(file, rank + 1, true);
            board.setBit(file, rank + 2, true);

            // Store the board in the right spot
            boards[WHITE][Bitboard.indexFromFileRank(file, rank)] = board;

            // Non-starting rank
            for (rank = 3; rank <= 7; rank++) {

                // Create new bitboard
                board = new Bitboard();

                // Add move forward
                board.setBit(file, rank + 1, true);

                // Store the board in the right spot
                boards[WHITE][Bitboard.indexFromFileRank(file, rank)] = board;
            }
        }
        return boards;
    }

    private static Bitboard[][] generateBlackPawnQuiets() {

        // Allocate the board arrays
        Bitboard[][] boards = Piece.allocateBitboards();

        // Span all starting squares
        for (char file = 'A'; file <= 'H'; file++) {

            // Starting rank
            int rank = 7;

            // Create new bitboard
            Bitboard board = new Bitboard();

            // The elusive double thrust
            board.setBit(file, rank - 1, true);
            board.setBit(file, rank - 2, true);

            // Store the board in the right spot
            boards[BLACK][Bitboard.indexFromFileRank(file, rank)] = board;

            // Non-starting rank
            for (rank = 2; rank <= 6; rank++) {

                // Create new bitboard
                board = new Bitboard();

                // Add move forward
                board.setBit(file, rank - 1, true);

                // Store the board in the right spot
                boards[BLACK][Bitboard.indexFromFileRank(file, rank)] = board;
            }
        }
        return boards;
    }

    // Pawn specific generations
    static Bitboard[][] generatePawnCaptures() {

        // Extract each color
        Bitboard[][] whiteMoves = generateWhitePawnCaptures();
        Bitboard[][] blackMoves = generateBlackPawnCaptures();

        return combinePawnBitboards(whiteMoves, blackMoves);
    }

    static Bitboard[][] generatePawnQuiets() {

        // Extract each color
        Bitboard[][] whiteMoves = generateWhitePawnQuiets();
        Bitboard[][] blackMoves = generateBlackPawnQuiets();

        return combinePawnBitboards(whiteMoves, blackMoves);
    }

    // Piece specific generations
    static Bitboard[][] generateKnightMoves() {

        // Construct all knight moves
        int[] dFile = {+1, -1, -2, -2, -1, +1, +2, +2};
        int[] dRank = {+2, +2, +1, -1, -2, -2, -1, +1};

        return generateJumpingBoards(dFile, dRank);
    }

    static Bitboard[][] generateKingMoves() {

        // Construct all king moves
        int[] dFile = {+1, +1, +0, -1, -1, -1, +0, +1};
        int[] dRank = {+0, +1, +1, +1, +0, -1, -1, -1};

        return generateJumpingBoards(dFile, dRank);
    }

    static Bitboard[][] generateRookMoves() {

        // Construct all rook moves
        int[] dFile = {+1, +0, -1, +0};
        int[] dRank = {+0, +1, +0, -1};

        return generateSlidingBoards(dFile, dRank);
    }

    static Bitboard[][] generateBishopMoves() {

        // Construct all bishop moves
        int[] dFile = {+1, +1, -1, -1};
        int[] dRank = {+1, -1, +1, -1};

        return generateSlidingBoards(dFile, dRank);
    }

    static Bitboard[][] generateQueenMoves() {

        // Construct all queen moves
        int[] dFile = {+1, +0, -1, +0, +1, +1, -1, -1};
        int[] dRank = {+0, +1, +0, -1, +1, -1, +1, -1};

        return generateSlidingBoards(dFile, dRank);
    }

    // Unit testing
    public static void main(String[] args) {

        // Create knight moves
        Bitboard[][] knightMoves = generateKnightMoves();

        // Let's focus on E4
        Bitboard knightBoard = knightMoves[WHITE][Bitboard.indexFromFileRank('E', 4)];
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

        // Let's focus on B1
        knightBoard = knightMoves[BLACK][Bitboard.indexFromFileRank('B', 1)];
        board = new Bitboard();
        board.setBit('A', 3, true);
        board.setBit('C', 3, true);
        board.setBit('D', 2, true);

        if (!board.equals(knightBoard))
            throw new UnitTestException("Knight move generation is bad.");

        // Create king moves
        Bitboard[][] kingMoves = generateKingMoves();

        // Let's focus on E4
        Bitboard kingBoard = kingMoves[WHITE][Bitboard.indexFromFileRank('E', 4)];
        board = new Bitboard();
        board.setBit('D', 3, true);
        board.setBit('D', 4, true);
        board.setBit('D', 5, true);
        board.setBit('E', 3, true);
        board.setBit('E', 5, true);
        board.setBit('F', 3, true);
        board.setBit('F', 4, true);
        board.setBit('F', 5, true);

        if (!board.equals(kingBoard))
            throw new UnitTestException("King move generation is bad.");

        // Let's focus on H8
        kingBoard = kingMoves[BLACK][Bitboard.indexFromFileRank('H', 8)];
        board = new Bitboard();
        board.setBit('G', 8, true);
        board.setBit('G', 7, true);
        board.setBit('H', 7, true);

        if (!board.equals(kingBoard))
            throw new UnitTestException("King move generation is bad.");

        // Create rook moves
        Bitboard[][] rookMoves = generateRookMoves();

        // Let's focus on E4
        Bitboard rookBoard = rookMoves[WHITE][Bitboard.indexFromFileRank('E', 4)];
        board = new Bitboard();
        board.setBit('E', 1, true);
        board.setBit('E', 2, true);
        board.setBit('E', 3, true);
        board.setBit('E', 5, true);
        board.setBit('E', 6, true);
        board.setBit('E', 7, true);
        board.setBit('E', 8, true);
        board.setBit('A', 4, true);
        board.setBit('B', 4, true);
        board.setBit('C', 4, true);
        board.setBit('D', 4, true);
        board.setBit('F', 4, true);
        board.setBit('G', 4, true);
        board.setBit('H', 4, true);

        if (!board.equals(rookBoard))
            throw new UnitTestException("Rook move generation is bad.");

        // Create bishop moves
        Bitboard[][] bishopMoves = generateBishopMoves();

        // Let's focus on E4
        Bitboard bishopBoard = bishopMoves[BLACK][Bitboard.indexFromFileRank('E', 4)];
        board = new Bitboard();
        board.setBit('D', 3, true);
        board.setBit('C', 2, true);
        board.setBit('B', 1, true);
        board.setBit('D', 5, true);
        board.setBit('C', 6, true);
        board.setBit('B', 7, true);
        board.setBit('A', 8, true);
        board.setBit('F', 3, true);
        board.setBit('G', 2, true);
        board.setBit('H', 1, true);
        board.setBit('F', 5, true);
        board.setBit('G', 6, true);
        board.setBit('H', 7, true);

        if (!board.equals(bishopBoard))
            throw new UnitTestException("Bishop move generation is bad.");

        // Create queen moves
        Bitboard[][] queenMoves = generateQueenMoves();

        // Let's focus on E4
        Bitboard queenBoard = queenMoves[WHITE][Bitboard.indexFromFileRank('E', 4)];
        board = new Bitboard();
        board.setBit('E', 1, true);
        board.setBit('E', 2, true);
        board.setBit('E', 3, true);
        board.setBit('E', 5, true);
        board.setBit('E', 6, true);
        board.setBit('E', 7, true);
        board.setBit('E', 8, true);
        board.setBit('A', 4, true);
        board.setBit('B', 4, true);
        board.setBit('C', 4, true);
        board.setBit('D', 4, true);
        board.setBit('F', 4, true);
        board.setBit('G', 4, true);
        board.setBit('H', 4, true);
        board.setBit('D', 3, true);
        board.setBit('C', 2, true);
        board.setBit('B', 1, true);
        board.setBit('D', 5, true);
        board.setBit('C', 6, true);
        board.setBit('B', 7, true);
        board.setBit('A', 8, true);
        board.setBit('F', 3, true);
        board.setBit('G', 2, true);
        board.setBit('H', 1, true);
        board.setBit('F', 5, true);
        board.setBit('G', 6, true);
        board.setBit('H', 7, true);

        if (!board.equals(queenBoard))
            throw new UnitTestException("Queen move generation is bad.");

        // Create pawn captures
        Bitboard[][] pawnCaptures = generatePawnCaptures();

        // Let's focus on white E4
        Bitboard pawnBoard = pawnCaptures[WHITE][Bitboard.indexFromFileRank('E', 4)];
        board = new Bitboard();
        board.setBit('D', 5, true);
        board.setBit('F', 5, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("White pawn capture move generation is bad.");

        // Let's focus on white H7
        pawnBoard = pawnCaptures[WHITE][Bitboard.indexFromFileRank('H', 7)];
        board = new Bitboard();
        board.setBit('G', 8, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("White pawn capture move generation is bad.");

        // Let's focus on black H7
        pawnBoard = pawnCaptures[BLACK][Bitboard.indexFromFileRank('H', 7)];
        board = new Bitboard();
        board.setBit('G', 6, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("Black pawn capture move generation is bad.");

        // Let's focus on black C2
        pawnBoard = pawnCaptures[BLACK][Bitboard.indexFromFileRank('C', 2)];
        board = new Bitboard();
        board.setBit('B', 1, true);
        board.setBit('D', 1, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("Black pawn capture move generation is bad.");

        // Create pawn quiets
        Bitboard[][] pawnQuiets = generatePawnQuiets();

        // Let's focus on white E4
        pawnBoard = pawnQuiets[WHITE][Bitboard.indexFromFileRank('E', 4)];
        board = new Bitboard();
        board.setBit('E', 5, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("White pawn quiet move generation is bad.");

        // Let's focus on white H2
        pawnBoard = pawnQuiets[WHITE][Bitboard.indexFromFileRank('H', 2)];
        board = new Bitboard();
        board.setBit('H', 3, true);
        board.setBit('H', 4, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("White pawn quiet move generation is bad.");

        // Let's focus on black B3
        pawnBoard = pawnQuiets[BLACK][Bitboard.indexFromFileRank('B', 3)];
        board = new Bitboard();
        board.setBit('B', 2, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("Black pawn quiet move generation is bad.");

        // Let's focus on black D7
        pawnBoard = pawnQuiets[BLACK][Bitboard.indexFromFileRank('D', 7)];
        board = new Bitboard();
        board.setBit('D', 6, true);
        board.setBit('D', 5, true);

        if (!board.equals(pawnBoard))
            throw new UnitTestException("Black pawn quiet move generation is bad.");
    }
}
