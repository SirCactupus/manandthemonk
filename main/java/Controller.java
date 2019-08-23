package main.java;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller {

    private FXMLLoader loadGame, createNewGame, startScreen, gameScreen;

    private PlayGame playGame;
    @FXML
    private TextField nameTF;
    @FXML
    private GameScreenController gsController;


    @FXML
    private void initialize() throws Exception {
        loadGame = new FXMLLoader(getClass().getResource("/main/fxml/loadGame.fxml"));
        createNewGame = new FXMLLoader(getClass().getResource("/main/fxml/createNewGame.fxml"));
        gameScreen = new FXMLLoader(getClass().getResource("/main/fxml/gameScreen.fxml"));
        startScreen = new FXMLLoader(getClass().getResource("/main/fxml/startScreen.fxml"));

        GameScreenController gs = new GameScreenController();
        gameScreen.setController(gs);
        gsController = gs;
    }

    @FXML
    private void loadGame() throws Exception {
        createNewGame.load();
        startScreen.load();
        gameScreen.load();
        Main.reload(loadGame);
    }

    @FXML
    private void createNewGame() throws Exception {
        Main.reload(createNewGame);
    }

    @FXML
    private void startScreen() throws Exception {
        Main.reload(startScreen);
    }

    @FXML
    private void gameScreen() throws Exception {
        Main.reload(gameScreen);
        gsController.populatePane();
    }

    @FXML
    private void charCreateButton() throws Exception {
        Character character = new Character(nameTF.getText());
        playGame = new PlayGame(character);
        gsController.setPlayGame(playGame);
        gameScreen();
    }
}
