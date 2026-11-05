import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chessboard {
    private int width, height;
    private boolean whiteTurn;
    private HashMap<String, ArrayList<Integer>> Board;

    public chessboard(int width, int height, boolean whiteTurn, ArrayList<Piece> Pieces) {
        setWidth(width);
        setHeight(height);
        setWhiteTurn(true);
        setBoard(Pieces);
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

    public void setBoard(ArrayList<Piece> Pieces) {
        this.Board = new HashMap<String, ArrayList<Integer>>();
        for (int i = 0; i < Pieces.size(); i++) {
            ArrayList<Integer> xy = new ArrayList<>();
            xy.add(Pieces.get(i).getCurrentPositionX());
            xy.add(Pieces.get(i).getCurrentPositionY());
            Board.put(Pieces.get(i).getName(),xy);
        }
    }
}
