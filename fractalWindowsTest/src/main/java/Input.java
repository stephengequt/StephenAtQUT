import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Input {
	int[] offset;
	int[] column;
	int E;
	int V;
	
	
	public int getVE (String fileName) {
		String line;
		int tmp=0;
		try {
			
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			line = in.readLine();
			String[] data = line.trim().split(",");
			tmp = Integer.valueOf(data[0]);
			in.close();
			return tmp;
		}catch(IOException iox) {
			System.out.println("Failed reading:"+fileName);
			return tmp;
		}
		
	}
	
	public void configuration (String fileName, int V, int E) { 
		
		String line;
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
	
			int[] offsetTmp = new int[V+1];   
			
	        //in.mark(1);
			for (int i=0; i<E; i++) {
				//System.out.println(i);
				line = in.readLine();
				String[] data = line.trim().split(",");  // Split elements in .dat file with ","
				offsetTmp[Integer.valueOf(data[0])]++;    // Calculate the number of links in each row;
			}
			
			for(int i=1; i<= V; i++) {
				offsetTmp[i] = offsetTmp[i] + offsetTmp[i-1];  // Accumulated value as the offset of each row
			}
			
			offset = offsetTmp;   // Set the offset of each row
			in.close();	
			
			in = new BufferedReader(new FileReader(fileName));
			
			int[] subOffset = new int[V];  // Sub-offset for each row
			int[] columnTmp = new int[E];  // Column values for each edge
			
			//in.reset();
			
			for(int i=0; i<E; i++ ) {
				line = in.readLine();
				String[] data = line.trim().split(",");  //split elements in .dat file with ","
				int k = Integer.valueOf(data[0]) - 1;  
				columnTmp[offset[k] + subOffset[k]] = Integer.valueOf(data[1])-1;
				subOffset[k] ++ ;
			}
			in.close();
			column = columnTmp;
			System.out.println("Initilization Completed");
		}
		catch(IOException iox)
		{
			System.out.println("Failed reading:"+fileName);
		}
	}
	
	
	
	
	/* This function is designed for standard row-based compress sparse matrix
	 * 
	 * public void configuration (String fileName, int V, int E) { 
	
		String line;
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
	
			int[] offsetTmp = new int[V+1];
			int[] columnTmp = new int[E];
	
			int rowIndex = 1;
			for(int i=0; i < E; i++) {
				line = in.readLine();
				String[] data = line.trim().split(",");  //split elements in .dat file with ","
		
				if ( Integer.valueOf(data[0]) == rowIndex  ) {
					offsetTmp[rowIndex-1] = i;
					rowIndex = rowIndex+1;
				}
				columnTmp[i] = Integer.valueOf(data[1])-1;  //Index of matlab matrix starts from 1; Index of java array starts from 0
			}
			offsetTmp[V] = E;   //The total number of edges
			in.close();
			
			offset = offsetTmp;
			column = columnTmp;
		}
		catch(IOException iox)
		{
			System.out.println("Problem reading:"+fileName);    
		}
	}*/
	
}
