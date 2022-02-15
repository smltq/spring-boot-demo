package javax0.license3j.io;

import javax0.license3j.crypto.LicenseKeyPair;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to write the key pair into two files. Key are read individually but they are written in pairs right
 * after they are generated. This class can be used by applications that generate keys, and it is used by the
 * repl application.
 *
 * Create an instance of the class using one of the constructors specifying the output files and then invoke the
 * {@link #write(LicenseKeyPair, IOFormat)} method to save the keys into the files.
 */
public class KeyPairWriter implements Closeable {
    private final OutputStream osPrivate;
    private final OutputStream osPublic;
    final AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * Create a target for license key pair writing specifying the outputs as streams.
     *
     * @param privateKeyOutputStream output stream for the private key
     * @param publicKeyOutputStream  output stream for the public key
     */
    public KeyPairWriter(OutputStream privateKeyOutputStream, OutputStream publicKeyOutputStream) {
        this.osPrivate = Objects.requireNonNull(privateKeyOutputStream);
        this.osPublic = Objects.requireNonNull(publicKeyOutputStream);
    }

    /**
     * Create a target for license key pair writing specifying the outputs as files.
     *
     * @param privateKeyFile file for the private key
     * @param publicKeyFile  file for the public key
     * @throws FileNotFoundException when some of the files cannot be opened to write into.
     */
    public KeyPairWriter(final File privateKeyFile, final File publicKeyFile) throws FileNotFoundException {
        this(new FileOutputStream(privateKeyFile), new FileOutputStream(publicKeyFile));
    }

    /**
     * Create a target for license key pair writing specifying the outputs as file names.
     *
     * @param privateKeyFileName the name of the file for the private key
     * @param publicKeyFileName  the name of the file for the public key
     * @throws FileNotFoundException when some of the files cannot be opened to write into.
     */
    public KeyPairWriter(final String privateKeyFileName, final String publicKeyFileName) throws FileNotFoundException {
        this(new File(privateKeyFileName), new File(publicKeyFileName));
    }

    /**
     * Write the key pair into the output files and then close the file.
     *
     * @param pair   the key pair to write.
     * @param format that can be {@link IOFormat#BINARY} or {@link IOFormat#BASE64}. Using {@link IOFormat#STRING} will
     *               throw exception as keys, as opposed to licenses, cannot be saved in string format.
     * @throws IOException when the underlying media cannot be written
     */
    public void write(LicenseKeyPair pair, IOFormat format) throws IOException {
        switch (format) {
            case BINARY:
                osPrivate.write(pair.getPrivate());
                osPublic.write(pair.getPublic());
                close();
                return;
            case BASE64:
                osPrivate.write(Base64.getEncoder().encode(pair.getPrivate()));
                osPublic.write(Base64.getEncoder().encode(pair.getPublic()));
                close();
                return;
            default:
                throw new IllegalArgumentException("Key format " + format + " is unknown.");
        }
    }

    /**
     * The class implements the {@link Closeable} interface. This method closes the license target streams.
     *
     * @throws IOException when some of the streams cannot be closed.
     */
    @Override
    public void close() throws IOException {
        if (closed.compareAndSet(false, true)) {

            IOException caught = null;
            try {
                osPrivate.close();
            } catch (IOException e) {
                // save the exception, try to close the other resource and throw it afterwards
                caught = e;
            }
            osPublic.close();
            if (caught != null) throw caught;
        }
    }
}
