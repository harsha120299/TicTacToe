package com.assignment.tictactoe.service;

import java.util.Scanner;

public class HumanPlayer extends Player {

    Scanner scan = new Scanner(System.in);
    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void move(int row, int col) {
        board.updateMove(row,col,Piece.X);

    }
}
