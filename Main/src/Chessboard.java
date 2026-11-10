import java.util.ArrayList;
import java.util.HashMap;

public class Chessboard {
    private int width, height;
    private boolean whiteTurn;
    private HashMap<String, ArrayList<Integer>> boardMap;

    public Chessboard(int width, int height, ArrayList<ArrayList<Piece>> pieces) {
        setWidth(width);
        setHeight(height);
        setWhiteTurn(true);
        setBoard(pieces);
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
        return boardMap;
    }

    public void setBoard(ArrayList<ArrayList<Piece>> pieces) {
        this.boardMap = new HashMap<>();
        for (ArrayList<Piece> allPiece : pieces) {
            for (Piece coloredPiece : allPiece) {
                ArrayList<Integer> xy = boardMap.computeIfAbsent(coloredPiece.getName(), k -> new ArrayList<>());
                xy.add(coloredPiece.getCurrentPositionX());
                xy.add(coloredPiece.getCurrentPositionY());
            }
        }
    }

    public void drawChessBoard() {
        ArrayList<ArrayList<String>> chessboardGrid = new ArrayList<>();
        for (int i = 0; i < getHeight(); i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < getWidth(); j++) {
                row.add(".");
            }
            chessboardGrid.add(row);
        }

        String[] pieces = {
                "Pawn",
                "Rook",
                "Knight",
                "Bishop",
                "Queen",
                "King"
        };

        for (String piece : pieces) {
                ArrayList<Integer> coords = boardMap.get(piece);
                if (coords != null) {
                    for (int j = 0; j < coords.size(); j+=2) {
                        int x = coords.get(j);
                        int y = coords.get(j + 1);
                        if (y >= 0 && y <= getHeight() && x >= 0 && x <= getWidth()) {

                            String symbol = piece.substring(0, 1).toUpperCase();
                            if (piece.endsWith("B")) {
                                symbol = symbol.toLowerCase();
                            }

                            chessboardGrid.get(y-1).set(x-1, symbol);
                        }

                }
            }
        }

        for (ArrayList<String> row : chessboardGrid) {
            System.out.println(row);
        }
    }
}