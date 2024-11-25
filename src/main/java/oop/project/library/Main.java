package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.command.CommandManager;
import oop.project.library.parser.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Initialize Command Manager
        CommandManager commandManager = new CommandManager();

        // Define Commands
        defineCommands(commandManager);

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

            Command command = commandManager.getCommand(commandName);

            if (command != null) {
                try {
                    Map<String, Object> result = command.parse(arguments);
                    System.out.println("Parsed arguments: " + result);

                    // Execute command logic
                    executeCommand(commandName, result);
                } catch (Exception e) {
                    System.out.println("Error parsing command: " + e.getMessage());
                }
            } else {
                System.out.println("Unknown command: " + commandName);
            }
        }
    }

    private static void defineCommands(CommandManager commandManager) {
        // FizzBuzz Command
        Command fizzBuzzCommand = new Command("fizzbuzz")
                .addArgument("number", new RangeParser(1, 100), true, false); // Positional required argument

        // Difficulty Command
        Command difficultyCommand = new Command("setDifficulty")
                .addArgument("level", new ChoiceParser(Set.of("easy", "normal", "hard", "peaceful")), true, false); // Positional required argument

        // Echo Command
        Command echoCommand = new Command("echo")
                .addArgument("message", new StringParser(), false, false); // Optional positional argument

        // Register Commands
        commandManager.registerCommand(fizzBuzzCommand);
        commandManager.registerCommand(difficultyCommand);
        commandManager.registerCommand(echoCommand);

        // Add more commands as needed
    }

    private static void executeCommand(String commandName, Map<String, Object> arguments) {
        switch (commandName) {
            case "fizzbuzz":
                int number = (Integer) arguments.get("number");
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
                break;
            case "setDifficulty":
                String level = (String) arguments.get("level");
                System.out.println("Difficulty set to: " + level);
                break;
            case "echo":
                String message = (String) arguments.get("message");
                System.out.println(message != null ? message : "");
                break;
            default:
                System.out.println("Command execution not implemented for: " + commandName);
                break;
        }
    }
}
