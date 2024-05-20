package controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class View1Controller {

    @FXML
    private Text someText;

    @FXML
    public void initialize() {
        // Initialization code here
        System.out.println("initialized");
        someText.setText("This is View 1");
    }
}
