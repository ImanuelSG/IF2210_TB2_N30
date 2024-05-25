package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

import libs.FileManager.FileManager;
import libs.GameWorld.GameWorld;

public class PluginController {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    void handleSelectPluginButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JAR File");

        // Set extension filter for JAR files
        FileChooser.ExtensionFilter jarFilter = new FileChooser.ExtensionFilter("JAR Files", "*.jar");
        fileChooser.getExtensionFilters().add(jarFilter);

        Window window = comboBox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String selectedFilePath = selectedFile.getAbsolutePath();
            // Do something with the selected file path
            System.out.println("Selected JAR file: " + selectedFilePath);
            try {
                FileManager.getInstance().loadJar(selectedFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }

        System.out.println(FileManager.getInstance().getSupportedExtensions());

        GameWorld.getInstance().notifyObserver();
        GameWorld.getInstance().movePhase(1);
    }
}
