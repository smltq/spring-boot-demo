package javax0.license3jrepl;

import javax0.license3j.Feature;
import javax0.license3j.License;
import javax0.license3j.crypto.LicenseKeyPair;
import javax0.license3j.io.IOFormat;
import javax0.license3j.io.KeyPairReader;
import javax0.license3j.io.KeyPairWriter;
import javax0.license3j.io.LicenseReader;
import javax0.license3j.io.LicenseWriter;
import javax0.repl.CommandEnvironment;
import javax0.repl.Repl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Set;

import static javax0.repl.CommandDefinitionBuilder.start;

/**
 * The main class of the REPL application
 */
public class App {
    private static final String PUBLIC_KEY_FILE = "publicKeyFile";
    private static final String PRIVATE_KEY_FILE = "privateKeyFile";
    private static final String ALGORITHM = "algorithm";
    private static final String DIGEST = "digest";
    private static final String SIZE = "size";
    private static final String FORMAT = "format";
    private static final String CONFIRM = "confirm";
    private static final String TEXT = "TEXT";
    private static final String BINARY = "BINARY";
    private static final String BASE_64 = "BASE64";
    private License license;
    private boolean licenseToSave = false;
    private LicenseKeyPair keyPair;
    final private static Properties properties = new Properties();
    private static String version;

    static {
        try {
            properties.load(App.class.getClassLoader().getResourceAsStream("version.properties"));
            version = properties.getProperty("version");
        } catch (IOException e) {
            version = "unknown version";
        }
    }

    private final Repl application = new Repl().command(
                    start().
                            kw("feature")
                            .usage("feature name:TYPE=value")
                            .help("feature name:TYPE=value to add a new feature to the actual license\n" +
                                    "Adding a new feature will invalidate the license.\n" +
                                    "Do not forget to sign and save the license after you are finished.\n")
                            .executor(this::feature)
            ).command(
                    start().
                            kw("licenseLoad").parameters(Set.of(FORMAT, CONFIRM))
                            .usage("licenseLoad [format=TEXT*|BINARY|BASE64] fileName")
                            .help("Load a license from a file to memory. Default assumption is that the license is TEXT format.\n" +
                                    "Use the parameter 'format' if the license was saved BINARY or BASE64.\n")
                            .executor(this::loadLicense)
            ).command(
                    start().
                            kw("saveLicense").parameter(FORMAT)
                            .usage("saveLicense [format=TEXT*|BINARY|BASE64] fileName")
                            .help("Save the license to a file. The file will be overwritten.\n" +
                                    "Use the 'format' parameter to specify the format to be used for saving\n" +
                                    "The default format is TEXT.\n")
                            .executor(this::saveLicense)
            ).command(
                    start().
                            kw("loadPrivateKey").parameter(FORMAT)
                            .usage("loadPrivateKey [format=BINARY*|BASE64] keyFile")
                            .help("Load a private key.\n" +
                                    "Use the parameter 'format' if the key was saved BASE64.\n" +
                                    "The default format is BINARY.\n")
                            .executor(this::loadPrivateKey)
            ).command(
                    start().
                            kw("loadPublicKey").parameter(FORMAT)
                            .usage("loadPublicKey [format=BINARY*|BASE64] keyFile")
                            .help("Load a public key.\n" +
                                    "Use the parameter 'format' if the key was saved BASE64.\n" +
                                    "The default format is BINARY.\n")
                            .executor(this::loadPublicKey)
            ).command(
                    start().
                            kw("sign").parameter(DIGEST)
                            .usage("sign [digest=SHA-512]")
                            .help("Sign the license in memory.\n" +
                                    "Specify the name of the digest in case you want something different from SHA-512.\n")
                            .executor(this::sign)
            ).command(
                    start().
                            kw("generateKeys").parameters(Set.of(ALGORITHM, SIZE, FORMAT, PUBLIC_KEY_FILE, PRIVATE_KEY_FILE))
                            .usage("generateKeys [algorithm=RSA/ECB/PKCS1Padding] [size=2048] [format=BINARY*|BASE64] public=xxx private=xxx")
                            .help("Generate public and private keys and save them into files.\n" +
                                    "You can specify the algorithm, key size and the format. The defaults are RSA/ECB/PKCS1Padding, 2048 and BINARY.\n" +
                                    "You should specify the file names using the parameters 'public' and 'private'.\n" +
                                    "The keys remain in the memory and can be used to sign and verify license.\n")
                            .executor(this::generate)
            ).command(
                    start().
                            kw("verify").noParameters()
                            .usage("verify")
                            .help("Verify the signature on a license.\n")
                            .executor(this::verify)
            ).command(
                    start().
                            kw("newLicense").parameter(CONFIRM)
                            .usage("newLicense")
                            .help("Wipe off any existing license from memory and start a new empty one.\n" +
                                    "If the license was modified and unsaved you should use the 'confirm=yes' parameter.\n")
                            .executor(this::newLicense)
            ).command(
                    start().
                            kw("dumpLicense").noParameters()
                            .usage("dumpLicense")
                            .help("Dump the license features to the screen as it would be saved to a file in TEXT format.\n")
                            .executor(this::dumpLicense)
            ).command(
                    start().
                            kw("dumpPublicKey").noParameters()
                            .usage("dumpPublicKey")
                            .help("Dump the public key onto the screen in Java format so that you can copy from the scree\n" +
                                    "and insert the code into your application. Note that you cannot dump the private key\n" +
                                    "as the private should never be encoded into the applciation protected by the license.\n")
                            .executor(this::digestPublicKey)
            )
            .alias("ll", "licenseload")
            .alias("lprk", "loadprivatekey")
            .alias("lpuk", "loadpublickey")
            .alias("dpk", "dumppublickey")
            .alias("dl", "dumplicense")
            .prompt("L3j> $ ")
            .startup(".license3j")
            .title("License3j REPL application " + version)
            .stateReporter(this::stateReporter)
            .allowExit(this::allowExit);

