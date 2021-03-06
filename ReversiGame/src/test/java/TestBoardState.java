import za.co.openwindow.reversigame.game.BoardState;
import za.co.openwindow.reversigame.game.Cell;
import za.co.openwindow.reversigame.game.Point;
import org.junit.Assert;
import org.junit.Test;

public class TestBoardState {


    @Test
    public void testGetCell() {
        BoardState board1 = new BoardState();
        board1.setCell(new Point(3,4), Cell.WHITE);

        Assert.assertEquals(board1.getCell(new Point(3,4)), Cell.WHITE);
        Assert.assertEquals(board1.getCell(new Point(1,0)), Cell.EMPTY);
        Assert.assertEquals(board1.getCell(new Point(-1,0)), Cell.OFFBOARD);
    }

    @Test
    public void testEquals() {
        BoardState board1 = new BoardState();
        BoardState board2 = new BoardState();

        Assert.assertTrue(board1.equals(board2));

        board2.setCell(new Point(2,2), Cell.WHITE);

        Assert.assertFalse(board1.equals(board2));
    }

    @Test
    public void testCopyIsDeep() {

        BoardState board1 = new BoardState();
        BoardState board2 = board1.copy();

        Assert.assertTrue(board1.equals(board2));

        board2.setCell(new Point(2,2), Cell.WHITE);

        Assert.assertFalse(board1.equals(board2));
    }

}
