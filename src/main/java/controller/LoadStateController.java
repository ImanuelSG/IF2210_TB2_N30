package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
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
    void handleSelectFolderButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        String selectedExtension = comboBox.getValue();
        if (selectedExtension != null && !selectedExtension.isEmpty()) {
            Window window = comboBox.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(window);
            if (selectedDirectory != null) {
                File stateFile = new File(selectedDirectory, "state." + selectedExtension);
                if (stateFile.exists()) {
                    String selectedFilePath = stateFile.getAbsolutePath();
                    // Do something with the selected file path
                    System.out.println("Selected file: " + selectedFilePath);
                    try {
                        FileManager.getInstance().loadFile(stateFile, selectedExtension);
                        showAlert("Success", "File loaded successfully.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Error", "Failed to load file: " + e.getMessage());
                    }
                } else {
                    showAlert("Error", "File state." + selectedExtension + " not found in the selected folder.");
                }
            } else {
                System.out.println("No folder selected.");
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
