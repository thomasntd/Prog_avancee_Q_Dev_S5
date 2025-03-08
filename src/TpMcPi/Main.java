package src.TpMcPi;

public class Main {
    public static void main(String[] args) {
        Cible cible = new Cible(1000000);
        cible.run_tirage();

        cible.wait_tirages();

        float result = cible.get_pi_mc();
        System.out.println(result);
    }
}
