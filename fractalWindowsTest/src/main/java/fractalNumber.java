import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.DoubleFlatMapFunction;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Encoders;
import scala.Tuple2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import javax.xml.ws.Response;

public class fractalNumber {


    public static void distributedBNumberCalculation(int N, int[] offset, int[] column, int[] centerNode) {


        //String fileName = "Aa9001.dat";
        //String line;
        //int N = 10924;    //Set the size of Network
//		int N_3 = N / 10;   //Set the number of center nodes
//		int num_q = 61;
//		int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.


        // long startTime=System.currentTimeMillis(); // Get start time
        // long loadTime=System.currentTimeMillis(); // Get start time

//		int[] centerNode;
//		centerNode = GenRandomOrder(N, N_3);
//		double[][] VV = new double[num_q][networkDiameter];
//		double[] UU = new double[networkDiameter];


        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkConf conf = new SparkConf().setAppName("numberGenerater").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> l = new ArrayList<>(centerNode.length);
        for (int i = 1; i < centerNode.length; i++) {
            l.add(i);
        }

//		JavaRDD<Integer> numberRDD = sc.parallelize(l).mapPartitions(
//				new FlatMapFunction<Iterator<Integer>, Integer>() {
//					@Override
//					public Iterator<Integer> call(Iterator<Integer> i) throws Exception {
//						ArrayList<Integer> numbers = new ArrayList<>();
//						int[] eachNumberArray;
//						try {
//							while (i.hasNext()) {
//								eachNumberArray = BFS.NumCount(centerNode[i.next()], offset, column, N);
////							numbers.add(eachNumberArray.length);
//								for (int eachNumber : eachNumberArray) {
//									numbers.add(eachNumber);
//								}
//							}
//						} catch (Exception e) {
//						}
//						return numbers.iterator();
//					}
//				});
//		numberRDD.saveAsTextFile("SparkOut/Test3.dat");

        JavaPairRDD<Integer, Double> flatMapTest = sc.parallelize(l).flatMapToPair(i -> {
            int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.
            int num_q = 61;
            int N_3 = N / 10;   //Set the number of center nodes
            double[][] VV = new double[num_q][networkDiameter];
            double[] UU = new double[networkDiameter];
            ArrayList<Tuple2<Integer, Double>> VVcontainer = new ArrayList<>();

            try {

                int[] number;
                number = BFS.NumCount(centerNode[i], offset, column, N);  // calculate the number of nodes within radius 'r' of centerNode[i]

                if (number.length <= networkDiameter) {            // the case when the depth of centerNode[i] is less than network diameter
                    for (int j = 0; j < num_q; j++) {
                        double q = -10 + 1 * ((double) j) / 3;    // Set the values of q
                        for (int k = 0; k < number.length; k++) {      // calculate VV , the contribution of each center node is 1/N_3
                            if (q == 1) {
                                VV[j][k] = VV[j][k] + number[k] * Math.log(number[k]) / N_3;
                            } else {
                                VV[j][k] = VV[j][k] + Math.pow(number[k], q - 1) / N_3;
                            }
                        }

                    }
                } else {                             // the case when the depth of centerNode[i] is greater than network diameter
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


//						for (int j = 0; j < num_q; j++) {
//							double q = -10 + 1 * ((double) j) / 3;
//							for (int k = 0; k < networkDiameter; k++) {
//								if (q == 1) {
//
//								} else {
//									VV[j][k] = Math.log(VV[j][k]) / (q - 1);
//								}
//							}
//						}

//						double[] UUTmp = new double[networkDiameter];
//						for (int j = 0; j < networkDiameter; j++) {              // Calculate UU
//							UUTmp[j] = Math.log(((double) (j + 1)) / networkDiameter);
//						}
//						UU = UUTmp;

//                for (double[] eachVVi : VV) {
//                    for (double eachVVj : eachVVi)
//                        VVcontainer.add(eachVVj);
//                }

                int index = 0;
                for (int j = 0; j < num_q; j++) {
                    for (int k = 0; k < networkDiameter; k++) {
//                        StringBuffer indexTemp1 = new StringBuffer();
//                        for (int z = 0; z < Integer.toString(num_q).length() - Integer.toString(jj).length(); z++) {
//                            indexTemp1.append("0");
//                        }
//                        indexTemp1.append(jj);
//                        String index1 = indexTemp1.toString();
//
//                        StringBuffer indexTemp2 = new StringBuffer();
//                        for (int a = 0; a < Integer.toString(networkDiameter).length() - Integer.toString(kk).length(); a++) {
//                            indexTemp2.append("0");
//                        }
//                        indexTemp2.append(kk);
//                        String index2 = indexTemp2.toString();
//                        String index = index1 + index2;


                        index++;

                        Tuple2 tp = new Tuple2<>(index, VV[j][k]);
                        VVcontainer.add(tp);
                    }
                }

                return VVcontainer.iterator();
            } catch (Exception e) {
                System.out.println("Fractal Error");
                return null;
            }
        });
//        flatMapTest.saveAsTextFile("SparkOut/indexTest.dat");

        JavaPairRDD<Integer, Double> reducedTest = flatMapTest.reduceByKey((x, y) -> x + y);

        JavaPairRDD<Integer, Double> sortedTest = reducedTest.sortByKey();

        sortedTest.saveAsTextFile("SparkOut/SortedReducedTest.dat");

//        for (int j = 0; j < num_q; j++) {
//							double q = -10 + 1 * ((double) j) / 3;
//							for (int k = 0; k < networkDiameter; k++) {
//								if (q == 1) {
//
//								} else {
//									VV[j][k] = Math.log(VV[j][k]) / (q - 1);
//								}
//							}
//						}

        //mapPartition
//        JavaRDD<Double> numberRDD = sc.parallelize(l).mapPartitions(
//                (FlatMapFunction<Iterator<Integer>, Double>) i -> {
//                    int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.
//                    int num_q = 61;
//                    int N_3 = N / 10;   //Set the number of center nodes
//                    double[][] VV = new double[num_q][networkDiameter];
//                    double[] UU = new double[networkDiameter];
//                    ArrayList<Double> VVcontainer = new ArrayList<>();
//
//                    try {
//                        while (i.hasNext()) {
//                            int[] number;
//                            number = BFS.NumCount(centerNode[i.next()], offset, column, N);  // calculate the number of nodes within radius 'r' of centerNode[i]
//
//                            if (number.length <= networkDiameter) {            // the case when the depth of centerNode[i] is less than network diameter
//                                for (int j = 0; j < num_q; j++) {
//                                    double q = -10 + 1 * ((double) j) / 3;    // Set the values of q
//                                    for (int k = 0; k < number.length; k++) {      // calculate VV , the contribution of each center node is 1/N_3
//                                        if (q == 1) {
//                                            VV[j][k] = VV[j][k] + number[k] * Math.log(number[k]) / N_3;
//                                        } else {
//                                            VV[j][k] = VV[j][k] + Math.pow(number[k], q - 1) / N_3;
//                                        }
//                                    }
//
//                                }
//                            } else {                             // the case when the depth of centerNode[i] is greater than network diameter
//                                double[][] VVTmp = new double[num_q][number.length];
//
//                                for (int j = 0; j < num_q; j++) {
//                                    double q = -10 + 1 * ((double) j) / 3;
//                                    for (int k = 0; k < networkDiameter; k++) {
//                                        if (q == 1) {
//                                            VVTmp[j][k] = VV[j][k] + number[k] * Math.log(number[k]) / N_3;
//                                        } else {
//                                            VVTmp[j][k] = VV[j][k] + Math.pow(number[k], q - 1) / N_3;
//                                        }
//                                    }
//                                    for (int k = networkDiameter; k < number.length; k++) {
//                                        if (q == 1) {
//                                            VVTmp[j][k] = number[k] * Math.log(number[k]) / N_3;
//                                        } else {
//                                            VVTmp[j][k] = Math.pow(number[k], q - 1) / N_3;
//                                        }
//                                    }
//                                }
//                                VV = VVTmp;
//                                networkDiameter = number.length;
//                            }
//                        }
//
////						for (int j = 0; j < num_q; j++) {
////							double q = -10 + 1 * ((double) j) / 3;
////							for (int k = 0; k < networkDiameter; k++) {
////								if (q == 1) {
////
////								} else {
////									VV[j][k] = Math.log(VV[j][k]) / (q - 1);
////								}
////							}
////						}
//
////						double[] UUTmp = new double[networkDiameter];
////						for (int j = 0; j < networkDiameter; j++) {              // Calculate UU
////							UUTmp[j] = Math.log(((double) (j + 1)) / networkDiameter);
////						}
////						UU = UUTmp;
//
//                        for (double[] eachVVi : VV) {
//                            for (double eachVVj : eachVVi)
//                                VVcontainer.add(eachVVj);
//                        }
//
////						for (int jj = 0; jj < num_q; jj++) {
////							for (int kk = 0; kk < networkDiameter; kk++) {
////								String index = Integer.toString(jj) + Integer.toString(kk);
//////								VVcontainer.add(Integer.parseInt(index), VV[jj][kk]);
////								VVcontainer.add(VV[jj][kk]);
////							}
////						}
//
//                        return VVcontainer.iterator();
//                    } catch (Exception e) {
//                        System.out.println("Fractal Error");
//                        return null;
//                    }
//
//                });
//        numberRDD.saveAsTextFile("SparkOut/Test3.dat");



        System.out.println("Fatal dimension calculation finished");
        //long endTime=System.currentTimeMillis(); // Get end time
        //System.out.println("Running time:"+(endTime-startTime)+"ms");
    }
}
