package javax0.license3j.parsers;

/**
 * A simple string parser that can parse decimal and hexa format numbers to be stored in byte, short, int or long.
 * It also takes care that all values in Java are signed and thus, for example, 0xFF would not be treated as a valid
 * byte value. Using this parser it will result a proper -1 byte.
 */
public class NumericParser {

    private static long numberParse(final String s, long minValue, long maxValue) {
        final var trimmed = s.trim();
        final long parsedValue;
        final long correctedValue;
        if (trimmed.startsWith("0x")) {
            parsedValue = java.lang.Long.parseLong(trimmed.substring(2), 16);
        } else {
            parsedValue = java.lang.Long.parseLong(trimmed);
        }
        if (parsedValue > maxValue && parsedValue < 2 * maxValue + 2) {
            correctedValue = parsedValue - 2 * maxValue - 2;
        } else {
            correctedValue = parsedValue;
        }

        if (correctedValue > maxValue || correctedValue < minValue) {
            throw new NumberFormatException(s);
        }
        return correctedValue;
    }

    public static class Byte {
        public static byte parse(final String s) {
            return (byte) numberParse(s, java.lang.Byte.MIN_VALUE, java.lang.Byte.MAX_VALUE);
        }
    }

    public static class Short {
        public static short parse(final String s) {
            return (short) numberParse(s, java.lang.Short.MIN_VALUE, java.lang.Short.MAX_VALUE);
        }
    }

    public static class Int {
        public static int parse(final String s) {
            return (int) numberParse(s, java.lang.Integer.MIN_VALUE, java.lang.Integer.MAX_VALUE);
        }
    }

    public static class Long {
        public static long parse(final String s) {
            return numberParse(s, java.lang.Long.MIN_VALUE, java.lang.Long.MAX_VALUE);
        }
    }
}
