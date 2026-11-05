import java.util.ArrayList;
import java.util.HashMap;

public class Chessboard {
    private int width, height;
    private boolean whiteTurn;
    private HashMap<String, ArrayList<ArrayList<Integer>>> Board;

    public Chessboard(int width, int height, boolean whiteTurn, ArrayList<Piece> Pieces) {
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
        this.Board = new HashMap<String, ArrayList<ArrayList<Integer>>>();
        for (int i = 0; i < Pieces.size(); i++) {
            ArrayList<Integer> xy = new ArrayList<>();
            xy.add(Pieces.get(i).getCurrentPositionX());
            xy.add(Pieces.get(i).getCurrentPositionY());
            Board.put(Pieces.get(i).getName());
        }
    }


    public void DrawChessBoard(HashMap<String, ArrayList<Integer>> Board) {
        ArrayList<ArrayList<String>> chessboard = new ArrayList<>();

        for (int i = 0; i < getHeight(); i++) {
            chessboard.add(new ArrayList<>());
            for (int j = 0; j < getWidth(); j++) {
                chessboard.get(i).add(" ");
            }
        }

        String[] pieces = {
                "pawnW", "pawnB",
                "rookW", "rookB",
                "knightW", "knightB",
                "bishopW", "bishopB",
                "queenW", "queenB",
                "kingW", "kingB"
        };

        for (String piece : pieces) {
            for (int i = 0; i < getBoard().get(piece).size(); i++) {
                int x = getBoard().get(piece).get(i)
                int y =
                        chessboard.get(y).set(x, piece);
            }



        }

        for (ArrayList<String> row : chessboard) {
            System.out.println(row);
        }
    }
}
