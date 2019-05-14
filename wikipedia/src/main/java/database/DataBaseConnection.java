package database;

import java.util.Properties;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataBaseConnection {

	SparkSession spark;

	public SparkSession getSpark() {
		return spark;
	}

	public void sparkConfig() {

		SparkConf sparkConf = new SparkConf().setAppName("wikipedia").setMaster("local[*]").set("spark.executor.memory",
				"1g");
		this.spark = SparkSession.builder().config(sparkConf).appName("wikipedia").getOrCreate();

	}

	public Dataset<Row> getDataset() {

		String url = "jdbc:mysql://localhost:3306"; // ?useSSL=false - security - optional
		String user = "root";
		String table = "wikipedia.people";
		String password = "5RrLlQ";

		Properties properties = new Properties();
		properties.put("password", password);
		properties.put("user", user);

		Dataset<Row> dataset = spark.read().jdbc(url, table, properties);

		return dataset;
	}

}
