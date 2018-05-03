import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Fractal {


	public static void calculate(int N, int[] offset, int[] column) {


		//String fileName = "Aa9001.dat";
		//String line;
		//int N = 10924;    //Set the size of Network
		int N_3 = N / 10;   //Set the number of center nodes
		int num_q = 61;
		int networkDiameter = 1;  //Set the network diameter to 1. It will increase as the calculation continues.


		// long startTime=System.currentTimeMillis(); // Get start time
		// long loadTime=System.currentTimeMillis(); // Get start time

		int[] centerNode;
		centerNode = GenRandomOrder(N, N_3);
		double[][] VV = new double[num_q][networkDiameter];
		double[] UU = new double[networkDiameter];



		try {

			for (int i = 1; i < centerNode.length; i++) {   // for sample nodes, calculate UU and VV
				if (i % 100 == 0) {
					System.out.printf("Progess: %.3f%%\n", (((double) i) / centerNode.length) * 100);
				}

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
			}

			for (int j = 0; j < num_q; j++) {
				double q = -10 + 1 * ((double) j) / 3;
				for (int k = 0; k < networkDiameter; k++) {
					if (q == 1) {

					} else {
						VV[j][k] = Math.log(VV[j][k]) / (q - 1);
					}
				}
			}

			double[] UUTmp = new double[networkDiameter];
			for (int j = 0; j < networkDiameter; j++) {              // Calculate UU
				UUTmp[j] = Math.log(((double) (j + 1)) / networkDiameter);
			}
			UU = UUTmp;
		} catch (Exception e) {
			System.out.println("Fractal Error");
		}
//		long calculationTime=System.currentTimeMillis(); // Get start time
//		System.out.println("Calculation time:"+(calculationTime-countTime)+"ms");
//
//		//Write UU, VV to .dat file
		try {

			FileWriter writer_UU = new FileWriter("Results/UU.dat");
			for (int i = 0; i < num_q; i++) {
				for (int j = 0; j < networkDiameter; j++) {
					writer_UU.write(Double.toString(UU[j]) + ",");
				}
				writer_UU.write("\n");
			}
			writer_UU.close();

			FileWriter writer_VV = new FileWriter("Results/VV.dat");
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

		System.out.println("Fatal dimension calculation finished");
		//long endTime=System.currentTimeMillis(); // Get end time
		//System.out.println("Running time:"+(endTime-startTime)+"ms");
	}


	// This function selects non-repeatable random numbers
	public static int[] GenRandomOrder(int NetworkSize, int CenterNum) {
		int[] order = new int[NetworkSize];

		int k, temp = 0;

		for (int i = 0; i < NetworkSize; i++) {
			order[i] = i;
		}

		if (CenterNum < NetworkSize / 2) {   // if number of center nodes is less than 50% of the network size, randomly select center nodes
			int[] order1 = new int[CenterNum];
			for (int j = 0; j < CenterNum; j++) {
				k = (int) (Math.random() * NetworkSize);
				temp = order[j];
				order[j] = order[k];
				order[k] = temp;
			}

			for (int i = 0; i < CenterNum; i++) {
				order1[i] = order[i];
			}

			return order1;
		} else {    // if number of center nodes is greater than 50% of the network size, randomly select non-center nodes
			if (NetworkSize == CenterNum) {
				return order;
			} else {

				for (int j = 0; j < (NetworkSize - CenterNum); j++) {
					k = (int) (Math.random() * NetworkSize);
					temp = order[j];
					order[j] = order[k];
					order[k] = temp;
				}

				int[] order1 = new int[CenterNum];
				for (int i = 0; i < CenterNum; i++) {
					order1[i] = order[NetworkSize - CenterNum + i];
				}
				return order1;
			}
		}
	}
}
