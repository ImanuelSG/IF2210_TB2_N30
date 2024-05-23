package controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class View2Controller {

    @FXML
    private Text someText;

    @FXML
    public void initialize() {
        // Initialization code here
        someText.setText("This is View 2");
    }
}
