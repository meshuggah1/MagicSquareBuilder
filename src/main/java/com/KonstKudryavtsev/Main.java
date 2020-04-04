package com.KonstKudryavtsev;

/*
Решение магического квадрата. На вход получаем параметр N. На выходе – заполненная матрица.class
Маги́ческий, или волше́бный квадра́т — квадратная таблица n x n, заполненная n^2 различными числами таким образом,
что сумма чисел в каждой строке, каждом столбце и на обеих диагоналях одинакова.
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
