import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;
import scala.tools.cmd.gen.AnyVals;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import scala.Tuple2;

public class distributedFractal {
	public static void distributedCalculate(int N, int[] offset, int[] column) {
		int N_3 = N / 10;   //Set the number of center nodes
		int num_q = 61;
		int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.

		int[] centerNode = Fractal.GenRandomOrder(N, N_3);
		double[][] VV = new double[num_q][networkDiameter];
		double[] UU = new double[networkDiameter];


		try {
			FileWriter writer_VV_spark = new FileWriter("SparkInput/VVTemp.text");

			for (int i = 0; i < centerNode.length; i++) {   // for sample nodes, calculate UU and VV
				if (i % 100 == 0) {
					System.out.printf("Progess: %.3f%%\n", (((double) i) / centerNode.length) * 100);
				}

				int[] number;
				number = BFS.NumCount(centerNode[i], offset, column, N);  // calculate the number of nodes within radius 'r' of centerNode[i]

				if (number.length <= networkDiameter) {            // the case when the depth of centerNode[i] is less than network diameter
					for (int j = 0; j < num_q; j++) {
						double q = -10 + 1 * ((double) j) / 3;    // Set the values of q
						for (int k = 0; k < number.length; k++) {      // calculate VV , the contribution of each center node is 1/N_3
//						if (q == 1) {
//							VV[j][k] = VV[j][k] + number[k] * Math.log(number[k]) / N_3;
//						} else {
//							VV[j][k] = VV[j][k] + Math.pow(number[k], q - 1) / N_3;
//						}
//							Tuple2<String, vvPairValue> vvPair = new Tuple2<String, vvPairValue>(new vvPairValue(j,k,q,number[k]).getIndex(), new vvPairValue(j,k,q,number[k]));
//							vvList.add(vvPair);

							writer_VV_spark.write(Integer.toString(j) + " " + Integer.toString(k) + "," + Double.toString(q) + "," + Integer.toString(number[k]) + "\n");
						}
					}
				}
 					else {                             // the case when the depth of centerNode[i] is greater than network diameter
					double[][] VVTmp = new double[num_q][number.length];

					for (int j = 0; j < num_q; j++) {
						double q = -10 + 1 * ((double) j) / 3;
						for (int k = 0; k < networkDiameter; k++) {
							if (q == 1) {
								VVTmp[j][k] = VV[j][k] + number[k] * Math.log(number[k]) / N_3;
							} else {
								VVTmp[j][k] = VV[j][k] + Math.pow(number[k], q - 1) / N_3;
							}
						}
						for (int k = networkDiameter; k < number.length; k++) {
							if (q == 1) {
								VVTmp[j][k] = number[k] * Math.log(number[k]) / N_3;
							} else {
								VVTmp[j][k] = Math.pow(number[k], q - 1) / N_3;
							}
						}
					}
					VV = VVTmp;
					networkDiameter = number.length;
				}
			}
			writer_VV_spark.close();

		} catch (IOException iox) {
			System.out.println("FailedWriting" + "VVtemp.text");
		}

		System.out.println("SucceedWriting" + "VVtemp.text");


		Logger.getLogger("org").setLevel(Level.ERROR);
		SparkConf conf = new SparkConf().setAppName("Calculation").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);

		JavaRDD<String> lines = sc.textFile("SparkInput/VVTemp.text");

		JavaPairRDD<String, entity.PairQAndNumk> qnumkPairRDD = lines.mapToPair(
				line -> new Tuple2<>(line.split(",")[0],
						new entity.PairQAndNumk(Double.parseDouble((line.split(",")[1])), Integer.valueOf(line.split(",")[2]))));

		//TODO: should N_3 be added directly as a number?

		//If case 1: q == 1
		JavaPairRDD<String, entity.PairQAndNumk> case1filter = qnumkPairRDD.filter(pairQAndNumk -> pairQAndNumk._2().getq() == 1.0 );
//		case1filter.saveAsTextFile("SparkOut/mapcase1filter.text");

