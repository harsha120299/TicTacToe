package com.assignment.tictactoe.service;

public interface BoardUI {

    void update(int column, int row, boolean isHuman);
    void notifyWinner();
}

