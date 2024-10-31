package com.assignment.tictactoe.service;

public class AIPlayer extends Player {

    public AIPlayer(Board board) {
        super(board);
    }

    @Override
    public void move(int row, int col) {
        board.updateMove(row,col,Piece.O);
    }

}
