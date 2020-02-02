package za.co.openwindow.reversigame.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import za.co.openwindow.reversigame.game.*;

public class MainSceneController extends SceneController {
    @FXML TextArea hostNameTextArea;
    @FXML Button twoPlayerButton;
    @FXML Button networkedButton;
    @FXML Button aiButton;
    @FXML ComboBox treeDepthComboBox;



    public MainSceneController() {}

    public void initialize() {
        treeDepthComboBox.getItems().add(1);
        treeDepthComboBox.getItems().add(2);
        treeDepthComboBox.getItems().add(3);
        treeDepthComboBox.getItems().add(4);
        treeDepthComboBox.setValue(1);

        
        twoPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                twoPlayerButton.setStyle("valid");
                StandardGameState standardGame = new StandardGameState();
                sceneSwitcher.setGameState(standardGame);
                sceneSwitcher.switchToScene("BoardScene");
            }
        });

        networkedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                NetworkGameState networkGameState = new NetworkGameState();
                sceneSwitcher.setGameState(networkGameState);
                GameClient gameClient = new GameClient();
                String hostName = hostNameTextArea.getText();
                gameClient.setHostName(hostName);
                gameClient.setNetworkGameState(networkGameState);
                gameClient.setSceneController(sceneSwitcher.getSceneController("BoardScene"));

                new Thread(gameClient).start();
                sceneSwitcher.switchToScene("BoardScene");

            }
        });

        aiButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MinimaxGameState minimaxGame = new MinimaxGameState();
                int treeDepth = Integer.parseInt(treeDepthComboBox.getValue().toString());
                minimaxGame.setTreeDepth(treeDepth);
                sceneSwitcher.setGameState(minimaxGame);
                sceneSwitcher.switchToScene("BoardScene");

            }
        });
        
    }
}
