import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

public class Main {
    public static void main(String[] args) {
        final int n = 15;
        final int s = 3;

        /* Generating polynom */
        var genPolynom = Code.stringToArray("111010001");
        System.out.println("Code (" + n + ", " + (n - genPolynom.length + 1) + "): " + Utils.arrayToString(genPolynom));

        /* Generating matrix */
        var genMatrix = Code.buildGenMatrix(genPolynom, n);

        System.out.println("\nGenerating matrix:");
        System.out.println(Utils.matrixToString(genMatrix));

        /* Coding */
        var message = "11001";
        System.out.println("Source message: " + message);

        System.out.println("Encoded message: " + Utils.arrayToString(Code.encode(Code.stringToArray(message), genMatrix)));

        /* Countring  G^ */
        var Gline = MatrixWorker.computeGline(genMatrix);

        System.out.println("\nG^: ");
        System.out.println(Utils.matrixToString(Gline));

        System.out.println("\nDeterminant: " + MatrixWorker.determinant(Gline));
        System.out.println("Rank: " + MatrixWorker.rankOfMatrix(Gline));

        /* Inverting G^ */
        var GlineInverted = MatrixWorker.invert(Gline);
        System.out.println("\nInverted G^: ");
        System.out.println(Utils.matrixToString(GlineInverted));

        /* invG^ * G */
        var GlineTimesG = MatrixWorker.matrixMultiply(GlineInverted, genMatrix);
        System.out.println("invG^ * G: ");
        System.out.println(Utils.matrixToString(GlineTimesG));
    }
}
