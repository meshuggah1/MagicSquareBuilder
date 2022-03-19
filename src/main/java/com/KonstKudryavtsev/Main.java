package com.KonstKudryavtsev;

/*
Solving the Magic Square. We have an input parameter N. The outcome is a filled matrix.
Magic square — is a matrix n x n, filled with n^2 of different numbers such that,
sum of those numbers in each row, column and both main diagonals is the same.
*/

public class Main {
    public static void main(String[] args) {

        int n = InputChecker.parseAndCheck(args);

        int [][]matrix = MagicSquareBuilder.build(n);

        assert matrix != null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print("[" + matrix[i][j] + "]");
            }
            System.out.println();
        }
    }
}
