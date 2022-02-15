package javax0.license3j.io;

import javax0.license3j.crypto.LicenseKeyPair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestKeyPairWriter {

    static final String CIPHER = "RSA";
    static final int KEY_SIZE = 1024;
    static final String PRIVATE_KEY_BINARY = "private.key.binary";
    static final String PUBLIC_KEY_BINARY = "public.key.binary";
    static final String PRIVATE_KEY_BASE64 = "private.key.base64";
    static final String PUBLIC_KEY_BASE64 = "public.key.base64";

    @Test
    @DisplayName("Generated keys are written into key file binary formatted.")
    void writesKeyPairsToBinaryFile() throws Exception {
        try (final var sut = new KeyPairWriter(PRIVATE_KEY_BINARY, PUBLIC_KEY_BINARY)) {
            final var keyPair = LicenseKeyPair.Create.from(CIPHER, KEY_SIZE);
            sut.write(keyPair, IOFormat.BINARY);
        }
        assertStartsWithRSA0(PRIVATE_KEY_BINARY);
        assertStartsWithRSA0(PUBLIC_KEY_BINARY);
        Assertions.assertTrue(new File(PRIVATE_KEY_BINARY).delete());
        Assertions.assertTrue(new File(PUBLIC_KEY_BINARY).delete());
    }

    @Test
    @DisplayName("Generated keys are written into key file base64 formatted.")
    void writesKeyPairsToBase64File() throws Exception {
        try (final var sut = new KeyPairWriter(PRIVATE_KEY_BASE64, PUBLIC_KEY_BASE64)) {
            final var keyPair = LicenseKeyPair.Create.from(CIPHER, KEY_SIZE);
            sut.write(keyPair, IOFormat.BASE64);
        }
        assertStartsWithUlNBADC(PRIVATE_KEY_BASE64);
        assertStartsWithUlNBADC(PUBLIC_KEY_BASE64);
        Assertions.assertTrue(new File(PRIVATE_KEY_BASE64).delete());
        Assertions.assertTrue(new File(PUBLIC_KEY_BASE64).delete());
    }

    @Test
    @DisplayName("Writing throws exception when trying to write it in STRING format.")
    void keyCannotBeWrittenInStringFormat() throws Exception {
        try (final var sut = new KeyPairWriter(PRIVATE_KEY_BASE64, PUBLIC_KEY_BASE64)) {
            final var keyPair = LicenseKeyPair.Create.from(CIPHER, KEY_SIZE);
            Assertions.assertThrows(IllegalArgumentException.class, () -> sut.write(keyPair, IOFormat.STRING));
        }
    }

    /**
     * Check the generated files are created and start with the characters 'R', 'S', 'A' and a zero.
     *
     * @param file the key file to check.
     * @throws IOException when the key file does not exist or cannot be read.
     */
    private void assertStartsWithRSA0(final String file) throws IOException {
        assertStartsWith(file, "RSA\u0000");
    }

    /**
     * Check the generated files are created and start with the characters "UlNBADC".
     *
     * @param file the key file to check.
     * @throws IOException when the key file does not exist or cannot be read.
     */
    private void assertStartsWithUlNBADC(final String file) throws IOException {
        assertStartsWith(file, "UlNBADC");
    }

    /**
     * Check the generated files are created and start with the characters given in {@code prefix}.
     * The rest of the file cannot be checked because key generation is random.
     *
     * @param file the key file to check.
     * @throws IOException when the key file does not exist or cannot be read.
     */
    private void assertStartsWith(final String file, final String prefix) throws IOException {
        final byte[] buffer = new byte[prefix.length()];
        int length;
        try (final var input = new FileInputStream(file)) {
            length = input.read(buffer);
        }
        Assertions.assertEquals(prefix.length(), length);
        Assertions.assertArrayEquals(prefix.getBytes(StandardCharsets.UTF_8), buffer);
    }

}
