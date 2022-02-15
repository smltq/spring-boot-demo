package javax0.license3j.io;

import javax0.license3j.License;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TestLicenseWriter {
    private static final String LIC_BASE64 = "Ic5OXgAAACAAAAACAAAABgAAAA5zaW1wbGVzdHJpbmcgZmVhdHVyZQ==";
    private static final byte[] LIC_BINARY = Base64.getDecoder().decode(LIC_BASE64);
    private static final String LIC_STRING = "simple=string feature\n";

    static final String FILE_TEXT = "temporaryLicenseFile.txt";
    static final String FILE_BASE64 = "temporaryLicenseFile.base64";
    static final String FILE_BINARY = "temporaryLicenseFile.binary";


    @Test
    void canWriteTextFile() throws IOException {
        final var lic = License.Create.from("simple:STRING=string feature");
        try (final var sut = new LicenseWriter(FILE_TEXT)) {
            sut.write(lic, IOFormat.STRING);
        }
        final byte[] buffer = new byte[22];
        final int length;
        try (final var input = new FileInputStream(FILE_TEXT)) {
            length = input.read(buffer);
        }
        Assertions.assertEquals(22, length);
        Assertions.assertArrayEquals(LIC_STRING.getBytes(StandardCharsets.UTF_8), buffer);
        Assertions.assertTrue(new File(FILE_TEXT).delete());
    }


    @Test
    void canWriteBase64File() throws IOException {
        final var lic = License.Create.from("simple:STRING=string feature");
        try (final var sut = new LicenseWriter(FILE_BASE64)) {
            sut.write(lic, IOFormat.BASE64);
        }
        final byte[] buffer = new byte[56];
        final int length;
        try (final var input = new FileInputStream(FILE_BASE64)) {
            length = input.read(buffer);
        }
        Assertions.assertEquals(56, length);
        Assertions.assertArrayEquals(LIC_BASE64.getBytes(StandardCharsets.UTF_8), buffer);
        Assertions.assertTrue(new File(FILE_BASE64).delete());
    }

    @Test
    void canWriteBinaryFile() throws IOException {
        final var lic = License.Create.from("simple:STRING=string feature");
        try (final var sut = new LicenseWriter(FILE_BINARY)) {
            sut.write(lic);
        }
        final byte[] buffer = new byte[40];
        final int length;
        try (final var input = new FileInputStream(FILE_BINARY)) {
            length = input.read(buffer);
        }
        Assertions.assertEquals(40, length);
        Assertions.assertArrayEquals(LIC_BINARY, buffer);
        Assertions.assertTrue(new File(FILE_BINARY).delete());
    }
}
