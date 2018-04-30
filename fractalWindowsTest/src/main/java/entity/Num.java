package entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Num implements Serializable {
	private int i;
	private int[] num;

	public Num(int root, int[] offset, int[] column, int N) {
		this.i = root;

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
			this.num = number;
		}catch (Exception e){
			System.out.println("BFS Error");
			this.num = null;
		}
	}

	@Override
	public String toString() {
		String numberArray = null;
		for (int j = 0; j < num.length; j++) {
			numberArray = numberArray + Integer.toString(num[i]) + ",";
		}
		return "centralNode=" + i + "{" + numberArray + "}";
	}
}
