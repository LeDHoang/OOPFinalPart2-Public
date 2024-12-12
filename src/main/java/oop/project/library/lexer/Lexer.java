// File: oop/project/library/lexer/Lexer.java
package oop.project.library.lexer;

import java.util.LinkedHashMap;
import java.util.Map;

public class Lexer {

    public static Map<String, String> lex(String arguments) throws ArgumentLexerException {
        Map<String, String> result = new LinkedHashMap<>();
        String[] tokens = arguments.trim().split("\\s+");
        int positionalIndex = 0;
        int i = 0;

        while (i < tokens.length) {
            String token = tokens[i];
            if (token.startsWith("---")) {
                throw new ArgumentLexerException("Invalid argument: " + token);
            } else if (token.startsWith("--")) {
                // Named argument
                String name = token.substring(2);
                if (name.isEmpty()) {
                    throw new ArgumentLexerException("Invalid argument: " + token);
                }
                i++;
                if (i >= tokens.length) {
                    throw new ArgumentLexerException("Missing value for named argument: " + name);
                }
                String value = tokens[i];
                if (value.startsWith("--")) {
                    throw new ArgumentLexerException("Invalid value for named argument '" + name + "': " + value);
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

    public static class LexerException extends Exception {
        public LexerException(String message) {
            super(message);
        }
    }
}
