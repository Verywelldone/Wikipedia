package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import database.DataBaseConnection;

/* Server side-ul. */
public class ServerMain {
	private static ServerSocket SS;
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	
	public static void main(String[] args) throws IOException, SQLException {

		DataBaseConnection database = new DataBaseConnection();

		database.sparkConfig();
		Dataset<Row> dataset = database.getDataset();

		SS = new ServerSocket(59092);

		LOGGER.info("Waiting for cleint: ");
		Socket socket = SS.accept();
		System.out.println("Client connected");

		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		String clientMessage = bf.readLine();

		String name = clientMessage;

		Dataset<Row> filtered = dataset.filter(dataset.col("name").contains(name));

		if (filtered.count() <= 1) {
			System.out.println(" I found your exact Search:");
			filtered.show((int) filtered.count());

		} else {
			System.out.println(" I found a list of matches");
			filtered.show((int) filtered.count());
		}

	}

}
