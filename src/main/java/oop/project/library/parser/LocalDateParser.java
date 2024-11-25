// File: oop/project/library/parser/LocalDateParser.java
package oop.project.library.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateParser implements ArgumentParser<LocalDate> {

    @Override
    public LocalDate parse(String value) throws ArgumentParseException {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new ArgumentParseException("Invalid date format: " + value, e);
        }
    }
}
