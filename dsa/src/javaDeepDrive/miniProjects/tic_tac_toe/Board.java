package javaDeepDrive.miniProjects.tic_tac_toe;

/**
 * Represents the Tic-Tac-Toe board.
 *
 * Responsibility:
 * - Maintains the game grid
 * - Validates moves
 * - Checks winning conditions
 * - Displays the board
 */
public class Board {

    private char[][] grid;
    private int size = 3;

    public Board() {
        grid = new char[size][size];
        initializeBoard();
    }

    public void initializeBoard() {
        // fill with ' '
        for(int i =0; i< size; i++){
            for(int j =0; j< size; j++){
                grid[i][j] = ' ';
            }
        }
    }

    public void printBoard() {
        // display board

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
                // print separator between columns
                if (j < size - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            // print row separator
            if (i < size - 1) {
                System.out.println("---------");
            }
        }
    }

    public boolean isValidMove(int row, int col) {
        // check bounds + empty cell

        // 1. Check bounds
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }

        // 2. Check if cell is empty
        if (grid[row][col] != ' ') {
            return false;
        }

        return true;
    }

    public void makeMove(int row, int col, char symbol) {
        // place symbol
        grid[row][col] = symbol;
    }

    public boolean checkWinner(char symbol) {

        // Check rows
        for (int i = 0; i < size; i++) {
            if (grid[i][0] == symbol &&
                    grid[i][1] == symbol &&
                    grid[i][2] == symbol) {
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < size; i++) {
            if (grid[0][i] == symbol &&
                    grid[1][i] == symbol &&
                    grid[2][i] == symbol) {
                return true;
            }
        }

        // Check main diagonal
        if (grid[0][0] == symbol &&
                grid[1][1] == symbol &&
                grid[2][2] == symbol) {
            return true;
        }

        // Check anti-diagonal
        if (grid[0][2] == symbol &&
                grid[1][1] == symbol &&
                grid[2][0] == symbol) {
            return true;
        }

        return false;
    }

    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                // if any cell is empty → not full
                if (grid[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true; // no empty cells found
    }
}
