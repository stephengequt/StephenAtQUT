import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class fractalNumber {


    public static void distributedVVCalculation(int N, int[] offset, int[] column) {


        //String fileName = "Aa9001.dat";
        //String line;
        //int N = 10924;    //Set the size of Network
        int N_3 = N / 10;   //Set the number of center nodes
        int num_q = 61;
//		int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.


        // long startTime=System.currentTimeMillis(); // Get start time
        // long loadTime=System.currentTimeMillis(); // Get start time

        int[] centerNode;
        centerNode = Fractal.GenRandomOrder(N, N_3);
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

//        JavaPairRDD<Integer, Double> flatMapTest = sc.parallelize(l).flatMapToPair(i -> {
//            int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.
//            int num_q = 61;
//            int N_3 = N / 10;   //Set the number of center nodes
//            double[][] VV = new double[num_q][networkDiameter];
//            double[] UU = new double[networkDiameter];
//            ArrayList<Tuple2<Integer, Double>> VVcontainer = new ArrayList<>();
//
//            try {
//
//                int[] number;
//                number = BFS.NumCount(centerNode[i], offset, column, N);  // calculate the number of nodes within radius 'r' of centerNode[i]
//
//                if (number.length <= networkDiameter) {            // the case when the depth of centerNode[i] is less than network diameter
//                    for (int j = 0; j < num_q; j++) {
//                        double q = -10 + 1 * ((double) j) / 3;    // Set the values of q
//                        for (int k = 0; k < number.length; k++) {      // calculate VV , the contribution of each center node is 1/N_3
//                            if (q == 1) {
//                                VV[j][k] = VV[j][k] + number[k] * Math.log(number[k]) / N_3;
//                            } else {
//                                VV[j][k] = VV[j][k] + Math.pow(number[k], q - 1) / N_3;
//                            }
//                        }
//
//                    }
//                } else {                             // the case when the depth of centerNode[i] is greater than network diameter
//                    double[][] VVTmp = new double[num_q][number.length];
//
//                    for (int j = 0; j < num_q; j++) {
//                        double q = -10 + 1 * ((double) j) / 3;
//                        for (int k = 0; k < networkDiameter; k++) {
//                            if (q == 1) {
//                                VVTmp[j][k] = VV[j][k] + number[k] * Math.log(number[k]) / N_3;
//                            } else {
//                                VVTmp[j][k] = VV[j][k] + Math.pow(number[k], q - 1) / N_3;
//                            }
//                        }
//                        for (int k = networkDiameter; k < number.length; k++) {
//                            if (q == 1) {
//                                VVTmp[j][k] = number[k] * Math.log(number[k]) / N_3;
//                            } else {
//                                VVTmp[j][k] = Math.pow(number[k], q - 1) / N_3;
//                            }
//                        }
//                    }
//                    VV = VVTmp;
//                    networkDiameter = number.length;
//                }
//
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
////                for (double[] eachVVi : VV) {
////                    for (double eachVVj : eachVVi)
////                        VVcontainer.add(eachVVj);
////                }
//
//                int index = 0;
//                for (int j = 0; j < num_q; j++) {
//                    for (int k = 0; k < networkDiameter; k++) {
////                        StringBuffer indexTemp1 = new StringBuffer();
////                        for (int z = 0; z < Integer.toString(num_q).length() - Integer.toString(jj).length(); z++) {
////                            indexTemp1.append("0");
////                        }
////                        indexTemp1.append(jj);
////                        String index1 = indexTemp1.toString();
////
////                        StringBuffer indexTemp2 = new StringBuffer();
////                        for (int a = 0; a < Integer.toString(networkDiameter).length() - Integer.toString(kk).length(); a++) {
////                            indexTemp2.append("0");
////                        }
////                        indexTemp2.append(kk);
////                        String index2 = indexTemp2.toString();
////                        String index = index1 + index2;
//
//
//                        index++;
//
//                        Tuple2 tp = new Tuple2<>(index, VV[j][k]);
//                        VVcontainer.add(tp);
//                    }
//                }
//
//                return VVcontainer.iterator();
//            } catch (Exception e) {
//                System.out.println("Fractal Error");
//                return null;
//            }
//        });
////        flatMapTest.saveAsTextFile("SparkOut/indexTest.dat");
//
//        JavaPairRDD<Integer, Double> reducedTest = flatMapTest.reduceByKey((x, y) -> x + y);

//        JavaPairRDD<Integer, Double> sortedTest = reducedTest.sortByKey();
//
//        sortedTest.saveAsTextFile("SparkOut/SortedReducedTest.dat");


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
        JavaPairRDD<Integer, Double> numberPartitionRDD = sc.parallelize(l).mapPartitionsToPair(
                (PairFlatMapFunction<Iterator<Integer>, Integer, Double>) i -> {
                    int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.
//                    int num_q = 61;
                    double[][] VV = new double[num_q][networkDiameter];
                    double[] UU = new double[networkDiameter];
                    ArrayList<Tuple2<Integer, Double>> VVcontainer = new ArrayList<>();

                    try {
                        while (i.hasNext()) {
                            int[] number;
                            number = BFS.NumCount(centerNode[i.next()], offset, column, N);  // calculate the number of nodes within radius 'r' of centerNode[i]

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

//                        for (double[] eachVVi : VV) {
//                            for (double eachVVj : eachVVi)
//                                VVcontainer.add(eachVVj);
//                        }


                        int index = 0;
                        for (int j = 0; j < num_q; j++) {
                            for (int k = 0; k < networkDiameter; k++) {
                                VVcontainer.add(new Tuple2<>(index, VV[j][k]));
                                index++;
                            }
                        }
                        return VVcontainer.iterator();
                    } catch (Exception e) {
                        System.out.println("Fractal Error");
                        return null;
                    }

                });

        JavaPairRDD<Integer, Double> reducedTest = numberPartitionRDD.reduceByKey((x, y) -> x + y);

        JavaPairRDD<Integer, Double> sortedTest = reducedTest.sortByKey();
        JavaRDD<Double> valueOfVV = sortedTest.map(m -> m._2());


//        valueOfVV.saveAsTextFile("SparkOut/PartitionTest.dat");
//
//        JavaPairRDD<Double, Integer> nextStepVV = sortedTest.mapToPair(vvRow -> new Tuple2<Double, Integer>(vvRow._2(), vvRow._1()));
//        JavaPairRDD<Double, Integer> nextStep2VV = nextStepVV.mapValues(index -> ((index.+1)/num_q));

//        nextStep2VV.saveAsTextFile("SparkOut/Index2.text");

        List<Double> vvPairs = valueOfVV.collect();
//
        int networkDiameter = vvPairs.size() / 61;
        int index = 0;
        double[][] VV = new double[num_q][networkDiameter];
        for (int j = 0; j < num_q; j++) {
            double q = -10 + 1 * ((double) j) / 3;
            for (int k = 0; k < networkDiameter; k++) {
                if (q == 1) {
                    VV[j][k] = vvPairs.get(index);
                } else {
                    VV[j][k] = Math.log(vvPairs.get(index)) / (q - 1);
                }
                index++;
            }
        }


        double[] UU = new double[networkDiameter];
        for (int j = 0; j < networkDiameter; j++) {              // Calculate UU
            UU[j] = Math.log(((double) (j + 1)) / networkDiameter);
        }

        try {

			FileWriter writer_UU = new FileWriter("Results/UUTemp.dat");
			for (int i = 0; i < num_q; i++) {
				for (int j = 0; j < networkDiameter; j++) {
					writer_UU.write(Double.toString(UU[j]) + ",");
				}
				writer_UU.write("\n");
			}
			writer_UU.close();

            FileWriter writer_VV = new FileWriter("Results/VVTemp.dat");
            for (int i = 0; i < num_q; i++) {
                for (int j = 0; j < networkDiameter; j++) {
                    writer_VV.write(Double.toString(VV[i][j]) + ",");
                }
                writer_VV.write("\n");
            }
            writer_VV.close();

        } catch (IOException iox) {
            System.out.println("Problemwriting" + "UU.dat" + "VV.dat");
        }


//        String line;
//        try{
//            BufferedReader in = new BufferedReader(new FileReader("SparkOut/PartitionTest.text"));
//            double[][] VV = new double[num_q][128];
//            for (int j =0; j < num_q; j++) {
//                for (int k = 0; k < 128; k++) {
//                    line = in.readLine();
//                    String[] data = line.trim().split(",");
//                    VV[j][k] = Double.valueOf(data[0]);
//                }
//            }
//            for (int j =0; j < num_q; j++) {
//                for (int k = 0; k < 10; k++) {
//                    System.out.println(VV[j][k]);
//                }
//            }
//
//        } catch (IOException iox) {
//            System.out.println("Failed Reading Array VV");
//        }


        System.out.println("Fatal dimension calculation finished");
        //long endTime=System.currentTimeMillis(); // Get end time
        //System.out.println("Running time:"+(endTime-startTime)+"ms");
    }
}
