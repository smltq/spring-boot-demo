package javax0.license3j;

import javax0.license3j.hardware.CloudProvider;
import javax0.license3j.hardware.Network;
import javax0.license3j.hardware.UUIDCalculator;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * The hardware binder binds a license to a certain hardware. The use of this
 * feature is optional. Calling methods from this class the license manager can
 * check that the license is deployed on the machine it was destined to and may
 * decide NOT to work on other machines than it was destined to.
 * <p>
 * It is recommended that such a checking is used only with warning purposes and
 * not treated as a strict license violation. It may happen that the ethernet
 * card of a server is replaced due to some failure and there is no time to
 * request a new license.
 * <p>
 * Therefore it is a recommended practice to note the difference of UUID in the
 * license and the calculated one and send it to the log, but do not deter the operation of the
 * software.
 */
public class HardwareBinder {

    private final Network.Interface.Selector selector = new Network.Interface.Selector();
    private final UUIDCalculator calculator = new UUIDCalculator(selector);

    private boolean useHostName = true;
    private boolean useNetwork = true;
    private boolean useArchitecture = true;
    private CloudProvider cloudProvider;

    /**
     * A very simple main that prints out the machine UUID to the standard output.
     * <p>
     * This code takes into account the hardware address (Ethernet MAC) when
     * calculating the hardware UUID.
     *
     * @param args not used in this version
     * @throws UnknownHostException     in case some error happens
     * @throws SocketException          in case some error happens
     * @throws NoSuchAlgorithmException in case some error happens
     */
    public static void main(final String[] args)
        throws NoSuchAlgorithmException, SocketException, UnknownHostException {
        final var hb = new HardwareBinder();
        System.out.println("The UUID of the machine is:\n" + hb.getMachineIdString() + "\n");
    }

    /**
     * Add a regular expression to the set of the regular expressions that are
     * checked against the display name of the network interface cards. If any
     * of the regular expressions are matched against the display name then the
     * interface is allowed taken into account during the calculation of the
     * machine id.
     * <p>
     * Note that there is also a denied set of regular expressions. A network
     * interface card is used during the calculation of the machine uuid if any
     * of the allowing regular expressions match and none of the denying regular
     * expressions match.
     * <p>
     * Note that if there is no any allowing regular expressions, then this is
     * treated that all the interface cards are allowed unless explicitly denied
     * by any of the denying regular expressions. This way the functionality of
     * the hardware binder class is compatible with previous versions. If you
     * do not define allowed set, nor denied set then the interface cards are
     * treated the same as with the old version.
     * <p>
     * This functionality is needed only when you have problem with some virtual
     * network interface cards that are erroneously reported by the Java run
     * time system as physical cards. This is a well known bug that is low
     * priority in the Java realm and there is no general workaround. If you
     * face that problem, then try programmatically exclude from the calculation
     * the network cards that cause you problem.
     *
     * @param regex the regular expression string
     * @return the HardwareBinder object so method calls can be chained
     */
    public HardwareBinder allowed(String regex) {
        selector.interfaceAllowed(regex);
        return this;
    }

    /**
     * Add a cloud provider if you want to take into account the unique and immutable instance/machine id reported by teh provider.
     * By default no cloud provider is taken into account.
     *
     * @param cloudProvider the desired cloud provider.
     * @return the HardwareBinder object so method calls can be chained.
     */
    public HardwareBinder forCloudProvider(CloudProvider cloudProvider) {
        this.cloudProvider = cloudProvider;
        return this;
    }

    /**
     * Add a regular expression to the set of the regular expressions that are
     * checked against the display name of the network interface cards. If any
     * of the regular expressions are matched against the display name then the
     * interface is denied taken into account during the calculation of the
     * machine id.
     * <p>
     * See also the documentation of the method
     * {@link #allowed(String)}.
     *
     * @param regex the regular expression string
     * @return the HardwareBinder object so method calls can be chained
     */
    public HardwareBinder denied(String regex) {
        selector.interfaceDenied(regex);
        return this;
    }


    public class Ignore {

        /**
         * When calculating the machine UUID the host name is also taken into
         * account by default. If you want the method to ignore the machine name
         * then call this method before calling any UUID calculation method.
         *
         * @return the hardware binder object so method calls can be chained
         */
        public HardwareBinder hostName() {
            useHostName = false;
            return HardwareBinder.this;
        }

        /**
         * When calculating the uuid of a machine the network interfaces are
         * enumerated and their parameters are taken into account. The names and the
         * hardware addresses are used.
         * <p>
         * If you want to ignore the network when generating the uuid then call this
         * method before any uuid calculating methods.
         *
         * @return the hardware binder object so method calls can be chained
         */
        public HardwareBinder network() {
            useNetwork = false;
            return HardwareBinder.this;
        }

        /**
         * The UUID generation uses the architecture string as returned by
         * {@code System.getProperty("os.arch")}. In some rare cases you want to
         * have a UUID that is independent of the architecture.
         *
         * @return the hardware binder object so method calls can be chained
         */
        public HardwareBinder architecture() {
            useArchitecture = false;
            return HardwareBinder.this;
        }
    }

    public final Ignore ignore = new Ignore();

    /**
     * Calculate the UUID for the machine this code is running on. To do this
     * the method lists all network interfaces that are real 'server' interfaces
     * (ignoring loop-back, virtual, and point-to-point interfaces). The method
     * takes each interface name (as a string) and hardware address into a MD5
     * digest one after the other and finally converts the resulting 128bit
     * digest into a UUID.
     * <p>
     * The method also feeds the local machine name into the digest.
     * <p>
     * This method relies on Java 6 methods, but also works with Java 5. However
     * the result will not be the same on Java 5 as on Java 6.
     *
     * @return the UUID of the machine or null if the uuid can not be
     * calculated.
     * @throws SocketException          in case some error
     * @throws NoSuchAlgorithmException in case some error
     * @throws UnknownHostException     in case some error
     */
    public UUID getMachineId() throws NoSuchAlgorithmException,
        SocketException, UnknownHostException {
        return calculator.getMachineId(cloudProvider, useNetwork, useHostName, useArchitecture);
    }

    /**
     * Get the machine id as an UUID string.
     *
     * @return the UUID as a string
     * @throws UnknownHostException     in case some error
     * @throws SocketException          in case some error
     * @throws NoSuchAlgorithmException in case some error
     */
    public String getMachineIdString() throws NoSuchAlgorithmException,
        SocketException, UnknownHostException {
        return calculator.getMachineIdString(cloudProvider, useNetwork, useHostName, useArchitecture);
    }

    /**
     * Asserts that the current machine has the UUID.
     *
     * @param uuid expected
     * @return true if the argument passed is the uuid of the current machine.
     * @throws UnknownHostException     in case some error
     * @throws SocketException          in case some error
     * @throws NoSuchAlgorithmException in case some error
     */
    public boolean assertUUID(final UUID uuid)
        throws NoSuchAlgorithmException, SocketException,
        UnknownHostException {
        return calculator.assertUUID(uuid, cloudProvider, useNetwork, useHostName, useArchitecture);
    }

    /**
     * Asserts that the current machine has the UUID.
     *
     * @param uuid expected in String format
     * @return true if the argument passed is the uuid of the current machine.
     */
    public boolean assertUUID(final String uuid) {
        return calculator.assertUUID(uuid, cloudProvider, useNetwork, useHostName, useArchitecture);
    }

}
