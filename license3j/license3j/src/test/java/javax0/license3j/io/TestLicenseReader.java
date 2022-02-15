package javax0.license3j.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestLicenseReader {

    @Test
    @DisplayName("Can read license from text file")
    void canReadLicenseFromTextFile() throws IOException {
        try (final var sut = new LicenseReader(this.getClass().getResource(TestLicenseWriter.FILE_TEXT).getFile())) {
            final var lic = sut.read(IOFormat.STRING);
            Assertions.assertEquals("string feature", lic.get("simple").getString());
        }
    }

    @Test
    @DisplayName("Can read license from binary file")
    void canReadLicenseFromBinaryFile() throws IOException {
        try (final var sut = new LicenseReader(this.getClass().getResource(TestLicenseWriter.FILE_BINARY).getFile())) {
            final var lic = sut.read(IOFormat.BINARY);
            Assertions.assertEquals("string feature", lic.get("simple").getString());
        }
    }

    @Test
    @DisplayName("Can read license from binary file using default read()")
    void canReadDefaultLicenseFromBinaryFile() throws IOException {
        try (final var sut = new LicenseReader(this.getClass().getResource(TestLicenseWriter.FILE_BINARY).getFile())) {
            final var lic = sut.read();
            Assertions.assertEquals("string feature", lic.get("simple").getString());
        }
    }

    @Test
    @DisplayName("Can read license from base64 file")
    void canReadLicenseFromBase64File() throws IOException {
        try (final var sut = new LicenseReader(this.getClass().getResource(TestLicenseWriter.FILE_BASE64).getFile())) {
            final var lic = sut.read(IOFormat.BASE64);
            Assertions.assertEquals("string feature", lic.get("simple").getString());
        }
    }

    @Test
    @DisplayName("Avoids reading too long file")
    void avoidTooLongFile() {
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> new LicenseReader(this.getClass().getResource(TestLicenseWriter.FILE_TEXT).getFile(), 1));
    }
}
