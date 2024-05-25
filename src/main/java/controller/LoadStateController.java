package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    }

    private void addComboBoxClickListener() {
        comboBox.setOnMouseClicked(event -> initializeComboBox());
    }

    @FXML
    void handleSelectFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        // Set extension filters based on supported extensions from FileManager
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(comboBox.getValue().toUpperCase() + " Files", "*." + comboBox.getValue())
        );
    
        Window window = comboBox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String selectedFilePath = selectedFile.getAbsolutePath();
            // Do something with the selected file path
            System.out.println("Selected file: " + selectedFilePath);
            FileManager.getInstance().loadFile(selectedFile, FileManager.getInstance().getFileExtension(selectedFilePath));
        } else {
            System.out.println("No file selected.");
        }

        GameWorld.getInstance().notifyObserver();
        GameWorld.getInstance().movePhase(1);
    }
}
