package representation;

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

    // Constructor
    Square(char file, int rank, int type, int color) {

        this.file  = file;
        this.rank  = rank;
        this.type  = type;
        this.color = color;
    }

    // Simpler constructor
    Square(char file, int rank) {

        this(file, rank, Piece.NONE, Piece.NONE);
    }
}