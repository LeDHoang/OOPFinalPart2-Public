// File: oop/project/library/command/Command.java
package oop.project.library.command;

import oop.project.library.parser.ArgumentParseException;
import oop.project.library.parser.ArgumentParser;
import oop.project.library.parser.Lexer;
import oop.project.library.parser.Lexer.ParseException;

import java.util.*;

public class Command {

    private final String name;
    private final List<Argument<?>> arguments = new ArrayList<>();

    public Command(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public <T> Command addArgument(String name, ArgumentParser<T> parser, boolean required, boolean isNamed) {
        arguments.add(new Argument<>(name, parser, required, isNamed));
        return this;
    }

    public Map<String, Object> parse(String input) throws ParseException, ArgumentParseException {
        Map<String, String> tokens = Lexer.lex(input);
        Map<String, Object> parsedArguments = new LinkedHashMap<>();

        int positionalIndex = 0;
        for (Argument<?> arg : arguments) {
            String value = null;

            if (arg.isNamed()) {
                if (tokens.containsKey(arg.getName())) {
                    value = tokens.get(arg.getName());
                }
            } else {
                if (tokens.containsKey(String.valueOf(positionalIndex))) {
                    value = tokens.get(String.valueOf(positionalIndex));
                    positionalIndex++;
                }
            }

            if (value == null) {
                if (arg.isRequired()) {
                    throw new ArgumentParseException("Missing required argument: " + arg.getName());
                } else {
                    continue; // Optional argument not provided
                }
            }

            Object parsedValue = arg.parse(value);
            parsedArguments.put(arg.getName(), parsedValue);
        }

        return parsedArguments;
    }
}
