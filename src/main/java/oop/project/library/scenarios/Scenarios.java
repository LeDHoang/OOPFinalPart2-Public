// File: oop/project/library/scenarios/Scenarios.java
package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.parser.*;
import oop.project.library.lexer.LexException;
import oop.project.library.scenarios.LocalDateParser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Scenarios {

    // Define commands here
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        // Add Command (exactly two positional arguments)
        Command addCommand = new Command("add")
                .addArgument("left", new IntegerParser(), true, false)
                .addArgument("right", new IntegerParser(), true, false);
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

        // Search Command (one positional arg and one optional named arg)
        Command searchCommand = new Command("search")
                .addArgument("term", new StringParser(), true, false)
                .addArgument("case-insensitive", new BooleanParser(), false, true);
        commands.put("search", searchCommand);

        // Weekday Command
        Command weekdayCommand = new Command("weekday")
                .addArgument("date", new LocalDateParser(), true, false)
                .addArgument("second", new StringParser(), false, false);
        commands.put("weekday", weekdayCommand);
    }

    public static Result<Map<String, Object>> parse(String command) {
        var split = command.trim().split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "lex" -> lex(arguments);
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "fizzbuzz" -> fizzbuzz(arguments);
            case "difficulty" -> difficulty(arguments);
            case "echo" -> echo(arguments);
            case "search" -> search(arguments);
            case "weekday" -> weekday(arguments);
            default -> new Result.Failure<>("Undefined command " + base + ".");
        };
    }

    private static Result<Map<String, Object>> lex(String arguments) {
        try {
            Map<String, String> tokens = oop.project.library.lexer.Lexer.lex(arguments);
            Map<String,Object> result = new HashMap<>(tokens);
            return new Result.Success<>(result);
        } catch (LexException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> add(String arguments) {
        var res = parseWithCommand("add", arguments);
        if (!(res instanceof Result.Success<?>)) {
            return res; // It's already a failure
        }

        // Ensure exactly two arguments were provided to "add"
        try {
            var lexedArgs = oop.project.library.lexer.Lexer.lex(arguments);
            // add expects exactly 2 positional arguments
            if (lexedArgs.size() != 2) {
                return new Result.Failure<>("Too many arguments provided to add command.");
            }
        } catch (LexException e) {
            return new Result.Failure<>(e.getMessage());
        }

        return res;
    }

    private static Result<Map<String, Object>> sub(String arguments) {
        return parseWithCommand("sub", arguments);
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        var res = parseWithCommand("fizzbuzz", arguments);
        if (!(res instanceof Result.Success<?> s)) {
            return res;
        }
        Map<String, Object> parsed = (Map<String, Object>) s.value();
        int number = (Integer) parsed.get("number");
        if (number < 1 || number > 100) {
            return new Result.Failure<>("Number must be between 1 and 100 inclusive.");
        }
        return res;
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        var res = parseWithCommand("difficulty", arguments);
        if (!(res instanceof Result.Success<?> s)) {
            return res;
        }

        Map<String,Object> parsed = (Map<String,Object>) s.value();
        String difficulty = (String) parsed.get("difficulty");
        Set<String> validDifficulties = Set.of("easy", "normal", "hard", "peaceful");

        if (!validDifficulties.contains(difficulty.toLowerCase())) {
            return new Result.Failure<>("Invalid difficulty: " + difficulty + ". Expected one of: " + validDifficulties);
        }

        return res;
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        var res = parseWithCommand("echo", arguments);
        if (!(res instanceof Result.Success<?> s)) {
            return res;
        }

        Map<String, Object> parsed = (Map<String, Object>) s.value();
        if (!parsed.containsKey("message") || ((String)parsed.get("message")).isEmpty()) {
            parsed.put("message", "Echo, echo, echo!");
        }
        return new Result.Success<>(parsed);
    }

    private static Result<Map<String, Object>> search(String arguments) {
        var res = parseWithCommand("search", arguments);
        if (!(res instanceof Result.Success<?> s)) {
            return res;
        }

        Map<String,Object> parsed = (Map<String,Object>) s.value();

        // Ensure only one positional argument is present
        try {
            var lexedArgs = oop.project.library.lexer.Lexer.lex(arguments);
            int positionalCount = 0;
            for (String key : lexedArgs.keySet()) {
                if (key.matches("\\d+")) {
                    positionalCount++;
                }
            }
            if (positionalCount > 1) {
                return new Result.Failure<>("Too many arguments provided to search command.");
            }
        } catch (LexException e) {
            return new Result.Failure<>(e.getMessage());
        }

        // If case-insensitive not provided, default to false
        if (!parsed.containsKey("case-insensitive")) {
            parsed.put("case-insensitive", false);
        }

        return new Result.Success<>(parsed);
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        return parseWithCommand("weekday", arguments);
    }

    private static Result<Map<String, Object>> parseWithCommand(String commandName, String arguments) {
        Command cmd = commands.get(commandName);
        if (cmd == null) {
            return new Result.Failure<>("Command not found: " + commandName);
        }

        try {
            Map<String, Object> parsed = cmd.parse(arguments);
            return new Result.Success<>(parsed);
        } catch (LexException | ArgumentParseException e) {
            return new Result.Failure<>(e.getMessage());
        }
    }
}
