package com.KonstKudryavtsev;

public class MagicSquareBuilder {

    public static int[][] build(int n) {

        if (n % 2 != 0)
            return buildOdd(n);
        else if (n % 4 == 0)
            return buildDoublyEven(n);
        else
            System.out.println("Yet to be done!");
        return null;
    }

    private static int[][] buildOdd(int n) {

        int [][]matrix = new int[n][n];
        int i = n/2;
        int j = n-1;
        int count = 1;
        matrix[n/2][n-1] = count;

        for (int k = 0; k < (n*n)-1; k++) {
            i -= 1;
            j += 1;
            if (i == -1 && j == n) {
                i = 0;
                j = n - 2;
            }
            if (i == -1)
                i = n-1;
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

    private static int[][] buildDoublyEven(int n) {

        int [][]matrix = new int[n][n];
        int count = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i < (n/4)) {
                    if (j < (n/4) || j >= n - (n/4))
                        matrix[i][j] = count;
                }
                else if (i >= n - (n/4)) {
                    if (j < (n/4) || j >= n - (n/4))
                        matrix[i][j] = count;
                    }
                else if ((i >= (n/4) && i < n - (n/4)) && (j >= (n/4) && j < n - (n/4) )) {
                    matrix[i][j] = count;
                }
                count++;
            }
        }

        count = 1;
        for (int i = n-1; i >= 0; i--) {
            for (int j = n-1; j >= 0; j--) {
                if (matrix[i][j] == 0)
                    matrix[i][j] = count;
                count++;
            }
        }

        return matrix;
    }
}


