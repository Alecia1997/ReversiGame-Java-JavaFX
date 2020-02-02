package za.co.openwindow.reversigame.application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import za.co.openwindow.reversigame.controller.SceneController;
import za.co.openwindow.reversigame.game.GameState;

import java.util.HashMap;

public class SceneSwitcher {

    private Stage stage;
    private HashMap<String, Scene> scenes;
    private HashMap<String, SceneController> controllers;

    public SceneSwitcher(Stage stage) {
        this.stage = stage;
        scenes = new HashMap<String, Scene>();
        controllers = new HashMap<String, SceneController>();
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public void addController(String name, SceneController controller) {
        controllers.put(name, controller);
    }

    public Stage getStage() {
        return stage;
    }

    public void switchToScene(String name) {
        stage.setScene(scenes.get(name));
        controllers.get(name).refresh();
    }
    
    public SceneController getSceneController(String name) {
        return controllers.get(name);
    }
    
    public void setGameState(GameState gameState) {
        for (SceneController controller: controllers.values()) {
            controller.setGameState(gameState);
        }
    }
}
