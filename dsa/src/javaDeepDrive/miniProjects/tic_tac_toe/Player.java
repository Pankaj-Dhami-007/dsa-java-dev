package javaDeepDrive.miniProjects.tic_tac_toe;

/**
 * Represents a player in the Tic-Tac-Toe game.
 *
 * Responsibility:
 * - Holds player details such as name and symbol (X or O)
 */
public class Player {

    private String name;
    private char symbol;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }
}