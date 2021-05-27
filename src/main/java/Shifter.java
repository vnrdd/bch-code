import java.util.List;

public class Shifter {
    public static int[] leftShift(int[] source) {
        int[] result = new int[source.length];
        int firstSymbol = source[0];

        System.arraycopy(source, 1, result, 0, source.length - 1);

        result[result.length - 1] = firstSymbol;

        return result;
    }

}
