package representation;

class Bitboard {

    // Track the 64 bits of a chess board
    private long bits;

    // A couple helpful constants
    private static final long ONE  = 0x0000000000000001L;
    private static final long ZERO = 0x0000000000000000L;
    private static final long FULL = 0xFFFFFFFFFFFFFFFFL;

    // Constructors
    Bitboard()          { this.bits = ZERO; }
    Bitboard(long bits) { this.bits = bits; }

    // Validate rank and file input
    static boolean isFileRankInBounds(char file, int rank) {
        return ('A' <= file && file <= 'H' && 1 <= rank && rank <= 8);
    }

    // Convert rank and file to bit index
    static int indexFromFileRank(char file, int rank) {

        // Make sure the inputs are good
        if (!isFileRankInBounds(file, rank))
            throw new IllegalArgumentException("File and rank input combination " + file + " " + rank + " is illegal.");

        return (file - 'A') + (rank - 1) * 8;
    }

    // Bit getter
    private boolean getBit(int index) {
        return (this.bits >> index & ONE) > 0;
    }
    boolean getBit(char file, int rank) {
        return this.getBit(indexFromFileRank(file, rank));
    }

    // Bit setter
    private void setBit(int index, boolean isActive) {
        if (isActive)
            this.bits |= ONE << index;
        else
            this.bits &= ~(ONE << index);
    }
    void setBit(char file, int rank, boolean isActive) {
        this.setBit(indexFromFileRank(file, rank), isActive);
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("---".repeat(8));
        output.append("\n");
        for (int rank = 8; rank >= 1; rank--) {
            for (char file = 'A'; file <= 'H'; file++) {
                if (this.getBit(file, rank))
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
        for (char file = 'A'; file <= 'H'; file++) {
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

    // Unit testing
    public static void main(String[] args) {

        // Create an empty bitboard
        Bitboard board = new Bitboard(ZERO);

        // Make sure each bit can be on while all others are off
        for (char focusFile = 'A'; focusFile <= 'H'; focusFile++)
            for (int focusRank = 1; focusRank <= 8; focusRank++) {

                // Set the focus bit on
                board.setBit(focusFile, focusRank, true);

                // Check focus bit on
                if (!board.getBit(focusFile, focusRank))
                    throw new UnitTestException("Issue at focus file " + focusFile +
                            ", focus rank " + focusRank);

                // Check every other bit is off
                for (char otherFile = 'A'; otherFile <= 'H'; otherFile++)
                    for (int otherRank = 1; otherRank <= 8; otherRank++) {

                        // Exclude focus square
                        if (focusFile == otherFile && focusRank == otherRank)
                            continue;

                        if (board.getBit(otherFile, otherRank))
                            throw new UnitTestException("Issue at focus file " + focusFile +
                                    ", focus rank " + focusRank +
                                    ", other file " + otherFile +
                                    ", other rank " + otherRank);
                    }

                // Set the focus bit off
                board.setBit(focusFile, focusRank, false);
            }

        // Create a full bitboard
        board = new Bitboard(FULL);

        // Make sure each bit can be off while all others are on
        for (char focusFile = 'A'; focusFile <= 'H'; focusFile++)
            for (int focusRank = 1; focusRank <= 8; focusRank++) {

                // Set the focus bit off
                board.setBit(focusFile, focusRank, false);

                // Check focus bit off
                if (board.getBit(focusFile, focusRank))
                    throw new UnitTestException("Issue at focus file " + focusFile +
                            ", focus rank " + focusRank);

                // Check every other bit is on
                for (char otherFile = 'A'; otherFile <= 'H'; otherFile++)
                    for (int otherRank = 1; otherRank <= 8; otherRank++) {

                        // Exclude focus square
                        if (focusFile == otherFile && focusRank == otherRank)
                            continue;

                        if (!board.getBit(otherFile, otherRank))
                            throw new UnitTestException("Issue at focus file " + focusFile +
                                    ", focus rank " + focusRank +
                                    ", other file " + otherFile +
                                    ", other rank " + otherRank);
                    }

                // Set the focus bit on
                board.setBit(focusFile, focusRank, true);
            }
    }
}
