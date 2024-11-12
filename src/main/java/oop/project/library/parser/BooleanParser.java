// File: oop/project/library/parser/BooleanParser.java
package oop.project.library.parser;

public class BooleanParser implements ArgumentParser<Boolean> {

    @Override
    public Boolean parse(String value) throws ArgumentParseException {
        if (value.equalsIgnoreCase("true")) {
            return true;
        } else if (value.equalsIgnoreCase("false")) {
            return false;
        } else {
            throw new ArgumentParseException("Invalid boolean value: " + value);
        }
    }
}
