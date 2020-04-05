package com.KonstKudryavtsev.Test;

import com.KonstKudryavtsev.MagicSquareBuilder;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Random;


public class TestMatrixBuilder {

    //Тестовый метод работает для входного числа < 216 (из-за переполнения magicNum и check)
    @ParameterizedTest
    @ValueSource(ints = { 3, 4, 7, 12, 20, 67, 216})
    public void testBuild(int n) {

        int [][]matrix = MagicSquareBuilder.build(n);
        assert matrix != null;


        //check if all numbers are different
        HashMap<Integer, Integer> usedNums = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Assert.assertFalse(usedNums.containsValue(matrix[i][j]));
                usedNums.put(matrix[i][j], matrix[i][j]);
            }
        }

        long magicNum = ((n * n * (n * n + 1)) / (n * 2));
        long check = 0;
        //check diagonal
        for (int i = 0; i < n; i++) {
            check += matrix[i][i];
        }
        Assert.assertEquals(magicNum, check);

        check = 0;
        //check another diagonal
        for (int i = n-1; i >= 0; i--) {
            check += matrix[i][i];
        }
        Assert.assertEquals(magicNum, check);

        check = 0;
        //check top row
        for (int j = 0; j < n; j++) {
            check += matrix[0][j];
        }
        Assert.assertEquals(magicNum, check);

        check = 0;
        //check most right column
        for (int i = 0; i < n; i++) {
            check += matrix[i][n-1];
        }
        Assert.assertEquals(magicNum, check);

        check = 0;
        //check random column
        Random random = new Random();
        int j = random.nextInt(n);
        for (int i = 0; i < n; i++) {
            check += matrix[i][j];
        }
        Assert.assertEquals(magicNum, check);
    }
}
