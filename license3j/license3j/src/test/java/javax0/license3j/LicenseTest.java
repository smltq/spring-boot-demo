package javax0.license3j;

import javax0.license3j.crypto.LicenseKeyPair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

class LicenseTest {

    /**
     * Add some sample features to the license tested.
     *
     * @param sut the license that we test
     * @param now the Date value used as current time. For reproducibility, we do not use the actual time when the test
     *            is executed, rather we use a fixed value. This value will be added to the license as the time when the
     *            license expires.
     */
    private void addSampleFeatures(License sut, Date now) {
        sut.add(Feature.Create.stringFeature("owner", "Peter Verhas"));
        sut.add(Feature.Create.stringFeature("title", "A license test, \ntest license"));
        sut.add(Feature.Create.dateFeature("expiry", now));
        sut.add(Feature.Create.stringFeature("template", "<<special template>>"));
    }

    @Test
    @DisplayName("Creates a license and then it can access the features.")
    void createLicenseViaAPI() {
        final var sut = new License();
        final var now = new Date(1545047719295L);
        addSampleFeatures(sut, now);
        Assertions.assertEquals("Peter Verhas", sut.get("owner").getString());
        Assertions.assertEquals(now, sut.get("expiry").getDate());
    }

    @Test
    @DisplayName("Create a license with features serialize and restore then the features are the same")
    void licenseSerializeAndDeserialize() {
        final var sut = new License();
        final var now = new Date(1545047719295L);
        addSampleFeatures(sut, now);
        byte[] buffer = sut.serialized();
        final var restored = License.Create.from(buffer);
        Assertions.assertEquals("Peter Verhas", restored.get("owner").getString());
        Assertions.assertEquals(now, restored.get("expiry").getDate());
        Assertions.assertEquals("expiry:DATE=2018-12-17 11:55:19.295\n" +
                "owner=Peter Verhas\n" +
                "template=<<null\n" +
                "<<special template>>\n" +
                "null\n" +
                "title=<<B\n" +
                "A license test, \n" +
                "test license\n" +
                "B\n", sut.toString());
    }

    @Test
    @DisplayName("Create a license with features convert to string and restore then the features are the same")
    void licenseStringifyAndDestringify() {
        final var sut = new License();
        final var now = new Date(1545047719295L);
        addSampleFeatures(sut, now);
        var string = sut.toString();
        final var restored = License.Create.from(string);
        Assertions.assertEquals("Peter Verhas", restored.get("owner").getString());
        Assertions.assertEquals(now, restored.get("expiry").getDate());
        Assertions.assertEquals("expiry:DATE=2018-12-17 11:55:19.295\n" +
            "owner=Peter Verhas\n" +
            "template=<<null\n" +
            "<<special template>>\n" +
            "null\n" +
            "title=<<B\n" +
            "A license test, \n" +
            "test license\n" +
            "B\n", sut.toString());
    }


