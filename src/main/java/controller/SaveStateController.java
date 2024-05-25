package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

import libs.FileManager.FileManager;

public class SaveStateController {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    void handleSelectFolderButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        Window window = comboBox.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(window);
        if (selectedDirectory != null) {
            String selectedFolderPath = selectedDirectory.getAbsolutePath();
            // Do something with the selected folder path
            System.out.println("Selected folder: " + selectedFolderPath);
            FileManager.getInstance().saveFile(selectedFolderPath, "txt");
        } else {
            System.out.println("No folder selected.");
        }
    }
}
