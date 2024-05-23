package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import libs.GameWorld.GameWorld;

import java.io.IOException;

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
        GameWorld main = GameWorld.getInstance();

        loadView("LadangKu.fxml"); // Corrected to load "View1.fxml"
        // main.run();

    }

    @FXML
    public void showLadangKu() {
        GameWorld.getInstance().nextTurn();
        System.out.println("Ayam2");
        loadView("LadangKu.fxml");
    }

    @FXML
    public void showView2() {
        System.out.println("AYam");
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

    }

}