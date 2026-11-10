import java.util.ArrayList;

public class Piece {
    private int currentPositionX;
    private int currentPositionY;
    private String name;
    private boolean color;

    public Piece( int currentPositionX, int currentPositionY, String name, boolean color) {
        setCurrentPositionX(currentPositionX);
        setCurrentPositionY(currentPositionY);
        setName(name);
        setColor(color);
    }


    public int getCurrentPositionX() {
        return currentPositionX;
    }

    public void setCurrentPositionX(int currentPositionX) {
        this.currentPositionX = currentPositionX;
    }

    public int getCurrentPositionY() {
        return currentPositionY;
    }

    public void setCurrentPositionY(int currentPositionY) {
        this.currentPositionY = currentPositionY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isColorWhite() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
