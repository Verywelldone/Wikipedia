package com.cloudComputing;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.ArrayList;
import java.util.List;


public class App {

    public static void main(String[] args) {
        BasicConfigurator.configure();
//        get("/hello", (req, res) -> "Hello World");

        /*
            Local configuration.
             [*] -> Use all availabale calls for this program ( cred ca merge pe toate threadurile posibile )
        */
        List<Double> inputData = new ArrayList<>();

        inputData.add(35.5);
        inputData.add(12.54322);
        inputData.add(90.32);
        inputData.add(20.35);
        inputData.add(412.42);
        inputData.add(652.234);
        inputData.add(9814.123);

        // surpress la TONA de useless loggs
        Logger.getLogger("org.apache").setLevel(Level.WARN);
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);

        SparkConf conf = new SparkConf().setAppName("WikiProcessing").setMaster("local[*]");

        JavaSparkContext context = new JavaSparkContext(conf);

        /*
         * parallelize transforma setul de date in RDD-uri
         * RDD - Resilient Distributed Datasets ( structura fundamentala de date din Spark) */
        JavaRDD<Double> inputRDD = context.parallelize(inputData);
        System.out.println();

        // aplica foreach pe obiectul inputRDD. Ca parametrul transmit o functie care afieaza elementele.
        inputRDD.foreach((VoidFunction<Double>) aDouble -> System.out.print(aDouble + "  "));


        context.close();
    }
}
