// File: oop/project/library/command/Command.java
package oop.project.library.command;

import oop.project.library.lexer.LexException; // Import once here
import oop.project.library.lexer.Lexer;
import oop.project.library.parser.ArgumentParseException;
import oop.project.library.parser.ArgumentParser;

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
        // Check if argument name already exists
        for (Argument<?> arg : arguments) {
            if (arg.name().equals(name)) {
                throw new IllegalArgumentException("Argument with name '" + name + "' already exists in command '" + this.name + "'");
            }
        }

        arguments.add(new Argument<>(name, parser, required, isNamed));
        return this;
    }

    public Map<String, Object> parse(String input) throws LexException, ArgumentParseException {
        // Lexer.lex throws LexException
        Map<String, String> tokens = Lexer.lex(input);
        Map<String, Object> parsedArguments = new LinkedHashMap<>();

        int positionalIndex = 0;
        for (Argument<?> arg : arguments) {
            String value = null;

            if (arg.isNamed()) {
                if (tokens.containsKey(arg.name())) {
                    value = tokens.get(arg.name());
                }
            } else {
                if (tokens.containsKey(String.valueOf(positionalIndex))) {
                    value = tokens.get(String.valueOf(positionalIndex));
                    positionalIndex++;
                }
            }

            if (value == null) {
                if (arg.required()) {
                    throw new ArgumentParseException("Missing required argument: " + arg.name());
                } else {
                    continue; // Optional argument not provided
                }
            }

            Object parsedValue = arg.parse(value);
            parsedArguments.put(arg.name(), parsedValue);
        }

        return parsedArguments;
    }
}
