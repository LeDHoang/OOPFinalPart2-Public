package oop.project.library.parser;

public class RangeParser implements ArgumentParser<Integer> {
    private final int min;
    private final int max;

    // Future Improvement:
    // Consider a generic version of RangeParser<T> that accepts a parser function to handle
    // different numeric types (e.g. Double, Long, etc.)

    public RangeParser(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Integer parse(String value) throws ArgumentParseException {
        try {
            int intValue = Integer.parseInt(value);
            if (intValue < min || intValue > max) {
                throw new ArgumentParseException("Value must be between " + min + " and " + max + ". Provided: " + intValue);
            }
            return intValue;
        } catch (NumberFormatException e) {
            throw new ArgumentParseException("Invalid integer value: " + value, e);
        }
    }
}
