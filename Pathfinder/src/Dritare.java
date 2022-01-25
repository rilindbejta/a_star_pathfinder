import javax.swing.*;
import java.awt.*;


public class Dritare extends JFrame {


    public Dritare(){
        add(Visualizimi.getinstanca());
        setTitle("Gjetja e rruges me te shkurte - A*");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(750,750));
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);

    }


    public static void main(String[] args) {
        new Dritare();
    }
}
