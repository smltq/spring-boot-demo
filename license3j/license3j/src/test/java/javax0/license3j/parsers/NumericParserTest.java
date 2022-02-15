package javax0.license3j.parsers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NumericParserTest {
    @Test
    public void testByteParser() {
        Assertions.assertEquals((byte) 0xFE, NumericParser.Byte.parse("0xfe"));
        Assertions.assertEquals((byte) -128, NumericParser.Byte.parse("128"));
        Assertions.assertEquals((byte) -1, NumericParser.Byte.parse("255"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Byte.parse("256"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Byte.parse("-129"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Byte.parse("0x100"));
    }

    @Test
    public void testShortParser() {
        Assertions.assertEquals((short) 0xFEFE, NumericParser.Short.parse("0xfefe"));
        Assertions.assertEquals((short) -32768, NumericParser.Short.parse("32768"));
        Assertions.assertEquals((short) -1, NumericParser.Short.parse("0xFFFF"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Short.parse("65536"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Short.parse("-32769"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Short.parse("0x10000"));
    }

    @Test
    public void testIntParser() {
        Assertions.assertEquals(0xFEFEFEFE, NumericParser.Int.parse("0xfefefefe"));
        Assertions.assertEquals(-2147483648, NumericParser.Int.parse("2147483648"));
        Assertions.assertEquals(-1, NumericParser.Int.parse("4294967295"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Int.parse("4294967296"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Int.parse("-2147483649"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Int.parse("0x100000000"));
    }

    @Test
    public void testLongParser() {
        Assertions.assertEquals(0x7EFEFEFEFEFEFEFEL, NumericParser.Long.parse("0x7EFEFEFEFEFEFEFE"));
        Assertions.assertEquals(576460752303423488L, NumericParser.Long.parse("576460752303423488"));
        Assertions.assertEquals(-1L,   NumericParser.Long.parse("-1"));
        Assertions.assertThrows(NumberFormatException.class, () -> NumericParser.Long.parse("0xFEFEFEFEFEFEFEFE"));
    }
}
