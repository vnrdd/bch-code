import java.util.ArrayList;
import java.util.List;

public class Code {

    /* Convert string-polynom into short array polynom */
    public static short[] stringToArray(String polynom) {
        short[] result = new short[polynom.length()];

        for(int i = 0; i < result.length; ++i)
            result[i] = (short) Integer.parseInt(String.valueOf(polynom.charAt(i)));

        return result;
    }

    /* Convert short array polynom into string-polynom */
    public static String arrayToString(short[] polynom) {
        StringBuilder sb = new StringBuilder();

        for(short member : polynom)
            sb.append(member);

        return sb.toString();
    }

    /* Build generating matrix G(x) */
    public static List<short[]> buildGenMatrix(short[] polynom, int n) {
        var result = new ArrayList<short[]>();

        short[] bufRow = new short[n];
        System.arraycopy(polynom, 0, bufRow, n - polynom.length, polynom.length);

        result.add(bufRow);

        int shift = 1;
        while(bufRow[0] != 1) {
            bufRow = Shifter.leftShift(bufRow, shift);
            result.add(bufRow);
        }

        return result;
    }

    public static String genMtoString(List<short[]> genM) {
        StringBuilder sb = new StringBuilder();

        for(short[] row : genM)
            sb.append("[").append(Code.arrayToString(row)).append("]\n");

        return sb.toString();
    }

    public static short[] xor(short[] first, short[] second) {
        short[] result = new short[first.length];

        for(int bit = 0; bit < first.length; ++bit)
            result[bit] = (short) ((first[bit] + second[bit]) % 2);

        return result;
    }

    public static short[] encode(short[] message, List<short[]> genM) {
        short[] res = new short[genM.get(0).length];

        for(int bit = 0; bit < message.length; ++bit) {
            if(message[bit] == 1)
                res = Code.xor(res, genM.get(bit));
        }

        return res;
    }
}
