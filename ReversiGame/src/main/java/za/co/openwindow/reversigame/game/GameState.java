package za.co.openwindow.reversigame.game;

import static za.co.openwindow.reversigame.game.BoardState.BOARD_SIZE;

public abstract class GameState {

    protected BoardState theBoard;
    protected Player blackPlayer;
    protected Player WhitePlayer;
    protected Player activePlayer;
    protected Point selectedPoint;
    protected String message;
    protected Player thisPlayer;
    protected String labelText;

    public GameState() {
        // This class models the state of the za.co.openwindow.reversigame.game
        // Including the board, active player, selected cell etc.
        // Each player starts with one cell
        theBoard = new BoardState();
        int center1 = BOARD_SIZE / 2 - 1;
        int center2 = BOARD_SIZE / 2;
        theBoard.setCell(new Point(center1, center1), Cell.WHITE);
        theBoard.setCell(new Point(center1, center2), Cell.BLACK);
        theBoard.setCell(new Point(center2, center1), Cell.BLACK);
        theBoard.setCell(new Point(center2, center2), Cell.WHITE);
        blackPlayer = new Player(Cell.BLACK);
        WhitePlayer = new Player(Cell.WHITE);
        activePlayer = blackPlayer;
        selectedPoint = null;

    }
    
    public String getLabelText() {
        return labelText;
    }

    public void doMoveFromView(int row, int col) {
    Point point = null;
        if (point == null && isLegalMove(row, col)) {
            point = new Point(row, col);
        }

        if (point == null && !isLegalMove(row, col)) {
            return;
        }

    if (point != null) {
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
        // Send message to server
        // Message will look like "1;2 2;3"
        String moveMessage = point.getRow() + ";" + point.getColumn();
        message =  moveMessage;
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
    
    public Player otherPlayer(Player player) {
        if (player.equals(blackPlayer)) {
            return WhitePlayer;
        } else {
            return blackPlayer;
        }
    }


    public boolean canFlip (final int cellX, final int cellY, final int directionX, final int directionY, Player activePlayer) {
        int x = cellX + directionX;
        int y = cellY + directionY;
        boolean first = true;
        while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && theBoard.getCell(x, y) != Cell.EMPTY) {
            if (theBoard.getCell(x, y) == activePlayer.colour) {
                return !first;
            }
            first = false;
            x += directionX;
            y += directionY;
        }
        return false;
    }

    public void flip(int cellX, int cellY, int directionX, int directionY, Player activePlayer) {
        if (canFlip(cellX, cellY, directionX, directionY, activePlayer)) {
            int x = cellX + directionX;
            int y = cellY + directionY;
            Point pointTest = null;
            while (x >= 0 && x < BOARD_SIZE && y >= 0 && y < BOARD_SIZE && theBoard.getCell(x, y) != activePlayer.colour) {
                //theBoard.setCell(x, y, Cell.EMPTY);
                pointTest = new Point(x, y);
                if (pointTest != null) {
                    theBoard.doFlipColours(pointTest, activePlayer.getColour());
                }
                x += directionX;
                y += directionY;
            }
        }
    }


    public boolean isLegalMove(int row, int col) {
        return theBoard.getCell(row, col).equals(Cell.EMPTY) && (
                canFlip(row, col, 0, -1, activePlayer)  ||
                        canFlip(row, col, -1, -1, activePlayer) ||
                        canFlip(row, col, -1, 0, activePlayer) ||
                        canFlip(row, col, -1, 1, activePlayer) ||
                        canFlip(row, col, 0, 1, activePlayer) ||
                        canFlip(row, col, 1, 1, activePlayer) ||
                        canFlip(row, col, 1, 0, activePlayer) ||
                        canFlip(row, col, 1, -1, activePlayer)
                );

    }

    public boolean gameIsOver() {
        return !theBoard.boardHasWhiteCells() || !theBoard.boardHasBlackCells() || !boardHasEmptySpace();
    }

    public boolean boardHasEmptySpace() {
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                if (theBoard.getCell(r,c).equals(Cell.EMPTY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public BoardState getTheBoard() {
        return theBoard;
    }

    public Point getSelectedPoint() {
        return selectedPoint;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setThisPlayer(Player thisPlayer) {
        this.thisPlayer = thisPlayer;
    }

}
