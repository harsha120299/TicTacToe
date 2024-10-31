package com.assignment.tictactoe.service;

import java.util.Arrays;

public class BoardImpl implements Board {
    public Piece[][] pieces;
    public BoardUI boardUi;
    public final int BOARD_SIZE = 3;

    public BoardImpl(BoardUI boardUi) {
        this.boardUi = boardUi;
        this.pieces = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    public int getSize() {
        return BOARD_SIZE;
    }

    @Override
    public BoardUI getBoardUI() {
        return null;
    }

    public void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            Arrays.fill(pieces[i], Piece.EMPTY);
        }
    }
    @Override
    public int[] findNextAvailableSpotUsingMiniMax() {
        int[] bestMove = {-1, -1};// Initialize the best move coordinates
        int bestScore = Integer.MIN_VALUE;// Initialize the best score as minimum possible

        for (int i = 0; i < BOARD_SIZE; i++) {// Iterate over each cell on the board
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (pieces[i][j] == Piece.EMPTY) {// Check if the cell is empty
                    pieces[i][j] = Piece.O;// Try placing 'O' (the maximizing player) in the empty cell
                    int score = minimax(false);// Evaluate the move using the minimax algorithm
                    pieces[i][j] = Piece.EMPTY; // Undo the move to reset the board state
                    if (score > bestScore) { // Update the best move if the current move has a higher score
                        bestScore = score;
                        bestMove = new int[]{i, j}; // Store the best move coordinates
                    }
                }
            }
        }
        return bestMove;// Return the best move found
    }

    private int minimax(boolean isMaximizing) {
        Winner winner = checkWinner();// Check if there's a winner and return score accordingly
        if (winner != null) {
            return winner.getWinnerPiece() == Piece.O ? 1 : -1;// 1 for 'Piece.O' win, -1 for 'X' win
        }
        if (isBoardFull()) return 0; // If board is full and no winner, it's a draw

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;// Initialize best score for maximizer and minimizer

        for (int i = 0; i < BOARD_SIZE; i++) { // Iterate through each cell on the board
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (pieces[i][j] == Piece.EMPTY) { // Check if the cell is empty
                    pieces[i][j] = isMaximizing ? Piece.O : Piece.X;// Simulate the move for current player
                    int score = minimax(!isMaximizing);// Recursively call minimax to evaluate the move
                    pieces[i][j] = Piece.EMPTY;// Undo the move
                    bestScore = isMaximizing ? Math.max(score, bestScore) : Math.min(score, bestScore);// Update best score based on whether maximizing or minimizing
                }
            }
        }
        return bestScore;// Return the best score found
    }
    public boolean isLegalMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && pieces[row][col] == Piece.EMPTY;
    }

    public void updateMove(int row, int col, Piece piece) {
        isLegalMove(row, col);
        pieces[row][col] = piece;
        if (boardUi != null) {
            boardUi.update(col,row,piece==Piece.X);
        }
    }

    public Winner checkWinner() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (pieces[i][0] != Piece.EMPTY && pieces[i][0] == pieces[i][1] && pieces[i][1] == pieces[i][2]) {
                return new Winner(pieces[i][0], 0, i, 1, i, 2, i);
            }
        }

        for (int j = 0; j < BOARD_SIZE; j++) {
            if (pieces[0][j] != Piece.EMPTY && pieces[0][j] == pieces[1][j] && pieces[1][j] == pieces[2][j]) {
                return new Winner(pieces[0][j], j, 0, j, 1, j, 2);
            }
        }

        if (pieces[0][0] != Piece.EMPTY && pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2]) {
            return new Winner(pieces[0][0], 0, 0, 1, 1, 2, 2);
        }

        if (pieces[0][2] != Piece.EMPTY && pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0]) {
            return new Winner(pieces[0][2], 0, 2, 1, 1, 2, 0);
        }

        return null;
    }
    public void printBoard() {
        for(Piece[] piece : pieces ) {
            System.out.println(Arrays.toString(piece));
        }
    }
    public boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

}

