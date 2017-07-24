package com.droidrank.tictactoe;

public class GameState {

    public static final int NUM_SQUARES = 9;

    private static int[][] squareTestPattern = new int[][]{
            { 0, 4, 8 }, // cross
            { 2, 4, 6 }, // cross
            { 0, 1, 2 }, // hz1
            { 3, 4, 5 }, // hz2
            { 6, 7, 8 }, // hz3
            { 0, 3, 6 }, // vt1
            { 1, 4, 7 }, // vt2
            { 2, 5, 8 } // vt3
    };

    private int[] squares;
    private int moves = 0;

    public GameState() {
        squares = new int[NUM_SQUARES];
        resetBoard();
    }

    public void resetBoard() {
        moves = 0;
        for ( int i = 0; i < NUM_SQUARES; i++ ) {
            squares[i] = 0;
        }
    }

    public void setSquare( int id ) {
        squares[id] = moves % 2 == 0 ? 1 : 2;
        moves++;
    }

    /**
     * <pre>
     * -1 = Game not over yet.
     * 0 = tie
     * 1 = player 1
     * 2 = player 2
     * </pre>
     *
     * @return -1, 0, 1, 2
     */
    public int winningPlayer() {
        if ( moves >= 5 ) {
            for ( int[] ids : squareTestPattern ) {
                int winner = checkSquares( ids[0], ids[1], ids[2] );
                if ( winner != 0 ) {
                    return winner;
                }
            }
        }
        if ( moves == 9 ) {
            return 0;
        }
        return -1; // tie
    }

    private int checkSquares( int a, int b, int c ) {
        if ( squares[a] != 0 && squares[a] == squares[b] && squares[a] == squares[c] ) {
            return squares[a];
        }
        return 0;
    }

    /**
     * <pre>
     * Which player currently occupies this square
     * 0 = empty
     * 1 = player 1
     * 2 = player 2
     *
     * Block IDs:
     * 0 1 2
     * 3 4 5
     * 6 7 8
     * </pre>
     *
     * @param id the blockId 0 <= id <= 8
     * @return 0, 1, 2
     */
    public int getPlayerAtSquare( int id ) {
        return squares[id];
    }
}
