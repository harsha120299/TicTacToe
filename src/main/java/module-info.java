module com.assignment.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;

    opens com.assignment.tictactoe.service to javafx.fxml;
    exports com.assignment.tictactoe.service;
}