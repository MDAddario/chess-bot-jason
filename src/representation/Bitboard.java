package representation;

class Bitboard {

    // Track the 64 bits of a chess board
    private long bits;

    // A couple helpful constants
    private static final long ONE  = 0x0000000000000001L;
    private static final long ZERO = 0x0000000000000000L;
    private static final long FULL = 0xFFFFFFFFFFFFFFFFL;

    // The main attraction
    public static void main(String[] args) {

        // Create a new bitboard
        Bitboard board = new Bitboard();
        System.out.println(board);

        // Set some positions up
        board.setBit('A', 1, true);
        board.setBit('H', 8, true);
        board.setBit('E', 4, true);
        board.setBit('E', 4, false);
        board.setBit('F', 6, false);
        board.setBit('F', 6, true);

        // Print the board again
        System.out.println(board);
    }

    // Constructors
    Bitboard()          { this.bits = ZERO; }
    Bitboard(long bits) { this.bits = bits; }

    // Convert rank and file to bit index
    private static int indexFromRankFile(char rank, int file) {

        // Make sure the inputs are good
        if (!('A' <= rank && rank <= 'H'))
            throw new IllegalArgumentException("Rank input must be between 'A' and 'H'");
        if (!(1 <= file && file <= 8))
            throw new IllegalArgumentException("File input must be between 1 and 8");

        return (rank - 'A') * 8 + (file - 1);
    }

    // Bit getter
    private boolean getBit(int index) {
        return (this.bits >> index & ONE) > 0;
    }
    private boolean getBit(char rank, int file) {
        return this.getBit(indexFromRankFile(rank, file));
    }

    // Bit setter
    private void setBit(int index, boolean isActive) {
        if (isActive)
            this.bits |= ONE << index;
        else
            this.bits &= ~(ONE << index);
    }
    private void setBit(char rank, int file, boolean isActive) {
        this.setBit(indexFromRankFile(rank, file), isActive);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("---".repeat(8));
        output.append("\n");
        for (char rank = 'H'; rank >= 'A'; rank--) {
            for (int file = 1; file <= 8; file++) {
                if (this.getBit(rank, file))
                    output.append(" X ");
                else
                    output.append(" . ");
            }
            output.append(" | ");
            output.append(rank);
            output.append("\n");
        }
        output.append("---".repeat(8));
        output.append("\n");
        for (int file = 1; file <= 8; file++) {
            output.append(" ");
            output.append(file);
            output.append(" ");
        }
        return output.toString();
    }
}
