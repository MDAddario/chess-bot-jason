package representation;

class SpecialFlags {

    private static final int  ONE  = 0x01;
    private static final byte ZERO = 0x00;

    // Attributes
    private byte enPassantData;
    private boolean whiteShort;
    private boolean whiteLong;
    private boolean blackShort;
    private boolean blackLong;

    // Default constructor
    SpecialFlags() {

        this.lowerEnPassant();
        this.whiteShort = true;
        this.whiteLong  = true;
        this.blackShort = true;
        this.blackLong  = true;
    }

    // Cloning constructor
    SpecialFlags(SpecialFlags flags) {

        this.enPassantData = flags.enPassantData;
        this.whiteShort    = flags.whiteShort;
        this.whiteLong     = flags.whiteLong;
        this.blackShort    = flags.blackShort;
        this.blackLong     = flags.blackLong;
    }

    // Getters
    boolean canEnPassant(char file) {
        return (this.enPassantData >> (file - 'A') & ONE) > 0;
    }

    boolean canWhiteShort() { return this.whiteShort; }
    boolean canWhiteLong()  { return this.whiteLong; }
    boolean canBlackShort() { return this.blackShort; }
    boolean canBlackLong()  { return this.blackLong; }

    // Setters
    void raiseEnPassant(char file) {
        this.enPassantData |= ONE << (file - 'A');
    }

    void lowerEnPassant()  { this.enPassantData = ZERO; }
    void lowerWhiteShort() { this.whiteShort = false; }
    void lowerWhiteLong()  { this.whiteLong  = false; }
    void lowerBlackShort() { this.blackShort = false; }
    void lowerBlackLong()  { this.blackLong  = false; }

    // Compound setters
    void lowerWhiteCastles() { this.lowerWhiteShort(); this.lowerWhiteLong(); }
    void lowerBlackCastles() { this.lowerBlackShort(); this.lowerBlackLong(); }

    // Unit testing
    public static void main(String[] args) {

        // Create new object
        SpecialFlags flags = new SpecialFlags();

        // Ensure no enpassant
        for (char file = 'A'; file <= 'H'; file++)
            if (flags.canEnPassant(file))
                throw new UnitTestException("Constructor is wrong.");

        // Ensure all castles
        if (!flags.canWhiteShort())
            throw new UnitTestException("Constructor is wrong.");
        if (!flags.canWhiteLong())
            throw new UnitTestException("Constructor is wrong.");
        if (!flags.canBlackShort())
            throw new UnitTestException("Constructor is wrong.");
        if (!flags.canBlackLong())
            throw new UnitTestException("Constructor is wrong.");

        // Check independence of en passant flags
        for (char focusFile = 'A'; focusFile <= 'H'; focusFile++) {

            // Raise flag
            flags.raiseEnPassant(focusFile);

            // Check flag raised
            if (!flags.canEnPassant(focusFile))
                throw new UnitTestException("EP is wrong.");

            // Check lowered for all other flags
            for (char otherFile = 'A'; otherFile <= 'H'; otherFile++) {

                // Ignore focus flag
                if (focusFile == otherFile)
                    continue;

                // Check flags lowered
                if (flags.canEnPassant(otherFile))
                    throw new UnitTestException("EP is wrong.");
            }

            // Lower the flags
            flags.lowerEnPassant();
        }

        // Check independence of castling rights
        flags.lowerWhiteShort();
        if (!flags.canWhiteLong() || !flags.canBlackShort() || !flags.canBlackLong())
            throw new UnitTestException("Castling is wrong.");
        flags.lowerWhiteLong();
        if (!flags.canBlackShort() || !flags.canBlackLong())
            throw new UnitTestException("Castling is wrong.");
        flags.lowerBlackShort();
        if (!flags.canBlackLong())
            throw new UnitTestException("Castling is wrong.");
        flags.lowerBlackLong();
        if (flags.canWhiteShort() || flags.canWhiteLong() || flags.canBlackShort() || flags.canBlackLong())
            throw new UnitTestException("Castling is wrong.");
    }
}
