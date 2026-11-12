import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


public class Game extends JFrame {
    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<>();
    private Controller controller;
    private final int SIZE = 8;
    private final JPanel[][] squares = new JPanel[SIZE][SIZE];
    private final Color lightColor = new Color(240, 217, 181);
    private final Color darkColor = new Color(181, 136, 99);
    private Chessboard ch = new Chessboard(8,8,pieces);
    private ArrayList<ArrayList<Piece>> chessBoard = new ArrayList<>();

    public Game(ArrayList<ArrayList<Piece>> pieces) {
        this.pieces = pieces;
        chessBoard = ch.drawChessBoard(pieces);
        setTitle("Sakk");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JPanel square = new JPanel();

                if (chessBoard.get(row).get(col) != null){
                    square.add(puppet(chessBoard.get(row).get(col).getName(),chessBoard.get(row).get(col).isColorWhite()));
                }
                if ((row + col) % 2 == 0) {
                    square.setBackground(lightColor);
                } else {
                    square.setBackground(darkColor);
                }
                final int r = row;
                final int c = col;
                MouseAdapter ms = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (chessBoard.get(r).get(c)!=null){
                            controller=new Controller();
                            controller.setPuppet(chessBoard.get(r).get(c));
                            drawAvailableSteps(controller.availableSteps(chessBoard));


                        }
                    }
                };
                square.removeMouseListener(ms);
                square.addMouseListener(ms);

                squares[row][col] = square;
                add(square);
            }
        }





    }
    public void refreshBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JPanel square = squares[i][j];
                square.removeAll();


                if ((i + j) % 2 == 0) {
                    square.setBackground(lightColor);
                } else {
                    square.setBackground(darkColor);
                }
                final int r = i;
                final int c = j;

                if (chessBoard.get(i).get(j) != null) {
                    square.add(puppet(chessBoard.get(i).get(j).getName(), chessBoard.get(i).get(j).isColorWhite()));
                    MouseAdapter ms = new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (chessBoard.get(r).get(c)!=null){
                                controller=new Controller();
                                controller.setPuppet(chessBoard.get(r).get(c));
                                drawAvailableSteps(controller.availableSteps(chessBoard));


                            }
                        }
                    };
                    square.removeMouseListener(ms);
                    square.addMouseListener(ms);

                }



                square.revalidate();
                square.repaint();
            }
        }
    }

    public void drawAvailableSteps(ArrayList<List<Integer>> availableSteps) {
        refreshBoard();

        for (List<Integer> availableStep : availableSteps) {
            int r = availableStep.get(1) - 1;
            int c = availableStep.get(0) - 1;

            JPanel targetSquare = this.squares[r][c];
            targetSquare.setLayout(new GridBagLayout());


            JLabel dotLabel = new JLabel("●");
            dotLabel.setFont(new Font("Serif", Font.BOLD, 20));
            dotLabel.setForeground(Color.DARK_GRAY);
            targetSquare.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                        chessBoard = controller.step(chessBoard, List.of(r+1,c+1));
                        refreshBoard();



                }
            });
            targetSquare.add(dotLabel);
            targetSquare.revalidate();
            targetSquare.repaint();
        }
    }
    public JLabel puppet(String name, boolean colorWhite){
        JLabel label = new JLabel("");

        switch (name) {
            case "Pawn":
                label = new JLabel("♟");
                break;

            case "Rook":
                label = new JLabel("♜");
                break;
            case "Knight":
                label = new JLabel("♞");
                break;
            case "Bishop":
                label = new JLabel("♝");
                break;
            case "Queen":
                label = new JLabel("♛");
                break;
            case "King":
                label = new JLabel("♚");
                break;
        }
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setForeground(colorWhite?Color.WHITE:Color.BLACK);
        return label;
    }



}