    public static void main(String[] args) {
        new App().application.args(args).run();
    }

    private Boolean allowExit(@SuppressWarnings("unused") CommandEnvironment ignored) {
        return !licenseToSave;
    }

    private void stateReporter(CommandEnvironment env) {
        final var w = env.console().writer();
        if (license == null) {
            w.print("No license in memory\n");
        } else {
            final var owner = license.get("owner");
            if (owner == null) {
                w.print("License w/o owner is in memory.\n");
            } else {
                if (owner.isString()) {
                    w.print("License for '" + owner.getString() + "' is in memory.\n");
                } else {
                    w.print("License with non-string owner is in memory.\n");
                }
            }
        }
        if (keyPair == null) {
            w.print("No keys in memory.\n");
        } else {
            if (keyPair.getPair().getPublic() == null) {
                w.print("No public key in memory.\n");
            } else {
                w.print("Public key in memory.\n");
            }
            if (keyPair.getPair().getPrivate() == null) {
                w.print("No private key in memory.\n");
            } else {
                w.print("Private key in memory.\n");
            }
        }
        w.flush();
    }

    private void dumpLicense(CommandEnvironment env) {
        if (license == null) {
            env.message().error("There is no license to show.");
            return;
        }
        try {
            final var baos = new ByteArrayOutputStream();
            final var reader = new LicenseWriter(baos);
            reader.write(license, IOFormat.STRING);
            env.message().info("License:\n" + new String(baos.toByteArray(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            env.message().error("Error writing license file " + e);
        }

    }

    private void saveLicense(CommandEnvironment env) {
        if (license == null) {
            env.message().error("There is no license to save.");
            return;
        }
        try {
            final var fileName = getLicenseFileName(env);
            final var reader = new LicenseWriter(fileName);
            final var format = env.parser().getOrDefault(FORMAT, TEXT, Set.of(TEXT, BINARY, BASE_64));
            switch (format) {
                case TEXT:
                    reader.write(license, IOFormat.STRING);
                    break;
                case BINARY:
                    reader.write(license, IOFormat.BINARY);
                    break;
                case BASE_64:
                    reader.write(license, IOFormat.BASE64);
                    break;
                default:
                    env.message().error("Invalid format to write the license: " + format);
                    return;
            }
            env.message().info("License was saved into the file " + new File(fileName).getAbsolutePath());
            licenseToSave = false;
        } catch (IOException e) {
            env.message().error("Error writing license file " + e);
        }
    }

    private void loadPrivateKey(CommandEnvironment env) {
        if (keyPair != null && keyPair.getPair() != null && keyPair.getPair().getPrivate() != null) {
            env.message().info("Overriding old key from file");
        }
        final var keyFile = env.parser().get(0);
        if (keyFile.isEmpty()) {
            env.message().error("keyFile has to be specified from where the key is loaded");
            return;
        }
        final var format = IOFormat.valueOf(env.parser().getOrDefault(FORMAT, BINARY, Set.of(BASE_64, BINARY)));
        try (final var reader = new KeyPairReader(keyFile.get())) {
            keyPair = merge(keyPair, reader.readPrivate(format));
            final var keyPath = new File(keyFile.get()).getAbsolutePath();
            env.message().info("Private key loaded from" + keyPath);
        } catch (Exception e) {
            env.message().error("An exception occurred loading the key: " + e);
            e.printStackTrace();
        }
    }

    private void loadPublicKey(CommandEnvironment env) {
        if (keyPair != null && keyPair.getPair() != null && keyPair.getPair().getPrivate() != null) {
            env.message().info("Overriding old key from file");
        }
        final var keyFile = env.parser().get(0);
        if (keyFile.isEmpty()) {
            env.message().error("keyFile has to be specified from where the key is loaded");
            return;
        }
        final var format = IOFormat.valueOf(env.parser().getOrDefault(FORMAT, BINARY, Set.of(BASE_64, BINARY)).toUpperCase());
        try (final var reader = new KeyPairReader(keyFile.get())) {
            keyPair = merge(keyPair, reader.readPublic(format));
            final var keyPath = new File(keyFile.get()).getAbsolutePath();
            env.message().info("Public key loaded from" + keyPath);
        } catch (Exception e) {
            env.message().error("An exception occurred loading the keys: " + e);
            e.printStackTrace();
        }
    }

    private LicenseKeyPair merge(LicenseKeyPair oldKp, LicenseKeyPair newKp) {
        if (oldKp == null) {
            return newKp;
        }
        final var cipher = oldKp.cipher();
        if (newKp.getPair().getPublic() != null) {
            return LicenseKeyPair.Create.from(newKp.getPair().getPublic(), oldKp.getPair().getPrivate(), cipher);
        }
        if (newKp.getPair().getPrivate() != null) {
            return LicenseKeyPair.Create.from(oldKp.getPair().getPublic(), newKp.getPair().getPrivate(), cipher);
        }
        return oldKp;
    }

    private void digestPublicKey(CommandEnvironment env) {
        try {
            if (keyPair == null) {
                env.message().error("There is no public key loaded");
                return;
            }
            final var key = keyPair.getPublic();
            final var md = MessageDigest.getInstance("SHA-512");
            final var calculatedDigest = md.digest(key);
            final var javaCode = new StringBuilder("--KEY DIGEST START\nbyte [] digest = new byte[] {\n");
            for (int i = 0; i < calculatedDigest.length; i++) {
                int intVal = ((int) calculatedDigest[i]) & 0xff;
                javaCode.append(String.format("(byte)0x%02X, ", intVal));
                if (i % 8 == 0) {
                    javaCode.append("\n");
                }
            }
            javaCode.append("\n};\n---KEY DIGEST END\n");

            javaCode.append("--KEY START\nbyte [] key = new byte[] {\n");
            for (int i = 0; i < key.length; i++) {
                int intVal = ((int) key[i]) & 0xff;
                javaCode.append(String.format("(byte)0x%02X, ", intVal));
                if (i % 8 == 0) {
                    javaCode.append("\n");
                }
            }
            javaCode.append("\n};\n---KEY END\n");

            env.message().info("\n" + javaCode.toString());
        } catch (NoSuchAlgorithmException e) {
            env.message().error("" + e);
        }
    }

    private void generate(CommandEnvironment env) {
        final var algorithm = env.parser().getOrDefault(ALGORITHM, "RSA");
        final var sizeString = env.parser().getOrDefault(SIZE, "2048");
        final var format = IOFormat.valueOf(env.parser().getOrDefault(FORMAT, BINARY));
        final var publicKeyFile = env.parser().get(PUBLIC_KEY_FILE);
        final var privateKeyFile = env.parser().get(PRIVATE_KEY_FILE);
        if (publicKeyFile.isEmpty() || privateKeyFile.isEmpty()) {
            env.message().error("Keypair generation needs output files specified where keys are to be saved. " +
                    "Use options 'publicKeyFile' and 'privateKeyFile'");
            return;
        }
        final int size;
        try {
            size = Integer.parseInt(sizeString);
        } catch (NumberFormatException e) {
            env.message().error("Option size has to be a positive decimal integer value. " +
                    sizeString + " does not qualify as such.");
            return;
        }
        generateKeys(algorithm, size);
        try (final var writer = new KeyPairWriter(privateKeyFile.get(), publicKeyFile.get())) {
            writer.write(keyPair, format);
            final var privateKeyPath = new File(privateKeyFile.get()).getAbsolutePath();
            env.message().info("Private key saved to " + privateKeyPath);
            env.message().info("Public key saved to " + new File(publicKeyFile.get()).getAbsolutePath());
        } catch (IOException e) {
            env.message().error("An exception occurred saving the keys: " + e);
        }
    }

    private void verify(CommandEnvironment env) {
        if (license == null) {
            env.message().error("There is no license to verify.");
            return;
        }
        if (keyPair == null || keyPair.getPair() == null || keyPair.getPair().getPublic() == null) {
            env.message().error("There is no public key to verify the license with.");
            return;
        }
        if (license.isOK(keyPair.getPair().getPublic())) {
            env.message().info("License is properly signed.");
        } else {
            env.message().error("License is not signed properly.");
        }
    }

    private void sign(CommandEnvironment env) {
        try {
            final var digest = env.parser().getOrDefault("digest", "SHA-512");
            if (license == null) {
                env.message().error("There is no license loaded to be signed");
            } else {
                license.sign(keyPair.getPair().getPrivate(), digest);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void feature(CommandEnvironment env) {
        if (license == null) {
            env.message().error("Feature cannot be added when there is no license loaded. Use 'loadLicense' or 'newLicense'");
            return;
        }
        license.add(Feature.Create.from(env.line()));
        licenseToSave = true;
    }

    private void newLicense(CommandEnvironment env) {
        if (licenseToSave) {
            if (env.parser().get(CONFIRM, Set.of("yes")).isPresent()) {
                licenseToSave = false;
            } else {
                env.message().error("There is an unsaved license in memory. Use 'newLicense confirm=yes'");
                return;
            }
        }
        license = new License();
    }

    private void loadLicense(CommandEnvironment env) {
        if (licenseToSave) {
            if (env.parser().get(CONFIRM, Set.of("yes")).isPresent()) {
                env.message().error("There is an unsaved license in memory. Use 'newLicense confirm=yes'");
                return;
            }
        }
        try (final var reader = new LicenseReader(getLicenseFileName(env))) {
            final String format = env.parser().getOrDefault(FORMAT, TEXT, Set.of(TEXT, BINARY, BASE_64));
            switch (format) {
                case TEXT:
                    license = reader.read(IOFormat.STRING);
                    break;
                case BINARY:
                    license = reader.read();
                    break;
                case BASE_64:
                    license = reader.read(IOFormat.BASE64);
                    break;
                default:
                    env.message().error("Invalid format to read the license: " + format);
            }
            licenseToSave = false;
        } catch (IOException e) {
            env.message().error("Error reading license file " + e);
        }
    }

    private String getLicenseFileName(CommandEnvironment env) {
        return env.parser().get(0).orElse("");
    }

    private void generateKeys(String algorithm, int size) {
        try {
            keyPair = LicenseKeyPair.Create.from(algorithm, size);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Algorithm " + algorithm + " is not handled by the current version of this application.", e);
        }
    }
}
