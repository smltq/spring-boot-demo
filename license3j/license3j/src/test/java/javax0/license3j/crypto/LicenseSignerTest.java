package javax0.license3j.crypto;

import javax0.license3j.Feature;
import javax0.license3j.License;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class LicenseSignerTest {

    @Test
    @DisplayName("License is encoded and verified with keys generated in the test on the fly")
    public void testSignature() throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        final var keyPair = LicenseKeyPair.Create.from("RSA", 2048);
        final var license = new License();
        license.add(Feature.Create.stringFeature("owner", "Peter Verhas"));
        license.sign(keyPair.getPair().getPrivate(), "SHA-512");
        // license is OK checking with the key
        Assertions.assertTrue(license.isOK(keyPair.getPair().getPublic()));
        // license is OK checking with the byte[] format of the key
        Assertions.assertTrue(license.isOK(keyPair.getPublic()));
        license.getSignature()[0] = (byte) ~license.getSignature()[0];
        // license check should fail using the key
        Assertions.assertFalse(license.isOK(keyPair.getPair().getPublic()));
        // license check should fail using the byte[] format of the key
        Assertions.assertFalse(license.isOK(keyPair.getPublic()));
    }

    @Test
    @DisplayName("License is encoded and verified properly when the keys are generated specifying the full cipher transformation string and not only the algorithm")
    public void testSignatureWithFullCipher() throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        final var keyPair = LicenseKeyPair.Create.from("RSA/ECB/PKCS1Padding", 2048);
        final var license = new License();
        license.add(Feature.Create.stringFeature("owner", "Peter Verhas"));
        license.sign(keyPair.getPair().getPrivate(), "SHA-512");
        Assertions.assertTrue(license.isOK(keyPair.getPair().getPublic()));
        license.getSignature()[0] = (byte) ~license.getSignature()[0];
        Assertions.assertFalse(license.isOK(keyPair.getPair().getPublic()));
    }

    @Test
    @DisplayName("The key contain at the start null terminated the full cipher transformation string not only the algorithm")
    public void testKeyContainsFullCipher() throws NoSuchAlgorithmException {
        final var keyPair = LicenseKeyPair.Create.from("RSA/ECB/PKCS1Padding", 2048);
        final var pubFull = new String(keyPair.getPublic());
        final var pub = pubFull.substring(0, pubFull.indexOf(0));
        Assertions.assertEquals("RSA/ECB/PKCS1Padding", pub);
        final var privateFull = new String(keyPair.getPublic());
        final var privat = privateFull.substring(0, privateFull.indexOf(0));
        Assertions.assertEquals("RSA/ECB/PKCS1Padding", privat);
    }
}
