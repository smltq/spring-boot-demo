package javax0.license3j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TestHardwareBinder {

    private static final boolean[] falseTrue = new boolean[]{false, true};

    @Test
    @DisplayName("calling hardwarebinder main() does not throw up")
    public void testMain() throws
        SocketException, UnknownHostException, NoSuchAlgorithmException {
        HardwareBinder.main(null);
    }

    @Test
    @DisplayName("hardware binder should accept the machine UUID it just calculated on the test machine")
    public void machineHasUuid() throws
        SocketException, UnknownHostException, NoSuchAlgorithmException {
        for (final boolean ignoreNetwork : falseTrue) {
            for (final boolean ignoreArchitecture : falseTrue) {
                for (final boolean ignoreHostName : falseTrue) {
                    final var hb = new HardwareBinder();
                    if (ignoreNetwork)
                        hb.ignore.network();
                    if (ignoreArchitecture)
                        hb.ignore.architecture();
                    if (ignoreHostName)
                        hb.ignore.hostName();
                    final UUID uuid = hb.getMachineId();
                    Assertions.assertTrue(hb.assertUUID(uuid));
                }
            }
        }
    }

    @Test
    @DisplayName("hardware binder should accept the machine UUID string format it just calculated on the test machine")
    public void machineHasUuidString() throws NoSuchAlgorithmException,
        SocketException, UnknownHostException {
        for (final boolean ignoreNetwork : falseTrue) {
            for (final boolean ignoreArchitecture : falseTrue) {
                for (final boolean ignoreHostName : falseTrue) {
                    final HardwareBinder hb = new HardwareBinder();
                    if (ignoreNetwork)
                        hb.ignore.network();
                    if (ignoreArchitecture)
                        hb.ignore.architecture();
                    if (ignoreHostName)
                        hb.ignore.hostName();
                    final String uuidS = hb.getMachineIdString();
                    Assertions.assertTrue(hb.assertUUID(uuidS));
                }
            }
        }
    }

    @Test
    @DisplayName("if the UUID string is too long UUID assertion returns false")
    public void tooLongUuidStringAssertsFalse()
        throws NoSuchAlgorithmException, SocketException,
        UnknownHostException {
        for (final boolean ignoreNetwork : falseTrue) {
            for (final boolean ignoreArchitecture : falseTrue) {
                for (final boolean ignoreHostName : falseTrue) {
                    final HardwareBinder hb = new HardwareBinder();
                    if (ignoreNetwork)
                        hb.ignore.network();
                    if (ignoreArchitecture)
                        hb.ignore.architecture();
                    if (ignoreHostName)
                        hb.ignore.hostName();
                    final String uuid = hb.getMachineIdString();
                    final String buuid = uuid + "*";
                    Assertions.assertFalse(hb.assertUUID(buuid));
                }
            }
        }
    }

    private String alterLastHexaChar(final String s) {
        String lastChar = s.substring(s.length() - 1);
        if (lastChar.equals("f")) {
            lastChar = "e";
        } else {
            lastChar = "f";
        }
        return s.substring(0, s.length() - 1) + lastChar;

    }

    @Test
    @DisplayName("if the UUID string is properly formatted but contains a wrong value then UUID assertion returns false")
    public void wrongUuidStringAssertsFalse()
        throws NoSuchAlgorithmException, SocketException,
        UnknownHostException {
        for (final boolean ignoreNetwork : falseTrue) {
            for (final boolean ignoreArchitecture : falseTrue) {
                for (final boolean ignoreHostName : falseTrue) {
                    final HardwareBinder hb = new HardwareBinder();
                    if (ignoreNetwork)
                        hb.ignore.network();
                    if (ignoreArchitecture)
                        hb.ignore.architecture();
                    if (ignoreHostName)
                        hb.ignore.hostName();
                    final String uuid = alterLastHexaChar(hb.getMachineIdString());
                    Assertions.assertFalse(hb.assertUUID(uuid));
                }
            }
        }
    }
}
