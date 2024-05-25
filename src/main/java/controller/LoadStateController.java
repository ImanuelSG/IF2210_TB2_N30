package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Window;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import libs.FileManager.FileManager;
import libs.GameWorld.GameWorld;

public class LoadStateController implements Initializable {

    @FXML
    private ComboBox<String> comboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComboBox();
        addComboBoxClickListener();
    }

    private void initializeComboBox() {
        comboBox.getItems().clear(); // Clear existing items to avoid duplication
        comboBox.getItems().addAll(FileManager.getInstance().getSupportedExtensions());
        comboBox.setValue("txt");
    }

    private void addComboBoxClickListener() {
        comboBox.setOnMouseClicked(event -> initializeComboBox());
    }

    @FXML
    void handleSelectFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        String selectedExtension = comboBox.getValue();
        if (selectedExtension != null && !selectedExtension.isEmpty()) {
            // Set extension filters based on the selected extension from the ComboBox
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(selectedExtension.toUpperCase() + " Files",
                            "*." + selectedExtension));

            Window window = comboBox.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(window);
            if (selectedFile != null) {
                String selectedFilePath = selectedFile.getAbsolutePath();
                // Do something with the selected file path
                System.out.println("Selected file: " + selectedFilePath);
                try {
                    FileManager.getInstance().loadFile(selectedFile, selectedExtension);
                    showAlert("Success", "File loaded successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to load file: " + e.getMessage());
                }
            } else {
                System.out.println("No file selected.");
            }
        } else {
            System.out.println("No extension selected.");
        }

        GameWorld.getInstance().notifyObserver();
        GameWorld.getInstance().movePhase(1);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
