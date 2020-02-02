package za.co.openwindow.reversigame.game;

import java.util.HashMap;
import java.util.Map;

public class BoardState {

    public static final int MIN = 0;
    public static final int MAX = 7;
    public static final int BOARD_SIZE = 8;



    private Map<Point, Cell> cells;

    public BoardState() {
        cells = new HashMap<Point, Cell>();
        for (int r = MIN; r <= MAX; r++) {
            for (int c = MIN; c <= MAX; c++) {
                setCell(new Point(r,c), Cell.EMPTY);
            }
        }
    }


    // Makes a new board with a copied board state (copy constructor)
    public BoardState(Map<Point, Cell> copyCells) {
        cells = new HashMap<Point, Cell>();
        for (Point copyPoint : copyCells.keySet()) {
            Point point = copyPoint.copy();
            Cell cell = copyCells.get(copyPoint);
            cells.put(point, cell);
        }
    }


    public void doMove(Point point, Cell colour) {
        setCell (point.getRow(), point.getColumn(), colour);
    }





    public void doFlipColours(Point point, Cell colour) {
        setCell(point.getRow(), point.getColumn(), colour);
    }

    
    public Cell getCell(Point point) {
        // Return OFFBOARD if location is off board
        if (point.getRow() < MIN || point.getColumn() < MIN) return Cell.OFFBOARD;
        if (point.getRow() > MAX || point.getColumn() > MAX) return Cell.OFFBOARD;

        return cells.get(point);
    }

    public Cell getCell(int row, int column) {
        return getCell(new Point(row,column));
    }

    public void setCell(Point point, Cell cell) {
        cells.put(point, cell);
    }

    public void setCell(int row, int column, Cell cell) {
        setCell(new Point(row, column), cell);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardState boardState = (BoardState) o;

        for (int r = MIN; r <= MAX; r++) {
            for (int c = MIN; c <= MAX; c++) {
                if (!getCell(new Point(r,c)).equals(boardState.getCell(new Point(r,c)))) {
                    return false;
                }
            }
        }

        return true;
    }
    
    public int getHeuristic() {
        if (!boardHasBlackCells()) {
            // Blue wins
            return -1000;
        } else if (!boardHasWhiteCells()) {
            // Red wins
            return +1000;
        } else {
            return countBlackCells() - countWhiteCells();
        }
    }
    
    public boolean boardHasWhiteCells() {
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                if (getCell(r,c).equals(Cell.WHITE)) {
                    return true;
                }
            }
        }
        return false;
    }

    
    public boolean boardHasBlackCells() {
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                if (getCell(r,c).equals(Cell.BLACK)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public int countBlackCells() {
        int count = 0;
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                if (getCell(r,c).equals(Cell.BLACK)) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public int countWhiteCells() {
        int count = 0;
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                if (getCell(r,c).equals(Cell.WHITE)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int hashCode() {
        return cells != null ? cells.hashCode() : 0;
    }

    public BoardState copy() {
        // Copy is deep
        // Creates an equal, but different, board
        return new BoardState(cells);
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        for (int r = MIN; r <= MAX; r++) {
            for (int c = MIN; c <= MAX; c++) {
                if (getCell(r, c).equals(Cell.BLACK)) {
                    builder.append("B");
                } else if (getCell(r, c).equals(Cell.WHITE)) {
                    builder.append("W");
                } else {
                    builder.append("#");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
