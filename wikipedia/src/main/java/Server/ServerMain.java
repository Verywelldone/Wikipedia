package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

	@SuppressWarnings({ "unlikely-arg-type", "unchecked" })
	public static void main(String[] args) throws IOException, SQLException {
		/*
		 * SparkConf conf = new
		 * SparkConf().setAppName("WikiProcessing").setMaster("local[*]");
		 * JavaSparkContext context = new JavaSparkContext(conf);
		 */

		DataBaseConnection database = new DataBaseConnection();
		// Statement statement = database.createConn();
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
//		String search = "SELECT * from wiki.people";


		Dataset<Row> filtered = dataset.filter(dataset.col("name").contains(name));

		if (filtered.count() <= 1) {
			System.out.println(" I found your exact Search:");
			/* PrintWriter pr = new PrintWriter(socket.getOutputStream()); */
			filtered.show((int) filtered.count());
			//pr.flush();
		} else {
			System.out.println(" I found a list of matches");
			filtered.show((int) filtered.count());
		}
		/*
		 * PrintWriter pr = new PrintWriter(socket.getOutputStream());
		 * System.out.println("Client: The name is: "); pr.println(); pr.flush();
		 */

//		dataset.foreach((ForeachFunction<Row>) row -> {
//			if(row.mkString("name").equals(clientMessage)) {
//				System.out.println(row);
//			}
//		});
//		// ResultSet resultSet = statement.executeQuery(search);

		/*
		 * while (resultSet.next()) { if (resultSet.getString("name").equals(name)) {
		 * 
		 * String uriString = "http://dbpedia.org/resource/Digby_Morrell"; URI uri =
		 * null; try { uri = new URI(uriString); } catch (URISyntaxException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 * java.awt.Desktop.getDesktop().browse(uri);
		 * 
		 * 
		 * 
		 * }
		 * 
		 * }
		 * 
		 * context.close();
		 */
	}

}
