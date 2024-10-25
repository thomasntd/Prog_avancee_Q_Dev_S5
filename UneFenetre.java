import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonListener;

class UneFenetre extends JFrame {
    private final int LARG=1200, HAUT=800;
    private Thread laTache;
    
    public UneFenetre() {
        super("Le mobile");
        GridLayout saGrille = new GridLayout(50, 1);
        setLayout(saGrille);
        for (int i = 0; i < 50; i++) {
            UnMobile sonMobile = new UnMobile(LARG, HAUT);
            add(sonMobile);
            laTache = new Thread(sonMobile);
            laTache.start();
            setVisible(true);
        }

        setSize(LARG, HAUT);
        setVisible(true);
    }
}
