import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Game extends JFrame {

    private final int SIZE = 8;
    private final JPanel[][] squares = new JPanel[SIZE][SIZE];

    public Game(ArrayList<ArrayList<Piece>> pieces) {

        setTitle("Sakk");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));
        Color lightColor = new Color(240, 217, 181);
        Color darkColor = new Color(181, 136, 99);
        Chessboard ch = new Chessboard(8,8,pieces);

        ArrayList<ArrayList<Piece>> chessBoard = ch.drawChessBoard(pieces);


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
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (chessBoard.get(r).get(c)!=null){
                            Piece grabbedPiece = chessBoard.get(r).get(c);
                            System.out.println(grabbedPiece.getName());
                        }


                    }
                });

                squares[row][col] = square;
                add(square);
            }
        }

        for (int row=0; row < SIZE; row++){
            for (int col = 0; col < SIZE; col++) {


            }
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