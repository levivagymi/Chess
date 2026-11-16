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

    public int Check(Chessboard chessboard) {
        int isCheckOrCheckMate = 0;
        Piece enemyKingPuppet = chessboard.getChessboard().stream().flatMap(List::stream).filter(item-> "King".equals(item.getName()) && item.isColorWhite() != chessboard.isWhiteTurn()).findFirst().orElse(null);
        for (int i = 0; i < chessboard.getChessboard().size(); i++) {
            for (int j = 0; j < chessboard.getChessboard().get(i).size(); j++) {
                setPuppet(chessboard.getChessboard().get(i).get(j));
                ArrayList<List<Integer>> availableStepsforCurrentPuppet = availableSteps(chessboard.getChessboard(),chessboard.getChessboard().get(i).get(j));
                for (int k = 0; k <  availableStepsforCurrentPuppet.size(); k++) {
                    if (availableStepsforCurrentPuppet.get(k).getFirst() == enemyKingPuppet.getCurrentPositionX() && availableStepsforCurrentPuppet.get(k).getLast() == enemyKingPuppet.getCurrentPositionY()){
                        isCheckOrCheckMate=1;

                    }
                }
            }
        }
        return isCheckOrCheckMate;
    }


    public ArrayList<ArrayList<Piece>> step(ArrayList<ArrayList<Piece>> chessBoardTarget, List<Integer> target, Chessboard ch){
        int targetY = target.getFirst();
        int targetX = target.getLast();


        int oldY = puppet.getCurrentPositionY();
        int oldX = puppet.getCurrentPositionX();

        chessBoardTarget.get(oldY - 1).set(oldX - 1, null);
        chessBoardTarget.get(targetY - 1).set(targetX - 1, puppet);

        puppet.setCurrentPositionX(targetX);
        puppet.setCurrentPositionY(targetY);
        ch.setWhiteTurn(!ch.isWhiteTurn());
        return chessBoardTarget;
    }

    public ArrayList<List<Integer>> availableSteps(ArrayList<ArrayList<Piece>> chessBoard) {
        ArrayList<List<Integer>> availableStepCoords = new ArrayList<>();
        boolean isWhite = puppet.isColorWhite();
        int x = puppet.getCurrentPositionX();
        int y = puppet.getCurrentPositionY();


        int[][] directions;

        switch (puppet.getName()) {
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
                break;

            case "Rook":
                directions = new int[][]{
                        {1, 0},
                        {-1, 0},
                        {0, 1},
                        {0, -1}
                };

                for (int[] dir : directions) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int currentX = x + dx;
                    int currentY = y + dy;

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
                        {x + 1, y - 2},
                        {x + 2, y - 1},
                        {x + 1, y + 2},
                        {x + 2, y + 1},
                        {x - 1, y - 2},
                        {x - 2, y - 1},
                        {x - 1, y + 2},
                        {x - 2, y + 1}
                };
                for (int[] step : steps) {
                    int stepX = step[0];
                    int stepY = step[1];
                    if (stepX >= 1 && stepX <= 8 && stepY >= 1 && stepY <= 8) {
                        Piece targetPiece = chessBoard.get(stepY - 1).get(stepX - 1);
                        if (targetPiece == null) {
                            availableStepCoords.add(List.of(stepX, stepY));
                        } else {
                            if (targetPiece.isColorWhite() != isWhite) {
                                availableStepCoords.add(List.of(stepX, stepY));
                            }
                        }
                    }
                }
                break;

            case "Bishop":
                directions = new int[][]{
                        {1, 1},
                        {-1, -1},
                        {1, -1},
                        {-1, 1}
                };

                for (int[] dir : directions) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int currentX = x + dx;
                    int currentY = y + dy;

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
                        {1, 1},
                        {-1, -1},
                        {1, -1},
                        {-1, 1},
                        {1, 0},
                        {-1, 0},
                        {0, 1},
                        {0, -1}
                };

                for (int[] dir : directions) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int currentX = x + dx;
                    int currentY = y + dy;

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
                break;

        }
        return availableStepCoords;
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
                break;

            case "Rook":
                directions = new int[][]{
                        {1, 0},
                        {-1, 0},
                        {0, 1},
                        {0, -1}
                };

                for (int[] dir : directions) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int currentX = x + dx;
                    int currentY = y + dy;

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
                        {x + 1, y - 2},
                        {x + 2, y - 1},
                        {x + 1, y + 2},
                        {x + 2, y + 1},
                        {x - 1, y - 2},
                        {x - 2, y - 1},
                        {x - 1, y + 2},
                        {x - 2, y + 1}
                };
                for (int[] step : steps) {
                    int stepX = step[0];
                    int stepY = step[1];
                    if (stepX >= 1 && stepX <= 8 && stepY >= 1 && stepY <= 8) {
                        Piece targetPiece = chessBoard.get(stepY - 1).get(stepX - 1);
                        if (targetPiece == null) {
                            availableStepCoords.add(List.of(stepX, stepY));
                        } else {
                            if (targetPiece.isColorWhite() != isWhite) {
                                availableStepCoords.add(List.of(stepX, stepY));
                            }
                        }
                    }
                }
                break;

            case "Bishop":
                directions = new int[][]{
                        {1, 1},
                        {-1, -1},
                        {1, -1},
                        {-1, 1}
                };

                for (int[] dir : directions) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int currentX = x + dx;
                    int currentY = y + dy;

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
                        {1, 1},
                        {-1, -1},
                        {1, -1},
                        {-1, 1},
                        {1, 0},
                        {-1, 0},
                        {0, 1},
                        {0, -1}
                };

                for (int[] dir : directions) {
                    int dx = dir[0];
                    int dy = dir[1];
                    int currentX = x + dx;
                    int currentY = y + dy;

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
                break;

        }
        return availableStepCoords;
    }
}