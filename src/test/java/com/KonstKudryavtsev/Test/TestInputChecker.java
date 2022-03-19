package com.KonstKudryavtsev.Test;

import com.KonstKudryavtsev.InputChecker;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInputChecker {
    @Test
    public void testInputChecker() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        System.setOut(stream);

        String []testStr = {"", "qwe"};
        try {
            InputChecker.parseAndCheck(testStr);
        } catch (IllegalArgumentException ignored){}

        assertTrue(outputStream.toString().contains("Please, run the program with only 1 number as an argument!"));

        outputStream.reset();

        String [][]testArrOfStrArrays = {{"7.0"}, {"-4"}, {"0"}, {"fd"}, {"5.f"}};

        for (String []s: testArrOfStrArrays) {
            try {
                InputChecker.parseAndCheck(s);
            } catch (IllegalArgumentException ignored){}

            assertFalse(outputStream.toString().contains("Please, run the program with only 1 number as an argument!"));
            assertTrue(outputStream.toString().contains("Please, run the program with correct argument! (Only natural number excepted, No zeroes"));

            outputStream.reset();
        }

        testStr = new String[]{"7"};
        try {
            InputChecker.parseAndCheck(testStr);
        } catch (IllegalArgumentException ignored){}

        assertFalse(outputStream.toString().contains("Please, run the program with only 1 number as an argument!"));
        assertFalse(outputStream.toString().contains("Please, run the program with correct argument! (Only natural number excepted, No zeroes"));
    }
}