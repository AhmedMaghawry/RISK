import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class TestsGenerator {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter the number of countries: ");
        int n = reader.nextInt(); // Scans the next token of the input as an int.
        //once finished
        reader.close();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter("test_generated.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        writer.println("V " + n);
        writer.println("E " + n * 5);
        for (int i = 0; i < n*5; i++) {
            Object[] set = pickRandom(2, n - 1).toArray();
            writer.println("(" + set[0] + " " + set[1] + ")");
        }
        Object[] agents = pickRandom(n, n - 1).toArray();
        int counter = 0;
        writer.print("A1 ");
        for (Object i : agents) {
            if (counter == n / 2) {
                writer.println("");
                writer.print("A2 ");
            }
            counter++;
            writer.print(i + " ");
        }
        writer.println("");

        for (int i = 1; i<= n; i++) {
            writer.println("c " + i + " " + (new Random().nextInt(6) + 1));
        }

        /*writer.println("P " + (n / 2 + 1));
        Object[] bounces = pickRandom((n / 2 + 1), 3).toArray();
        int i = 1;
        for (int j = 0; j < bounces.length; j++) {
            writer.print(bounces[j] + " ");
            writer.print(i + " ");
            i++;
            for (; i <= n; i++) {
                if (i% (n / 2 + 1) == 0) {
                    break;
                }
                writer.print(i + " ");
            }
            writer.println("");
        }*/
        writer.close();
    }

    public static Set<Integer> pickRandom(int n, int k) {
        final Set<Integer> picked = new HashSet<>();
        while (picked.size() < n) {
            picked.add(new Random().nextInt(k + 1) + 1);
        }
        return picked;
    }
}
