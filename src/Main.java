import java.time.Duration;
import java.time.Instant;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main (String[] args) {
	// Press Alt+Enter with your caret at the highlighted text to see how
	// IntelliJ IDEA suggests fixing it.
	System.out.printf ("Hello and welcome!");

	// Press Shift+F10 or click the green arrow button in the gutter to run the code.
	int k = 50;
	int size = 300_000_000;
	int[] test = new int[size];
	Random r = new Random (1337);
	for(int i = 0; i < test.length; i++) {
	    test[i] = r.nextInt();
	}
	Instant start = Instant.now();
	System.out.println("\nTime is: " + start);
	int res1 = FindTopK.findKthLargestPQParallel (test, k);
	//int res1 = FindTopK.findKthLargestPQ (test, k);
	Instant end = Instant.now();
	Duration elapsed = Duration.between (start, end);
	System.out.println("Time is: " + Instant.now());
	System.out.println("Time elapsed: " + elapsed);

	System.out.println(res1);
	//System.out.println(res2);

    }
}
