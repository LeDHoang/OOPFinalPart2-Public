// File: oop/project/library/lexer/Lexer.java
package oop.project.library.lexer;  // Make sure this matches your package structure

import java.util.LinkedHashMap;
import java.util.Map;

public class Lexer {

    // Ensure this method throws the standalone LexException
    public static Map<String, String> lex(String arguments) throws LexException {
        Map<String, String> result = new LinkedHashMap<>();
        String[] tokens = arguments.trim().split("\\s+");
        int positionalIndex = 0;
        int i = 0;

        while (i < tokens.length) {
            String token = tokens[i];
            if (token.startsWith("---")) {
                throw new LexException("Invalid argument: " + token);
            } else if (token.startsWith("--")) {
                // Named argument
                String name = token.substring(2);
                if (name.isEmpty()) {
                    throw new LexException("Invalid argument: " + token);
                }
                i++;
                if (i >= tokens.length) {
                    throw new LexException("Missing value for named argument: " + name);
                }
                String value = tokens[i];
                if (value.startsWith("--")) {
                    throw new LexException("Invalid value for named argument '" + name + "': " + value);
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
}
