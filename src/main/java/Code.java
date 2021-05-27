import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;
import java.util.List;

public class Code {

    /* Convert string-polynom into int array polynom */
    public static int[] stringToArray(String polynom) {
        int[] result = new int[polynom.length()];

        for (int i = 0; i < result.length; ++i)
            result[i] = (int) Integer.parseInt(String.valueOf(polynom.charAt(i)));

        return result;
    }

    /* Build generating matrix G(x) */
    public static List<int[]> buildGenMatrix(int[] polynom, int n) {
        var result = new ArrayList<int[]>();

        int[] bufRow = new int[n];
        System.arraycopy(polynom, 0, bufRow, n - polynom.length, polynom.length);

        result.add(bufRow);

        int shift = 1;
        while (bufRow[0] != 1) {
            bufRow = Shifter.leftShift(bufRow);
            result.add(bufRow);
        }

        return result;
    }

    public static int[] xor(int[] first, int[] second) {
        int[] result = new int[first.length];

        for (int bit = 0; bit < first.length; ++bit)
            result[bit] = (int) ((first[bit] + second[bit]) % 2);

        return result;
    }

    public static int[] encode(int[] message, List<int[]> genM) {
        int[] res = new int[genM.get(0).length];

        for (int bit = 0; bit < message.length; ++bit) {
            if (message[bit] == 1)
                res = Code.xor(res, genM.get(bit));
        }

        return res;
    }

    /* Syndrome */
    public static List<int[]> getSyndrome(int[] encoded, List<int[]> Hy, List<Integer> infoSystem) {
        Matrix msg = new Basic2DMatrix(MatrixWorker.messageToMatrix(encoded));
        Matrix H = new Basic2DMatrix(MatrixWorker.convertTo2DdoubleArray(Hy));
        H = H.transpose();

        Matrix res = msg.multiply(H);

        return MatrixWorker.convertToList(res);
    }

    /* Fixing erros */
    public static List<int[]> getFixedWord(int[] encoded, List<int[]> Gy, List<Integer> infoSystem) {
        var by = new ArrayList<int[]>();
        int[] buf = new int[infoSystem.size()];

        by.add(buf);

        for(int i = 0; i < infoSystem.size(); ++i)
            by.get(0)[i] = encoded[infoSystem.get(i)];

        Matrix G = new Basic2DMatrix(MatrixWorker.convertTo2DdoubleArray(Gy));
        Matrix msg = new Basic2DMatrix(MatrixWorker.convertTo2DdoubleArray(by));

        Matrix res = msg.multiply(G);

        return MatrixWorker.convertToList(res);
    }
}
