package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import libs.GameWorld.AudioPlayer;

public class StartViewController {

    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Set fixed width and height from MainApp
        Scene scene = new Scene(root, 1375, 1200.0);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        // AudioPlayer audioPlayer = AudioPlayer.getInstance("/audio/backsound.mp3", "/audio/attack.mp3");
        // audioPlayer.play();
    }
}
