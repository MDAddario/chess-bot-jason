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
}
