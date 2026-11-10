public class Controller {

    private Piece puppet;
    private int stepX;
    private int stepY;

    public Controller(Piece puppet, int stepX, int setY) {
        setPuppet(puppet);
        setStepX(stepX);
        setStepY(stepY);
    }

    public Piece getPuppet() {
        return puppet;
    }

    public void setPuppet(Piece puppet) {
        this.puppet = puppet;
    }

    public int getStepX() {
        return stepX;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public int getStepY() {
        return stepY;
    }

    public void setStepY(int setY) {
        this.stepY = stepY;
    }

    public static void Check(){

    }

    public static void step(Piece piece, Chessboard ch){

    }
}
