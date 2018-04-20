import java.io.Serializable;

public class PairQAndNumk implements Serializable {
	private double q;
	private int numk;


	public PairQAndNumk(double q, int numk) {
		this.q = q;
		this.numk = numk;
//		this.q = q;
//		this.numk = numk;
	}
//
//	public String getIndex() {
//		return String.valueOf(j) + String.valueOf(k);
//	}

	public double getq() {
		return q;
	}

	public double getnumk() {
		return numk;
	}

	@Override
	public String toString() {
		return "{" +
				"q=" + q +
				", numk=" + numk +
				'}';
	}
}
