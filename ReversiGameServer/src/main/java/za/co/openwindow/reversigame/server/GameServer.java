package za.co.openwindow.reversigame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class GameServer implements Runnable {
    
    @Override
    public void run() {
    
        try {
            System.out.println("This server's IP address is " + Inet4Address.getLocalHost().getHostAddress());
            System.out.println("This server's name is " + Inet4Address.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    
        int portNumber = 9933;
        try {
            ServerSocket serverSocket =
                    new ServerSocket(portNumber);
            Socket blackSocket = serverSocket.accept();
            PrintWriter blackOut = new PrintWriter(blackSocket.getOutputStream(), true);
            BufferedReader blackIn = new BufferedReader(new InputStreamReader(blackSocket.getInputStream()));
            System.out.println("Black Player connected");
            Socket whiteSocket = serverSocket.accept();
            PrintWriter whiteOut = new PrintWriter(whiteSocket.getOutputStream(), true);
            BufferedReader whiteIn = new BufferedReader(new InputStreamReader(whiteSocket.getInputStream()));
            System.out.println("White Player connected");

            blackOut.println("SENDING");
            blackOut.println("BLACK");
            whiteOut.println("LISTENING");
            whiteOut.println("WHITE");
            
            String inputLine;
            BufferedReader activeIn = blackIn;
            PrintWriter activeOut = whiteOut;
            
            while ((inputLine = activeIn.readLine()) != null) {
                activeOut.println(inputLine);
                System.out.println("Message Received: " + inputLine);
                if (activeIn.equals(blackIn)) {
                    activeIn = whiteIn;
                    activeOut = blackOut;
                } else {
                    activeIn = blackIn;
                    activeOut = whiteOut;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
    
}