    @Test
    @DisplayName("Throw an exception when the string representation of the license contains malformed feature")
    void licenseDestringifyBadFeature() {
        final var licenseText = "expiry:DATE=2018-12-17 11:55:19.295\n" +
            "owner=Peter Verhas\n" +
            "bad feature : 55\n" +
            "template=<<null\n" +
            "<<special template>>\n" +
            "null\n" +
            "title=<<B\n" +
            "A license test, \n" +
            "test license\n" +
            "B\n";
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> License.Create.from(licenseText));
    }

    @Test
    @DisplayName("Throw an exception when the string representation of the license contains malformed DATE feature")
    void licenseDestringifyBadDate() {
        final var licenseText = "expiry:DATE=this is just something that is not a date\n" +
            "owner=Peter Verhas\n" +
            "template=<<null\n" +
            "<<special template>>\n" +
            "null\n" +
            "title=<<B\n" +
            "A license test, \n" +
            "test license\n" +
            "B\n";
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> License.Create.from(licenseText));
    }

    @Test
    @DisplayName("Read a license from a string that contains different features")
    void licenseDestringifyWithVariousFeatures() {
        final var SAMPLE_BIGDECIMAL_VALUE =
            "123456789012345678901234567890123456789012345678901234567890.123456789012345678901234567890123456789012345678901234567890";
        final var SAMPLE_BIGINTEGER_VALUE = "123456789012345678901234567890123456789012345678901234567890";
        final var licenseText =
            "" +
                "sampleBinary:BINARY=T2gsIG15IEdvZCE=\n" +
                "sampleString:STRING=Oh, my God!\n" +
                "sampleByte:BYTE=0xFF\n" +
                "sampleShort:SHORT=0xFFFF\n" +
                "sampleInt:INT=0xFFFFFFFF\n" +
                "sampleLong:LONG=0x7FFFFFFFFFFFFFFF\n" +
                "sampleFloat:FLOAT=3.1415926\n" +
                "sampleDouble:DOUBLE=3.1415926\n" +
                "sampleBigInteger:BIGINTEGER=" + SAMPLE_BIGINTEGER_VALUE + "\n" +
                "sampleBigDecimal:BIGDECIMAL=" + SAMPLE_BIGDECIMAL_VALUE + "\n" +
                "sampleDate:DATE=2018-12-17 11:55:19.295\n" +
                "sampleUUID:UUID=f1977334-f163-405c-938f-40ea4343fcb1\n" +

                "";
        final var sut = License.Create.from(licenseText);

        Assertions.assertArrayEquals("Oh, my God!".getBytes(StandardCharsets.UTF_8), sut.get("sampleBinary").getBinary());
        Assertions.assertEquals("Oh, my God!", sut.get("sampleString").getString());
        Assertions.assertEquals(-1, sut.get("sampleByte").getByte());
        Assertions.assertEquals(-1, sut.get("sampleShort").getShort());
        Assertions.assertEquals(-1, sut.get("sampleInt").getInt());
        Assertions.assertEquals(Long.MAX_VALUE, sut.get("sampleLong").getLong());
        Assertions.assertEquals(3.1415926, sut.get("sampleFloat").getFloat(),0.000001);
        Assertions.assertEquals(3.1415926, sut.get("sampleDouble").getDouble(),0.000001);
        Assertions.assertEquals(new BigInteger(SAMPLE_BIGINTEGER_VALUE), sut.get("sampleBigInteger").getBigInteger());
        Assertions.assertEquals(new BigDecimal(SAMPLE_BIGDECIMAL_VALUE), sut.get("sampleBigDecimal").getBigDecimal());
        final var date = sut.get("sampleDate").getDate();
        Assertions.assertEquals(1545047719295L,date.getTime());
        Assertions.assertEquals(UUID.fromString("f1977334-f163-405c-938f-40ea4343fcb1"), sut.get("sampleUUID").getUUID());
    }

    @Test
    @DisplayName("Test that a feature does not give the value in a different format than it is")
    void featureThrowsWhenWrongFormatIsQueried(){
        final var sut = Feature.Create.from("sampleBinary:BINARY=T2gsIG15IEdvZCE=");
        //Assertions.assertThrows(IllegalArgumentException.class,() -> sut.getBinary());
        Assertions.assertThrows(IllegalArgumentException.class, sut::getString);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getByte);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getShort);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getInt);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getLong);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getFloat);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getDouble);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getBigInteger);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getBigDecimal);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getDate);
        Assertions.assertThrows(IllegalArgumentException.class, sut::getUUID);
    }

    @Test
    @DisplayName("Throw an exception when the string representation of the license contains malformed DATE feature")
    void licenseDestringifyBadBigDecimal() {
        final var licenseText = "expiry:DATE=2018-12-17 11:55:19.295\n" +
            "owner=Peter Verhas\n" +
            "licenseCost:BIGDECIMAL=something that is not big decimal\n" +
            "template=<<null\n" +
            "<<special template>>\n" +
            "null\n" +
            "title=<<B\n" +
            "A license test, \n" +
            "test license\n" +
            "B\n";
        Assertions.assertThrows(IllegalArgumentException.class,
            () -> License.Create.from(licenseText));
    }

    @Test
    @DisplayName("Test the fingerprint does not change even if we change the signature algorithm")
    void testLicenseFingerprint() throws NoSuchAlgorithmException, IllegalBlockSizeException,
        InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        final var sut = new License();
        final var now = new Date(1545047719295L);
        addSampleFeatures(sut, now);
        final var fpUnsigned = sut.fingerprint();
        var keysRSA1024 = LicenseKeyPair.Create.from("RSA", 1000);
        sut.sign(keysRSA1024.getPair().getPrivate(), "SHA-512");
        final var fpSignedSHA512 = sut.fingerprint();
        var keysRSA4096 = LicenseKeyPair.Create.from("RSA", 4096);
        sut.sign(keysRSA4096.getPair().getPrivate(), "MD5");
        final var fpSignedMD5 = sut.fingerprint();
        Assertions.assertEquals(fpUnsigned,fpSignedSHA512);
        Assertions.assertEquals(fpUnsigned,fpSignedMD5);
    }

    @Test
    @DisplayName("A license with an expiry date a day ago has expired")
    void pastExpiryTimeReportsExpired() {
        final var license = new License();
        license.setExpiry(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
        Assertions.assertTrue(license.isExpired());
    }

    @Test
    @DisplayName("A license with an expiry date a day ahead has not expired")
    void futureExpiryTimeReportsNonExpired()  {
        final var lic = new License();
        lic.setExpiry(new Date(new Date().getTime() + 24 * 60 * 60 * 1000));
        Assertions.assertFalse(lic.isExpired());
    }

    @Test
    @DisplayName("Setting random license UUID will result non-null license id")
    void uuidGenerationResultsNonNullUuid() {
        final var lic = new License();
        lic.setLicenseId();
        Assertions.assertNotNull(lic.getLicenseId());
    }

}
