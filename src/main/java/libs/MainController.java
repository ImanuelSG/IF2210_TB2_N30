package libs;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController {

    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
        // You can also set the image programmatically if needed
        Image image = new Image(getClass().getResource("/images/myImage.png").toString());
        imageView.setImage(image);
    }
}
