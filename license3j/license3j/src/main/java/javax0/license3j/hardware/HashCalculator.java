package javax0.license3j.hardware;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Calculator to calculate a more or less unique code for the actual machine. The calculation uses the
 * network cards, host name and architecture of the machine.
 * <p>
 * This class is used by the {@link UUIDCalculator}. You, using License3j as a library, do not call methods in
 * this class directly.
 */
class HashCalculator {
    private final Network.Interface.Selector selector;

    HashCalculator(Network.Interface.Selector selector) {
        this.selector = selector;
    }

    private void updateWithNetworkData(final MessageDigest md5,
                                       final Stream<Network.Interface.Data> interfaces) {
        interfaces.forEach(ni -> {
            md5.update(ni.name.getBytes(StandardCharsets.UTF_8));
            if (ni.hwAddress != null) {
                md5.update(ni.hwAddress);
            }
        });
    }

    void updateWithNetworkData(final MessageDigest md5) throws SocketException {
        final Stream<Network.Interface.Data> networkInterfaces = Network.Interface.Data.gatherUsing(selector);
        updateWithNetworkData(md5, networkInterfaces.sorted(Comparator.comparing(a -> a.name)));
    }

    void updateWithHostName(final MessageDigest md5) throws UnknownHostException {
        final String hostName = InetAddress.getLocalHost().getHostName();
        update(md5, hostName);
    }

    void updateWithArchitecture(final MessageDigest md5) {
        final String architecture = System.getProperty("os.arch");
        update(md5, architecture);
    }

    void updateWithCloudInstanceId(final MessageDigest md5, final CloudProvider cloudProvider) {
        final String instanceId = cloudProvider.getInstanceId();
        update(md5, instanceId);
    }

    private void update(final MessageDigest md5, final String input) {
        final byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        md5.update(bytes, 0, bytes.length);
    }
}
