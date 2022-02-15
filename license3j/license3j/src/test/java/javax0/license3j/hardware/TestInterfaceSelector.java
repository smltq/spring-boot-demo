package javax0.license3j.hardware;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.net.NetworkInterface;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestInterfaceSelector {
    private static final boolean USABLE = true;
    private static final boolean EXCLUDED = false;

    private static Network.Interface.Selector newSut() {

        return new Network.Interface.Selector() {
            boolean isSpecial(NetworkInterface netIf) {
                return false;
            }
        };
    }

    private static IfTest test(final String ifName) throws NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return new IfTest(ifName);
    }

    private static Network.NetworkInterface mockInterface(final String name) {
        final var mockNetworkInterface = Mockito.mock(Network.NetworkInterface.class);
        Mockito.when(mockNetworkInterface.getDisplayName()).thenReturn(name);
        return mockNetworkInterface;
    }

    @Test
    @DisplayName("If there is no regular expression defined as allowed nor as denied then everything is allowed")
    public void testJustAnyName() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        test("just-any-name").isUsable();
    }

    @Test
    @DisplayName("If there is a regular expression allowing an interface then an interface matching the regex will be allowed")
    public void explicitlyAllowed() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        test("allowed").allowed("allowed").isUsable();
    }

    @Test
    @DisplayName("If there is a regular expression allowing an interface then an interface NOT matching the regex will be denied")
    public void explicitlyNotAllowed() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        test("not allowed").allowed("allowed").isDenied();
    }

    @Test
    @DisplayName("If there is a regular expression denying an interface then an interface matching the regex will be denied")
    public void explicitlyDenied() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        test("denied").allowed("allowed").denied("denied").isDenied();
    }

    @Test
    @DisplayName("If there is a regular expression denying an interface then an interface matching the regex will be denied EVEN if it matches an regex allowing it")
    public void explicitlyDeniedEvenIfAllowed() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        test("denied").allowed("denied").denied("denied").isDenied();
    }

    @Test
    @DisplayName("If there is a regular expression allowing it and the denying regex does not match then it is allowed")
    public void explicitlyAllowedNotDenied() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        test("allowed").allowed("allowed").denied("denied").isUsable();
    }

    @Test
    @DisplayName("If there is a regular expression allowing it, and the denying regular expressions do not match then it is allowed")
    public void explicitlyAllowedNotDeniedByAny() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        test("allowed").allowed("allowed").denied("denied", "denied2").isUsable();
    }

    private static class IfTest {
        Network.NetworkInterface ni;
        Network.Interface.Selector sut;

        IfTest(String name) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
            ni = mockInterface(name);
            sut = newSut();
        }

        IfTest allowed(String... alloweds) {
            for (var allowed : alloweds) {
                sut.interfaceAllowed(allowed);
            }
            return this;
        }

        IfTest denied(String... denieds) {
            for (var denied : denieds) {
                sut.interfaceDenied(denied);
            }
            return this;
        }

        void isUsable() {
            assertTrue(sut.usable(ni));
        }

        void isDenied() {
            assertFalse(sut.usable(ni));
        }

    }
}
