package javax0.license3j.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class TestKeyPairReader {

    @Test
    @DisplayName("Can read public key from BINARY file")
    void canReadPublicKeyFromBinaryFile() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        try (final var sut = new KeyPairReader(this.getClass().getResource(TestKeyPairWriter.PUBLIC_KEY_BINARY).getFile())) {
            final var key = sut.readPublic();
            Assertions.assertEquals("RSA", key.cipher());
        }
    }

    @Test
    @DisplayName("Can read private key from BINARY file")
    void canReadPrivateKeyFromBinaryFile() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        try (final var sut = new KeyPairReader(this.getClass().getResource(TestKeyPairWriter.PRIVATE_KEY_BINARY).getFile())) {
            final var key = sut.readPrivate();
            Assertions.assertEquals("RSA", key.cipher());
        }
    }

    @Test
    @DisplayName("Can read public key from BASE64 file")
    void canReadPublicKeyFromBase64File() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        try (final var sut = new KeyPairReader(this.getClass().getResource(TestKeyPairWriter.PUBLIC_KEY_BASE64).getFile())) {
            final var key = sut.readPublic(IOFormat.BASE64);
            Assertions.assertEquals("RSA", key.cipher());
        }
    }

    @Test
    @DisplayName("Can read private key from BASE64 file")
    void canReadPrivateKeyFromBase64File() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        try (final var sut = new KeyPairReader(this.getClass().getResource(TestKeyPairWriter.PRIVATE_KEY_BASE64).getFile())) {
            final var key = sut.readPrivate(IOFormat.BASE64);
            Assertions.assertEquals("RSA", key.cipher());
        }
    }

    @Test
    @DisplayName("Throws exception when trying to read key file using STRING format")
    void throwsUpOnStringFormat() throws IOException {
        try (final var sut = new KeyPairReader(this.getClass().getResource(TestKeyPairWriter.PRIVATE_KEY_BASE64).getFile())) {
            Assertions.assertThrows(IllegalArgumentException.class, () -> sut.readPrivate(IOFormat.STRING));
        }
    }
}
