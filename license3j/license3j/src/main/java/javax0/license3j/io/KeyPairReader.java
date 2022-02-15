package javax0.license3j.io;

import javax0.license3j.crypto.LicenseKeyPair;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to read a key from file. The class can be used in the application to load the public key, and it is also used
 * in the repl application. Note that the applications that need to validate a license can only read the public key. The
 * environment (client machine) where the license is checked should not have the private key available and hence it
 * cannot be read.
 * <p>
 * Create an instance of this class using one of the constructors specifying the source of the license key and then use
 * one of the {@code read...()} methods to read the key into a {@link LicenseKeyPair} object.
 * <p>
 * Note: the class file is named {@code KeyPairReader} because it is the reading part of the class {@link
 * KeyPairWriter}. However, this class reads one key at a time and not a pair. The keys, when created are written in
 * pairs to save them, but then only one is read when encoding or decoding. On the other hand the return value of the
 * read methods are {@link LicenseKeyPair} objects that are filled with either a public or private key and the other key
 * in these objects are {@code null}.
 */
public class KeyPairReader implements Closeable {
    private final InputStream is;
    final AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * Create a reader with the input stream {@code is} as the source for the key.
     *
     * @param is where the key bytes or characters in case of base64 encoding are coming from.
     */
    public KeyPairReader(InputStream is) {
        this.is = Objects.requireNonNull(is);
    }

    /**
     * Create a reader with the {@code file} as the source for the key.
     *
     * @param file where the key bytes or characters in case of base64 encoding are coming from.
     * @throws FileNotFoundException when the file cannot be found
     */
    public KeyPairReader(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    /**
     * Create a reader with the {@code fileName} as the source for the key.
     *
     * @param fileName where the key bytes or characters in case of base64 encoding are coming from.
     * @throws FileNotFoundException when the file cannot be found
     */
    public KeyPairReader(String fileName) throws FileNotFoundException {
        this(new File(fileName));
    }

    /**
     * Read the public key from the source specified in the constructor in BINARY format.
     *
     * @return the license key pair that contains only the public key.
     * @throws IOException              when the source cannot be read.
     * @throws InvalidKeySpecException  if the format of the key in the source is invalid.
     * @throws NoSuchAlgorithmException if the format of the key in the source is invalid.
     */
    public LicenseKeyPair readPublic() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return readPublic(IOFormat.BINARY);
    }

    /**
     * Read the public key from the source specified in the constructor in the specified format.
     *
     * @param format can be either BINARY or BASE64.
     * @return the license key pair that contains only the public key.
     * @throws IOException              when the source cannot be read.
     * @throws InvalidKeySpecException  if the format of the key in the source is invalid.
     * @throws NoSuchAlgorithmException if the format of the key in the source is invalid.
     */
    public LicenseKeyPair readPublic(IOFormat format) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return read(format, Modifier.PUBLIC);
    }

    /**
     * Read the private key from the source specified in the constructor in BINARY format.
     *
     * @return the license key pair that contains only the private key.
     * @throws IOException              when the source cannot be read.
     * @throws InvalidKeySpecException  if the format of the key in the source is invalid.
     * @throws NoSuchAlgorithmException if the format of the key in the source is invalid.
     */
    public LicenseKeyPair readPrivate() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return readPrivate(IOFormat.BINARY);
    }

    /**
     * Read the private key from the source specified in the constructor in the specified format.
     *
     * @param format can be either BINARY or BASE64.
     * @return the license key pair that contains only the private key.
     * @throws IOException              when the source cannot be read.
     * @throws InvalidKeySpecException  if the format of the key in the source is invalid.
     * @throws NoSuchAlgorithmException if the format of the key in the source is invalid.
     */
    public LicenseKeyPair readPrivate(IOFormat format) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        return read(format, Modifier.PRIVATE);
    }

    /**
     * Read a key from the source specified in the constructor in the specified format.
     *
     * @param format can be either BINARY or BASE64.
     * @param type   can either be {@link Modifier#PRIVATE} or {@link Modifier#PUBLIC} from the JDK reflection class
     * @return the license key pair that contains only the private or only the public key.
     * @throws IOException              when the source cannot be read.
     * @throws InvalidKeySpecException  if the format of the key in the source is invalid.
     * @throws NoSuchAlgorithmException if the format of the key in the source is invalid.
     */
    private LicenseKeyPair read(IOFormat format, int type) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        switch (format) {
            case BINARY:
                return LicenseKeyPair.Create.from(ByteArrayReader.readInput(is), type);
            case BASE64:
                return LicenseKeyPair.Create.from(Base64.getDecoder().decode(ByteArrayReader.readInput(is)), type);
            default:
                throw new IllegalArgumentException("License format " + format + " is unknown.");
        }
    }

    /**
     * The class implements the {@link Closeable} interface. This method closes the license source stream.
     *
     * @throws IOException when the stream cannot be closed.
     */
    @Override
    public void close() throws IOException {
        if (closed.compareAndSet(false, true)) {
            is.close();
        }

    }
}
