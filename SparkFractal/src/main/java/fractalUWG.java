
public class fractalUWG {
	public static void main(String arg[]) {
		
//		String fileName = "Input/11_sparse.dat" ;
		String fileName = "Input/sparse.dat" ;
		Input sparse = new Input();
		
		long startTime=System.currentTimeMillis();
		
		sparse.E = sparse.getVE("Input/E.dat");   // Read the number of edges in the network
		sparse.V = sparse.getVE("Input/V.dat");   // Read the number of nodes in the network
		sparse.configuration(fileName, sparse.V, sparse.E);   // Convert the standard sparse matrix to compressed sparse matrix

		long inputProcessTime = System.currentTimeMillis();

		System.out.println("Input Process Time:"+(inputProcessTime-startTime)+"ms");

		Fractal.calculate(sparse.V, sparse.offset, sparse.column);

		long calculationTime=System.currentTimeMillis(); // Get start time
		System.out.println("Calculation time:"+(calculationTime-inputProcessTime)+"ms");

	}
}
