import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class InteractiveTicTacToeGame {
    private JFrame frame;
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel textfield = new JLabel();
    private JButton[][] buttons;
    private JButton replayButton;

    private Board board;
    private Player currentPlayer;
    private Human human;
    private Computer computer;

    public InteractiveTicTacToeGame(Human human, Computer computer, int size) {
        this.human = human;
        this.computer = computer;
        currentPlayer = human;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());

        initializeGUI(size);
        initializeGame(size);

        frame.setVisible(true);
    }

    public void play() {}

    private void initializeGUI(int size) {
        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);

        textfield = new JLabel("Tic-Tac-Toe");
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setOpaque(true);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(size, size));
        buttonPanel.setBackground(new Color(150, 150, 150));

        buttons = new JButton[size][size];
        replayButton = new JButton("Replay");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                buttons[row][col] = createButton();
                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(e -> handleButtonClick(r, c));
                buttonPanel.add(buttons[row][col]);
            }
        }

        replayButton.setFont(new Font("MV Boli", Font.BOLD, 24));
        replayButton.setFocusPainted(false);
        replayButton.addActionListener(e -> handleReplay());

        titlePanel.add(textfield);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(replayButton, BorderLayout.SOUTH);
    }

    private void handleButtonClick(int row, int col) {
        if (board.isEmptyCell(row, col) && !board.isGameOver()) {
            buttons[row][col].setForeground(new Color(255, 0, 0));
            buttons[row][col].setText(String.valueOf(currentPlayer.getSymbol()));

            board.placeSymbol(row, col, currentPlayer.getSymbol());

            if (board.checkWin(currentPlayer.getSymbol())) {
                displayWinner();
            } else if (board.isFull()) {
                displayDraw();
            } else {
                // Switch to the other player
                currentPlayer = (currentPlayer == human) ? computer : human;

                if (currentPlayer instanceof Computer) {
                    handleComputerMove();
                }
            }
        }
    }

    private JButton createButton() {
        JButton button = new JButton();
        button.setFont(new Font("MV Boli", Font.BOLD, 120));
        button.setFocusPainted(false);
        return button;
    }

    private void initializeGame(int size) {
        board = new Board(size);
        currentPlayer = human;
    }

    private void handleComputerMove() {
        if (currentPlayer instanceof Computer && !board.isGameOver()) {
            Computer computerPlayer = (Computer) currentPlayer;

            // Call the Computer class's makeMove method
            boolean validMove = computerPlayer.makeMove(board);

            if (validMove) {
                updateGUI(); // Update the GUI based on the move
            }

            if (board.checkWin(currentPlayer.getSymbol())) {
                displayWinner();
            } else if (board.isFull()) {
                displayDraw();
            } else {
                // Switch back to the human player
                currentPlayer = human;
            }
        }

    }

    private void handleReplay() {
        int choice = JOptionPane.showConfirmDialog(frame, "Do you want to replay the game?",
                "Replay Confirmation", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            // Clear the board and enable buttons
            board.initializeBoard();
            enableButtons();

            // Start a new game
            currentPlayer = human;
        } else {
            endGame();
        }
    }

    private void updateGUI() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.getCellValue(row, col) != ' ') {
                    buttons[row][col].setForeground(new Color(255, 0, 0));
                    buttons[row][col].setText(String.valueOf(board.getCellValue(row, col)));
                }
            }
        }
    }

    private void displayWinner() {
        JOptionPane.showMessageDialog(frame, "Player " + currentPlayer.getSymbol() + " wins!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
        handleReplay();
    }

    private void displayDraw() {
        JOptionPane.showMessageDialog(frame, "It's a draw!", "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
        handleReplay();
    }

    private void enableButtons() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                buttons[row][col].setText("");
                buttons[row][col].setEnabled(true);
            }
        }
        replayButton.setEnabled(true);
    }

    private void endGame() {
        frame.dispose();
        System.exit(0);
    }
}
