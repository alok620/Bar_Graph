import java.util.ArrayList;

public class Step {

  // constants
	public static final int COMPARE = 0;
	public static final int SWAP = 1;
	public static final int TOBUFFER = 3;
	public static final int TOBAR = 4;
	public static final int PIVOT = 5;
	
  // fields
	private int index1;
	private int index2;
	private int type;
	
  // constructor
	public Step(int index1, int index2, int type) {
		this.index1 = index1;
		this.index2 = index2;
		this.type = type;
	}
	
  // methods
	public int getType() {
		return type;
	}
	
	public int getIndex1() {
		return index1;
	}
	
	public int getIndex2() {
		return index2;
	}
}
