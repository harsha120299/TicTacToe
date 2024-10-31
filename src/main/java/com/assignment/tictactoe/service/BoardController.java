package com.assignment.tictactoe.service;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class BoardController implements BoardUI {

    public Board board;
    public Player hPlayer;
    public Player aPlayer;
    public Winner winner;
    private int row;
    private int col;
    private boolean useMiniMax = false;
    private int[] aiMove;

    @FXML
    private JFXToggleButton ooptionButton;

    @FXML
    private JFXButton button;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label resetLable;

    @FXML
    private Label responseLabl;

    public BoardController() {
        this.board = new BoardImpl(this);
        hPlayer = new HumanPlayer(board);
        aPlayer = new AIPlayer(board);
        winner = null;
        aiMove = null;
    }

    @FXML
    private void initialize() {
        resetLable.setVisible(false);
        responseLabl.setText("Tic Tac Toe");
    }

    @FXML
    void buttonClick(ActionEvent event) {
        JFXButton button = (JFXButton) event.getSource();
        Integer rowIndex = GridPane.getRowIndex(button);
        Integer colIndex = GridPane.getColumnIndex(button);

        row = (rowIndex == null) ? 0 : rowIndex;
        col = (colIndex == null) ? 0 : colIndex;

        hPlayer.move(row, col);
        update(col, row, true); // Update after human move
        board.printBoard();

        winner = board.checkWinner();
        if (winner != null) {
            notifyWinner();
            return;
        }
        if (board.isBoardFull()) {
            responseLabl.setText("It's a tie!");
            resetLable.setVisible(true);
            return;
        }
        if(!useMiniMax){
            aiMoveMiniMax();
        }else{
            aiMoveRandom();
        }
        winner = board.checkWinner();
        if (winner != null) {
            notifyWinner();
        }
    }

    private void aiMoveRandom() {
        Random rand = new Random();
        row = rand.nextInt(board.getSize());
        col = rand.nextInt(board.getSize());
        if(board.isLegalMove(row, col)){
            aPlayer.move(row, col);
            update(col, row, false);
        }else{
            aiMoveRandom();
        }
    }
    private void aiMoveMiniMax() {
        aiMove = board.findNextAvailableSpotUsingMiniMax();
        if (aiMove != null) {
            row = aiMove[0];
            col = aiMove[1];
            aPlayer.move(row, col);
            update(col, row, false); // Update after AI move
            board.printBoard();
        }
    }
    private JFXButton selectButton(int row, int col) {
        for (Node node : gridPane.getChildren()) {
            Integer nodeRowIndex = GridPane.getRowIndex(node);
            Integer nodeColIndex = GridPane.getColumnIndex(node);
            if ((nodeRowIndex == null ? 0 : nodeRowIndex) == row &&
                    (nodeColIndex == null ? 0 : nodeColIndex) == col &&
                    node instanceof JFXButton) {

                JFXButton button = (JFXButton) node;
                return button;
            }
        }
        return null;
    }
    @FXML
    void resetGame(MouseEvent event) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof JFXButton) {
                JFXButton button = (JFXButton) node;
                button.setText("");
                button.setDisable(false);
                button.getStyleClass().removeAll("winner");
            }
        }
        board.initializeBoard();
        ooptionButton.setSelected(false);
        useMiniMax =false;
        ooptionButton.setDisable(false);
        winner = null;
        responseLabl.setText("Tic Tac Toe");
        gridPane.setDisable(false);
        resetLable.setVisible(false);}

    @Override
    public void update(int column, int row, boolean isHuman) {
        Piece pieceToDisplay = isHuman ? Piece.X : Piece.O;
        button = selectButton(row, column);
        button.setText(pieceToDisplay.toString());
        button.setDisable(true);
    }
    @Override
    public void notifyWinner() {
        for (int[] coords : winner.getWinner()) {
            int row = coords[0];
            int col = coords[1];System.out.println(row + " " + col);
            JFXButton button = selectButton(row, col);
            if (button != null) {
                button.getStyleClass().add("winner"); // Add the winning style
            }
        }
        if (winner.winningPiece == Piece.X) {
            responseLabl.setText("Human Wins!");
        } else {
            responseLabl.setText("AI Wins!");
        }
        gridPane.setDisable(true);
        resetLable.setVisible(true);
    }

    public void changeDificulty(ActionEvent actionEvent) {
        useMiniMax = ooptionButton.isSelected();
        ooptionButton.setDisable(true);
    }
}
