import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int n = 15;
        final int s = 3;

        /* Generating polynom */
        var genPolynom = Code.stringToArray("10100110111");
        System.out.println("Code (" + n + ", " + (n - genPolynom.length + 1) + "): " + Code.arrayToString(genPolynom));

        /* Generating matrix */
        var genMatrix = Code.buildGenMatrix(genPolynom, n);

        System.out.println("\nGenerating matrix:");
        System.out.println(Code.genMtoString(genMatrix));

        /* Coding */
        var message = "11001";
        System.out.println("Source message: " + message);

        System.out.println("Encoded message: " + Code.arrayToString(Code.encode(Code.stringToArray(message), genMatrix)));
    }
}
