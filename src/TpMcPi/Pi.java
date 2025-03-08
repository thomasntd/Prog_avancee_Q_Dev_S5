package src.TpMcPi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Approximates PI using the Monte Carlo method.  Demonstrates
 * use of Callables, Futures, and thread pools.
 */
public class Pi
{
    public static void main(String[] args) throws Exception
    {
        long total=0;
        // 10 workers, 50000 iterations each
        long [] tabTC = {720720, 7207200, 72072000, 720720000, 7207200000L,
                         72072000000L, 720720000000L, 7207200000000L, 72072000000000L};
        for (long count : tabTC) {
            for (int nwork = 12; nwork <= 12; nwork++){
                for (int i = 0;i <= 10; i++){
                    total = new Master().doRun(count, nwork);
                    System.out.println("total from Master = " + total + " i nb : " + i);
                }
            }
        }
    }
}

/**
 * Creates workers to run the Monte Carlo simulation
 * and aggregates the results.
 */
class Master {
    public long doRun(long totalCount, int numWorkers) throws InterruptedException, ExecutionException, IOException {
        long startTime = System.currentTimeMillis();
        List<Callable<Long>> tasks = new ArrayList<>();
        for (int i = 0; i < numWorkers; ++i) {
            tasks.add(new Worker(totalCount/numWorkers));
        }

        ExecutorService exec = Executors.newFixedThreadPool(numWorkers);
        List<Future<Long>> results = exec.invokeAll(tasks);
        long total = 0;

        for (Future<Long> f : results) {
            total += f.get();
        }
        double pi = (4.0 * total) / (totalCount);

        long stopTime = System.currentTimeMillis();

        double error = Math.abs((pi - Math.PI)) / Math.PI;
        long duration = stopTime - startTime;

        System.out.println("\nPi : " + pi);
        System.out.println("Error: " + error + "\n");
        System.out.println("Ntot: " + totalCount);
        System.out.println("Available processors: " + numWorkers);
        System.out.println("Time Duration (ms): " + duration + "\n");
        System.out.println(error + " " + totalCount + " " + numWorkers + " " + duration);

        logToCSV(totalCount, numWorkers, duration, error);

        exec.shutdown();
        return total;
    }

    private void logToCSV(long totalCount, int numWorkers, long duration, double error) throws IOException {
        String fileName = "dataPi_Err.csv";
        File file = new File(fileName);
        boolean fileExists = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            if (!fileExists) {
                writer.write("TotalCount NumWorkers Duration Pi_Error\n");
            }
            writer.write(String.format("%d %d %d %.15f \n", totalCount, numWorkers, duration, error));
        }
    }
}


class Worker implements Callable<Long>
{
    private final long numIterations;
    public Worker(long num)
    {
        this.numIterations = num;
    }

    @Override
    public Long call()
    {
        long circleCount = 0;
        Random prng = new Random ();
        for (int j = 0; j < numIterations; j++)
        {
            double x = prng.nextDouble();
            double y = prng.nextDouble();
            if ((x * x + y * y) < 1)  ++circleCount;
        }
        return circleCount;
    }
}
