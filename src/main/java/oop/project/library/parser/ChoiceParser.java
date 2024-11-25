// File: oop/project/library/parser/ChoiceParser.java
package oop.project.library.parser;

import java.util.Set;

public class ChoiceParser implements ArgumentParser<String> {

    private final Set<String> choices;

    public ChoiceParser(Set<String> choices) {
        this.choices = choices;
    }

    @Override
    public String parse(String value) throws ArgumentParseException {
        if (choices.contains(value)) {
            return value;
        } else {
            throw new ArgumentParseException("Invalid choice: " + value + ". Expected one of: " + choices);
        }
    }
}
