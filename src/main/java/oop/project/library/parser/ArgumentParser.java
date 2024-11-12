// File: oop/project/library/parser/ArgumentParser.java
package oop.project.library.parser;

public interface ArgumentParser<T> {
    T parse(String value) throws ArgumentParseException;
}
