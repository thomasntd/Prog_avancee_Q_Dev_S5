package src.TP3;

public class Producer implements Runnable {
    private BAL bal;
    private String lettreAEnvoyer;

    public Producer(BAL bal, String lettreAEnvoyer) {
        this.bal = bal;
        this.lettreAEnvoyer = lettreAEnvoyer;
    }

    public void run(){
        try {
            bal.deposer(lettreAEnvoyer);
            System.out.println("Lettre envoyée : " + lettreAEnvoyer);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lettreAEnvoyer = null;
    }
}
