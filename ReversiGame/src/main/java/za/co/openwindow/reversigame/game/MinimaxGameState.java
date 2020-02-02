package za.co.openwindow.reversigame.game;

import java.util.LinkedList;
import java.util.List;

import static za.co.openwindow.reversigame.game.MoveSet.clonedMoveSet;

public class MinimaxGameState extends GameState {

    private int treeDepth;

    public MinimaxGameState() {
        thisPlayer = blackPlayer;
    }

    public int getTreeDepth() {
        return treeDepth;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }



    public List<Point> getPossibleDestinations(BoardState board, Point selection) {
        List<Point> possibleDestinations = new LinkedList<Point>();
        for(int row = selection.getRow() - 2; row <= selection.getRow() + 2; row++) {
            for(int col = selection.getColumn() - 2; col <= selection.getColumn() + 2; col++) {
                if(board.getCell(row, col).equals(Cell.EMPTY)){
                    possibleDestinations.add(new Point(row, col));
                }
            }
        }
        return possibleDestinations;
    }



    @Override
    public void switchPlayers() {
        super.switchPlayers();
        if(!gameIsOver() && !thisPlayer.colour.equals((activePlayer.getColour()))) {
            MoveSet moveSet = minimax(theBoard, activePlayer, getTreeDepth());
            doMoiveFromAI(moveSet.getDestinationPoint().getRow(), moveSet.getDestinationPoint().getColumn());
        }
    }

    public MoveSet minimax(BoardState board, Player player, int depth) {
        List<MoveSet> possibleMoveSets = new LinkedList<MoveSet>();

        //Find all possible moves
        for (int r = BoardState.MIN; r <= BoardState.MAX; r++) {
            for (int c = BoardState.MIN; c <= BoardState.MAX; c++) {
                if(board.getCell(r, c).equals(player.getColour())) {
                    //found a selection point
                    Point selection = new Point(r, c);
                    //find all possible moves from selection
                    List<Point> possibleDestinations = getPossibleDestinations(board, selection);
                    for(Point destinations : possibleDestinations) {
                        MoveSet moveSet = new MoveSet();
                        moveSet.setSelectionPoint(selection);
                        moveSet.setDestinationPoint(destinations);
                        possibleMoveSets.add(moveSet);
                    }
                }
            }
        }



        //If this is a leaf node, return the board's heuristic
        if (depth == 0 || possibleMoveSets.isEmpty()) {
            MoveSet moveSet = new MoveSet();
            moveSet.setHeuristic(board.getHeuristic());
            return moveSet;

        } else { // Otherwise, bubble up a heuristic
            Integer bestHeuristic = null;
            // The AI wants to minimize the heuristic

            if (player == thisPlayer) {
                bestHeuristic = Integer.MIN_VALUE;
            } else {
                bestHeuristic = Integer.MAX_VALUE;
            }
            MoveSet bestMove = null;
            //Consider each possible move

            for(MoveSet moveSet: possibleMoveSets) {
                //Play each possible move on a cloned hypothetical board
                BoardState clonedBoard = board.copy();
               // clonedBoard.doMove(point, activePlayer.getColour());
                clonedMoveSet = minimax(clonedBoard, otherPlayer(player), depth-1);
                if(player == thisPlayer && clonedMoveSet.getHeuristic() > bestHeuristic) {
                    bestHeuristic = clonedMoveSet.getHeuristic();
                    bestMove = moveSet;
                }
                if (player != thisPlayer && clonedMoveSet.getHeuristic() < bestHeuristic) {
                    bestHeuristic = clonedMoveSet.getHeuristic();
                    bestMove = moveSet;
                }
            }

            bestMove.setHeuristic(bestHeuristic);
            return bestMove;
        }
    }


    public void doMoiveFromAI(int row, int col) {
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
        // Switch players
        switchPlayers();
    }



}