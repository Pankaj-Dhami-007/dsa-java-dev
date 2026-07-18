package javaDeepDrive.miniProjects.tic_tac_toe;

public class TicTacToe {
    public static void main(String[] args) {

        TicTacToeGame game = new TicTacToeGame();
        game.start();
    }
}




































/*
Tic-Tac-Toe
It’s a simple 2-player game played on a 3×3 grid.

Player 1 → X
Player 2 → O

Players take turns placing their symbol.

Rules of the Game====
1. Board Structure
3 rows × 3 columns = 9 cells
Initially, all cells are empty

2. Turns
Player 1 (X) goes first
Then Player 2 (O)
Alternate turns

3. Valid Move
Player can only place symbol in empty cell
If cell is already filled → ❌ invalid move

4. Winning Conditions
A player wins if they fill:
Any Row  -> X | X | X
Any Column ->
O
O
O

Any Diagonal ->

5. Draw Condition 🤝
All 9 cells filled
No winner

6. Game End
When:
A player wins OR
Board is full (draw)

   ------- Think Like a Developer -----

   Now convert rules → logic
   1. Board Representation
   char[][] board = new char[3][3];

   2. Players
   Player 1 → 'X'
Player 2 → 'O'

3. Turn Handling
currentPlayer = 'X' or 'O'

4. Input
Row (0–2)
Column (0–2)

5. Validations
Check bounds
Check empty cell

6. Winner Logic
We need to check:
Rows
Columns
Diagonals
7. Game Loop

Repeat until:
Winner found OR
Board full

We’ll break the game into 3 main classes:

1. Game      → Controls game flow
2. Board     → Manages grid & rules
3. Player    → Holds player info



 */
