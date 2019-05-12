package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataBaseConnection {

	/* NU UITATI SA MODIFICATI CONEXIUNEA IN FUNCTIE DEBAZA VOASTRA DE DATE */

	/*
	 * public Statement createConn() throws SQLException { String url =
	 * "jdbc:mysql://localhost:3306/wiki"; // ?useSSL=false - security - optional
	 * String user = "root"; // String password="5RrLlQ"; String password = "1234";
	 * 
	 * Connection con = DriverManager.getConnection(url, user, password); Statement
	 * st = con.createStatement();
	 * 
	 * return st; }
	 */
	SparkSession spark;

	public SparkSession getSpark() {
		return spark;
	}

	public void sparkConfig() {

		SparkConf sparkConf = new SparkConf().setAppName("wikiTest").setMaster("local[*]").set("spark.executor.memory",
				"1g");
		this.spark = SparkSession.builder().config(sparkConf).appName("wikiTest").getOrCreate();

	}

	public Dataset<Row> getDataset() {

		String url = "jdbc:mysql://localhost:3306"; // ?useSSL=false - security - optional
		String user = "root";
		String table = "wiki.people";
		String password = "1234";

		Properties properties = new Properties();
		properties.put("password", password);
		properties.put("user", user);

		Dataset<Row> dataset = spark.read().jdbc(url, table, properties);

		return dataset;
	}

}
