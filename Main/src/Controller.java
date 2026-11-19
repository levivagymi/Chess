import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Piece puppet;

    public Controller() {
    }

    public Piece getPuppet() {
        return puppet;
    }

    public void setPuppet(Piece puppet) {
        this.puppet = puppet;
    }
    public boolean isInsufficientMaterial(ArrayList<ArrayList<Piece>> board) {
        ArrayList<Piece> pieces = new ArrayList<>();

        for (ArrayList<Piece> row : board) {
            for (Piece p : row) {
                if (p != null && !p.getName().equals("King")) {
                    pieces.add(p);
                }
            }
        }

        if (pieces.isEmpty()) return true;

        if (pieces.size() == 1) {
            return pieces.getFirst().getName().equals("Bishop")
                    || pieces.getFirst().getName().equals("Knight");
        }

        return false;
    }
    public int Check(Chessboard chessboard) {
        boolean defendingColor = chessboard.isWhiteTurn();
        boolean attackingColor = !defendingColor;

        Piece defendingKing = chessboard.getChessboard().stream()
                .flatMap(List::stream)
                .filter(item -> item != null && "King".equals(item.getName()) && item.isColorWhite() == defendingColor)
                .findFirst()
                .orElse(null);

        if (defendingKing == null) return 0;
        if (isInsufficientMaterial(chessboard.getChessboard())) return 4;
        boolean isKingInCheck = false;

        for (ArrayList<Piece> row : chessboard.getChessboard()) {
            for (Piece p : row) {
                if (p != null && p.isColorWhite() == attackingColor) {
                    ArrayList<List<Integer>> enemySteps = availableSteps(chessboard.getChessboard(), p);

                    for (List<Integer> step : enemySteps) {
                        if (step.getFirst() == defendingKing.getCurrentPositionX()
                                && step.getLast() == defendingKing.getCurrentPositionY()) {
                            isKingInCheck = true;
                            break;
                        }
                    }
                }

                if (isKingInCheck) break;
            }

            if (isKingInCheck) break;
        }

        boolean hasAnyMove = false;

        for (ArrayList<Piece> row : chessboard.getChessboard()) {
            for (Piece p : row) {
                if (p != null && p.isColorWhite() == defendingColor) {
                    ArrayList<List<Integer>> legalSteps = getLegalAvailableSteps(chessboard.getChessboard(), p);

                    if (!legalSteps.isEmpty()) {
                        hasAnyMove = true;
                        break;
                    }
                }
            }

            if (hasAnyMove) break;
        }

        if (!hasAnyMove) {
            return isKingInCheck ? 2 : 3;
        }

        return isKingInCheck ? 1 : 0;
    }

    public Boolean isLegalMove(ArrayList<ArrayList<Piece>> chessBoard, Piece fPiece, List<Integer> targetCoord) {
        boolean playerColor = fPiece.isColorWhite();
        Piece king = chessBoard.stream()
                .flatMap(List::stream)
                .filter(item -> item != null && "King".equals(item.getName()) && item.isColorWhite() == playerColor)
                .findFirst()
                .orElse(null);

        if (king == null) return false;

        int oldX = fPiece.getCurrentPositionX();
        int oldY = fPiece.getCurrentPositionY();

        int targetX = targetCoord.getFirst();
        int targetY = targetCoord.getLast();

        Piece targetPiece = chessBoard.get(targetY - 1).get(targetX - 1);
        Piece enPassantCaptured = null;

        if ("Pawn".equals(fPiece.getName()) &&
                targetPiece == null &&
                oldX != targetX) {

            enPassantCaptured = chessBoard.get(oldY - 1).get(targetX - 1);

            if (enPassantCaptured != null &&
                    "Pawn".equals(enPassantCaptured.getName()) &&
                    enPassantCaptured.isColorWhite() != playerColor &&
                    enPassantCaptured.isEnPassantVulnerable()) {

                chessBoard.get(oldY - 1).set(targetX - 1, null);
            } else {
                enPassantCaptured = null;
            }
        }
        chessBoard.get(oldY - 1).set(oldX - 1, null);
        chessBoard.get(targetY - 1).set(targetX - 1, fPiece);
        fPiece.setCurrentPositionX(targetX);
        fPiece.setCurrentPositionY(targetY);

        boolean inCheck = false;
        for (ArrayList<Piece> row : chessBoard) {
            for (Piece p : row) {
                if (p != null && p.isColorWhite() != playerColor) {
                    ArrayList<List<Integer>> enemySteps = availableSteps(chessBoard, p);
                    if (p.getName().equals("Pawn")){
                        if (p.getCurrentPositionX()-1 == king.getCurrentPositionX() && p.getCurrentPositionY()+1 == king.getCurrentPositionY()
                                || p.getCurrentPositionX()+1 == king.getCurrentPositionX() && p.getCurrentPositionY()+1 == king.getCurrentPositionY()){
                            inCheck = true;
                            break;
                        }
                    }else{
                        for (List<Integer> stepCoord : enemySteps) {
                            if (stepCoord.getFirst() == king.getCurrentPositionX() && stepCoord.getLast() == king.getCurrentPositionY()) {
                                inCheck = true;
                                break;
                            }
                        }
                    }

                }
                if (inCheck) break;
            }
            if (inCheck) break;
        }

        chessBoard.get(targetY - 1).set(targetX - 1, targetPiece);
        chessBoard.get(oldY - 1).set(oldX - 1, fPiece);
        if (enPassantCaptured != null) {
            chessBoard.get(oldY - 1).set(targetX - 1, enPassantCaptured);
        }
        fPiece.setCurrentPositionX(oldX);
        fPiece.setCurrentPositionY(oldY);

        return !inCheck;
    }

    public ArrayList<List<Integer>> getLegalAvailableSteps(ArrayList<ArrayList<Piece>> chessBoard, Piece fPiece) {
        ArrayList<List<Integer>> rawSteps = availableSteps(chessBoard, fPiece);
        ArrayList<List<Integer>> legalSteps = new ArrayList<>();
        for (List<Integer> step : rawSteps) {
            if (isLegalMove(chessBoard, fPiece, step)) {
                legalSteps.add(step);
            }
        }
        return legalSteps;
    }

    public ArrayList<List<Integer>> availableSteps(ArrayList<ArrayList<Piece>> chessBoard, Piece fPiece) {
        ArrayList<List<Integer>> availableStepCoords = new ArrayList<>();
        boolean isWhite = fPiece.isColorWhite();
        int x = fPiece.getCurrentPositionX();
        int y = fPiece.getCurrentPositionY();

        int[][] directions;

        switch (fPiece.getName()) {
            case "Pawn":
                if (x - 1 >= 1 && y - 1 >= 1) {
                    Piece target = chessBoard.get(y - 2).get(x - 2);
                    if (target != null && target.isColorWhite() != isWhite) {
                        availableStepCoords.add(List.of(x - 1, y - 1));
                    }
                }
                if (x + 1 <= 8 && y - 1 >= 1) {
                    Piece target = chessBoard.get(y - 2).get(x);
                    if (target != null && target.isColorWhite() != isWhite) {
                        availableStepCoords.add(List.of(x + 1, y - 1));
                    }
                }

                if (y == 7) {
                    if (y - 1 >= 1 && chessBoard.get(y - 2).get(x - 1) == null) {
                        availableStepCoords.add(List.of(x, y - 1));
                        if (y - 2 >= 1 && chessBoard.get(y - 3).get(x - 1) == null) {
                            availableStepCoords.add(List.of(x, y - 2));
                        }
                    }
                } else {
                    if (y - 1 >= 1 && chessBoard.get(y - 2).get(x - 1) == null) {
                        availableStepCoords.add(List.of(x, y - 1));
                    }
                }
                if (x - 1 >= 1) {
                    Piece target = chessBoard.get(y - 1).get(x - 2);

                    if (target != null &&
                            "Pawn".equals(target.getName()) &&
                            target.isColorWhite() != isWhite &&
                            target.isEnPassantVulnerable()) {

                        availableStepCoords.add(List.of(x - 1, y - 1));
                    }
                }


                if (x + 1 <= 8) {
                    Piece target = chessBoard.get(y - 1).get(x);

                    if (target != null &&
                            "Pawn".equals(target.getName()) &&
                            target.isColorWhite() != isWhite &&
                            target.isEnPassantVulnerable()) {

                        availableStepCoords.add(List.of(x + 1, y - 1));
                    }
                }
                break;

            case "Rook":
                directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
                for (int[] dir : directions) {
                    int dx = dir[0], dy = dir[1];
                    int currentX = x + dx, currentY = y + dy;
                    while (currentX >= 1 && currentX <= 8 && currentY >= 1 && currentY <= 8) {
                        Piece targetPiece = chessBoard.get(currentY - 1).get(currentX - 1);
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
                        {x + 1, y - 2}, {x + 2, y - 1}, {x + 1, y + 2}, {x + 2, y + 1},
                        {x - 1, y - 2}, {x - 2, y - 1}, {x - 1, y + 2}, {x - 2, y + 1}
                };
                for (int[] step : steps) {
                    int stepX = step[0], stepY = step[1];
                    if (stepX >= 1 && stepX <= 8 && stepY >= 1 && stepY <= 8) {
                        Piece targetPiece = chessBoard.get(stepY - 1).get(stepX - 1);
                        if (targetPiece == null || targetPiece.isColorWhite() != isWhite) {
                            availableStepCoords.add(List.of(stepX, stepY));
                        }
                    }
                }
                break;

            case "Bishop":
                directions = new int[][]{{1, 1}, {-1, -1}, {1, -1}, {-1, 1}};
                for (int[] dir : directions) {
                    int dx = dir[0], dy = dir[1];
                    int currentX = x + dx, currentY = y + dy;
                    while (currentX >= 1 && currentX <= 8 && currentY >= 1 && currentY <= 8) {
                        Piece targetPiece = chessBoard.get(currentY - 1).get(currentX - 1);
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

            case "Queen":
                directions = new int[][]{
                        {1, 1}, {-1, -1}, {1, -1}, {-1, 1},
                        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
                };
                for (int[] dir : directions) {
                    int dx = dir[0], dy = dir[1];
                    int currentX = x + dx, currentY = y + dy;
                    while (currentX >= 1 && currentX <= 8 && currentY >= 1 && currentY <= 8) {
                        Piece targetPiece = chessBoard.get(currentY - 1).get(currentX - 1);
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

            case "King":
                directions = new int[][]{
                        {1, 1}, {-1, -1}, {1, -1}, {-1, 1},
                        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
                };
                for (int[] dir : directions) {
                    int targetX = x + dir[0];
                    int targetY = y + dir[1];
                    if (targetX >= 1 && targetX <= 8 && targetY >= 1 && targetY <= 8) {
                        Piece targetPiece = chessBoard.get(targetY - 1).get(targetX - 1);
                        if (targetPiece == null || targetPiece.isColorWhite() != isWhite) {
                            availableStepCoords.add(List.of(targetX, targetY));
                        }
                    }
                }
                if (!fPiece.isHasMoved()) {
                    boolean kingInCheck = false;
                    for (ArrayList<Piece> row : chessBoard) {
                        for (Piece p : row) {
                            if (p != null && p.isColorWhite() != isWhite && !"King".equals(p.getName())) {
                                for (List<Integer> stepCoord : availableSteps(chessBoard, p)) {
                                    if (stepCoord.getFirst() == x && stepCoord.getLast() == y) {
                                        kingInCheck = true;
                                    }
                                }
                            }
                        }
                    }

                    if (!kingInCheck) {
                        Piece rightRook = chessBoard.get(y - 1).get(7);
                        if (rightRook != null && "Rook".equals(rightRook.getName()) && !rightRook.isHasMoved() &&
                                chessBoard.get(y - 1).get(5) == null && chessBoard.get(y - 1).get(6) == null) {

                            boolean pathBlocked = false;
                            for (ArrayList<Piece> row : chessBoard) {
                                for (Piece p : row) {
                                    if (p != null && p.isColorWhite() != isWhite  && !"King".equals(p.getName())) {
                                        for (List<Integer> stepCoord : availableSteps(chessBoard, p)) {
                                            if ((stepCoord.getFirst() == x + 1 && stepCoord.getLast() == y) ||
                                                    (stepCoord.getFirst() == x + 2 && stepCoord.getLast() == y)) {
                                                pathBlocked = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!pathBlocked) {
                                availableStepCoords.add(List.of(x + 2, y));
                            }
                        }

                        Piece leftRook = chessBoard.get(y - 1).get(0);
                        if (leftRook != null && "Rook".equals(leftRook.getName()) && !leftRook.isHasMoved() &&
                                chessBoard.get(y - 1).get(1) == null && chessBoard.get(y - 1).get(2) == null && chessBoard.get(y - 1).get(3) == null) {

                            boolean pathBlocked = false;
                            for (ArrayList<Piece> row : chessBoard) {
                                for (Piece p : row) {
                                    if (p != null && p.isColorWhite() != isWhite && !"King".equals(p.getName())) {
                                        for (List<Integer> stepCoord : availableSteps(chessBoard, p)) {
                                            if ((stepCoord.getFirst() == x - 1 && stepCoord.getLast() == y) ||
                                                    (stepCoord.getFirst() == x - 2 && stepCoord.getLast() == y)) {
                                                pathBlocked = true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!pathBlocked) {
                                availableStepCoords.add(List.of(x - 2, y));
                            }
                        }
                    }
                }
                break;
        }
        return availableStepCoords;
    }

    public ArrayList<ArrayList<Piece>> step(ArrayList<ArrayList<Piece>> chessBoardTarget, List<Integer> target){
        int targetY = target.getFirst();
        int targetX = target.getLast();

        int oldY = getPuppet().getCurrentPositionY();
        int oldX = getPuppet().getCurrentPositionX();
        if ("Pawn".equals(getPuppet().getName()) &&
                oldX != targetX &&
                chessBoardTarget.get(targetY - 1).get(targetX - 1) == null) {

            Piece capturedPawn = chessBoardTarget.get(oldY - 1).get(targetX - 1);

            if (capturedPawn != null &&
                    "Pawn".equals(capturedPawn.getName()) &&
                    capturedPawn.isColorWhite() != getPuppet().isColorWhite() &&
                    capturedPawn.isEnPassantVulnerable()) {

                chessBoardTarget.get(oldY - 1).set(targetX - 1, null);
            }
        }

        for (ArrayList<Piece> row : chessBoardTarget) {
            for (Piece p : row) {
                if (p != null) {
                    p.setEnPassantVulnerable(false);
                }
            }
        }
        if ("King".equals(getPuppet().getName()) && Math.abs(targetX - oldX) == 2) {
            if (targetX == oldX + 2) {
                Piece rook = chessBoardTarget.get(oldY - 1).get(7);
                chessBoardTarget.get(oldY - 1).set(7, null);
                chessBoardTarget.get(oldY - 1).set(5, rook);
                rook.setCurrentPositionX(6);
                rook.setCurrentPositionY(oldY);
                rook.setHasMoved(true);
            } else if (targetX == oldX - 2) {
                Piece rook = chessBoardTarget.get(oldY - 1).get(0);
                chessBoardTarget.get(oldY - 1).set(0, null);
                chessBoardTarget.get(oldY - 1).set(3, rook);
                rook.setCurrentPositionX(4);
                rook.setCurrentPositionY(oldY);
                rook.setHasMoved(true);
            }
        }

        chessBoardTarget.get(oldY - 1).set(oldX - 1, null);
        chessBoardTarget.get(targetY - 1).set(targetX - 1, getPuppet());

        getPuppet().setCurrentPositionX(targetX);
        getPuppet().setCurrentPositionY(targetY);
        getPuppet().setHasMoved(true);
        if ("Pawn".equals(getPuppet().getName()) && targetY == 1) {
            getPuppet().setName("Queen");
        }
        if ("Pawn".equals(getPuppet().getName()) &&
               oldY - targetY == 2) {

            getPuppet().setEnPassantVulnerable(true);
        }
        return chessBoardTarget;
    }
}