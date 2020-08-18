import static org.junit.Assert.*;
import org.junit.Test;

public class AnimalTest {
    @Test
    public void testGreet() {
        Animal a = new Animal("Pluto", 10);
        Cat c = new Cat("Garfield", 1);
        assertEquals(a.greet(), "Pluto: Huh?");
        assertEquals(c.greet(), "Garfield: MEOW!");

        a = c;
        assertEquals(a.greet(), "Garfield: MEOW!");
    }
    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(AnimalTest.class));
    }
}

