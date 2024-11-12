// File: oop/project/library/parser/CustomParser.java
package oop.project.library.parser;

import java.util.function.Function;

public class CustomParser<T> implements ArgumentParser<T> {

    private final Function<String, T> parserFunction;

    public CustomParser(Function<String, T> parserFunction) {
        this.parserFunction = parserFunction;
    }

    @Override
    public T parse(String value) throws ArgumentParseException {
        try {
            return parserFunction.apply(value);
        } catch (Exception e) {
            throw new ArgumentParseException("Invalid custom value: " + value, e);
        }
    }
}
