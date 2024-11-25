// File: oop/project/library/command/Argument.java
package oop.project.library.command;

import oop.project.library.parser.ArgumentParseException;
import oop.project.library.parser.ArgumentParser;

public class Argument<T> {

    private final String name;
    private final ArgumentParser<T> parser;
    private final boolean required;
    private final boolean isNamed;

    public Argument(String name, ArgumentParser<T> parser, boolean required, boolean isNamed) {
        this.name = name;
        this.parser = parser;
        this.required = required;
        this.isNamed = isNamed;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isNamed() {
        return isNamed;
    }

    public T parse(String value) throws ArgumentParseException {
        return parser.parse(value);
    }
}
