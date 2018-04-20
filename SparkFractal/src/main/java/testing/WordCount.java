package testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import scala.Tuple2;


public class WordCount {

    public static void main(String[] args) {
        List<Tuple2<String, Double>> list = new ArrayList<Tuple2<String, Double>>();


        for (int jj = 0; jj < 61; jj++) {
            double q = -10 + 1 * ((double) jj) / 3;
            Tuple2<String, Double> t1 = new Tuple2<String, Double>(String.valueOf(jj), q);
            list.add(t1);
        }


        int j = 1;
        int k = 2;

		System.out.println(list);

    }
}