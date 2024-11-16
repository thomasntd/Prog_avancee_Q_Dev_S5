package src.TP3;

public class Consumer implements Runnable{
    private BAL bal;
    private String lettreRecue;

    public Consumer(BAL bal) {
        this.bal = bal;
    }

    public void run() {
        try {
            lettreRecue = bal.retirer();
            System.out.println("Lettre reçue : " + lettreRecue);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
