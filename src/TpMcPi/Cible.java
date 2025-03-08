package src.TpMcPi;

public class Cible {
    private final int nb_1000_tirages;
    private int nb_result_recus;
    private int total_hors_cible;

    public Cible(int nb_1000_tirages) {
        this.nb_1000_tirages = nb_1000_tirages;
    }

    public void run_tirage() {
        for (int i = 0; i < nb_1000_tirages; i++) {
            GenerePoints points = new GenerePoints(this);
            Thread tirage = new Thread(points);
            tirage.start();
        }
    }

    public synchronized void ajouter_hors_cible(int hors_cible) {
        this.total_hors_cible += hors_cible;
        this.nb_result_recus++;
        if (nb_result_recus % 10000 == 0) {
            System.out.println((int) (((float) nb_result_recus/nb_1000_tirages) * 100) + "% resultats reçus ");
        }
    }

    public synchronized float get_pi_mc(){
        return ((float) ((nb_1000_tirages * 1000) - total_hors_cible) / (nb_1000_tirages *1000)) * 4;
    }

    private synchronized int getNb_result_recus(){
        return this.nb_result_recus;
    }

    public void wait_tirages() {
        while (getNb_result_recus() < nb_1000_tirages);
    }
}
