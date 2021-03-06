package cloudComputing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import model.People;
import server.ServerMain;

/*
 * @Bogdan
 * 
 * IN CAZ CA VA DA O EROARE LA BAZA DE DATE LEGAT DE TIME ZONE: 
 *  
 *  	DESCHIDETI WORKBENCH-UL SI SETATI ASTA: 
 *  
 *     SET GLOBAL time_zone = '+2:00';
 *     
 *    	LIPITI-O ORIUNDE SI RULATI-O
 *    
 *   
 *    
 * */

public class App {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, SQLException, URISyntaxException {

		BasicConfigurator.configure();

		/*
		 * @Bogdan: suppress la TONA de useless loggs
		 */

		Logger.getLogger("org.apache").setLevel(Level.WARN);
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);

		/*
		 * @Bogdan: deschid socket pentru conexiunea cu serverul
		 */
		Socket socket = new Socket("localhost", 59091);
		Scanner sc = new Scanner(System.in);
//
//		LinkedList<People> ls = Server.ServerMain.peopleList;
//		System.out.println(ls.size());
//		for (People p : ls) {
//			System.out.println(p.getName() + " " + p.getSearched());
//		}
		
		ServerMain.getPeopleFromList();
		
		/*
		 * @Bogdan: transmit catre server numele pe care vreau sa il caut:
		 */ PrintWriter pr = new PrintWriter(socket.getOutputStream());
		System.out.println("The name is: ");
		String msg = sc.nextLine();
		pr.println(msg);
		pr.flush();

		/*
		 * @Bogdan: Primesc de la server URI-ul atribuit numelui cautat.
		 */ InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		String serverURI = bf.readLine();

		System.out.println("Your search-ID is: ");
		String message = sc.nextLine();
		pr.println(message);
		pr.flush();

		/*
		 * @Bogdan: Deschid browser pe URI-ul primit de la server
		 */
		URI uri = new URI(serverURI);
		System.out.println(uri.toString());

//		java.awt.Desktop.getDesktop().browse(uri);

	}
}
