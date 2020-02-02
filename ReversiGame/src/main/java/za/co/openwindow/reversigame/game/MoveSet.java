package za.co.openwindow.reversigame.game;

public class MoveSet {
    public static MoveSet clonedMoveSet;
    private Point selectionPoint;
    private Point destinationPoint;
    private int heuristic;
    
    public Point getSelectionPoint() {
        return selectionPoint;
    }
    
    public void setSelectionPoint(Point selectionPoint) {
        this.selectionPoint = selectionPoint;
    }
    
    public Point getDestinationPoint() {
        return destinationPoint;
    }
    
    public void setDestinationPoint(Point point) {
        this.destinationPoint = point;
    }
    
    public int getHeuristic() {
        return heuristic;
    }
    
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }
}
