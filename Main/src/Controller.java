import java.util.ArrayList;
import java.util.List;

public class Controller{
    private Piece puppet;
    private Chessboard setChessboard;
    public Controller() {

    }

    public Piece getPuppet() {
        return puppet;
    }

    public void setPuppet(Piece puppet) {
        this.puppet = puppet;
    }

    public void setChessboard(Chessboard ch) {
        this.setChessboard = ch;
    }

    public static void Check(){

    }

    public void availableSteps(ArrayList<ArrayList<Piece>> chessBoard){
        ArrayList<List<Integer>> availableStepCoords = new ArrayList<>();
        boolean isWhite = puppet.isColorWhite();
        switch (puppet.getName()){
            case "Pawn":
                    if (puppet.getCurrentPositionY() == 7){
                        availableStepCoords.add(List.of(puppet.getCurrentPositionX(),puppet.getCurrentPositionY()+2));
                    }else {
                        availableStepCoords.add(List.of(puppet.getCurrentPositionX(),puppet.getCurrentPositionY()+1));
                    }
                break;


            case "Rook":

                int[][] directions = {
                        {1, 0},   // jobbra
                        {-1, 0},  // balra
                        {0, 1},   // le
                        {0, -1}   // fel
                };

                for (int[] dir : directions) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int currentX = puppet.getCurrentPositionX() + dx;
                    int currentY = puppet.getCurrentPositionY() + dy;

                    while (currentX >= 0 && currentX < 8 && currentY >= 0 && currentY < 8) {
                        Piece targetPiece = chessBoard.get(currentX).get(currentY);
                        if (targetPiece == null) {
                            availableStepCoords.add(List.of(currentX, currentY));
                        } else {
                            if (targetPiece.isColorWhite() != isWhite) {
                                availableStepCoords.add(List.of(currentX, currentY));
                            }
                            break;
                        }
                        currentX += dx;
                        currentY += dy;
                    }
                }
                break;
            case "Knight":

                int[][] steps = {
                        {puppet.getCurrentPositionX()+1, puppet.getCurrentPositionY()-2},   // jobbrafelnagy
                        {puppet.getCurrentPositionX()+2, puppet.getCurrentPositionY()-1},   // jobbrafelkicsi
                        {puppet.getCurrentPositionX()+1, puppet.getCurrentPositionY()+2},  // jobraleNagy
                        {puppet.getCurrentPositionX()+2, puppet.getCurrentPositionY()+1},   //jobraleKicsi
                        {puppet.getCurrentPositionX()-1, puppet.getCurrentPositionY()-2},   //balrafelNagy
                        {puppet.getCurrentPositionX()-2, puppet.getCurrentPositionY()-1},   //balrafelKicsi
                        {puppet.getCurrentPositionX()-1, puppet.getCurrentPositionY()+2},   //balraleNagy
                        {puppet.getCurrentPositionX()-2, puppet.getCurrentPositionY()+1}    //balraleKicsi

                };
                for (int[] step : steps){
                    if (step[0] > 0 && step[0] <=8 && step[1] > 0 && step[1] <= 8){
                        Piece targetPiece = chessBoard.get(step[0]).get(step[1]);
                        if (targetPiece == null){
                            availableStepCoords.add(List.of(step[0],step[1]));
                        }else {
                            if (targetPiece.isColorWhite() != isWhite) {
                                availableStepCoords.add(List.of(step[0],step[1]));
                            }
                        }
                    }
                }
                break;
            case "Bishop":
                break;
            case "Queen":
                break;
            case "King":
                break;

        }
    }

    public void availableAttacks(ArrayList<ArrayList<Piece>> chessBoard){

    }
}
