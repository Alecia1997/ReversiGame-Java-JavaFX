package za.co.openwindow.reversigame.controller;

import za.co.openwindow.reversigame.application.SceneSwitcher;
import za.co.openwindow.reversigame.game.GameState;

public abstract class SceneController {

    protected SceneSwitcher sceneSwitcher;
    protected GameState gameState;

    public SceneSwitcher getSceneSwitcher() {
        return sceneSwitcher;
    }

    public void setSceneSwitcher(SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void refresh() {}
}
