// File: oop/project/library/scenarios/Scenarios.java
package oop.project.library.scenarios;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.*;

public class Scenarios {

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
            default -> Result.error(new IllegalArgumentException("Undefined command " + base + "."));
        };
    }

    private static Result<Map<String, Object>> lex(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);
            Map<String, Object> result = new LinkedHashMap<>(lexedArgs);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    private static Result<Map<String, Object>> add(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);
            if (lexedArgs.size() != 2) {
                return Result.error(new IllegalArgumentException("Invalid number of arguments for add command. Expected 2 arguments."));
            }

            ArgumentParser<Integer> intParser = new IntegerParser();

            String leftStr = lexedArgs.get("0");
            String rightStr = lexedArgs.get("1");

            if (leftStr == null || rightStr == null) {
                return Result.error(new IllegalArgumentException("Missing arguments for add command"));
            }

            Integer left = intParser.parse(leftStr);
            Integer right = intParser.parse(rightStr);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("left", left);
            result.put("right", right);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    private static Result<Map<String, Object>> sub(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);
            if (lexedArgs.size() != 2 || !lexedArgs.containsKey("left") || !lexedArgs.containsKey("right")) {
                return Result.error(new IllegalArgumentException("Missing or invalid arguments for sub command. Expected named arguments: --left and --right."));
            }

            ArgumentParser<Double> doubleParser = new DoubleParser();

            Double left = doubleParser.parse(lexedArgs.get("left"));
            Double right = doubleParser.parse(lexedArgs.get("right"));

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("left", left);
            result.put("right", right);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);
            if (lexedArgs.size() != 1) {
                return Result.error(new IllegalArgumentException("Invalid number of arguments for fizzbuzz command. Expected 1 argument."));
            }

            ArgumentParser<Integer> intParser = new IntegerParser();
            Integer number = intParser.parse(lexedArgs.get("0"));

            if (number < 1 || number > 100) {
                return Result.error(new IllegalArgumentException("Number must be between 1 and 100 inclusive."));
            }

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("number", number);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);
            if (lexedArgs.size() != 1) {
                return Result.error(new IllegalArgumentException("Invalid number of arguments for difficulty command. Expected 1 argument."));
            }

            String difficulty = lexedArgs.get("0");
            Set<String> validDifficulties = Set.of("easy", "normal", "hard", "peaceful");

            if (difficulty == null || !validDifficulties.contains(difficulty.toLowerCase())) {
                return Result.error(new IllegalArgumentException("Invalid difficulty: " + difficulty + ". Expected one of: " + validDifficulties));
            }

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("difficulty", difficulty);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);

            // If no arguments are provided, set the default message
            String message = lexedArgs.get("0");
            if (message == null || message.isEmpty()) {
                message = "Echo, echo, echo!";
            }

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("message", message);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }


    private static Result<Map<String, Object>> search(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);

            // Count positional arguments
            int positionalArgCount = 0;
            for (String key : lexedArgs.keySet()) {
                if (key.matches("\\d+")) {
                    positionalArgCount++;
                }
            }

            // Validate that there is exactly one positional argument
            if (positionalArgCount != 1) {
                return Result.error(new IllegalArgumentException("Invalid number of positional arguments for search command. Expected exactly 1 positional argument."));
            }

            // Validate that there are no unexpected named arguments
            for (String key : lexedArgs.keySet()) {
                if (!key.equals("0") && !key.equals("case-insensitive")) {
                    return Result.error(new IllegalArgumentException("Unknown named argument: " + key));
                }
            }

            String term = lexedArgs.get("0");
            String caseInsensitiveStr = lexedArgs.get("case-insensitive");

            if (term == null || term.isEmpty()) {
                return Result.error(new IllegalArgumentException("Missing term for search command."));
            }

            ArgumentParser<Boolean> boolParser = new BooleanParser();
            boolean caseInsensitive = false;

            if (caseInsensitiveStr != null) {
                caseInsensitive = boolParser.parse(caseInsensitiveStr);
            }

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("term", term);
            result.put("case-insensitive", caseInsensitive);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        try {
            Map<String, String> lexedArgs = Lexer.lex(arguments);
            if (lexedArgs.size() != 1) {
                return Result.error(new IllegalArgumentException("Invalid number of arguments for weekday command. Expected 1 argument."));
            }

            String dateStr = lexedArgs.get("0");

            if (dateStr == null) {
                return Result.error(new IllegalArgumentException("Missing date for weekday command"));
            }

            ArgumentParser<LocalDate> dateParser = new CustomParser<>(LocalDate::parse);
            LocalDate date = dateParser.parse(dateStr);

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("date", date);
            return Result.ok(result);
        } catch (Exception e) {
            return Result.error(e);
        }
    }
}
