package com.assignment.tictactoe.service;

public class Winner {
    public Piece winningPiece;
    public int[][]winner = new int[3][2];

    public Winner(Piece winningPiece) {
        this.winningPiece = winningPiece;
    }

    public Winner(Piece winningPiece, int col1, int row1, int col2, int row2, int col3, int row3) {
        this.winningPiece = winningPiece;
        this.winner[0][0] = row1;
        this.winner[0][1] = col1;
        this.winner[1][0] = row2;
        this.winner[1][1] = col2;
        this.winner[2][0] = row3;
        this.winner[2][1] = col3;
    }

    public Piece getWinnerPiece() {
        return winningPiece;
    }
    public int[][] getWinner() {
        return winner;
    }
}

