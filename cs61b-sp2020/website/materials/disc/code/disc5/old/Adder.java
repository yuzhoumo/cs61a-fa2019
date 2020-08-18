/** Adder
 *  @author Josh Hug
 */

public class Adder implements IntUnaryFunction {
	/** Adder
	 */
	private int n;

	public Adder(int n) {
		this.n = n;
	}

	public int apply(int x) {
		return x + n;
	}
} 