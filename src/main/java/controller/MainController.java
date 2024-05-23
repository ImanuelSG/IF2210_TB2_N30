package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import libs.GameWorld.GameWorld;

public class MainController {

    @FXML
    private Pane contentPane;

    @FXML
    private Label turnLabel;

    @FXML
    private Label player1GuldenLabel;

    @FXML
    private Label player2GuldenLabel;

    @FXML
    private void initialize() {

        loadView("LadangKu.fxml"); // Corrected to load "View1.fxml"
        // main.run();

    }

    @FXML
    public void showLadangKu() {

        loadView("LadangKu.fxml");
    }

    @FXML
    public void showView2() {

        loadView("View2.fxml");
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml));
            Pane newLoadedPane = loader.load();
            contentPane.getChildren().setAll(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateView() {
        GameWorld main = GameWorld.getInstance();
        turnLabel.setText("Turn: " + main.getTurn());
        player1GuldenLabel.setText("Player 1 Gulden: " +
                main.getPlayer1().getGulden());
        player2GuldenLabel.setText("Player 2 Gulden: " +
                main.getPlayer2().getGulden());
    }

}
