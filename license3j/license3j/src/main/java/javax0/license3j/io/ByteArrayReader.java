package javax0.license3j.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Simple helper class to read all the bytes from an input stream into a byte array.
 */
class ByteArrayReader {
    static byte[] readInput(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int len;
        final var data = new byte[4096];
        while ((len = is.read(data)) != -1) {
            buffer.write(data, 0, len);
        }
        return buffer.toByteArray();
    }
}
