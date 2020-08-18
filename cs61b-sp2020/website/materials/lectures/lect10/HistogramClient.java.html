import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class HistogramClient {

    static void fillHistogram(Histogram H, Scanner in)
    {
        while (in.hasNextDouble()) {
            H.add(in.nextDouble());
        }
    }

    static void printHistogram(Histogram H) {
        for (int i = 0; i < H.size(); i += 1) {
            System.out.printf(">=%5.2f | %4d%n", H.low(i), H.count(i));
        }
    }

    public static void main(String... args) {
        Scanner inp;
                                  
        try {
            inp = new Scanner(new File(args[0]));
            System.out.println("Fixed:");
            Histogram h1 = new FixedHistogram(10, 0.0, 10.0);
            fillHistogram(h1, inp);
            printHistogram(h1);

            inp = new Scanner(new File(args[1]));
            System.out.printf("%nFlexible:%n");
            Histogram h2 = new FlexHistogram(10);
            fillHistogram(h2, inp);
            printHistogram(h2);
        } catch (IOException excp) {
            System.err.printf("Error: %s%n", excp);
            System.exit(1);
        }
    }


}
