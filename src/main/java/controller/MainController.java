package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainController {

    @FXML
    private Pane contentPane;

    @FXML
    private void initialize() {
        System.out.println("KELOAD");
        loadView("LadangKu.fxml"); // Corrected to load "View1.fxml"
    }


    @FXML
    public void showLadangKu() {
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

}
