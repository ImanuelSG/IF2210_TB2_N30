package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import libs.GameWorld.GameWorld;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable, Observerable {

    @FXML
    private BorderPane contentPane;

    @FXML
    private Label turnLabel;

    @FXML
    private Label player1GuldenLabel;

    @FXML
    private Label player2GuldenLabel;

    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameWorld.getInstance();

        loadView("LadangKu.fxml"); // Corrected to load "View1.fxml"
        updateView();
        // main.run();

    }

    @FXML
    public void showLadangKu() {
        loadView("LadangKu.fxml");
    }

    @FXML
    public void showLadangMusuh() {
        loadView("LadangMusuh.fxml");
    }

    @FXML
    public void showToko() {
        loadView("ShuffleView.fxml");
    }

    @FXML
    public void handleNextTurn() {
        GameWorld main = GameWorld.getInstance();
        main.nextTurn();
        updateView();
        play();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.updateView();
        }
    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml));
            Pane newLoadedPane = loader.load();
            contentPane.setCenter(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateView() {
        GameWorld main = GameWorld.getInstance();

        if (main.getTurn() == 20) {
            // End game
            System.out.println();
            return;
        }

        // Update turn label
        turnLabel.setText("Turn: " + main.getTurn());

        // Update player 1 gulden label, ensuring it's a string
        player1GuldenLabel.setText(String.valueOf(main.getPlayer1().getGulden()));

        // Update player 2 gulden label, ensuring it's a string
        player2GuldenLabel.setText(String.valueOf(main.getPlayer2().getGulden()));

    }

    private void play() {
        // Shuffling phase
        this.loadView("ShuffleView.fxml");


        // if 


    }

    private void seranganBeruang() {

    }

}
