/** Adder
 *  @author Josh Hug
 */

public class Maximizer implements IntUnaryFunction {
	/** Adder
	 */
	public int max;

	public Maximizer() {
		max = -1;
	}

	public int apply(int x) {
		max = Math.max(x, max);
		return max;
	}
} 