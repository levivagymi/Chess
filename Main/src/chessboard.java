import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chessboard {
    private int width, height;
    private boolean whiteTurn;
    private HashMap<String, ArrayList<Integer>> Board;

    public chessboard(int width, int height, boolean whiteTurn, HashMap<String, ArrayList<Piece>Pieces) {
        setWidth(width);
        setHeight(height);
        setWhiteTurn(true);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    public HashMap<String, ArrayList<Integer>> getBoard() {
        return Board;
    }

    public void setBoard(HashMap<String, ArrayList<Integer>> board) {
        Board = board;
    }
}
