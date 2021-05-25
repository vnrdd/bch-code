import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixWorker {
    /* Counting G^ with determinant == k */
    public static List<int[]> computeGline(List<int[]> G) {
        var result = new ArrayList<int[]>();
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
                var bufRow = new int[randomized.size()];
                for (int j = 0; j < G.size(); ++j)
                    bufRow[j] = G.get(j)[randomized.get(i)];
                result.add(bufRow);
            }

            determinant = MatrixWorker.rankOfMatrix(result);
        }
        return result;
    }

    static void swap(int[][] mat, int row1, int row2, int col) {
        for (int i = 0; i < col; i++) {
            int temp = mat[row1][i];
            mat[row1][i] = mat[row2][i];
            mat[row2][i] = temp;
        }
    }

    /* Matrix rank counting */
    static int rankOfMatrix(List<int[]> matrix) {
        int[][] mat = new int[matrix.size()][matrix.get(0).length];

        for (int i = 0; i < matrix.size(); ++i) {
            System.arraycopy(matrix.get(i), 0, mat[i], 0, matrix.get(0).length);
        }

        int dim = mat[0].length;

        int rank = dim;

        for (int row = 0; row < rank; row++) {
            if (mat[row][row] != 0) {
                for (int col = 0; col < dim; col++) {
                    if (col != row) {
                        double mult =
                                (double) mat[col][row] / mat[row][row];

                        for (int i = 0; i < rank; i++)
                            mat[col][i] -= mult * mat[row][i];
                    }
                }
            } else {
                boolean reduce = true;
                for (int i = row + 1; i < dim; i++) {
                    if (mat[i][row] != 0) {
                        swap(mat, row, i, rank);
                        reduce = false;
                        break;
                    }
                }
                if (reduce) {
                    rank--;

                    for (int i = 0; i < dim; i++)
                        mat[i][row] = mat[i][rank];
                }

                row--;
            }
        }

        return rank;
    }
}
