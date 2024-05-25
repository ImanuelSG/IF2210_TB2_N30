package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import libs.Deck.Deck;
import libs.GameWorld.BearAttack;
import libs.GameWorld.BearAttackListener;
import libs.GameWorld.GameWorld;
import libs.GameWorld.SpecialObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, SpecialObserver, BearAttackListener, Observer {

    @FXML
    private BorderPane contentPane;

    @FXML
    private Label turnLabel;

    @FXML
    private Label player1GuldenLabel;

    @FXML
    private Label player2GuldenLabel;

    @FXML
    private VBox saveStateBox;

    @FXML
    private VBox loadStateBox;

    @FXML
    private VBox loadPluginBox;

    @FXML
    private Label timerLabel;

    @FXML
    private Label deckLabel;

    @FXML
    private Label phaseLabel;

    // Buttons

    @FXML
    private Button nextButton;

    @FXML
    private Button ladangKuButton;

    @FXML
    private Button ladangMusuhButton;

    @FXML
    private Button tokoButton;

    @FXML
    private Button loadStateButton;

    @FXML
    private Button loadPluginButton;

    @FXML
    private Button saveStateButton;

    @FXML
    private VBox sideBar;

    // Beruang

    @FXML
    private VBox beruangPopUp;

    @FXML
    private Label beruangText;

    @FXML
    private Label beruangLabel;

    @FXML
    private ImageView beruangImg;

    @FXML
    private Button okButton;

    @FXML
    private VBox winningState;

    @FXML
    private ImageView winningImg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GameWorld main = GameWorld.getInstance();
        main.addObserver(this);
        main.getCurrentPlayer().addObserver(this);
        main.getEnemy().addObserver(this);

        saveStateBox.setVisible(false);
        loadStateBox.setVisible(false);
        loadPluginBox.setVisible(false);
        beruangPopUp.setVisible(false);
        winningState.setVisible(false);


        main.registerObserver(this);
        main.addListener(this);

        Deck d1 = main.getPlayer1().getDeck();
        Deck d2 = main.getPlayer2().getDeck();

        d1.addObserver(this);

        d2.addObserver(this);

        timerLabel.setVisible(false);
        updateView();
        play(0);
    }

    @FXML
    public void disableAllButton() {
        nextButton.setDisable(true);
        ladangKuButton.setDisable(true);
        ladangMusuhButton.setDisable(true);
        tokoButton.setDisable(true);
        loadStateButton.setDisable(true);
        loadPluginButton.setDisable(true);
        saveStateButton.setDisable(true);
    }

    @FXML
    public void enableAllButton() {
        nextButton.setDisable(false);
        ladangKuButton.setDisable(false);
        ladangMusuhButton.setDisable(false);
        tokoButton.setDisable(false);
        loadStateButton.setDisable(false);
        loadPluginButton.setDisable(false);
        saveStateButton.setDisable(false);

    }

    @FXML
    public void closeOtherBox() {
        saveStateBox.setVisible(false);
        loadStateBox.setVisible(false);
        loadPluginBox.setVisible(false);
    }

    @FXML
    public void setTimer(String duration) {
        timerLabel.setText("Time Left: " + duration + "s");

    }

    @FXML
    public void onTimer() {
        timerLabel.setVisible(true);
    }

    @FXML
    public void offTimer() {
        timerLabel.setVisible(false);
    }

    @FXML
    public void showLadangKu() {
        loadView("LadangKu.fxml");
        closeOtherBox();
    }

    @FXML
    public void showLadangMusuh() {
        loadView("LadangMusuh.fxml");
        closeOtherBox();
    }

    @FXML
    public void showToko() {
        loadView("TokoView.fxml");
        closeOtherBox();
    }

    @FXML
    public void showSaveState() {
        saveStateBox.setVisible(!saveStateBox.isVisible());
        loadStateBox.setVisible(false);
        loadPluginBox.setVisible(false);
    }

    @FXML
    public void showLoadState() {
        loadStateBox.setVisible(!loadStateBox.isVisible());
        saveStateBox.setVisible(false);
        loadPluginBox.setVisible(false);
    }

    @FXML
    public void showPlugin() {
        loadPluginBox.setVisible(!loadPluginBox.isVisible());
        saveStateBox.setVisible(false);
        loadStateBox.setVisible(false);

    }

    @FXML
    public void handleNextTurn() {
        GameWorld main = GameWorld.getInstance();
        main.nextTurn();

    }

    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml));
            Pane newLoadedPane = loader.load();
            contentPane.setCenter(newLoadedPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateView() {
        GameWorld main = GameWorld.getInstance();

        if (main.getTurn() == 21) {
            // End game
            setWinningState(getWinningPlayer());

        }

        // Update turn label
        turnLabel.setText("Turn: " + main.getTurn());

        // Update player 1 gulden label, ensuring it's a string
        player1GuldenLabel.setText(String.valueOf(main.getPlayer1().getGulden()));

        // Update player 2 gulden label, ensuring it's a string
        player2GuldenLabel.setText(String.valueOf(main.getPlayer2().getGulden()));

        // Update deck label

        deckLabel.setText("Deck: " + main.getCurrentPlayer().getDeck().getSize() + "/" + " 40");

    }


    private void play(int state) {
        GameWorld main = GameWorld.getInstance();
        switch (state) {
            case 0:

                disableAllButton();
                if (main.getCurrentPlayer().getActiveDeck().isFull()) {
                    main.movePhase(0);

                } else {
                    this.phaseLabel.setText("Phase: Shuffling");
                    this.loadView("ShuffleView.fxml");
                }
                break;
            case 1:
                this.phaseLabel.setText("Phase: Serangan Beruang");
                this.loadView("LadangKu.fxml");
                break;
            case 2:
                // Harvesting phase
                enableAllButton();
                closeOtherBox();
                this.phaseLabel.setText("Phase: Aksi Bebas");
                this.loadView("LadangKu.fxml");
                break;
        }
    }

    @Override
    public void update() {
        // Update the view
        GameWorld main = GameWorld.getInstance();
        int state = main.getState();
        updateView();
        play(state);
    }

    @Override
    public void setupBearAttack(int rowstart, int rowend, int colstart, int colend, int duration) {
        disableAllButton();
        BearAttack bearAttack = new BearAttack(rowstart, rowend, colstart, colend, duration, this);
        bearAttack.start();
    }

    @Override
    public void endBearAttack() {
        offTimer();
        enableAllButton();
    }

    private void initializeBearPopUp()
    {
        Platform.runLater(()->{
            beruangText.setText("BERHASIL DITANGKAP!");
            beruangLabel.setText("Anda berhasil ditangkap Beruang!!");
            beruangImg.setImage(new Image("/img/gui/cry_bear.gif"));
            beruangImg.setFitHeight(150);
            beruangImg.setFitWidth(150);
        });


    }
    public void showBearPopUp() {
        initializeBearPopUp();;
        beruangPopUp.setVisible(true);
        disableAllButton();
    }

    @FXML
    private void handleOKButton() {
        beruangPopUp.setVisible(false);
        GameWorld.getInstance().movePhase(1);
    }

    private int getWinningPlayer() {
        int p1Gulden = GameWorld.getInstance().getPlayer1().getGulden();
        int p2Gulden = GameWorld.getInstance().getPlayer2().getGulden();

        if (p1Gulden >= p2Gulden) {
            return 1;
        }
        else {
            return 2;
        }
    }

    @FXML
    private void setWinningState(int player) {
        sideBar.setPrefWidth(0);
        sideBar.setVisible(false);

        if (player == 1)
        {
            winningImg.setImage(new Image("/img/gui/p1_win.png"));
        }
        else
        {
            winningImg.setImage(new Image("/img/gui/p2_win.png"));
        }

        winningImg.setFitWidth(1400);
        winningImg.setFitHeight(1400);
        winningState.setVisible(true);
    }
}
