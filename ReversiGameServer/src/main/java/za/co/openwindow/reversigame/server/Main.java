package za.co.openwindow.reversigame.server;

public class Main {
    
    public static void main(String[] args) {
        GameServer gameServer = new GameServer();
        new Thread(gameServer).start();
    }
    
}
