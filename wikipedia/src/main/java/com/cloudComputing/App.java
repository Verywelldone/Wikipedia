package com.cloudComputing;

import database.DataBaseConnection;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

;


public class App {

    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        BasicConfigurator.configure();

        // suppress la TONA de useless loggs
        Logger.getLogger("org.apache").setLevel(Level.WARN);
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);


        SparkConf conf = new SparkConf().setAppName("WikiProcessing").setMaster("local[*]");
        JavaSparkContext context = new JavaSparkContext(conf);

        DataBaseConnection database = new DataBaseConnection();

        Statement statement = null;
        try {
            statement = database.createConn();
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        String search = "SELECT * from wiki.people";
        ResultSet resultSet = statement.executeQuery(search);


        while(resultSet.next()){
            for(int i = 1; i<resultSet.getMetaData().getColumnCount()+1;i++){
                System.out.println(resultSet.getMetaData().getColumnName(i) + "=" + resultSet.getObject(i));
            }
            System.out.println(" \n ");
        }


////        get("/hello", (req, res) -> "Hello World");
//
//        /*
//            Local configuration.
//             [*] -> Use all availabale calls for this program ( cred ca merge pe toate threadurile posibile )
//        */
//        List<Double> inputData = new ArrayList<>();
//
//        inputData.add(35.5);
//        inputData.add(12.54322);
//        inputData.add(90.32);
//        inputData.add(20.35);
//        inputData.add(412.42);
//        inputData.add(652.234);
//        inputData.add(9814.123);
//
//
//

//
//
//        /*
//         * parallelize transforma setul de date in RDD-uri
//         * RDD - Resilient Distributed Datasets ( structura fundamentala de date din Spark) */
//        JavaRDD<Double> inputRDD = context.parallelize(inputData);
//        System.out.println();
//
//        inputRDD.foreach((VoidFunction<Double>) data -> System.out.println(data+" "));
//
//
//        // aplica foreach pe obiectul inputRDD. Ca parametrul transmit o functie care afieaza elementele.
////        inputRDD.foreach((VoidFunction<Double>) aDouble -> System.out.print(aDouble + "  "));
//
//        // citeste datele dintr-un fisier CSV
////        JavaRDD<String> csvFile = context.textFile("C:\\Users\\Bogdan\\Desktop\\test\\csv.csv");
//
////        csvFile.foreach((VoidFunction<String>) s -> System.out.println(s));
//
//        System.out.println("TEST CRISTINA");
//

        {
            context.close();
        }
    }
}
