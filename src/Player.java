package src;
public abstract class Player {
    protected char symbol;

    protected Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public abstract boolean makeMove(Board board);
}
