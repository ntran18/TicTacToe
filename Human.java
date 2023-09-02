import java.util.InputMismatchException;
import java.util.Scanner;

public class Human extends Player {
    private Scanner scanner;

    public Human(char symbol) {
        super(symbol);
        scanner = new Scanner(System.in);
    }

    @Override
    public boolean makeMove(Board board) {
        int row = -1;
        int col = -1;
        boolean validMove = false;

        while (!validMove) {
            try {
                System.out.println("Player " + symbol + ", enter your move (row and column): ");
                row = scanner.nextInt();
                col = scanner.nextInt();

                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board.isEmptyCell(row, col)) {
                    validMove = true;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid row and column as integers.");
                scanner.nextLine();
            }
        }

        board.placeSymbol(row, col, symbol);
        return true;
    }
}
