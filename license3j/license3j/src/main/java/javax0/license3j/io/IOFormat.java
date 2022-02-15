package javax0.license3j.io;

/**
 * License can be stored in files in three different format:
 *
 * <ol>
 *     <li>BINARY is as the name suggests binary format</li>
 *     <li>BASE64 is the same as binary but encoded to be ascii using the Base64 encoding</li>
 *     <li>STRING is a textual, human readable format that can be edited using text editor</li>
 * </ol>
 *
 */
public enum IOFormat {
    BINARY, BASE64, STRING
}
