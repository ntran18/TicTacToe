package src;
import java.util.Scanner;

public class ConsoleTicTacToeGame {
    private final Board board;
    private final Human human;
    private final Computer computer;
    private Player currentPlayer;
    private final Scanner scanner;

    public ConsoleTicTacToeGame(Human human, Computer computer, int size) {
        board = new Board(size);
        this.human = human;
        this.computer = computer;
        currentPlayer = human;
        scanner = new Scanner(System.in);
    }

    public void play() {
        String replay;
        do {
            while (!board.isGameOver()) {
                printGameStatus();
                performPlayerMove();
                currentPlayer = (currentPlayer == human) ? computer : human;
            }
            printGameStatus();
            printGameResult();

            replay = askForReplay();
        } while (replay.equals("y"));

        endGame();
    }

    private void printGameStatus() {
        board.printBoard();
        if (currentPlayer != human) {
            System.out.println("Computer is making a move");
        }
    }

    private void performPlayerMove() {
        boolean validMove = currentPlayer.makeMove(board);
        if (!validMove) {
            System.out.println("Invalid move. Try again.");
            performPlayerMove();
        }
    }

    private void printGameResult() {
        if (board.checkWin('X')) {
            System.out.println("Player X wins!");
        } else if (board.checkWin('O')) {
            System.out.println("Computer (Player O) wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    private String askForReplay() {
        System.out.println("Replay? (y to continue, else end the game)");
        return scanner.next().toLowerCase().trim();
    }

    private void endGame() {
        System.out.println("Thank you for playing the game!");
        scanner.close();
    }
}
