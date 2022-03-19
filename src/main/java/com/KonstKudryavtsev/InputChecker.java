package com.KonstKudryavtsev;

public class InputChecker {

    public static int parseAndCheck(String[] args) {

        if (args.length != 1) {
            System.out.println("Please, run the program with only 1 number as an argument!");
            throw new IllegalArgumentException();
        }

        int parsedInt;   // int, because bigger numbers would be computed for a very long time
        try {
            parsedInt = Integer.parseInt(args[0]);
            if (parsedInt <= 0)
                throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            System.out.println("Please, run the program with correct argument! (Only natural number excepted, No zeroes)");
            throw new IllegalArgumentException();
        }
        return parsedInt;
    }
}
