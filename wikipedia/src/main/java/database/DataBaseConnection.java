package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

//		public DatabaseConnection() throws SQLException{}
//


    /* NU UITATI SA MODIFICATI CONEXIUNEA IN FUNCTIE DEBAZA VOASTRA DE DATE*/

    public Statement createConn() throws SQLException {
        String url ="jdbc:mysql://localhost:3306/wikipedia?useSSL=false"; //?useSSL=false - security - optional
        String user="root";
        String password="5RrLlQ";
        //String password="1234";

        Connection con= DriverManager.getConnection(url, user, password);
        Statement st = con.createStatement();

        return st;

    }

}
