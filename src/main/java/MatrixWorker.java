import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixWorker {
    final static int EPS = Integer.MIN_VALUE;

    public static int computeRank(List<short[]> M) {

        var A = new ArrayList<short[]>();

        for(short[] row : M) {
            short[] newRow = new short[row.length];
            System.arraycopy(row, 0, newRow, 0, row.length);
            A.add(newRow);
        }

        if (A.size() == 0)
            return Integer.MIN_VALUE;

        int n = A.size();
        int m = A.get(0).length;

        int rank = 0;
        boolean[] row_selected = new boolean[n];
        for (int i = 0; i < m; ++i) {
            int j;
            for (j = 0; j < n; ++j) {
                if (!row_selected[j])
                    break;
            }

            if (j != n) {
                ++rank;
                row_selected[j] = true;
                for (int p = i + 1; p < m; ++p) {
                    if (A.get(j)[i] != 0)
                        A.get(j)[p] /= A.get(j)[i];
                }
                for (int k = 0; k < n; ++k) {
                    if (k != j) {
                        for (int p = i + 1; p < m; ++p)
                            A.get(k)[p] -= A.get(j)[p] * A.get(k)[i];
                    }
                }
            }
        }
        return rank;
    }

    public static List<short[]> computeGline(List<short[]> G) {
        var result = new ArrayList<short[]>();
        int determinant = Integer.MIN_VALUE;

        while (determinant != G.size()) {
            result = new ArrayList<>();
            var randomized = new ArrayList<Integer>();

            for (int i = 0; i < G.size(); ++i) {
                int random = ThreadLocalRandom.current().nextInt(0, G.get(0).length);
                while (randomized.contains(random))
                    random = ThreadLocalRandom.current().nextInt(0, G.get(0).length);
                randomized.add(random);
            }

            Collections.sort(randomized);

            for (int i = 0; i < randomized.size(); ++i) {
                var bufRow = new short[randomized.size()];
                for (int j = 0; j < G.size(); ++j)
                    bufRow[j] = G.get(j)[randomized.get(i)];
                result.add(bufRow);
            }

            determinant = MatrixWorker.computeRank(result);
        }
        return result;
    }
}