		//TODO: Check filter
		//TODO: Combine two cases
		JavaPairRDD<String, Double> mapcase1 = case1filter.mapValues(pairQAndNumk -> pairQAndNumk.getnumk() * Math.log(pairQAndNumk.getnumk()));
		JavaPairRDD<String, Double> reducecase1 = mapcase1.reduceByKey((x, y) -> x + y);

		reducecase1.saveAsTextFile("SparkOut/mapcase1.text");

		//If case 2: q != 1
		JavaPairRDD<String, entity.PairQAndNumk> case2filter = qnumkPairRDD.filter(pairQAndNumk -> pairQAndNumk._2().getq() != 1.0 );
//		case2filter.saveAsTextFile("SparkOut/mapcase2filter.text");

		JavaPairRDD<String, Double> mapcase2 = case2filter.mapValues(pairQAndNumk -> Math.pow(pairQAndNumk.getnumk(),pairQAndNumk.getq() - 1));
		JavaPairRDD<String, Double> reducecase2 = mapcase2.reduceByKey((x, y) -> x + y);

		reducecase2.saveAsTextFile("SparkOut/mapcase2.text");



        //Pair Example
		JavaRDD<String> wordRdd = lines.map(line -> line.split(",")[0]);
		JavaPairRDD<String, Integer> wordPairRdd = wordRdd.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));

		JavaPairRDD<String, Integer> wordToCountPairs = wordPairRdd.reduceByKey((x, y) -> x + y);

		JavaPairRDD<Integer, String> countToWordParis = wordToCountPairs.mapToPair(wordToCount -> new Tuple2<>(wordToCount._2(),
				wordToCount._1()));
		JavaPairRDD<Integer, String> sortedCountToWordParis = countToWordParis.sortByKey(false);

		JavaPairRDD<String, Integer> sortedWordToCountPairs = sortedCountToWordParis
				.mapToPair(countToWord -> new Tuple2<>(countToWord._2(), countToWord._1()));

		for (Tuple2<String, Integer> wordToCount : sortedWordToCountPairs.collect()) {
			System.out.println(wordToCount._1() + " : " + wordToCount._2());
		}





		//		JavaRDD<Double> rddqq = sc.parallelize(qq);
//		JavaRDD<Double> rddvv = sc.parallelize(vv);
//		JavaPairRDD<JavaRDD<Double>, List<JavaRDD<Double>>> rddY = sc.union(rddqq, List<rddvv>);
//
//		JavaPairRDD<Integer, List<Double>> deviceRdd = rddqq.mapToPair(new PairFunction<List<Double>, Integer, List<Double>>() {
//			public Tuple2<Integer, List<Double>> call(List<Double> list) throws Exception {
//				Tuple2<Integer, List<Double>>  tuple = new Tuple2<Integer, List<Double>>(list.indexOf(0)), list);
//				return tuple;
//			}
//		});
////
//		for (int i = 0; i < strCe.length; i++) {
//			List<String> columnList = new ArrayList<String>();
//			for (int j = 0; j < strCe[i].length; j++) {
//
//				columnList.add(j, strCe[i][j]);
//
//			}
//			listTest.add(i, columnList);
//		}
//
//		List<Tuple2<Double, Double>> datasetContent = sc.parallelize(vv, qq)
//
//		JavaPairRDD<Double, Double> PairRdd = rddvv.mapToPair(
//				line -> new Tuple2<>(line.split(",")[3],
//						Double.parseDouble(line.split(",")[2])));

//
//		JavaPairRDD<Integer, Double> firstRDD = rddvv.mapToPair(new PairFunction<Double, Integer, Integer>() {
//			@Override
//			public Tuple2<Integer, Integer> call(Integer num) throws Exception {
//				return new Tuple2<>(num, num * num);
//			}
//		});
//
//		JavaPairRDD<Double, Double> joins = rddvv.join(rddqq);
//
//		JavaRDD<Double> result = rdd.map(
//				(Function<Double, Double>) x -> Math.log(x));



	}

	private static PairFunction<String, String, Integer> pairIndexAndNumk() {
		return s -> new Tuple2<>(s.split(",")[0], Integer.valueOf(s.split(",")[2]));
	}
}
