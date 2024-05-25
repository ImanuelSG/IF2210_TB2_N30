package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;

import libs.FileManager.FileManager;
import libs.GameWorld.GameWorld;

public class PluginController {

    @FXML
    void handleSelectPluginButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JAR File");

        // Set extension filter for JAR files
        FileChooser.ExtensionFilter jarFilter = new FileChooser.ExtensionFilter("JAR Files", "*.jar");
        fileChooser.getExtensionFilters().add(jarFilter);

        // Get the window from the source of the event
        Node source = (Node) event.getSource();
        Window window = source.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String selectedFilePath = selectedFile.getAbsolutePath();
            // Do something with the selected file path
            System.out.println("Selected JAR file: " + selectedFilePath);
            try {
                FileManager.getInstance().loadJar(selectedFile);
                // Display a success message
                showAlert("Success", "JAR file loaded successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                // Display an error message
                showAlert("Error", "Failed to load JAR file.");
            }
        } else {
            System.out.println("No file selected.");
        }

        System.out.println(FileManager.getInstance().getSupportedExtensions());

        GameWorld.getInstance().notifyObserver();

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
