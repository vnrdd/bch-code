import java.util.List;

public class Utils {
    /* Convert int array polynom into string-polynom */
    public static String arrayToString(int[] polynom) {
        StringBuilder sb = new StringBuilder();

        for (int member : polynom)
            sb.append(member);

        return sb.toString();
    }

    /* Convert list int matrix into string-matrix */
    public static String matrixToString(List<int[]> genM) {
        StringBuilder sb = new StringBuilder();

        for (int[] row : genM) {
            sb.append("[");
            for (int i : row)
                sb.append(i).append("  ");
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append("]\n");
        }

        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public static int vectorWeight(List<int[]> vector) {
        int count = 0;
        for(int bit : vector.get(0))
            count += bit;
        return count;
    }
}
