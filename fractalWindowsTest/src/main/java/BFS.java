import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
//import java.util.Vector;

public class BFS {

	public static int[] NumCount(int root, int[] offset, int[] column, int N){
		
		int[] layer = new int[N];  // the layer of each node
		//N = the total number of central Nodes
		//root = central node
		
		//Vector<Integer> blackNode = new Vector<Integer>(0);
		Queue<Integer> blackNode = new LinkedList<>();

		try {
			blackNode.offer(root);   // Set of black nodes
			int l= 0;
			while(!blackNode.isEmpty()) {
				l = l+1;
				Queue<Integer> greyNode = new LinkedList<>();   //Set of grey nodes
				while (!blackNode.isEmpty()) {
					int tmp = blackNode.poll();
					for(int j=offset[tmp]; j < offset[tmp+1]; j++) {   // get the neighbors of node "tmp" . offset is compressed sparse matrix.
						int child = column[j];
						if(layer[child] == 0 & child!=root) {     // if the layer is not assigned yet, assign the layer for this node
							layer[child] = l;                     
							greyNode.offer(child);
						}
					}
				}
				blackNode = greyNode;
			}

			int[] number= new int[l-1];  // the number of nodes for each radius r. number[0] indicates the number of nodes with r=1.
		
			for (int i=0; i<N; i++) {
				if (layer[i]!=0) {
					number[layer[i]-1] = number[layer[i]-1]+1;			
				}
			}
		
			for(int i=1; i<number.length; i++) {   // Calculate the number of nodes in each radius 'r'
				number[i] = number[i-1]+number[i]; 
			}
			return number;
		}catch (Exception e){
			System.out.println("BFS Error");
			return null;
		}
	}
}
