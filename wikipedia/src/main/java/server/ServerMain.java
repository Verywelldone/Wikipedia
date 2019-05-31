package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Logger;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RefineryUtilities;

import database.DataBaseConnection;
import model.People;

/* Server side-ul. */
public class ServerMain {
	private static ServerSocket SS;
	private static final Logger LOGGER = Logger.getAnonymousLogger();
	public static LinkedList<People> peopleList = new LinkedList<>();

	
	public static DefaultPieDataset chartDataset = new DefaultPieDataset();

	public static LinkedList<People> getPeopleList() {
		return peopleList;
	}

	public static void setPeopleList(LinkedList<People> peopleList) {
		ServerMain.peopleList = peopleList;
	}

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
		SS = new ServerSocket(59091);
		LOGGER.info("Waiting for client: ");
		while (true) {

			Socket socket = SS.accept();
			System.out.println("Client connected");

			/*
			 * @Bogdan: Primesc de la client numele pe care vrea sa il caute
			 */
			InputStreamReader in = new InputStreamReader(socket.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			String clientMessage = bf.readLine();
			if (clientMessage.equals("chart")) {
				showChart();
			}
			System.out.println(clientMessage);
			Scanner sc = new Scanner(System.in);

			/*
			 * @Bogdan Filtrez setul de date si verific daca sunt una sau mai multe persoane
			 * care coincid cu ceea ce cauta clientul
			 */
			Dataset<Row> filtered = dataset.filter(dataset.col("name").contains(clientMessage));

			/*
			 * @Bogdan: Daca e o singura persoana, transmit catre client URI-ul
			 */
			if (filtered.count() < 1) {
				PrintWriter pr = new PrintWriter(socket.getOutputStream());
				pr.println("No results found");
				pr.flush();
			} else if (filtered.count() == 1) {

				System.out.println(" I found your exact Search:");
				String peopleID = filtered.first().get(0).toString();
				String peopleURI = removeFirstAndLastCharacter(filtered.first().get(1).toString());
				String peopleName = filtered.first().get(2).toString();
				createNewPeople(peopleID, peopleURI, peopleName);

				PrintWriter pr = new PrintWriter(socket.getOutputStream());
				pr.println(peopleURI);
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

				String peopleID = filtered.first().get(0).toString();
				String peopleURI = removeFirstAndLastCharacter(filtered.first().get(1).toString());
				String peopleName = filtered.first().get(2).toString();

				createNewPeople(peopleID, peopleURI, peopleName);

				PrintWriter pr = new PrintWriter(socket.getOutputStream());
				pr.println(peopleURI);
				pr.flush();

			}
			sc.close();
		}

	}

	/*
	 * @Bogdan URI-Ul e de forma " < URI > " . Functia doar scoate " <> " din string
	 */
	public static String removeFirstAndLastCharacter(String string) {
		String first = string.substring(1);
		String last = first.substring(0, first.length() - 1);
		return last;
	}

	public static void createNewPeople(String id, String URI, String name) {
		getPeopleFromList();
		for (People people : peopleList) {
			if (people.getId().equals(id)) {
				people.incrementSearch();
				return;
			}
		}

		People people = new People();
		people.setId(id);
		people.setName(name);
		people.setUrl(URI);
		peopleList.add(people);

	}

	public static void getPeopleFromList() {
		for (People people : peopleList) {
			chartDataset.setValue(people.getName(), people.getSearched());
		}

	}

	public static void showChart() {
		getPeopleFromList();
		PieChart pc = new PieChart("People List",chartDataset);

		System.out.println(chartDataset.getItemCount() + "    ITEM COUNT ");

	
		pc.setSize(560, 367);
		RefineryUtilities.centerFrameOnScreen(pc);
		pc.setVisible(true);
	}

}
