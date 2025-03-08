package src.TpMcPi;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class RunTests {
    static int nbTotalThrowLow = 720720;
    static int nbTotalThrowMid = 7207200;
    static int nbTotalThrowHig = 72072000;
    static int nbMaxProc = 16;
    /*

    public static void main(String[] args) throws IOException {

        for(int nbProc = 1; nbProc <=nbMaxProc; nbProc++){
            long[] timeValues = new long[10];
            double[] errorValues = new double[10];
            for(int i = 0; i < 10; i++){

                timeValues[i] = timeDuration;
                errorValues[i] = error;

                System.out.println("Iteration " + nbProc + " finished in : " + timeDuration + "ms");

            }

            System.out.println(Arrays.toString(timeValues));

            Arrays.sort(timeValues);
            Arrays.sort(errorValues);

            System.out.println(Arrays.toString(timeValues));

            CsvOutput outputFile = new CsvOutput("./output_assignment102.csv");
            outputFile.write(errorValues[4], nbTotalThrowMid, nbProc, timeValues[4]);
        }
    }

    private int[] launch_A102(int nbProc, int nbThrow) throws IOException {
        PiMonteCarlo PiVal = new PiMonteCarlo(nbTotalThrowMid/nbProc);
        long startTime = System.currentTimeMillis();
        double value = PiVal.getPi(nbProc);
        long stopTime = System.currentTimeMillis();

        double error = Math.abs((value - Math.PI) / Math.PI);
        long timeDuration = stopTime - startTime;

        return ...
    }

         */
}
