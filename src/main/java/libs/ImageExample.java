package libs;

import java.io.FileInputStream;
import java.io.InputStream;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ImageExample extends Application {
        public void start(Stage stage) throws IOException {
                // Creating an image
                InputStream stream = new FileInputStream("src/main/resources/img/hewan/bear.png");
                Image image = new Image(stream);

                // Setting the image view
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                // Setting the position of the image
                imageView.setX(300);
                imageView.setY(25);
                imageView.setFitWidth(575);
                // setting the fit height and width of the image view

                // Setting the preserve ratio of the image view
                imageView.setPreserveRatio(true);

                // Creating a Group object
                Group root = new Group(imageView);

                // Creating a scene object
                Scene scene = new Scene(root, 800, 800);

                // Setting title to the Stage
                stage.setTitle("Loading an image");

                // Adding scene to the stage
                stage.setScene(scene);

                // Displaying the contents of the stage
                stage.show();
        }

        public static void main(String args[]) {
                launch(args);
        }
}