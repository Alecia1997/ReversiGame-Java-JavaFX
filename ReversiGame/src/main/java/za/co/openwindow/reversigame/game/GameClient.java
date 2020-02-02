package za.co.openwindow.reversigame.game;

import za.co.openwindow.reversigame.controller.SceneController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class GameClient implements Runnable {

    private String hostName;
    private NetworkGameState networkGameState;
    private SceneController sceneController;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public NetworkGameState getNetworkGameState() {
        return networkGameState;
    }

    public void setNetworkGameState(NetworkGameState networkGameState) {
        this.networkGameState = networkGameState;
    }

    public SceneController getSceneController() {
        return sceneController;
    }

    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    @Override
    public void run() {
        int portNumber = 9933;

        try {
            Socket socket = new Socket(hostName, portNumber);
            PrintWriter out =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
            String userInput;
            // Set mode and colour
            String mode = in.readLine();
            String colour = in.readLine();
            if (colour.equals("BLACK")) {
                networkGameState.setThisPlayer(new Player(Cell.BLACK));
                System.out.println("BLACK");
            } else {
                networkGameState.setThisPlayer(new Player(Cell.WHITE));
                System.out.println("WHITE");
            }

            // Send and read data
            while (true) {
                if (mode.equals("SENDING")) {
                    System.out.println("SENDING");
                    if (networkGameState.getMessage() != null) {
                        out.println(networkGameState.getMessage());
                        networkGameState.setMessage(null);
                        mode = "LISTENING";
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (mode.equals("LISTENING")) {
                    // Read data
                    System.out.println("LISTENING");
                    userInput = in.readLine();
                    StringTokenizer tokenizer = new StringTokenizer(userInput, " ");

                    String moveMessage = tokenizer.nextToken();

                    StringTokenizer moveTokenizer = new StringTokenizer(moveMessage, ";");

                    // Process data
                    int moveRow = Integer.parseInt(moveTokenizer.nextToken());
                    int moveCol = Integer.parseInt(moveTokenizer.nextToken());

                    // Update the board
                    networkGameState.doMoveFromServer(moveRow, moveCol);
                    sceneController.refresh();

                    mode = "SENDING";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
