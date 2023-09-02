package src;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameCoordinator {
    private static Scanner scanner;
    private static Human human;
    private static Computer computer;
    private static final int BOARD_SIZE = 3;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        human = new Human('X');
        computer = new Computer('O');

        System.out.println("Welcome to Tic-Tac-Toe!");
        setupComputerDifficulty();

        int choice = -1;

        do {
            try {
                System.out.println("Choose a game mode:");
                System.out.println("1. Console Mode");
                System.out.println("2. Interactive Mode");
                choice = scanner.nextInt();

                if (choice == 1) {
                    startConsoleGame();
                } else if (choice == 2) {
                    startInteractiveGame();
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid choice. Please choose 1 for Console Mode or 2 for Interactive Mode.");
                scanner.nextLine();
            }
        } while (!(choice == 1 || choice == 2));

        scanner.close();
    }

    private static void setupComputerDifficulty() {
        int level = -1;
        do {
            System.out.println("Choose game level:");
            System.out.println("1. Easy\n2. Hard");

            try {
                level = scanner.nextInt();

                if (!(level == 1 || level == 2)) {
                    throw new InputMismatchException();
                }

                if (level == 2) {
                    computer.setLevel(Computer.Level.HARD);
                }
            } catch (InputMismatchException e) {
                System.out.println(
                        "Invalid choice. Please choose 1 for Easy Level or 2 for Hard Level.");
            }
        } while (!(level == 1 || level == 2));
    }

    private static void startConsoleGame() {
        ConsoleTicTacToeGame consoleGame = new ConsoleTicTacToeGame(human, computer, BOARD_SIZE);
        consoleGame.play();
    }

    private static void startInteractiveGame() {
        InteractiveTicTacToeGame interactiveGame =
                new InteractiveTicTacToeGame(human, computer, BOARD_SIZE);
        interactiveGame.play();
    }
}
