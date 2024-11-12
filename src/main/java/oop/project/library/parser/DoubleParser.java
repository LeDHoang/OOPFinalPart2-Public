// File: oop/project/library/parser/DoubleParser.java
package oop.project.library.parser;

public class DoubleParser implements ArgumentParser<Double> {

    @Override
    public Double parse(String value) throws ArgumentParseException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid double value: " + value, e);
        }
    }
}
