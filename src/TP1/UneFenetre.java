package src.TP1;

import java.awt.*;
import javax.swing.*;

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
