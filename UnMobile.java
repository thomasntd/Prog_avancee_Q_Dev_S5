import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

class UnMobile extends JPanel implements Runnable {
	static Semaphore leurSemaphore = new Semaphore(2);
    int saLargeur, saHauteur, sonDebDessin = 0;
    final int sonPas = 10;
    int sonTemps;
    final int sonCote=40;
    
    UnMobile(int telleLargeur, int telleHauteur) {
		super();
		saLargeur = telleLargeur;
		saHauteur = telleHauteur;
		Random random = new Random();
		sonTemps = random.nextInt(10, 50);
		setSize(telleLargeur, telleHauteur);
    }

    public void run() {
		while(true){
			for (sonDebDessin=0; sonDebDessin + sonCote < saLargeur / 3; sonDebDessin += sonPas) {
				repaint();
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp)
				{telleExcp.printStackTrace();}
			}

			while(!leurSemaphore.tryAcquire()){
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp){
					telleExcp.printStackTrace();
				}
			}
			for (sonDebDessin = sonDebDessin; sonDebDessin + sonCote < (saLargeur / 3) * 2; sonDebDessin += sonPas) {
				repaint();
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp)
				{telleExcp.printStackTrace();}
			}
			leurSemaphore.release();
			for (sonDebDessin = sonDebDessin; sonDebDessin + sonCote < saLargeur; sonDebDessin += sonPas) {
				repaint();
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp)
				{telleExcp.printStackTrace();}
			}

			for (sonDebDessin= sonDebDessin; sonDebDessin > (saLargeur / 3) * 2; sonDebDessin -= sonPas) {
				repaint();
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp)
				{telleExcp.printStackTrace();}
			}

			while(!leurSemaphore.tryAcquire()){
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp){
					telleExcp.printStackTrace();
				}
			}
			for (sonDebDessin= sonDebDessin; sonDebDessin > saLargeur / 3; sonDebDessin -= sonPas) {
				repaint();
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp)
				{telleExcp.printStackTrace();}
			}
			leurSemaphore.release();
			for (sonDebDessin= sonDebDessin; sonDebDessin > 0; sonDebDessin -= sonPas) {
				repaint();
				try{Thread.sleep(sonTemps);}
				catch (InterruptedException telleExcp)
				{telleExcp.printStackTrace();}
			}
		}
	}

    public void paintComponent(Graphics telCG) {
		super.paintComponent(telCG);
		telCG.fillRect(sonDebDessin, 0, sonCote, sonCote);
    }
}
