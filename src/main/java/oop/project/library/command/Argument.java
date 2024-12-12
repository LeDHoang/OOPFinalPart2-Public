package oop.project.library.command;

import oop.project.library.parser.ArgumentParseException;
import oop.project.library.parser.ArgumentParser;

public record Argument<T>(
        String name,
        ArgumentParser<T> parser,
        boolean required,
        boolean isNamed
) {
    public T parse(String value) throws ArgumentParseException {
        return parser.parse(value);
    }
}
