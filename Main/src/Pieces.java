import java.util.ArrayList;

public class Pieces {
    private ArrayList<ArrayList<Integer>> step;
    private int currentPositionX;
    private int currentPositionY;
    private String name;
    private boolean color;

    public Pieces(ArrayList<ArrayList<Integer>> step, int currentPositionX, int currentPositionY, String name, boolean color) {
        setStep(step);
        setCurrentPositionX(currentPositionX);
        setCurrentPositionY(currentPositionY);
        setName(name);
        setColor(color);
    }

    public ArrayList<ArrayList<Integer>> getStep() {
        return step;
    }

    public void setStep(ArrayList<ArrayList<Integer>> step) {
        this.step = step;
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

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
