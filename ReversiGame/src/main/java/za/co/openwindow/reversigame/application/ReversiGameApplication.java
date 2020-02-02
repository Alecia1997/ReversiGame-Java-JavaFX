package za.co.openwindow.reversigame.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import za.co.openwindow.reversigame.controller.SceneController;

import java.io.IOException;

public class ReversiGameApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneSwitcher sceneSwitcher = new SceneSwitcher(primaryStage);
        loadSceneFromFile("InitialMainScene", sceneSwitcher);
        loadSceneFromFile("MainScene", sceneSwitcher);
        loadSceneFromFile("BoardScene", sceneSwitcher);

        sceneSwitcher.switchToScene("InitialMainScene");

        primaryStage.setTitle("Reversi Game");
        primaryStage.show();
    }

    private void loadSceneFromFile(String name, SceneSwitcher sceneSwitcher) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/za/co/openwindow/reversigame/view/" + name + ".fxml"));
        Parent root = loader.load();
        SceneController controller = loader.getController();
        Scene scene = new Scene(root);
        controller.setSceneSwitcher(sceneSwitcher);
        controller.setGameState(null);
        sceneSwitcher.addScene(name, scene);
        sceneSwitcher.addController(name, controller);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
