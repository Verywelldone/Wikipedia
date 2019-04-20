package Server;

import database.DataBaseConnection;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* Server side-ul. */
public class ServerMain {
	private static ServerSocket SS;

	public static void main(String[] args) throws IOException, SQLException {

		SparkConf conf = new SparkConf().setAppName("WikiProcessing").setMaster("local[*]");
		JavaSparkContext context = new JavaSparkContext(conf);

		DataBaseConnection database = new DataBaseConnection();
		Statement statement = database.createConn();

		
		SS = new ServerSocket(59090);
		Socket socket = SS.accept();
		System.out.println("Client connected");
		
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		String clientMessage = bf.readLine();
				
		String name = clientMessage;
		String search = "SELECT * from wiki.people";
		ResultSet resultSet = statement.executeQuery(search);

	
		while (resultSet.next()) {
			if (resultSet.getString("name").equals(name)) {
				
				PrintWriter pr = new PrintWriter(socket.getOutputStream());
				System.out.println("The name is: ");
				pr.println(resultSet.getString("text"));
				pr.flush();

			}
			
		}
		context.close();
	}
	
}
