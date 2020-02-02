package za.co.openwindow.reversigame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import za.co.openwindow.reversigame.game.BoardState;
import za.co.openwindow.reversigame.game.Cell;
import za.co.openwindow.reversigame.game.Point;
import java.util.HashMap;

public class BoardSceneController extends SceneController {

    private HashMap<Point, Pane> panes;

    @FXML GridPane boardGrid;
    @FXML Label messageLabel;


    public void initialize() {
        panes = new HashMap<Point, Pane>();
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("cell");
                boardGrid.add(pane, c, r);
                panes.put(new Point(r, c), pane);
                final int finalR = r;
                final int finalC = c;
                pane.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        doClick(finalR, finalC);
                    }
                });
            }
        }
    }


    public void doClick(int row, int col) {
        gameState.doMoveFromView(row, col);
        refresh();
    }


    @Override
    public void refresh() {
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                messageLabel.setText(gameState.getLabelText());

                Pane pane = panes.get(new Point(r, c));
                Cell cell = gameState.getTheBoard().getCell(r, c);
                pane.getStyleClass().remove("black");
                pane.getStyleClass().remove("white");
                pane.getStyleClass().remove("valid");

                if (cell.equals(Cell.BLACK)) {
                    pane.getStyleClass().add("black");
                }
                if (cell.equals(Cell.WHITE)) {
                    pane.getStyleClass().add("white");
                }
                if (cell.equals(Cell.VALID)) {
                    pane.getStyleClass().add("valid");
                }

            }
        }
    }
}
