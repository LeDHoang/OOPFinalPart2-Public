// File: oop/project/library/lexer/LexException.java
package oop.project.library.lexer;

public class LexException extends Exception {
    public LexException(String message) {
        super(message);
    }

    public LexException(String message, Throwable cause) {
        super(message, cause);
    }
}
