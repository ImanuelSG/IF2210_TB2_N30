package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import libs.FileManager.FileManager;

public class SaveStateController implements Initializable {

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
        // Set the default value to .txt
        comboBox.setValue("txt");
    }

    private void addComboBoxClickListener() {
        comboBox.setOnMouseClicked(event -> initializeComboBox());
    }
    
    @FXML
    void handleSelectFolderButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        Window window = comboBox.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(window);
        if (selectedDirectory != null) {
            String selectedFolderPath = selectedDirectory.getAbsolutePath();
            // Get the selected file extension from the ComboBox
            String selectedExtension = comboBox.getValue();
            if (selectedExtension != null) {
                // Do something with the selected folder path and selected extension
                System.out.println("Selected folder: " + selectedFolderPath);
                System.out.println("Selected file extension: " + selectedExtension);
                FileManager.getInstance().saveFile(selectedFolderPath, selectedExtension);
                showAlert("Success", "File saved successfully.");
            } else {
                System.out.println("No file extension selected.");
                showAlert("Error", "No file extension selected.");
            }
        } else {
            System.out.println("No folder selected.");
            showAlert("Error", "No folder selected.");
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
