package javax0.license3j;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

/**
 * Extended license works with a license object and provides features
 * that is not core license functionality.
 *
 * @author Peter Verhas
 */
public class RevocableLicense {

    final private static String REVOCATION_URL = "revocationUrl";
    HttpHandler httpHandler = new HttpHandler();
    private final License license;

    public RevocableLicense(License license) {
        this.license = license;
    }

    /**
     * <p>Get the revocation URL of the license. This feature is stored
     * in the license under the name {@code revocationUrl}. This URL may
     * contain the string <code>${licenseId}</code> which is replaced by
     * the actual license ID. Thus there is no need to wire into the
     * revocation URL the license ID.</p>
     *
     * <p> If there is no license id defined in the license then the
     * place holder will be replaced using the fingerprint of the
     * license.</p>
     *
     * @return the revocation URL with the license id place holder
     * filled in, or {@code null} if there is no revocation URL template
     * defined in the license.
     * @throws MalformedURLException when the revocation url is not well
     *                               formatted
     */
    public URL getRevocationURL() throws MalformedURLException {
        final var revocationURLTemplate = license.get(REVOCATION_URL) == null ? null : license.get(REVOCATION_URL).getString();
        final String revocationURL;
        if (revocationURLTemplate != null) {
            final var id = Optional.ofNullable(license.getLicenseId()).orElse(license.fingerprint());
            if (id != null) {
                return new URL(revocationURLTemplate.replaceAll("\\$\\{licenseId}", id.toString()));
            } else {
                return new URL(revocationURLTemplate);
            }
        } else {
            return null;
        }
    }

    /**
     * Set the revocation URL. This method accepts the url as a string that
     * makes it possible to use a string that contains the
     * <code>${licenseId}</code> place holder.
     *
     * @param url the url from where the revocation information can be
     *            downloaded
     */
    public void setRevocationURL(final String url) {
        license.add(Feature.Create.stringFeature(REVOCATION_URL, url));
    }

    /**
     * Set the revocation URL. Using this method is discouraged in case the URL
     * contains the <code>${licenseId}</code> place holder. In that case it is
     * recommended to use the {@link #setRevocationURL(String)} method instead.
     *
     * @param url the revocation url
     */
    public void setRevocationURL(final URL url) {
        setRevocationURL(url.toString());
    }

    /**
     * Check if the license was revoked or not. For more information see the
     * documentation of the method {@link #isRevoked(boolean)}. Calling this
     * method is equivalent to calling {@code isRevoked(false)}, meaning that
     * the license is signaled not revoked if the revocation URL can not be
     * reached.
     *
     * @return {@code true} if the license was revoked and {@code false} if the
     * license was not revoked. It also returns {@code true} if the
     * revocation url is unreachable.
     */
    public boolean isRevoked() {
        return isRevoked(false);
    }

    /**
     * Check if the license is revoked or not. To get the revocation information
     * the method tries to issue a http connection (GET) to the url specified in
     * the license feature {@code revocationUrl}. If the URL returns anything
     * with http status code {@code 200} then the license is not revoked.
     * <p>
     * The url string in the feature {@code revocationUrl} may contain the place
     * holder <code>${licenseId}</code>, which is replaced by the feature value
     * {@code licenseId}. This feature makes it possible to setup a revocation
     * service and use a constant string in the different licenses.
     * <p>
     * The method can work in two different ways. One way is to ensure that the
     * license is not revoked and return {@code true} only if it is sure that
     * the license is revoked or revocation information is not available.
     * <p>
     * The other way is to ensure that the license is revoked and return
     * {@code false} if the license was not revoked or the revocation
     * information is not available.
     * <p>
     * The difference is whether to treat the license revoked when the
     * revocation service is not reachable.
     *
     * @param defaultRevocationState should be {@code true} to treat the license revoked when the
     *                               revocation service is not reachable. Setting this argument
     *                               {@code false} makes the revocation handling more polite: if
     *                               the license revocation service is not reachable then the
     *                               license is treated as not revoked.
     * @return {@code true} if the license is revoked and {@code false} if the
     * license is not revoked.
     */
    public boolean isRevoked(final boolean defaultRevocationState) {
        var revoked = true;
        try {
            final var url = getRevocationURL();
            if (url == null) {
                return false;
            }
            final var con = httpHandler.open(url);
            con.setUseCaches(false);
            if (con instanceof HttpURLConnection) {
                final var hCon = (HttpURLConnection) con;
                hCon.connect();
                return httpHandler.responseCode(hCon) != HttpURLConnection.HTTP_OK;
            }
            return false;
        } catch (final IOException exception) {
            revoked = defaultRevocationState;
        }
        return revoked;
    }

    /**
     * A simple wrapper class to make it possible to mock the network use when revocation
     * is tested. In tests a mock class extending this is injected.
     */
    static class HttpHandler {

        int responseCode(final HttpURLConnection connection)
                throws IOException {
            return connection.getResponseCode();
        }

        /**
         * This should be mocked when testing revocation not to wait for a
         * connection build up to a remote server.
         *
         * @param url the url to which connection is to be opened
         * @return the connection
         * @throws IOException if the connection can not be made
         */
        URLConnection open(URL url) throws IOException {
            return url.openConnection();
        }
    }
}
