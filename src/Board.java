package src;
public class Board {
    private char[][] gameBoard;
    private int size;

    public Board(int size) {
        gameBoard = new char[size][size];
        this.size = size;
        initializeBoard();
    }

    public void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                gameBoard[row][col] = ' ';
            }
        }
    }

    public int getSize() {
        return size;
    }

    public void printBoard() {
        System.out.println("-------------");
        for (int row = 0; row < size; row++) {
            System.out.print("| ");
            for (int col = 0; col < size; col++) {
                System.out.print(gameBoard[row][col] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public void clearCell(int row, int col) {
        gameBoard[row][col] = ' ';
    }

    public char getCellValue(int row, int col) {
        return gameBoard[row][col];
    }

    public boolean isEmptyCell(int row, int col) {
        return gameBoard[row][col] == ' ';
    }

    public void placeSymbol(int row, int col, char symbol) {
        gameBoard[row][col] = symbol;
    }

    public boolean checkWin(char symbol) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < size; i++) {
            if ((gameBoard[i][0] == symbol && gameBoard[i][1] == symbol
                    && gameBoard[i][2] == symbol)
                    || (gameBoard[0][i] == symbol && gameBoard[1][i] == symbol
                            && gameBoard[2][i] == symbol)) {
                return true;
            }
        }
        return (gameBoard[0][0] == symbol && gameBoard[1][1] == symbol && gameBoard[2][2] == symbol)
                || (gameBoard[0][2] == symbol && gameBoard[1][1] == symbol
                        && gameBoard[2][0] == symbol);
    }

    public boolean isFull() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (gameBoard[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameOver() {
        return checkWin('X') || checkWin('O') || isFull();
    }
}
