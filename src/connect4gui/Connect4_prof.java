
package connect4gui;

import java.util.Scanner;

public class Connect4_prof {

    private static final int ROWS = 6;
    private static final int COLS = 7;
    // Constants for scoring potential quads
    private static final int SCORE_ONE_CHECKER = 1;
    private static final int SCORE_TWO_CHECKERS = 4;
    private static final int SCORE_THREE_CHECKERS = 16;
    private static final int SCORE_FOUR_CHECKERS = 10000;
    private static final int SCORE_WIN = 1000000;    

    // Evaluate a potential quad and return its score but the other color has negative score
    // Parameter player can be either 1 (for me) or -1 (for the opponent).
    public static int evaluateQuad(int[] quad, int player) {
        /* 
         * Προσοχή! Μπορεί να έχουμε κόκκινα και κίτρινα στην ίδια τετράδα
         */
        int score = 0;
        int numPlayerCheckers = 0;
        int numOpponentCheckers = 0;
        for (int i = 0; i < 4; i++) {
            if (quad[i] == player) {
                numPlayerCheckers++;
            } else if (quad[i] == - player) {
                numOpponentCheckers++;
            }
        }
        
        if(numPlayerCheckers > 0 && numOpponentCheckers > 0)
            return 0;

       // Add code to evaluate the quad and return its score
        if (numPlayerCheckers == 4) {
            score = SCORE_FOUR_CHECKERS;
        } else if (numPlayerCheckers == 3 ) {
            score = SCORE_THREE_CHECKERS;
        } else if (numPlayerCheckers == 2 ) {
            score = SCORE_TWO_CHECKERS;
        } else if (numPlayerCheckers == 1 ) {
            score = SCORE_ONE_CHECKER;
        } else if (numOpponentCheckers == 3 ) {
            score = SCORE_THREE_CHECKERS;
        } else if (numOpponentCheckers == 2 ) {
            score = SCORE_TWO_CHECKERS;
        } else if (numOpponentCheckers == 1 ) {
            score = SCORE_ONE_CHECKER;
        } else if (numOpponentCheckers == 4) {
            score = SCORE_FOUR_CHECKERS;
        }
        return score;
    }

