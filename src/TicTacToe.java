import java.util.*;

public class TicTacToe {
    private char[][] board;
    private static final char HUMAN = 'X';
    private static final char AI = 'O';
    private static ArrayList<Integer> humanPositions = new ArrayList<Integer>();
    private static ArrayList<Integer> aiPositions = new ArrayList<Integer>();
    private final static List<List> WIN_CONDITIONS = new ArrayList<List>();

    public TicTacToe(char[][] board) {
        this.board = board;

        // Setting up win conditions
        var topRow = List.of(1, 2, 3);
        var midRow = List.of(4, 5, 6);
        var botRow = List.of(7, 8, 9);
        var leftCol = List.of(1, 4, 7);
        var midCol = List.of(2, 5, 8);
        var rightCol = List.of(3, 6, 9);
        var cross1 = List.of(1, 5, 9);
        var cross2 = List.of(3, 5, 7);

        WIN_CONDITIONS.add(topRow);
        WIN_CONDITIONS.add(midRow);
        WIN_CONDITIONS.add(botRow);
        WIN_CONDITIONS.add(leftCol);
        WIN_CONDITIONS.add(midCol);
        WIN_CONDITIONS.add(rightCol);
        WIN_CONDITIONS.add(cross1);
        WIN_CONDITIONS.add(cross2);

    }

    public char[][] board() {
        return board;
    }

    public void play(String level) {
        printBoard();
        ArrayList<Character> playerList = new ArrayList<Character>();
        playerList.add(HUMAN);
        playerList.add(AI);

        while (true) {
            var stop = false;
            for (int i = 0; i < playerList.size(); i++) {
                playTurn(level, playerList.get(i));
                printBoard();
                char result = checkWinner();
                if (result != ' ') {
                    if (result == HUMAN) {
                        System.out.println("Congratulations, human won!");
                    } else if (result == AI) {
                        System.out.println("Unfortunately, AI beats you");
                    } else if (result == 'T') {
                        System.out.println("You two are both strong players");
                    }
                    stop = true;
                    break;
                }
            }
            if (stop)
                break;
        }
    }

    public void playTurn(String level, char player) {
        var pos = 0;
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        if (player == HUMAN) {
            System.out.println("Enter your placement (1-9):");
            pos = scan.nextInt();
            while (humanPositions.contains(pos) || aiPositions.contains(pos)) {
                System.out.println("Position taken! Enter a correct Position");
                pos = scan.nextInt();
            }
        } else {
            if (level.equals("easy")) {
                pos = rand.nextInt(9) + 1;
                while (humanPositions.contains(pos) || aiPositions.contains(pos)) {
                    pos = rand.nextInt(9) + 1;
                }
            } else {
                pos = bestMove();
            }
        }
        placePiece(pos, player, "play");
    }

    // Find the best position to move for AI using Minimax Algorithm
    public int bestMove() {
        var bestScore = -1000;
        var bestPos = 0;
        for (int pos = 1; pos <= 9; pos++) {
            if (!(humanPositions.contains(pos)) && !(aiPositions.contains(pos))) {
                placePiece(pos, AI, "check");
                int score = minimax(board, 0, false);
                if (score > bestScore) {
                    bestScore = score;
                    bestPos = pos;
                }
                aiPositions.remove(Integer.valueOf(pos));
            }
        }
        return bestPos;
    }

    /*
     * Implementing minimax algorithm
     * First, check the result. If AI won, return 1. If human won, return -1. if
     * Tie, return 0.
     * If isMaximizing, find the best score of each move, try to get Maximum score,
     * which is 1
     * If isMaximizing is false, find the lowest score of each move.
     */
    public int minimax(char[][] board, int depth, boolean isMaximizing) {
        char result = checkWinner();
        if (result == AI)
            return 1;
        else if (result == HUMAN)
            return -1;
        else if (result == 'T')
            return 0;

        if (isMaximizing) {
            int bestScore = -1000;
            for (int pos = 1; pos <= 9; pos++) {
                if (!(humanPositions.contains(pos)) && !(aiPositions.contains(pos))) {
                    placePiece(pos, AI, "check");
                    int score = minimax(board, 0, false);
                    bestScore = Math.max(score, bestScore);
                    aiPositions.remove(Integer.valueOf(pos));
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int pos = 1; pos <= 9; pos++) {
                if (!(humanPositions.contains(pos)) && !(aiPositions.contains(pos))) {
                    placePiece(pos, HUMAN, "check");
                    int score = minimax(board, 0, true);
                    bestScore = Math.min(score, bestScore);
                    humanPositions.remove(Integer.valueOf(pos));
                }
            }
            return bestScore;
        }
    }

    /*
     * Add position to either humanPositions list or aiPositions list
     * if action is play, then add the piece to the board
     */
    private void placePiece(int pos, char player, String action) {

        if (player == HUMAN)
            humanPositions.add(pos);
        else
            aiPositions.add(pos);

        if (action.equals("play")) {
            switch (pos) {
                case 1:
                    board[0][0] = player;
                    break;
                case 2:
                    board[0][2] = player;
                    break;
                case 3:
                    board[0][4] = player;
                    break;
                case 4:
                    board[2][0] = player;
                    break;
                case 5:
                    board[2][2] = player;
                    break;
                case 6:
                    board[2][4] = player;
                    break;
                case 7:
                    board[4][0] = player;
                    break;
                case 8:
                    board[4][2] = player;
                    break;
                case 9:
                    board[4][4] = player;
                    break;
                default:
                    break;

            }
        }

    }

    private static char checkWinner() {
        char result = ' ';
        for (List l : WIN_CONDITIONS) {
            if (humanPositions.containsAll(l)) {
                return HUMAN;
            } else if (aiPositions.containsAll(l)) {
                return AI;
            }
        }
        if (result == ' ' && humanPositions.size() + aiPositions.size() == 9) {
            return 'T';
        }
        return result;
    }

    private void printBoard() {
        for (char[] row : board) {
            for (char col : row) {
                System.out.print(col);
            }
            System.out.println();
        }
    }
}
