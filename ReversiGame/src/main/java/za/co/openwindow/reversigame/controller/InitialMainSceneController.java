package za.co.openwindow.reversigame.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import za.co.openwindow.reversigame.game.StandardGameState;

public class InitialMainSceneController extends SceneController {
    @FXML
    Button startButton;

    public InitialMainSceneController() {}

    public void initialize() {

        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                StandardGameState standardGame = new StandardGameState();
                sceneSwitcher.setGameState(standardGame);
                sceneSwitcher.switchToScene("MainScene");
            }
        });

    }

}
