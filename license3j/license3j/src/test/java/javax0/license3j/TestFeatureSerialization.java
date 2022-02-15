package javax0.license3j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestFeatureSerialization {

    public static String toHex(byte[] ba) {
        StringBuilder hex = new StringBuilder(ba.length * 2);
        for (byte b : ba) {
            hex.append(String.format("%02X", b));
        }
        return hex.toString();
    }

    public static byte[] fromHex(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    @Test
    @DisplayName("Zero length name byte feature is serialized ok")
    void testZeroLengthNameByteSerialize() {
        final var sut = Feature.Create.byteFeature("", (byte)0xAF);
        Assertions.assertEquals(
            "00000003" +//type 1 binary
                "00000000" + // name length
                "AF" // value
            , toHex(sut.serialized()));
    }
    @Test
    @DisplayName("Zero length name byte feature is loaded ok")
    void testZeroLengthByteLoad() {
        final var sut = Feature.Create.from(fromHex(
            "00000003" +//type 1 binary
                "00000000" + // name length
                "AF" // value
        ));
        Assertions.assertEquals(":BYTE=0xAF", sut.toString());
    }
    @Test
    @DisplayName("Zero length binary is serialized ok")
    void testZeroLengthBinarySerialize() {
        final var sut = Feature.Create.binaryFeature("", new byte[0]);
        Assertions.assertEquals(
            "00000001" +//type 1 binary
                "00000000" + // name length
                "00000000" // value length
            , toHex(sut.serialized()));
    }

    @Test
    @DisplayName("Zero length binary is loaded ok")
    void testZeroLengthBinaryLoad() {
        final var sut = Feature.Create.from(fromHex(
            "00000001" +//type 1 binary
                "00000000" + // name length
                "00000000" // value length
        ));
        Assertions.assertEquals(":BINARY=", sut.toString());
    }

    @Test
    @DisplayName("One byte binary is serialized ok")
    void testOneByteBinarySerialize() {
        final var sut = Feature.Create.binaryFeature("", new byte[]{(byte) 0xAF});
        Assertions.assertEquals(
            "00000001" +//type 1 binary
                "00000000" + // name length
                "00000001" +// value length
                "AF" // value
            , toHex(sut.serialized()));
    }

    @Test
    @DisplayName("One byte binary is loaded ok")
    void testOneByteBinaryLoad() {
        final var sut = Feature.Create.from(fromHex(
            "00000001" //type 1 binary
                + "00000000"  // name length
                + "00000001" // value length
                + "AF" // value
        ));
        Assertions.assertEquals(":BINARY=rw==", sut.toString());
    }

    @Test
    @DisplayName("Extra bytes at the end throw exception")
    void testExcessBytesThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Feature.Create.from(fromHex(
            "00000001" //type 1 binary
                + "00000000"  // name length
                + "00000001" // value length
                + "AFAF" // value
        )));
    }

    @Test
    @DisplayName("Extra bytes at the end throw exception")
    void testInvalidTypeThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Feature.Create.from(fromHex(
            "0000000F" //type 1 binary
                + "00000000"  // name length
                + "00000001" // value length
                + "AF" // value
        )));
    }

    @Test
    @DisplayName("Too long, 32bit name throws exception")
    void testInvalidNameLengthThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Feature.Create.from(fromHex(
            "00000001" //type 1 binary
                + "F0000000"  // name length
                + "00000001" // value length
                + "AF" // value
        )));
    }

    @Test
    @DisplayName("Too long, 32bit value throws exception")
    void testInvalidValueLengthThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Feature.Create.from(fromHex(
            "00000001" //type 1 binary
                + "00000000"  // name length
                + "F0000000" // value length
                + "AF" // value
        )));
    }
    @Test
    @DisplayName("Too long name throws exception")
    void testLongNameLengthThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Feature.Create.from(fromHex(
            "00000001" //type 1 binary
                + "0000000F"  // name length
                + "00000001" // value length
                + "AF" // value
        )));
    }
    @Test
    @DisplayName("Too long value throws exception")
    void testLongValueLengthThrowException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Feature.Create.from(fromHex(
            "00000001" //type 1 binary
                + "00000000"  // name length
                + "0000000F" // value length
                + "AF" // value
        )));
    }}
