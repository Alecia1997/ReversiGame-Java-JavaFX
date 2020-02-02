package za.co.openwindow.reversigame.game;

public class StandardGameState extends GameState {
    
    public StandardGameState() {
        thisPlayer = blackPlayer;
    }
    
    public void switchPlayers() {
        super.switchPlayers();
        if (thisPlayer == blackPlayer) {
            thisPlayer = WhitePlayer;
        } else {
            thisPlayer = blackPlayer;
        }
    }

}
