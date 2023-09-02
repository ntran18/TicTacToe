package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Computer extends Player {
    // Define an enum for difficulty levels
    public enum Level {
        EASY, HARD
    }

    private Level difficultyLevel;

    public Computer(char symbol) {
        super(symbol);
        this.difficultyLevel = Level.EASY;
    }

    public void setLevel(Level level) {
        this.difficultyLevel = level;
    }

    @Override
    public boolean makeMove(Board board) {
        int row;
        int col;

        if (difficultyLevel == Level.EASY) {
            Random random = new Random();

            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (!board.isEmptyCell(row, col));
        } else {
            Move bestMove = minimax(board, this.symbol);
            row = bestMove.row;
            col = bestMove.col;
        }

        board.placeSymbol(row, col, symbol);
        return true;
    }

    private Move minimax(Board board, char currentPlayer) {
        if (board.isGameOver()) {
            int score = evaluate(board);
            return new Move(score);
        }

        List<Move> moves = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.isEmptyCell(row, col)) {
                    board.placeSymbol(row, col, currentPlayer);
                    int score = minimax(board, (currentPlayer == 'X') ? 'O' : 'X').score;
                    moves.add(new Move(row, col, score));
                    board.clearCell(row, col);
                }
            }
        }

        if (currentPlayer == this.symbol) {
            return Collections.max(moves, Comparator.comparing(move -> move.score));
        } else {
            return Collections.min(moves, Comparator.comparing(move -> move.score));
        }
    }

    private int evaluate(Board board) {
        if (board.checkWin(this.symbol)) {
            // Computer wins
            return 1;
        } else if (board.checkWin(getOpponentSymbol())) {
            // Opponent wins
            return -1;
        } else {
            // It's a draw or the game is ongoing
            return 0;
        }
    }

    private char getOpponentSymbol() {
        return (this.symbol == 'X') ? 'O' : 'X';
    }

    class Move {
        int row;
        int col;
        int score;

        public Move(int score) {
            this.score = score;
        }

        public Move(int row, int col, int score) {
            this.row = row;
            this.col = col;
            this.score = score;
        }
    }
}
