public class Shifter {
    public static short[] leftShift(short[] source, int shift) {
        short[] result = new short[source.length];

        if (source.length - 1 >= 0)
            System.arraycopy(source, 1, result, 0, source.length - 1);

        return result;
    }
}
