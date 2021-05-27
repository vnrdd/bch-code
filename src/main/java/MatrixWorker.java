import org.la4j.LinearAlgebra;
import org.la4j.Matrix;
import org.la4j.inversion.MatrixInverter;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MatrixWorker {

    /* Counting G^ with determinant == k */
    public static List<int[]> computeGline(List<int[]> G, List<Integer> infoSystem) {
        var result = new ArrayList<int[]>();
        int rank = Integer.MIN_VALUE;
        boolean invertFlag = false;
        var randomized = new ArrayList<Integer>();

        while (rank != G.size() || (determinant(result) == 0) || !invertFlag) {
            result = new ArrayList<>();
            randomized = new ArrayList<Integer>();
            invertFlag = false;

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
                    bufRow[j] = G.get(i)[randomized.get(j)];
                result.add(bufRow);
            }

            rank = (new Basic2DMatrix(convertTo2DdoubleArray(result))).rank();

            if (determinant(result) != 0) {
                var inverted = invert(result);
                invertFlag = isUnitMatrix(matrixMultiply(result, inverted));
            }
        }

        infoSystem.addAll(randomized);
        return result;
    }

    public static boolean isUnitMatrix(List<int[]> matrix) {
        for (int i = 0; i < matrix.size(); ++i) {
            for (int j = 0; j < matrix.get(0).length; ++j) {
                if (i == j) {
                    if (matrix.get(i)[j] != 1)
                        return false;
                } else {
                    if (matrix.get(i)[j] != 0)
                        return false;
                }
            }
        }
        return true;
    }

    static void swap(int[][] mat, int row1, int row2, int col) {
        for (int i = 0; i < col; i++) {
            int temp = mat[row1][i];
            mat[row1][i] = mat[row2][i];
            mat[row2][i] = temp;
        }
    }

    /* Matrix invertion */
    public static List<int[]> invert(List<int[]> matrix) {
        Matrix source = new Basic2DMatrix(convertTo2DdoubleArray(matrix));
        Matrix inverted = source.withInverter(LinearAlgebra.InverterFactory.GAUSS_JORDAN).inverse();

        return convertToList(inverted);
    }

    /* Convert Matrix to list int matrix */
    public static List<int[]> convertToList(Matrix matrix) {
        var result = new ArrayList<int[]>();

        for (int i = 0; i < matrix.rows(); ++i) {
            int[] bufRow = new int[matrix.columns()];
            for (int j = 0; j < matrix.columns(); ++j)
                bufRow[j] = ((int) matrix.get(i, j) + 2) % 2;
            result.add(bufRow);
        }

        return result;
    }

    /* List<int> matrix to double[][]*/
    public static double[][] convertTo2DdoubleArray(List<int[]> matrix) {
        double[][] result = new double[matrix.size()][matrix.get(0).length];

        for (int i = 0; i < result.length; ++i) {
            for (int j = 0; j < result[0].length; ++j)
                result[i][j] = matrix.get(i)[j];
        }

        return result;
    }

    /* Calculate determinant */
    public static int determinant(List<int[]> matrix) {
        if (matrix.size() == 0)
            return 0;

        Matrix a = new Basic2DMatrix(convertTo2DdoubleArray(matrix));
        return (int) a.determinant();
    }

    /* Matrix multiplying */
    public static List<int[]> matrixMultiply(List<int[]> first, List<int[]> second) {
        Matrix matrix1 = new Basic2DMatrix(convertTo2DdoubleArray(first));
        Matrix matrix2 = new Basic2DMatrix(convertTo2DdoubleArray(second));

        Matrix result = matrix1.multiply(matrix2);

        return convertToList(result);
    }

    /* Calculatin Hy */
    public static List<int[]> makeHy(List<int[]> Gy, List<Integer> infoSystem) {
        var result = new ArrayList<int[]>();

        var matrixWithoutUnit = new ArrayList<int[]>();
        for (int i = 0; i < Gy.size(); ++i) {
            var bufIntArray = new int[Gy.get(0).length - infoSystem.size()];
            matrixWithoutUnit.add(bufIntArray);
        }

        int k = 0;
        for (int col = 0; col < Gy.get(0).length; ++col) {
            if (!infoSystem.contains(col)) {
                for (int row = 0; row < Gy.size(); ++row)
                    matrixWithoutUnit.get(row)[k] = Gy.get(row)[col];
                ++k;
            }
        }

        Matrix matrix = new Basic2DMatrix(convertTo2DdoubleArray(matrixWithoutUnit));
        var transpose = convertToList(matrix.transpose());

        for (int i = 0; i < Gy.get(0).length - Gy.size(); ++i) {
            var bufIntArray = new int[Gy.get(0).length];
            result.add(bufIntArray);
        }

        int l = 0;
        for (int i = 0; i < infoSystem.size(); ++i) {
            for (int row = 0; row < transpose.size(); ++row)
                result.get(row)[infoSystem.get(i)] = transpose.get(row)[l];
            ++l;
        }

        int m = 0;
        for (int i = 0; i < Gy.get(0).length; ++i) {
            if(!infoSystem.contains(i)) {
                result.get(m)[i] = 1;
                ++m;
            }
        }

        return result;
    }

    public static double[][] messageToMatrix(int[] message) {
        var result = new double[1][message.length];

        for(int i = 0; i < message.length; ++i)
            result[0][i] = message[i];

        return result;
    }
}