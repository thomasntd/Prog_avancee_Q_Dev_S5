import java.io.*;
import java.net.*;


/** Master is a client. It makes requests to numWorkers.
 *   
 */
public class MasterSocket {
    static int maxServer = 16;
    static final int[] tab_port = {25546,25547,25548,25549,25550,25551,25552,25553,
			                       25554,25555,25556,25557,25558,25559,25560,25561};
    static String[] tab_total_workers = new String[maxServer];
    static final String ip = "127.0.0.1";
    static BufferedReader[] reader = new BufferedReader[maxServer];
    static PrintWriter[] writer = new PrintWriter[maxServer];
    static Socket[] sockets = new Socket[maxServer];
    
    
    public static void main(String[] args) throws Exception {

		// MC parameters
		long [] tabTotalCount = {720720, 7207200, 72072000, 720720000, 7207200000L, 72072000000L}; // total number of throws on a Worker
		long total; // total number of throws inside quarter of disk
		double pi;

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String s; // for bufferRead

		System.out.println("#########################################");
		System.out.println("# Computation of PI by MC method        #");
		System.out.println("#########################################");

		System.out.println("Appyuez sur entrer une fois tous les Workers prets");
		bufferRead.readLine();
		System.out.println("Connexion aux Workers ...");

		//create worker's socket

		for(int i = 0 ; i < maxServer ; i++) {
			sockets[i] = new Socket(ip, tab_port[i]);
			System.out.println("SOCKET = " + sockets[i]);

			reader[i] = new BufferedReader( new InputStreamReader(sockets[i].getInputStream()));
			writer[i] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream())),true);
		}

		long stopTime, startTime;


		for (long totalCount : tabTotalCount) {
			for (int numWorkers = 1; numWorkers <= maxServer; numWorkers++) {
				for (int nbTry = 0 ; nbTry <= 10 ; nbTry++) {
					String message_to_send;
					message_to_send = String.valueOf(totalCount);

					startTime = System.currentTimeMillis();
					// initialize workers
					for(int i = 0 ; i < numWorkers ; i++) {
						writer[i].println(message_to_send);          // send a message to each worker
					}

					//listen to workers's message
					for(int i = 0 ; i < numWorkers ; i++) {
						tab_total_workers[i] = reader[i].readLine();      // read message from server
						System.out.println("Client sent: " + tab_total_workers[i]);
					}

					// compute PI with the result of each workers
					total = 0;
					for(int i = 0 ; i < numWorkers ; i++) {
						total += Long.parseLong(tab_total_workers[i]);
					}

					pi = 4.0 * total / (totalCount * numWorkers);

					stopTime = System.currentTimeMillis();

					long duration = (stopTime - startTime);
					double error = (Math.abs((pi - Math.PI)) / Math.PI);

					System.out.println("\nPi : " + pi );
					System.out.println("Error: " + error +"\n");

					System.out.println("Ntot: " + totalCount * numWorkers );
					System.out.println("Available processors: " + numWorkers);
					System.out.println("Iteration nb : " + nbTry);
					System.out.println("Time Duration (ms): " + duration + "\n");

					System.out.println( error +" "+ totalCount +" "+ numWorkers +" "+ duration);

					MasterSocket.logToCSV(totalCount*numWorkers, numWorkers, duration, error);
				}
			}
	    }

		for(int i = 0 ; i < maxServer ; i++) {
			System.out.println("END");     // Send ending message
			writer[i].println("END") ;
			reader[i].close();
			writer[i].close();
			sockets[i].close();
		}
	}

	private static void logToCSV(long totalCount, int numWorkers, long duration, double error) throws IOException {
		String fileName = "dataMW_SFa" + totalCount/numWorkers + ".csv";
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
