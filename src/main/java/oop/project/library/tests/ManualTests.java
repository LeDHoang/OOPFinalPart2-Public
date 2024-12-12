// File: oop/project/library/tests/ManualTests.java
package oop.project.library.tests;

import oop.project.library.command.Command;
import oop.project.library.parser.*;
import oop.project.library.scenarios.LocalDateParser;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class ManualTests {

    public static void main(String[] args) {
        testFizzBuzzCommandValidInput();
        testFizzBuzzCommandInvalidInput();
        testFizzBuzzCommandOutOfRange();
        testDifficultyCommandValidInput();
        testDifficultyCommandInvalidChoice();
        testEchoCommandWithMessage();
        testEchoCommandWithoutMessage();
        testSubCommandValidInput();
        testSubCommandInvalidInput();
        testWeekdayCommandValidInput();
        testWeekdayCommandInvalidInput();
        // Add more test methods as needed
    }

    public static void testFizzBuzzCommandValidInput() {
        Command fizzBuzzCommand = new Command("fizzbuzz")
                .addArgument("number", new IntegerParser(), true, false);

        try {
            Map<String, Object> result = fizzBuzzCommand.parse("15");
            int number = (Integer) result.get("number");
            if (number == 15) {
                System.out.println("testFizzBuzzCommandValidInput passed");
            } else {
                System.out.println("testFizzBuzzCommandValidInput failed: Expected 15, got " + number);
            }
        } catch (Exception e) {
            System.out.println("testFizzBuzzCommandValidInput failed with exception: " + e.getMessage());
        }
    }

    public static void testFizzBuzzCommandInvalidInput() {
        Command fizzBuzzCommand = new Command("fizzbuzz")
                .addArgument("number", new IntegerParser(), true, false);

        try {
            fizzBuzzCommand.parse("abc");
            System.out.println("testFizzBuzzCommandInvalidInput failed (expected exception)");
        } catch (ArgumentParseException e) {
            System.out.println("testFizzBuzzCommandInvalidInput passed with expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testFizzBuzzCommandInvalidInput failed with unexpected exception: " + e.getMessage());
        }
    }

    public static void testFizzBuzzCommandOutOfRange() {
        Command fizzBuzzCommand = new Command("fizzbuzz")
                .addArgument("number", new IntegerParser(), true, false);

        try {
            Map<String, Object> result = fizzBuzzCommand.parse("150");
            int number = (Integer) result.get("number");
            if (number < 1 || number > 100) {
                System.out.println("testFizzBuzzCommandOutOfRange passed (number out of range)");
            } else {
                System.out.println("testFizzBuzzCommandOutOfRange failed: Number within range");
            }
        } catch (Exception e) {
            System.out.println("testFizzBuzzCommandOutOfRange failed with exception: " + e.getMessage());
        }
    }

    public static void testDifficultyCommandValidInput() {
        Command difficultyCommand = new Command("difficulty")
                .addArgument("difficulty", new StringParser(), true, false);

        try {
            Map<String, Object> result = difficultyCommand.parse("hard");
            String difficulty = (String) result.get("difficulty");
            Set<String> validDifficulties = Set.of("easy", "normal", "hard", "peaceful");
            if (validDifficulties.contains(difficulty.toLowerCase())) {
                System.out.println("testDifficultyCommandValidInput passed");
            } else {
                System.out.println("testDifficultyCommandValidInput failed: Invalid difficulty");
            }
        } catch (Exception e) {
            System.out.println("testDifficultyCommandValidInput failed with exception: " + e.getMessage());
        }
    }

    public static void testDifficultyCommandInvalidChoice() {
        Command difficultyCommand = new Command("difficulty")
                .addArgument("difficulty", new StringParser(), true, false);

        try {
            Map<String, Object> result = difficultyCommand.parse("extreme");
            String difficulty = (String) result.get("difficulty");
            Set<String> validDifficulties = Set.of("easy", "normal", "hard", "peaceful");
            if (!validDifficulties.contains(difficulty.toLowerCase())) {
                System.out.println("testDifficultyCommandInvalidChoice passed (invalid choice)");
            } else {
                System.out.println("testDifficultyCommandInvalidChoice failed: Valid difficulty");
            }
        } catch (Exception e) {
            System.out.println("testDifficultyCommandInvalidChoice failed with exception: " + e.getMessage());
        }
    }

    public static void testEchoCommandWithMessage() {
        Command echoCommand = new Command("echo")
                .addArgument("message", new StringParser(), false, false);

        try {
            Map<String, Object> result = echoCommand.parse("Hello, World!");
            String message = (String) result.get("message");
            if ("Hello, World!".equals(message)) {
                System.out.println("testEchoCommandWithMessage passed");
            } else {
                System.out.println("testEchoCommandWithMessage failed: Expected 'Hello, World!', got '" + message + "'");
            }
        } catch (Exception e) {
            System.out.println("testEchoCommandWithMessage failed with exception: " + e.getMessage());
        }
    }

    public static void testEchoCommandWithoutMessage() {
        Command echoCommand = new Command("echo")
                .addArgument("message", new StringParser(), false, false);

        try {
            Map<String, Object> result = echoCommand.parse("");
            if (!result.containsKey("message")) {
                System.out.println("testEchoCommandWithoutMessage passed");
            } else {
                System.out.println("testEchoCommandWithoutMessage failed: Message should not be present");
            }
        } catch (Exception e) {
            System.out.println("testEchoCommandWithoutMessage failed with exception: " + e.getMessage());
        }
    }

    public static void testSubCommandValidInput() {
        Command subCommand = new Command("sub")
                .addArgument("left", new DoubleParser(), true, true)
                .addArgument("right", new DoubleParser(), true, true);

        try {
            Map<String, Object> result = subCommand.parse("--left 5.0 --right 3.0");
            double left = (Double) result.get("left");
            double right = (Double) result.get("right");
            if (left == 5.0 && right == 3.0) {
                System.out.println("testSubCommandValidInput passed");
            } else {
                System.out.println("testSubCommandValidInput failed: Expected 5.0 and 3.0, got " + left + " and " + right);
            }
        } catch (Exception e) {
            System.out.println("testSubCommandValidInput failed with exception: " + e.getMessage());
        }
    }

    public static void testSubCommandInvalidInput() {
        Command subCommand = new Command("sub")
                .addArgument("left", new DoubleParser(), true, true)
                .addArgument("right", new DoubleParser(), true, true);

        try {
            subCommand.parse("--left abc --right 3.0");
            System.out.println("testSubCommandInvalidInput failed (expected exception)");
        } catch (ArgumentParseException e) {
            System.out.println("testSubCommandInvalidInput passed with expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testSubCommandInvalidInput failed with unexpected exception: " + e.getMessage());
        }
    }

    public static void testWeekdayCommandValidInput() {
        Command weekdayCommand = new Command("weekday")
                .addArgument("date", new LocalDateParser(), true, false);

        try {
            Map<String, Object> result = weekdayCommand.parse("2024-10-23");
            LocalDate date = (LocalDate) result.get("date");
            if (date.equals(LocalDate.of(2024, 10, 23))) {
                System.out.println("testWeekdayCommandValidInput passed");
            } else {
                System.out.println("testWeekdayCommandValidInput failed: Expected 2024-10-23, got " + date);
            }
        } catch (Exception e) {
            System.out.println("testWeekdayCommandValidInput failed with exception: " + e.getMessage());
        }
    }

    public static void testWeekdayCommandInvalidInput() {
        Command weekdayCommand = new Command("weekday")
                .addArgument("date", new LocalDateParser(), true, false);

        try {
            weekdayCommand.parse("invalid-date");
            System.out.println("testWeekdayCommandInvalidInput failed (expected exception)");
        } catch (ArgumentParseException e) {
            System.out.println("testWeekdayCommandInvalidInput passed with expected exception: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testWeekdayCommandInvalidInput failed with unexpected exception: " + e.getMessage());
        }
    }
}
