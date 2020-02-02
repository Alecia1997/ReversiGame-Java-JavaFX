package za.co.openwindow.reversigame.game;

public class NetworkGameState extends GameState {


    public void doMoveFromServer(int row, int col) {
        Point point = null;
        if (point == null && isLegalMove(row, col)) {
            point = new Point(row, col);
        }

        if (point == null && !isLegalMove(row, col)) {
            return;
        }

        if (point != null) {
            MoveSet moveSet = new MoveSet();
            moveSet.setSelectionPoint(point);
            moveSet.setDestinationPoint(point);

            theBoard.doMove(point, activePlayer.getColour());
            flip(row, col, 0, -1, activePlayer);
            flip(row, col, -1, -1, activePlayer);
            flip(row, col, -1, 0, activePlayer);
            flip(row, col, -1, 1, activePlayer);
            flip(row, col, 0, 1, activePlayer);
            flip(row, col, 1, 1, activePlayer);
            flip(row, col, 1, 0, activePlayer);
            flip(row, col, 1, -1, activePlayer);
        }
        // Switch players
        switchPlayers();
    }

    public void switchPlayers() {
        activePlayer = otherPlayer(activePlayer);
        if (!gameIsOver()) {
            labelText = activePlayer.getColour().toString() + " Player's turn.";
        } else {
            labelText = "Game Over. ";
            if (theBoard.countBlackCells() > theBoard.countWhiteCells()) {
                labelText += "BLACK Player wins.";
            } else if (theBoard.countWhiteCells() > theBoard.countBlackCells()) {
                labelText += "WHITE Player wins.";
            } else {
                labelText += "Game is a draw.";
            }
        }
    }

}