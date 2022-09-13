import java.util.Scanner;

public class TicTacToeRunner {
    public static void main(String[] args) {
        String level;
        String replay;
        Scanner scan = new Scanner(System.in);

        do {
            // Ask what level player wants to play?
            do {
                System.out.println("What level do you want to play? (easy/ hard)");
                level = scan.next().trim().toLowerCase();
                if (!(level.equals("easy") || level.equals("hard"))) {
                    System.out.println("Please type easy or hard");
                }
            } while (!(level.equals("easy") || level.equals("hard")));

            char[][] gameBoard = { { ' ', '|', ' ', '|', ' ' },
                    { '-', '+', '-', '+', '-' },
                    { ' ', '|', ' ', '|', ' ' },
                    { '-', '+', '-', '+', '-' },
                    { ' ', '|', ' ', '|', ' ' },
            };
            TicTacToe game = new TicTacToe(gameBoard);
            game.play(level);
            // Ask if player wants to replay
            System.out.println("Replay? (y to continue, else end the game)");
            replay = scan.next().toLowerCase().trim();
        } while (replay.equals("y"));
        System.out.println("Thank you for playing the game!");
    }
}
