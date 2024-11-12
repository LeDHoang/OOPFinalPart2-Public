// File: oop/project/library/parser/Lexer.java
package oop.project.library.parser;

import java.util.LinkedHashMap;
import java.util.Map;

public class Lexer {

    public static Map<String, String> lex(String arguments) throws ParseException {
        Map<String, String> result = new LinkedHashMap<>();
        String[] tokens = arguments.trim().split("\\s+");
        int positionalIndex = 0;
        int i = 0;

        while (i < tokens.length) {
            String token = tokens[i];
            if (token.startsWith("---")) {
                throw new ParseException("Invalid argument: " + token);
            } else if (token.startsWith("--")) {
                // Named argument
                String name = token.substring(2);
                if (name.isEmpty()) {
                    throw new ParseException("Invalid argument: " + token);
                }
                i++;
                if (i >= tokens.length) {
                    throw new ParseException("Missing value for named argument: " + name);
                }
                String value = tokens[i];
                if (value.startsWith("--")) {
                    throw new ParseException("Invalid value for named argument '" + name + "': " + value);
                }
                result.put(name, value);
            } else {
                // Positional argument
                result.put(String.valueOf(positionalIndex), token);
                positionalIndex++;
            }
            i++;
        }
        return result;
    }

    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }
    }
}
