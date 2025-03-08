package src.TpMcPi;

public class GenerePoints implements Runnable {
    private Cible cible;
    private int pts_hors_cible;


    public GenerePoints(Cible cible) {
        this.cible = cible;
    }


    public void run() {
        for (int i = 0; i < 1000; i++) {
            double x = Math.random();
            double y = Math.random();

            if (x*x + y*y > 1){
                pts_hors_cible++;
            }
        }

        cible.ajouter_hors_cible(pts_hors_cible);
    }
}
