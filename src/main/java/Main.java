import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final int n = 15;
        final int s = 2;

        /* Generating polynom */
        var genPolynom = Code.stringToArray("111010001");
        System.out.println("Code (" + n + ", " + (n - genPolynom.length + 1) + "): " + Utils.arrayToString(genPolynom));

        /* Generating matrix */
        var genMatrix = Code.buildGenMatrix(genPolynom, n);

        System.out.println("\nGenerating matrix:");
        System.out.println(Utils.matrixToString(genMatrix));

        /* Coding */
        var message = "1100101";
        System.out.println("Source message: " + message);

        var encoded = Code.encode(Code.stringToArray(message), genMatrix);
        System.out.println("Encoded message: " + Utils.arrayToString(encoded));

        /* Countring  G^ */
        var infoSystem = new ArrayList<Integer>();
        var Gline = MatrixWorker.computeGline(genMatrix, infoSystem);

        System.out.println("\nG^: ");
        System.out.println(Utils.matrixToString(Gline));

        System.out.println("\nDeterminant: " + MatrixWorker.determinant(Gline));
        System.out.println("Rank: " + (new Basic2DMatrix(MatrixWorker.convertTo2DdoubleArray(Gline))).rank());
        System.out.println("Info system: " + infoSystem);

        /* Inverting G^ */
        var GlineInverted = MatrixWorker.invert(Gline);
        System.out.println("\nInverted G^: ");
        System.out.println(Utils.matrixToString(GlineInverted));

        /* invG^ * G */
        var Gy = MatrixWorker.matrixMultiply(GlineInverted, genMatrix);
        System.out.println("Gy: ");
        System.out.println(Utils.matrixToString(Gy));


        /* Hy */
        var Hy = MatrixWorker.makeHy(Gy, infoSystem);
        System.out.println("Hy: ");
        System.out.println(Utils.matrixToString(Hy));

        encoded[2] ^= 1;
        encoded[3] ^= 1;

        var syndrome = Code.getSyndrome(encoded, Hy, infoSystem);
        System.out.println("Syndrome: " + Utils.matrixToString(syndrome));

        int shifted = 0;
        while (Utils.vectorWeight(syndrome) > s) {
            encoded = Shifter.leftShift(encoded);
            ++shifted;

            syndrome = Code.getSyndrome(encoded, Hy, infoSystem);
            System.out.println("Syndrome: " + Utils.matrixToString(syndrome));
        }

        var fixedWord = Code.getFixedWord(encoded, Gy, infoSystem);
        System.out.println(Utils.matrixToString(fixedWord));

        var fixedWordArray = fixedWord.get(0);

        for (int i = 0; i < n - shifted; ++i)
            fixedWordArray = Shifter.leftShift(fixedWordArray);

        System.out.println(Arrays.toString(fixedWordArray));
    }
}