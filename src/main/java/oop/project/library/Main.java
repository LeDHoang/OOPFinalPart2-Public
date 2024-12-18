package oop.project.library;

import oop.project.library.command.Command;
import oop.project.library.lexer.LexException;
import oop.project.library.lexer.Lexer;
import oop.project.library.parser.*;
import oop.project.library.scenarios.LocalDateParser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static final Map<String, Command> commands = new HashMap<>();

    public static void main(String[] args) {
        defineCommands();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }

            // Split input into command name and arguments
            String[] parts = input.trim().split("\\s+", 2);
            String commandName = parts[0];
            String arguments = parts.length > 1 ? parts[1] : "";

            Command command = commands.get(commandName);

            if (command != null) {
                try {
                    Map<String, Object> parsedArguments = command.parse(arguments);
                    executeCommand(commandName, parsedArguments);
                } catch (LexException | ArgumentParseException e) {
                    System.out.println("Error executing command: " + e.getMessage());
                } catch (RuntimeException e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }
            } else if (commandName.equals("lex")) {
                // Special case for lex command
                try {
                    Map<String, String> tokens = Lexer.lex(arguments);
                    System.out.println("Lexed arguments: " + tokens);
                } catch (LexException e) {
                    System.out.println("Error during lexing: " + e.getMessage());
                }
            } else {
                System.out.println("Unknown command: " + commandName);
            }
        }
    }

    private static void defineCommands() {
        // Add Command
        Command addCommand = new Command("add")
                .addArgument("left", new IntegerParser(), true, false)
                .addArgument("right", new IntegerParser(), true, false)
                .addArgument("third", new StringParser(), false, false);
        commands.put("add", addCommand);

        // Sub Command
        Command subCommand = new Command("sub")
                .addArgument("left", new DoubleParser(), true, true)
                .addArgument("right", new DoubleParser(), true, true)
                .addArgument("third", new StringParser(), false, false);
        commands.put("sub", subCommand);

        // FizzBuzz Command
        Command fizzBuzzCommand = new Command("fizzbuzz")
                .addArgument("number", new IntegerParser(), true, false)
                .addArgument("second", new StringParser(), false, false);
        commands.put("fizzbuzz", fizzBuzzCommand);

        // Difficulty Command
        Command difficultyCommand = new Command("difficulty")
                .addArgument("difficulty", new StringParser(), true, false)
                .addArgument("second", new StringParser(), false, false);
        commands.put("difficulty", difficultyCommand);

        // Echo Command
        Command echoCommand = new Command("echo")
                .addArgument("message", new StringParser(), false, false);
        commands.put("echo", echoCommand);

        // Search Command
        Command searchCommand = new Command("search")
                .addArgument("term", new StringParser(), true, false)
                .addArgument("second", new StringParser(), false, false)
                .addArgument("case-insensitive", new BooleanParser(), false, true);
        commands.put("search", searchCommand);

        // Weekday Command
        Command weekdayCommand = new Command("weekday")
                .addArgument("date", new LocalDateParser(), true, false)
                .addArgument("second", new StringParser(), false, false);
        commands.put("weekday", weekdayCommand);
    }

    private static void executeCommand(String commandName, Map<String, Object> arguments) {
        switch (commandName) {
            case "add":
                executeAddCommand(arguments);
                break;
            case "sub":
                executeSubCommand(arguments);
                break;
            case "fizzbuzz":
                executeFizzBuzzCommand(arguments);
                break;
            case "difficulty":
                executeDifficultyCommand(arguments);
                break;
            case "echo":
                executeEchoCommand(arguments);
                break;
            case "search":
                executeSearchCommand(arguments);
                break;
            case "weekday":
                executeWeekdayCommand(arguments);
                break;
            default:
                System.out.println("Unknown command: " + commandName);
                break;
        }
    }

    // Command execution methods

    private static void executeAddCommand(Map<String, Object> arguments) {
        String third = (String) arguments.get("third");
        if (third != null) {
            throw new IllegalArgumentException("Error: add command should only have two terms, but a third term was provided.");
        }
        int left = (Integer) arguments.get("left");
        int right = (Integer) arguments.get("right");
        int result = left + right;
        System.out.println("Result: " + result);
    }

    private static void executeSubCommand(Map<String, Object> arguments) {
        String third = (String) arguments.get("third");
        if (third != null) {
            throw new IllegalArgumentException("Error: sub command should only have two terms, but a third term was provided.");
        }
        double left = (Double) arguments.get("left");
        double right = (Double) arguments.get("right");
        double result = left - right;
        System.out.println("Result: " + result);
    }

    private static void executeFizzBuzzCommand(Map<String, Object> arguments) {
        String second = (String) arguments.get("second");
        if (second != null) {
            throw new IllegalArgumentException("Error: fizzbuzz command should only have one term, but a second term was provided.");
        }
        int number = (Integer) arguments.get("number");
        if (number < 1 || number > 100) {
            System.out.println("Error: Number must be between 1 and 100.");
            return;
        }
        for (int i = 1; i <= number; i++) {
            if (i % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(i);
            }
        }
    }

    private static void executeDifficultyCommand(Map<String, Object> arguments) {
        String second = (String) arguments.get("second");
        if (second != null) {
            throw new IllegalArgumentException("Error: difficulty command should only have one term, but a second term was provided.");
        }
        String difficulty = (String) arguments.get("difficulty");
        Set<String> validDifficulties = Set.of("easy", "normal", "hard", "peaceful");
        if (!validDifficulties.contains(difficulty.toLowerCase())) {
            System.out.println("Error: Invalid difficulty level. Valid options are: " + validDifficulties);
            return;
        }
        System.out.println("Difficulty set to: " + difficulty);
    }

    private static void executeEchoCommand(Map<String, Object> arguments) {
        String message = (String) arguments.get("message");
        if (message == null) {
            message = "Echo, echo, echo...";
        }
        System.out.println(message);
    }

    private static void executeSearchCommand(Map<String, Object> arguments) {
        String second = (String) arguments.get("second");
        if (second != null) {
            throw new IllegalArgumentException("Error: search command should only have one term, but a second term was provided.");
        }
        String term = (String) arguments.get("term");
        Boolean caseInsensitive = (Boolean) arguments.get("case-insensitive");
        if (caseInsensitive == null) {
            caseInsensitive = false;
        }
        System.out.println("Searching for '" + term + "' with case-insensitive set to " + caseInsensitive);
        // Implement your search logic here
    }

    private static void executeWeekdayCommand(Map<String, Object> arguments) {
        String second = (String) arguments.get("second");
        if (second != null) {
            throw new IllegalArgumentException("Error: weekday command should only have one term, but a second term was provided.");
        }
        LocalDate date = (LocalDate) arguments.get("date");
        System.out.println("The day of the week is: " + date.getDayOfWeek());
    }
}
