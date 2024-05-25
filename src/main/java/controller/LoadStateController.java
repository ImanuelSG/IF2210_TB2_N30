package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.stage.FileChooser;

import java.io.File;

import libs.Deck.*;
import libs.Field.*;
import libs.FileManager.FileManager;
import libs.GameWorld.GameWorld;
import libs.Player.Player;

public class LoadStateController {

    @FXML
    private ComboBox<String> comboBox;
    
    @FXML
    void handleSelectFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        
        // Set extension filters if needed
        // fileChooser.getExtensionFilters().addAll(
        //     new FileChooser.ExtensionFilter("Text Files", "*.txt"),
        //     new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
        //     new FileChooser.ExtensionFilter("All Files", "*.*")
        // );
    
        Window window = comboBox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(window);
        if (selectedFile != null) {
            String selectedFilePath = selectedFile.getAbsolutePath();
            // Do something with the selected file path
            System.out.println("Selected file: " + selectedFilePath);
            FileManager.getInstance().loadFile(selectedFile, "txt");
        } else {
            System.out.println("No file selected.");
        }

        

        GameWorld.getInstance().notifyObserver();
        GameWorld.getInstance().movePhase(1);
       
    }
    
}
