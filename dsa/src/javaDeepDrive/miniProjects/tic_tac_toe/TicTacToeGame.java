package javaDeepDrive.miniProjects.tic_tac_toe;
import java.util.Scanner;

/**
 * Controls the overall flow of the Tic-Tac-Toe game.
 *
 * Responsibility:
 * - Manages players and turns
 * - Handles user input
 * - Controls game loop
 * - Decides game result (win/draw)
 */
public class TicTacToeGame {

    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public TicTacToeGame() {
        board = new Board();
        player1 = new Player("Player 1", 'X');
        player2 = new Player("Player 2", 'O');
        currentPlayer = player1;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.printBoard();

            System.out.println(currentPlayer.getName() + "'s turn");
            System.out.println("Enter row and column: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // validate + move
            if (!board.isValidMove(row, col)) {
                System.out.println("Invalid move! Try again.");
                continue;
            }
            board.makeMove(row, col, currentPlayer.getSymbol());

            // check winner
            if (board.checkWinner(currentPlayer.getSymbol())) {
                board.printBoard();
                System.out.println(currentPlayer.getName() + " wins!");
                break;
            }

            // Check draw
            if (board.isBoardFull()) {
                board.printBoard();
                System.out.println("Game is a draw!");
                break;
            }
            // Switch player
            switchPlayer();
        }
    }

    private void switchPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
    }
}
