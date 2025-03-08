// Estimate the value of Pi using Monte-Carlo Method, using parallel program
package src.TpMcPi;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

class PiMonteCarlo {
    AtomicLong nAtomSuccess;
    long nThrows;
    double value;

    class MonteCarlo implements Runnable {
        @Override
        public void run() {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1)
                nAtomSuccess.incrementAndGet();
        }
    }

    public PiMonteCarlo(long i) {
        this.nAtomSuccess = new AtomicLong(0);
        this.nThrows = i;
        this.value = 0;
    }

    public double getPi(int nProcessors) {
        ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
        for (int i = 1; i <= nThrows; i++) {
            Runnable worker = new MonteCarlo();
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        value = 4.0 * nAtomSuccess.get() / nThrows;
        return value;
    }
}

class Assignment102 {
    public static void main(String[] args) throws IOException {
        long [] tabNT = {720720, 7207200, 72072000};
        for (long nThrow : tabNT) {
            for (int nProc = 1; nProc <= 16; nProc++) {
                for (int i = 0; i <= 10; i++) {
                    PiMonteCarlo PiVal = new PiMonteCarlo(nThrow*nProc);
                    long startTime = System.currentTimeMillis();
                    double value = PiVal.getPi(nProc);
                    long stopTime = System.currentTimeMillis();

                    double error = Math.abs((value - Math.PI) / Math.PI);
                    long timeDuration = stopTime - startTime;

                    System.out.println("----------\nApprox value: " + value);
                    System.out.println("Difference to exact value of pi: " + (value - Math.PI));
                    System.out.println("Available processors: " + nProc + " iteration nb : " + i);
                    System.out.println("Time Duration: " + timeDuration + "ms");

                    CsvOutput outputFile = new CsvOutput("./dataA102_SFa" + nThrow +".csv");
                    outputFile.write(nThrow*nProc, nProc, timeDuration, error);
                }
            }
        }
    }
}