    // Calculate the scores for all potential quads on the board and return them in a 2D array
    public static int calculateScore(int[][] board, int player) {
        int score = 0;

        // Calculate horizontal quads
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                int[] quad = {board[row][col], board[row][col+1], board[row][col+2], board[row][col+3]};
                score += evaluateQuad(quad, player);
            }
        }
        // Calculate vertical quads
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS; col++) {
                int[] quad = {board[row][col], board[row+1][col], board[row+2][col], board[row+3][col]};
                score += evaluateQuad(quad, player);
            }
        }
        // Calculate upper diagonal quads
        for (int row = 0; row < ROWS - 3; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                int[] quad = {board[row][col], board[row+1][col+1], board[row+2][col+2], board[row+3][col+3]};
                score += evaluateQuad(quad, player);
            }
        }
        // Calculate lower diagonal quads
        for (int row = ROWS - 3; row < ROWS; row++) {
            for (int col = 0; col < COLS - 3; col++) {
                int[] quad = {board[row][col], board[row-1][col+1], board[row-2][col+2], board[row-3][col+3]};
                score += evaluateQuad(quad, player);
            }
        }
        return score;
    }
    
    public static boolean checkWin(int[][] board, int player) {
        // Check horizontally
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS-3; j++) {
                if (board[i][j] == player &&
                    board[i][j+1] == player &&
                    board[i][j+2] == player &&
                    board[i][j+3] == player) {
                    return true;
                }
            }
        }

        // Check vertically
        for (int i = 0; i < ROWS-3; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == player &&
                    board[i+1][j] == player &&
                    board[i+2][j] == player &&
                    board[i+3][j] == player) {
                    return true;
                }
            }
        }

        // Check diagonally (positive slope)
        for (int i = 0; i < ROWS-3; i++) {
            for (int j = 0; j < COLS-3; j++) {
                if (board[i][j] == player &&
                        board[i+1][j+1] == player &&
                        board[i+2][j+2] == player &&
                        board[i+3][j+3] == player) {
                    return true;
                }
            }
        }

        // Check diagonally (negative slope)
        for (int i = ROWS-3; i < ROWS; i++) {
            for (int j = 0; j < COLS-4; j++) {
                if (board[i][j] == player &&
                    board[i-1][j+1] == player &&
                    board[i-2][j+2] == player &&
                    board[i-3][j+3] == player) {
                    return true;
                }
            }
        }

        // If no winning combination is found, return false
        return false;
    }

    // Calculate the minimax score for a given board and depth, return the best column to play and the score
    public static int[] minimax(int[][] board, int depth, int player, int alpha, int beta) {
        int[] bestMove = new int[2];

        // Start the game with the middle column
        boolean empty = true;
        for (int col = 0; col < COLS; col++) {
            if (board[ROWS-1][col] != 0) {
                empty = false;
                break;
            }
        }
        if(empty){
            bestMove[0] = 3;
            return bestMove;
        }

        if (depth == 0) {
            int[] returnValue = new int[2];
            returnValue[0] = 0;
            returnValue[1] = calculateScore(board, player);
            return returnValue;
        } else {
            int bestScore = (player == 1 ? Integer.MIN_VALUE : Integer.MAX_VALUE);

            for (int col = 0; col < COLS; col++) {
                int row = ROWS - 1;
                while (row >= 0 && board[row][col] != 0) {
                    row--;
                }
                
                if(row < 0) 
                    continue;
                    
                board[row][col] = player;
                if(checkWin(board, player)){
                    bestMove[0] = col;
                    bestMove[1] = player == 1 ? SCORE_WIN : -SCORE_WIN;
                    board[row][col] = 0;
                    return bestMove;
                }

                int[] move = minimax(board, depth - 1, (player == 1 ? -1 : 1), alpha, beta);
                board[row][col] = 0;
                if (player == 1) {
                    if (move[1] > bestScore) {
                        bestScore = move[1];
                        bestMove[0] = col;
                        bestMove[1] = bestScore;
                    }
                    alpha = (move[1] > alpha ? move[1] : alpha);
                    if (alpha >= beta)
                        break;
                } else { //player == -1
                    if(move[1] < bestScore) {
                        bestScore = move[1];
                        bestMove[0] = col;
                        bestMove[1] = bestScore;
                    }
                    beta = (move[1] < beta ? move[1] : beta);
                    if (alpha >= beta)
                        break;
                }
            }
        }

        return bestMove;
    }
    
    public static String board2String(int[][] board) {
    
        StringBuilder bld = new StringBuilder("-----------------------------\n");
        for(int row=0; row<ROWS; row++) {
            bld.append("|");
            for(int col=0; col<COLS; col++) {
                char c = ' ';
                if(board[row][col] > 0)
                    c = 'O';
                else if(board[row][col] < 0)
                    c = 'X';
                bld.append( String.format(" %c |", c) );
            }
            bld.append("\n-----------------------------\n");
        }
        bld.append("\n| 0 | 1 | 2 | 3 | 4 | 5 | 6 |\n");
        return bld.toString();
    }
    
    public static  void print_board(int[][]board) {
        System.out.print("\n--------------------------------\n");
        for(int row=0; row<ROWS; row++) {
            System.out.print("|");
            for(int col=0; col<COLS; col++) {
                System.out.print( String.format(" %d |", board[row][col]) );
            }
            System.out.print("\n--------------------------------\n");
        }
    }
    
    public static void updateBoard(int[][] board, int col, int player) {
        int row;
        for(row = ROWS-1; row>=0 && board[row][col] != 0; row--)
            ;
        if(row < 0)
            return;
        board[row][col] = player;
    }
/*
    public static void main(String[] args) {
        int[][] board = {
                {0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0},
                {0,  0,  0,  0,  0,  0,  0}
        };
        
        if(args.length < 2) {
            System.out.println("Insufficient number of arguments!");
            System.exit(0);
        }
        int depth = Integer.valueOf(args[0]);
        int player = (args[1].equals("AI") ? 1 : -1);
        
        if(player == -1)
            System.out.println(board2String(board));
        
        Scanner sc = new Scanner(System.in);
        
        while(true) {
            if(player == -1) {
                System.out.print("Next ? ");
                int nextMove = sc.nextInt();
                if(nextMove<0 || nextMove>6 || board[0][nextMove] != 0) {
                    System.out.println("Invalid move! Try again....\n");
                    continue;
                }
                updateBoard(board, nextMove, player);
                print_board(board);
                System.out.println("\n --- YOU ---");
                System.out.println(board2String(board));
                if(checkWin(board, player)) {
                    System.out.println("  ==== GAME OVER ====  ");
                    System.exit(0);
                }
                    
                //print_board(board);
                player = -player;
            }
            else {
                int alpha = Integer.MIN_VALUE;
                int beta = Integer.MAX_VALUE;
                int[] bestMove = minimax(board, depth, player, alpha, beta);
                int nextMove = bestMove[0];
                updateBoard(board, nextMove, player);
                System.out.println("\n --- AI ---");
                System.out.println(board2String(board));
                if(checkWin(board, player)) {
                    System.out.println("  ==== GAME OVER ==== ");
                    System.exit(0);
                }
                //print_board(board);
                player =  -player;
            }
        }

    }
*/
}
