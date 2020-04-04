package com.KonstKudryavtsev.Test;

import com.KonstKudryavtsev.InputChecker;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestInputChecker {
    @Test
    public void testInputChecker() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        System.setOut(stream);

        String []testStr = {"", "qwe"};
        try {
            InputChecker.parseAndCheck(testStr);
        }catch (IllegalArgumentException ignored){}

        Assert.assertTrue(outputStream.toString().contains("Please, run the program with only 1 number as an argument!"));

        outputStream.reset();

        String [][]testArrOfStrArrays = {{"7.0"}, {"-4"}, {"0"}, {"fd"}, {"5.f"}};

        for (String []s: testArrOfStrArrays) {
            try {
                InputChecker.parseAndCheck(s);
            }catch (IllegalArgumentException ignored){}

            Assert.assertFalse(outputStream.toString().contains("Please, run the program with only 1 number as an argument!"));
            Assert.assertTrue(outputStream.toString().contains("Please, run the program with correct argument! (Only natural number excepted, No zeroes"));

            outputStream.reset();
        }

        testStr = new String[]{"7"};
        try {
            InputChecker.parseAndCheck(testStr);
        }catch (IllegalArgumentException ignored){}

        Assert.assertFalse(outputStream.toString().contains("Please, run the program with only 1 number as an argument!"));
        Assert.assertFalse(outputStream.toString().contains("Please, run the program with correct argument! (Only natural number excepted, No zeroes"));
    }
}