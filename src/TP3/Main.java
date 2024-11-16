package src.TP3;

public class Main {
    public static void main(String[] args) {
        BAL bal = new BAL();
        Producer producteur1 = new Producer(bal, "Producer1");
        Producer producteur2 = new Producer(bal, "Producer2");
        Producer producteur3 = new Producer(bal, "Producer3");
        Producer producteur4 = new Producer(bal, "Producer4");
        Consumer consommateur = new Consumer(bal);

        new Thread(producteur1).start();
        new Thread(producteur2).start();
        new Thread(producteur3).start();
        new Thread(producteur4).start();

        new Thread(consommateur).start();
        new Thread(consommateur).start();
        new Thread(consommateur).start();
        new Thread(consommateur).start();
    }
}
