/** Adder
 *  @author Josh Hug
 */

public class Summer implements IntUnaryFunction {
	/** Adder
	 */
	private int s;

	public Summer() {
		s = 0;
	}

	public int getS() {
		return s;
	}

	public int apply(int x) {
		s += x;
		return s;
	}
} 