import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;

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
        var infoSystem = new ArrayList<Integer>();
        var Gline = MatrixWorker.computeGline(genMatrix, infoSystem);

        System.out.println("\nG^: ");
        System.out.println(Utils.matrixToString(Gline));

        System.out.println("Determinant: " + MatrixWorker.determinant(Gline));
        System.out.println("Rank: " + MatrixWorker.rankOfMatrix(Gline));

        /* Inverting G^ */
        var GlineInverted = MatrixWorker.invert(Gline);
        System.out.println("\nInverted G^: ");
        System.out.println(Utils.matrixToString(GlineInverted));

        /* invG^ * G */
        var Gy = MatrixWorker.matrixMultiply(GlineInverted, genMatrix);
        System.out.println("invG^ * G: ");
        System.out.println(Utils.matrixToString(Gy));
        System.out.println("Info system: " + infoSystem);

        /* Hy */
        MatrixWorker.computeH(Gy, infoSystem);
    }
}
