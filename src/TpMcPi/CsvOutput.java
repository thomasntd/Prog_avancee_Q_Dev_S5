package src.TpMcPi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CsvOutput {
    private FileWriter csvFile;
    public CsvOutput(String pathToCsvFile) {
        try {
            csvFile = new FileWriter(pathToCsvFile, true);

            if (new File(pathToCsvFile).length() == 0) {
                csvFile.write("TotalCount NumWorkers Duration Pi_Error\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String buildLine(long nTotal, int nProc, long temps, Double error) {
        return nTotal + " " + nProc + " " + temps + " " + error + "\n";
    }

    public void write(long nTotal, int nProc, long temps, Double error) throws IOException {
        // data sous forme "erreur;ntot;nproc;temps\n"
        csvFile.write(CsvOutput.buildLine(nTotal, nProc, temps, error));
        csvFile.close();
    }
}
