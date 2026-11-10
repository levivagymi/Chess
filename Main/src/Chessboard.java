import java.util.ArrayList;
import java.util.HashMap;

public class Chessboard {
    private int width, height;
    private boolean whiteTurn;

    public Chessboard(int width, int height, ArrayList<ArrayList<Piece>> pieces) {
        setWidth(width);
        setHeight(height);
        setWhiteTurn(true);
        drawChessBoard(pieces);
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




    public ArrayList<ArrayList<Piece>> drawChessBoard(ArrayList<ArrayList<Piece>> pieces) {

        ArrayList<ArrayList<Piece>> chessboardGrid = new ArrayList<>();
        for (int i = 0; i < getHeight(); i++) {
            ArrayList<Piece> row = new ArrayList<>();
            for (int j = 0; j < getWidth(); j++) {
                row.add(null);
            }
            chessboardGrid.add(row);
        }


        for (ArrayList<Piece> Allpiece : pieces) {
            for (Piece piece:Allpiece){
                int x = piece.getCurrentPositionX();
                int y = piece.getCurrentPositionY();
                if (y >= 0 && y <= getHeight() && x >= 0 && x <= getWidth()) {
                    chessboardGrid.get(y-1).set(x-1, piece);
                }
                }
            }



        return chessboardGrid;
    }
}