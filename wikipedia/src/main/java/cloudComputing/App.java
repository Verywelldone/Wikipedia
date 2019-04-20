package cloudComputing;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;



public class App {


	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, SQLException {

		BasicConfigurator.configure();

		// suppress la TONA de useless loggs
		Logger.getLogger("org.apache").setLevel(Level.WARN);
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);

		Socket socket = new Socket("localhost", 59090);
		Scanner sc = new Scanner(System.in);
		
		PrintWriter pr = new PrintWriter(socket.getOutputStream());
		System.out.println("The name is: ");
		String msg = sc.nextLine();
		pr.println(msg);
		pr.flush();
		
		
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		String serverMessage = bf.readLine();
		
		System.out.println(serverMessage);
		
		
		
		
		
	}
}
