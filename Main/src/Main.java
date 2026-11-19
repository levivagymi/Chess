import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<Piece>> pieces = InitPieces();

        SwingUtilities.invokeLater(() -> {
            new Game(pieces).setVisible(true);
        });
    }

    public static ArrayList<ArrayList<Piece>> InitPieces(){
        ArrayList<ArrayList<Piece>> pieces = new ArrayList<>(2);
        pieces.add(new ArrayList<>());
        pieces.add(new ArrayList<>());
        try{
            File f = new File("pieces.txt");

            BufferedReader bf = new BufferedReader(new FileReader(f));
            bf.readLine();
            String sor = bf.readLine();

            while (sor!=null){
                StringTokenizer st = new StringTokenizer(sor, ";");
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());

                String name = st.nextToken();
                boolean color = Objects.equals(st.nextToken(), "White");
                if (color){
                    pieces.getFirst().add(new Piece(x,y,name,true));
                }else{
                    pieces.getLast().add(new Piece(x,y,name,false));
                }
                sor= bf.readLine();
            }
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
        return pieces;
    }
}