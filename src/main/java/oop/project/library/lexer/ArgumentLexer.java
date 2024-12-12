// File: oop/project/library/parser/ArgumentParser.java
package oop.project.library.lexer;

import oop.project.library.lexer.LexException;

public interface ArgumentLexer<T> {
    T lexer(String value) throws LexException;
}
