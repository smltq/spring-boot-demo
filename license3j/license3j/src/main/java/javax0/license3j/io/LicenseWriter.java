package javax0.license3j.io;

import javax0.license3j.License;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Write the license into the output. Use the constructors to create a writer object specifying the target for the
 * license, and then use one of the 'write()' methods to perform writing specifying the format. The writing, being
 * atomic in the sense that there is no possible way or reason to write anything more into the destination, closes the
 * output. Nevertheless, the class contains a {@link #close()} method that can be called.
 * <p>
 * The class also implements the {@link Closeable} interface, thus it can be used in try-with-resources block to call
 * the {@link #close()} method.
 */
public class LicenseWriter implements Closeable {
    private final OutputStream os;
    final AtomicBoolean closed = new AtomicBoolean(false);

    public LicenseWriter(OutputStream os) {
        this.os = Objects.requireNonNull(os);
    }

    public LicenseWriter(File file) throws FileNotFoundException {
        this(new FileOutputStream(file));
    }

    public LicenseWriter(String fileName) throws FileNotFoundException {
        this(new File(fileName));
    }

    /**
     * Write the license into the output and close the output.
     *
     * @param license the license itself
     * @param format  the desired format of the license, can be {@link IOFormat#STRING}, {@link IOFormat#BASE64} or
     *                {@link IOFormat#BINARY}
     * @throws IOException if the output cannot be written
     */
    public void write(License license, IOFormat format) throws IOException {
        switch (format) {
            case BINARY:
                os.write(license.serialized());
                break;
            case BASE64:
                os.write(Base64.getEncoder().encode(license.serialized()));
                break;
            case STRING:
                os.write(license.toString().getBytes(StandardCharsets.UTF_8));
                break;
            default:
                throw new IllegalArgumentException(IOFormat.class.getName() +
                    " is incompatible with License3j, and was used with the value " +
                    format + " which is unknown");
        }
        close();
    }

    /**
     * Write the license to the output in binary format.
     * @param license to write to the file
     * @throws IOException if the output cannot be written
     */
    public void write(License license) throws IOException {
        write(license, IOFormat.BINARY);
    }

    @Override
    public void close() throws IOException {
        if (closed.compareAndSet(false, true)) {
            os.close();
        }
    }
}
