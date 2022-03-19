package com.KonstKudryavtsev;


public class MagicSquareBuilder {

    public static int[][] build(int n) {

        if (n % 2 != 0)
            return buildOdd(n);
        else if (n % 4 == 0)
            return buildDoublyEven(n);
        else
            return buildWithLUXmethod(n);
    }

    /*
     ** Widely used algorithm for squares with odd size (n).
     ** 1) We start from the middle of the top row. Place 1.
     ** 2) First we check the cell which is up and left from the one we just filled.
     ** 3) If we ended up being out of the matrix borders, we fill the cell with same 'x' and 'y-n'.
     **      I.E. for a square 3х3 (coordinates start from 0):
     ** from a cell {2, 2} we get into a non-existent cell {1, 3}. 'х' stays the same, and 'y - 3' = 0.
     ** So, we get to the cell {1, 0}
     ** If we end up being out of the matrix borders, we fill the cell with same 'y' and 'x+n'.
     ** 4) If current coordinates are out of existing rows and columns (further than far right angle),
     **      then we use the "filled cell" rule:
     ** 5) If we get to an already filled cell, we get back to the previous one and from there one cell down.
     */

    private static int[][] buildOdd(int n) {

        int[][] matrix = new int[n][n];
        int i = n / 2;
        int j = n - 1;
        int count = 1;
        matrix[n / 2][n - 1] = count;

        for (int k = 0; k < (n * n)-1; k++) {
            i -= 1;
            j += 1;
            if (i == -1 && j == n) {
                i = 0;
                j = n - 2;
            }
            if (i == -1)
                i = n - 1;
            if (j == n)
                j = 0;
            if (matrix[i][j] != 0) {
                i += 1;
                j -= 2;
            }
            matrix[i][j] = ++count;
        }
        return matrix;
    }

    /*
     ** An algorithm for n, divisible by 4.
     ** 1) We divide our square into smaller square areas: squares n/4 * n/4 near angle and a center square n/2 на n/2.
     ** 2) We move from cell to cell (right-and-down), starting from the top-left and fill (starting with 1)
     **      with digits only the areas, described in p.1,
     **      but we don't stop the count, when we move through the cell, which we don't fill.
     ** 3) Now we move through the main square (left-and-up), staring again from 1 and from bottom-left corner.
     **      We fill the cells using the same principles, but now ignoring already filled cells.
     */

    private static int[][] buildDoublyEven(int n) {

        int[][] matrix = new int[n][n];
        int count = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i < (n / 4)) {
                    if (j < (n / 4) || j >= n - (n / 4))
                        matrix[i][j] = count;
                } else if (i >= n - (n / 4)) {
                    if (j < (n / 4) || j >= n - (n / 4))
                        matrix[i][j] = count;
                } else if ((i >= (n / 4) && i < n - (n / 4)) && (j >= (n / 4) && j < n - (n / 4))) {
                    matrix[i][j] = count;
                }
                count++;
            }
        }

        count = 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == 0)
                    matrix[i][j] = count;
                count++;
            }
        }

        return matrix;
    }

    /*
    ** https://en.wikipedia.org/wiki/Conway%27s_LUX_method_for_magic_squares
     */

    private static int[][] buildWithLUXmethod(int n) {

        int k = n / 4;
        int smallMatrixSize = 2 * k + 1;
        String[][] smallerMatrix = new String[smallMatrixSize][smallMatrixSize];
        int[][] matrix = new int[n][n];

        fillSmallerMatrix(smallerMatrix, 2 * k + 1, k);

        int i = 1;
        int j = (smallMatrixSize/2)-1;
        int count = 1;

        for (int z = 0; z < (smallMatrixSize * smallMatrixSize) ; z++) {
            i -= 1;
            j += 1;
            if (i == -1 && j == smallMatrixSize) {
                i += 2;
                j -= 1;
            }
            if (i == -1)
                i = smallMatrixSize - 1;
            if (j == smallMatrixSize)
                j = 0;
            if (smallerMatrix[i][j].equals("F")) {
                i += 2;
                j -= 1;
            }
            count = LUXpatternFill(matrix, smallerMatrix[i][j], i, j, count);
            smallerMatrix[i][j] = "F";
        }

        return matrix;
    }


    private static void fillSmallerMatrix(String[][] smallerMatrix, int n, int k) {

        int i;
        int j;

        for (i = 0; i <= k; i++) {
            for (j = 0; j < n; ) {
                smallerMatrix[i][j] = "L";
                j++;
            }
        }

        for (j = 0; j < n; j++) {
            smallerMatrix[i][j] = "U";
        }
        i++;

        for (; i < n; i++) {
            for (j = 0; j < n; ) {
                smallerMatrix[i][j] = "X";
                j++;
            }
        }

        String temp = smallerMatrix[k][k];
        smallerMatrix[k][k] = smallerMatrix[k + 1][k];
        smallerMatrix[k + 1][k] = temp;
    }

    private static int LUXpatternFill(int [][]matrix, String str, int i, int j, int count) {

        i *= 2;
        j *= 2;

        switch (str) {
            case "L":
                matrix[i][j + 1] = count++;
                matrix[i + 1][j] = count++;
                matrix[i + 1][j + 1] = count++;
                matrix[i][j] = count++;
                break;
            case "U":
                matrix[i][j] = count++;
                matrix[i + 1][j] = count++;
                matrix[i + 1][j + 1] = count++;
                matrix[i][j + 1] = count++;
                break;
            case "X":
                matrix[i][j] = count++;
                matrix[i + 1][j + 1] = count++;
                matrix[i + 1][j] = count++;
                matrix[i][j + 1] = count++;
                break;
        }
        return count;
    }
}


