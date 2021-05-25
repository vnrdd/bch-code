public class Shifter {
    public static int[] leftShift(int[] source, int shift) {
        int[] result = new int[source.length];

        if (source.length - 1 >= 0)
            System.arraycopy(source, 1, result, 0, source.length - 1);

        return result;
    }
}
