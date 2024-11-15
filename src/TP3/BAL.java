package src.TP3;

public class BAL {
    private String lettre;
    private boolean available = false;

    public synchronized void deposer(String nouvelleLettre) throws InterruptedException {
        while (available) {
            wait();
        }
        lettre = nouvelleLettre;
        available = true;
        System.out.println("Lettre dispo dans la BAL : " + lettre);
        notifyAll();
    }

    public synchronized String retirer() throws InterruptedException {
        while (!available) {
            wait();
        }
        String lettreRecuperee = lettre;
        lettre = null;
        available = false;
        System.out.println("Lettre retirée de la BAL : " + lettreRecuperee);
        notifyAll();
        return lettreRecuperee;
    }
}
