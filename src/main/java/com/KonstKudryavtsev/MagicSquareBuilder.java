package com.KonstKudryavtsev;

import java.io.IOException;

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
     ** Широко применяемый алгоритм для квадратов с нечетным n.
     ** 1) Начинаем с середины верхнего ряда. Ставим 1.
     ** 2) Сначала проверяем ячейку наверх по диагонали от только что заполненной.
     ** 3) Если вышли за пределы нумерации столбцов, заполняем ячейку с той же x и y-n.
     **      Напр. для кв. 3х3 (коорд. начинаются с 0):
     ** из яч {2, 2} попали по диагонали в несуществующую яч. {1, 3}. х не меняем, y - 3 = 0.
     ** Соответственно, попадаем в яч. с коорд. {1, 0}
     ** Если вышли за пределы нумерации строк, заполняем ячейку с той же y и x+n.
     ** 4) Если вышли за пределы одновременно и строк и столбцов (за правый верхний угол), то
     **      применяется правило занятой ячейки.
     ** 5) Если попали на занятую ячейку, то возвращаемся на предыдущую и от нее и на одну вниз.
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
     ** Алгоритм для n, кратных 4.
     ** 1) Выделяются области квадрата: углы размером n/4 * n/4 и центр размером n/2 на n/2.
     ** 2) Идем по ячейкам квадрата (направо-вниз), начиная с верхней левой и заполняем (начиная с 1)
     **      цифрами только выделенные в п.1 области,
     **      счет при этом не прекращая даже во время прохода незаполняемых ячеек.
     ** 3) Теперь идем по квадрату (налево-вверх), начиная счет снова с 1 и с нижнего правого угла.
     **      Заполняем по тому же принципу, только теперь игнорируя ранее заполненные ячейки.
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


