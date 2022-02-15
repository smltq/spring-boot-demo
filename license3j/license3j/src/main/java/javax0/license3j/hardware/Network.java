package javax0.license3j.hardware;

import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Network {
    public static class NetworkInterface {
        private final java.net.NetworkInterface ni;

        private NetworkInterface(java.net.NetworkInterface ni) {
            this.ni = ni;
        }

        /*!jamal
        {%!@for [separator="\\s*\n\\s*" skipEmpty]($rettype,$name,$exceptions) in
        `LOOP`
        String|getName|
        byte[]|getHardwareAddress|SocketException
        String|getDisplayName|
        boolean|isLoopback|SocketException
        boolean|isVirtual|SocketException
        boolean|isPointToPoint|SocketException
        `LOOP`=
        public $rettype $name(){%#if/$exceptions/ throws $exceptions/%}{
            return ni.$name();
            }
        %}
         */

        public String getName(){
            return ni.getName();
            }

        public byte[] getHardwareAddress() throws SocketException{
            return ni.getHardwareAddress();
            }

        public String getDisplayName(){
            return ni.getDisplayName();
            }

        public boolean isLoopback() throws SocketException{
            return ni.isLoopback();
            }

        public boolean isVirtual() throws SocketException{
            return ni.isVirtual();
            }

        public boolean isPointToPoint() throws SocketException{
            return ni.isPointToPoint();
            }

        //__END__

        public static Enumeration<NetworkInterface> getNetworkInterfaces()
            throws SocketException {
            return Collections.enumeration(Collections.list(java.net.NetworkInterface.getNetworkInterfaces()).stream().map(ni -> new NetworkInterface(ni)).collect(Collectors.toList()));
        }
    }

    public static class Interface {
        public static class Data {
            public final String name;
            byte[] hwAddress;

            private Data(final NetworkInterface networkInterface) {
                name = networkInterface.getName();
                try {
                    hwAddress = networkInterface.getHardwareAddress();
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }

            static Stream<Data> gatherUsing(Network.Interface.Selector selector) throws SocketException {
                return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                    .filter(selector::usable)
                    .map(Network.Interface.Data::new);
            }
        }

        public static class Selector {

            private final Set<String> allowedInterfaceNames = new HashSet<>();
            private final Set<String> deniedInterfaceNames = new HashSet<>();

            /**
             * Check if any of the set of regular expressions match the given string.
             *
             * @param string   to match
             * @param regexSet regular expressions provided as set of strings
             * @return {@code true} if the {@code string} matches any of the regular expressions
             */
            private static boolean matchesAny(final String string, Set<String> regexSet) {
                return regexSet.stream().anyMatch(string::matches);
            }

            /**
             * Checks the sets of regular expressions against the display name of the
             * network interface. If there is a set of denied names then if any of the
             * regular expressions matches the name of the interface then the interface
             * is denied. If there is no denied set then the processing is not affected
             * by the non-existence. In other words, not specifying any denied interface
             * name means that no interface is denied explicitly.
             * <p>
             * If there is a set of permitted names then if any of the regular
             * expressions matches the name of the interface then the interface is
             * permitted. If there is no set then the interface is permitted. In other
             * words it is not possible to deny all interfaces specifying an empty set.
             * Although this would mathematically logical, but there is no valuable use
             * case that would require this feature.
             * <p>
             * Note that the name, which is checked is not the basic name (e.g.
             * {@code eth0}), but the display name, which is more human readable.
             *
             * @param netIf the network interface
             * @return {@code true} if the interface has to be taken into the
             * calculation of the license and {@code false} (ignore the
             * interface) otherwise.
             */
            private boolean matchesRegexLists(final NetworkInterface netIf) {
                final String name = netIf.getDisplayName();

                return !matchesAny(name, deniedInterfaceNames)
                    &&
                    (allowedInterfaceNames.isEmpty() ||
                        matchesAny(name, allowedInterfaceNames));
            }

            /**
             * Add a regular expression to the set of regular expressions that may match the name of the interface names
             * to be included into the calculation of the machine ID. If any of such regular expressions matches the
             * name of the network interface name and none of the {@link #interfaceDenied(String) denied list} then the
             * interface will be used in the calculation of the machine id.
             *
             * @param regex the regular expression that may match the name of the interface name
             */
            public void interfaceAllowed(String regex) {
                allowedInterfaceNames.add(regex);
            }

            /**
             * Add a regular expression to the set of regular expressions that may match the name of the interface names
             * to be excluded from the calculation of the machine ID. If any of such regular expressions matches the
             * name of the network interface name then the interface will NOT be used in the calculation of the machine
             * id.
             *
             * @param regex the regular expression that may match the name of the interface name to exclude the
             *              interface from the machine ID calculation
             */
            public void interfaceDenied(String regex) {
                deniedInterfaceNames.add(regex);
            }

            /**
             * @param netIf the network interface
             * @return {@code true} if the actual network interface has to be used for
             * the calculation of the hardware identification id.
             */
            boolean usable(final NetworkInterface netIf) {
                return !isSpecial(netIf)
                    && matchesRegexLists(netIf);
            }

            /**
             * @param netIf the network interface to be check for being special
             * @return {@code true} if the interface is a special interface, like virtual or loopback interface.
             * Such interfaces are not suitable to be used for the machine ID calculation because
             * they can easily be added, removed from the machine configuration.
             */
            boolean isSpecial(NetworkInterface netIf) {
                try {
                    return netIf.isLoopback() || netIf.isVirtual() || netIf.isPointToPoint();
                } catch (SocketException e) {
                    return true;
                }
            }
        }
    }

}


