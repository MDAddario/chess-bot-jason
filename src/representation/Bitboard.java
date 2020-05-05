package representation;

class Bitboard {

    // Track the 64 bits of a chess board
    private long bits;

    // A couple helpful constants
    private static final long ONE  = 0x0000000000000001L;
    private static final long ZERO = 0x0000000000000000L;
    private static final long FULL = 0xFFFFFFFFFFFFFFFFL;

    // Used for unit testing
    public static void main(String[] args) {

        // Create a new empty bitboard
        Bitboard board = new Bitboard(ZERO);

        // Make sure each bit can be on while all others are off
        for (char focusRank = 'A'; focusRank <= 'H'; focusRank++)
            for (int focusFile = 1; focusFile <= 8; focusFile++) {

                // Set the focus bit on
                board.setBit(focusRank, focusFile, true);

                // Check focus bit on
                if (!board.getBit(focusRank, focusFile))
                    throw new UnitTestException("Issue at focus rank " + focusRank +
                                                ", focus file " + focusFile);

                // Check every other bit is off
                for (char otherRank = 'A'; otherRank <= 'H'; otherRank++)
                    for (int otherFile = 1; otherFile <= 8; otherFile++) {

                        // Exclude focus square
                        if (focusRank == otherRank && focusFile == otherFile)
                            continue;

                        if (board.getBit(otherRank, otherFile))
                            throw new UnitTestException("Issue at focus rank " + focusRank +
                                                        ", focus file " + focusFile +
                                                        ", other rank " + otherRank +
                                                        ", other file " + otherFile);
                    }

                // Set the focus bit off
                board.setBit(focusRank, focusFile, false);
            }

        // Create a new full bitboard
        board = new Bitboard(FULL);

        // Make sure each bit can be off while all others are on
        for (char focusRank = 'A'; focusRank <= 'H'; focusRank++)
            for (int focusFile = 1; focusFile <= 8; focusFile++) {

                // Set the focus bit off
                board.setBit(focusRank, focusFile, false);

                // Check focus bit off
                if (board.getBit(focusRank, focusFile))
                    throw new UnitTestException("Issue at focus rank " + focusRank +
                            ", focus file " + focusFile);

                // Check every other bit is on
                for (char otherRank = 'A'; otherRank <= 'H'; otherRank++)
                    for (int otherFile = 1; otherFile <= 8; otherFile++) {

                        // Exclude focus square
                        if (focusRank == otherRank && focusFile == otherFile)
                            continue;

                        if (!board.getBit(otherRank, otherFile))
                            throw new UnitTestException("Issue at focus rank " + focusRank +
                                    ", focus file " + focusFile +
                                    ", other rank " + otherRank +
                                    ", other file " + otherFile);
                    }

                // Set the focus bit on
                board.setBit(focusRank, focusFile, true);
            }
    }

    // Constructors
    Bitboard()          { this.bits = ZERO; }
    Bitboard(long bits) { this.bits = bits; }

    // Validate rank and file input
    static boolean isRankFileInBounds(char rank, int file) {
        return ('A' <= rank && rank <= 'H' && 1 <= file && file <= 8);
    }

    // Convert rank and file to bit index
    static int indexFromRankFile(char rank, int file) {

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
    void setBit(char rank, int file, boolean isActive) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Bitboard)
            return this.bits == ((Bitboard)obj).bits;
        return false;
    }
}
