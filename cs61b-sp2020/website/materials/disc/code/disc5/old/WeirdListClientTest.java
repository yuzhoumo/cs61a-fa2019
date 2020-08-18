import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author Josh Hug
 */

public class WeirdListClientTest {
	/** Tests the WeirdListClient class
	 */

	public static void main(String[] args) {
		WeirdList wl1 = new WeirdList(5, WeirdList.EMPTY);
		WeirdList wl2 = new WeirdList(6, wl1);
		WeirdList wl3 = new WeirdList(10, wl2);

		assertEquals(WeirdListClient.sum(wl3), 10+6+5);
		assertEquals(WeirdListClient.sum(wl1), 5);

		WeirdList nwl = WeirdListClient.add(wl2, 4);
		assertEquals(WeirdListClient.sum(nwl), (6+4)+(5+4));

		assertEquals(WeirdListClient.sum(wl3), 21);

	}
} 