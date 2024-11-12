// File: oop/project/library/parser/IntegerParser.java
package oop.project.library.parser;

public class IntegerParser implements ArgumentParser<Integer> {

    @Override
    public Integer parse(String value) throws ArgumentParseException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid integer value: " + value, e);
        }
    }
}
