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
            bufRow = Shifter.leftShift(bufRow, shift);
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
}
