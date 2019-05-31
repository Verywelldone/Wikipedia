package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import database.DataBaseConnection;

/* Server side-ul. */
public class ServerMain {
	private static ServerSocket SS;
	private static final Logger LOGGER = Logger.getAnonymousLogger();

	public static void main(String[] args) throws IOException, SQLException, URISyntaxException {

		/*
		 * @Bogdan: Deschid conexiunea la BD + Configurare spark
		 */ DataBaseConnection database = new DataBaseConnection();
		database.sparkConfig();

		/*
		 * @Bogdan: Instantiez un nou set de date cu tot ce e in tabela People
		 */
		Dataset<Row> dataset = database.getDataset();
		/*
		 * @Bogdan: Deschid socket pe server + accept conexiunea clientului pe portul
		 * respectiv in caz de eroare JVM_BIND, inseamna ca portul e ocupat. Schimbati
		 * portul.
		 */
		SS = new ServerSocket(59093);
		LOGGER.info("Waiting for cleint: ");
		Socket socket = SS.accept();
		System.out.println("Client connected");

		/*
		 * @Bogdan: Primesc de la client numele pe care vrea sa il caute
		 */ InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		String clientMessage = bf.readLine();
		String name = clientMessage;
		Scanner sc = new Scanner(System.in);

		/*
		 * @Bogdan Filtrez setul de date si verific daca sunt una sau mai multe persoane
		 * care coincid cu ceea ce cauta clientul
		 */
		Dataset<Row> filtered = dataset.filter(dataset.col("name").contains(name));

		/*
		 * @Bogdan: Daca e o singura persoana, transmit catre client URI-ul
		 */
		if (filtered.count() <= 1) {
			System.out.println(" I found your exact Search:");
			String stringURI = removeFirstAndLastCharacter(filtered.first().get(1).toString());

			PrintWriter pr = new PrintWriter(socket.getOutputStream());
			pr.println(stringURI);
			pr.flush();

		} else {
			/*
			 * @Bogdan: Daca sunt mai multe, printez datele din DATASET si las clientul sa
			 * aleaga ID-ul persoanei pe care o cauta, cu exactitate.
			 */
			System.out.println(" I found a list of potential matches");
			filtered.show((int) filtered.count());
			System.out.println(" Select exact requested searchs` ID: ");
			String searchedID = sc.next();
			filtered = filtered.filter(filtered.col("id").equalTo(searchedID));
			System.out.println("Your search is: ");

			String stringURI = removeFirstAndLastCharacter(filtered.first().get(1).toString());
			URI uri = new URI(stringURI);
			java.awt.Desktop.getDesktop().browse(uri);
			filtered.show();
		}

		filtered.show((int) filtered.count());
		InputStreamReader input = new InputStreamReader(socket.getInputStream());
		BufferedReader buff = new BufferedReader(input);
		String newClientMessage = bf.readLine();
		String newId = newClientMessage;

		Dataset<Row> newFiltered = dataset.filter(dataset.col("ID").contains(newId));
		/*System.out.println(" I found your exact Search:");
		String stringURI = removeFirstAndLastCharacter(newFiltered.first().get(1).toString());

		PrintWriter pr = new PrintWriter(socket.getOutputStream());
		pr.println(stringURI);
		pr.flush();*/

		sc.close();
	}

	/*
	 * @Bogdan URI-Ul e de forma " < URI > " . Functia doar scoate " <> " din string
	 */
	public static String removeFirstAndLastCharacter(String string) {
		String first = string.substring(1);
		String last = first.substring(0, first.length() - 1);
		return last;
	}

}
