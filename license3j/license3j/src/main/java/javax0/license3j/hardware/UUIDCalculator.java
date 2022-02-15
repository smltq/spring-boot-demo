package javax0.license3j.hardware;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Calculate a UUID that is specific to the machne. Note that machines are hard to identify and therefore
 * there is no guarantee that two machines will not ever have the same UUID and also there is no guarantee that
 * a single machine will always have the same UUID. The first one is less of a problem. The later, the stability
 * of the UUID of a single machine can be managed with the parameters of the calculator, controlling what the
 * calculation takes into account. The less parameters you select the more stable the UUID will be. On the other hand
 * the less parameter you use the more machines may end-up having the same UUID.
 * <p>
 * Machne UUIDs may be used to restrict the usage of a software to certain machines.
 */
public class UUIDCalculator {
    private final HashCalculator calculator;

    public UUIDCalculator(Network.Interface.Selector selector) {
        this.calculator = new HashCalculator(selector);
    }

    public UUID getMachineId(CloudProvider cloudProvider, boolean useNetwork, boolean useHostName, boolean useArchitecture)
            throws SocketException, UnknownHostException, NoSuchAlgorithmException {
        final var md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        if (cloudProvider != null) {
            calculator.updateWithCloudInstanceId(md5, cloudProvider);
        }
        if (useNetwork) {
            calculator.updateWithNetworkData(md5);
        }
        if (useHostName) {
            calculator.updateWithHostName(md5);
        }
        if (useArchitecture) {
            calculator.updateWithArchitecture(md5);
        }
        final byte[] digest = md5.digest();
        return UUID.nameUUIDFromBytes(digest);
    }

    public String getMachineIdString(CloudProvider cloudProvider, boolean useNetwork, boolean useHostName, boolean useArchitecture) throws
            SocketException, UnknownHostException, NoSuchAlgorithmException {
        final UUID uuid = getMachineId(cloudProvider, useNetwork, useHostName, useArchitecture);
        return uuid.toString();
    }

    public boolean assertUUID(final UUID uuid, CloudProvider cloudProvider, boolean useNetwork, boolean useHostName, boolean useArchitecture)
            throws SocketException, UnknownHostException, NoSuchAlgorithmException {
        final UUID machineUUID = getMachineId(cloudProvider, useNetwork, useHostName, useArchitecture);
        return machineUUID != null && machineUUID.equals(uuid);
    }

    public boolean assertUUID(final String uuid, CloudProvider cloudProvider, boolean useNetwork, boolean useHostName, boolean useArchitecture) {
        try {
            return assertUUID(UUID.fromString(uuid), cloudProvider, useNetwork, useHostName, useArchitecture);
        } catch (Exception e) {
            return false;
        }
    }
}
