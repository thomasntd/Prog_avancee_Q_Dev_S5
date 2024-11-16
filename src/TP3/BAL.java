package src.TP3;

import java.util.concurrent.ArrayBlockingQueue;

public class BAL {
    private final ArrayBlockingQueue<String> lettres = new ArrayBlockingQueue<>(1);

    public void deposer(String nouvelleLettre) throws InterruptedException {
        lettres.put(nouvelleLettre);
    }

    public String retirer() throws InterruptedException {
        return lettres.take();
    }
}
