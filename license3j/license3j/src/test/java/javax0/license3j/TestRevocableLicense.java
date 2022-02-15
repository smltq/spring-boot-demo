package javax0.license3j;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class TestRevocableLicense {
    private static final String licenceUrlTemplate = "https://github.com/verhas/License3j/blob/master/src/test/resources/${licenseId}";
    private static final String reachableUrlString = "https://github.com/verhas/License3j";
    private static final String unreachableUrlString = "https://any.com";

    /**
     * Set up a mock HttpHandler in the license that will return with the status or will throw exception.
     *
     * @param lic       the license where the mock will be inserted into
     * @param status    to be returned when {@code exception} is {@code null}
     * @param exception the exception to be thrown or {@code null} if there should be no exception calling the handler
     */
    private void mockHttpFetch(final RevocableLicense lic, final int status, final IOException exception) {
        var handler = new MockHttpHandler();
        if (exception == null) {
            handler.setResponseCode(status);
        } else {
            handler.setException(exception);
        }
        lic.httpHandler = handler;
    }

    @Test
    @DisplayName("license is not revoked when the http request returns true")
    public void licenseIsNotRevokedWhenHttpReturns200OK() throws IOException {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        mockHttpFetch(lic, 200, null);
        lic.setRevocationURL(new URL(reachableUrlString));
        Assertions.assertFalse(lic.isRevoked(true));
    }

    @Test
    @DisplayName("license is not revoked when the URL is not reachable")
    public void licenseIsNotRevokedWhenUnreachableGraceful() throws IOException {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        mockHttpFetch(lic, 0, new IOException());
        lic.setRevocationURL(new URL(unreachableUrlString));
        Assertions.assertFalse(lic.isRevoked(false));
    }

    @Test
    @DisplayName("License is revoked default when the URL is not reachable")
    public void licenseIsNotRevokedWhenUnreachableStrict() throws IOException {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        mockHttpFetch(lic, 0, new IOException());
        lic.setRevocationURL(new URL(unreachableUrlString));
        Assertions.assertTrue(lic.isRevoked(true));
    }

    @Test
    @DisplayName("License is not revoked when the URL is general and there is no placeholder in the revocation url and response is 200")
    public void licenseIsNotRevokedWhenNoIdIsInUrlTemplateAndTheUrlIsOK() {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        mockHttpFetch(lic, 200, null);
        lic.setRevocationURL(reachableUrlString);
        license.setLicenseId(new UUID(0, 1L));
        Assertions.assertFalse(lic.isRevoked());
    }

    @Test
    public void licenseIsNotRevokedWhenIdFileIsThere() {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        mockHttpFetch(lic, 0, new IOException());
        lic.setRevocationURL(licenceUrlTemplate);
        license.setLicenseId(new UUID(0, 1L));
        Assertions.assertFalse(lic.isRevoked(false));
    }

    @Test
    public void licenseIsRevokedWhenIdFileIsNotThere() {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        mockHttpFetch(lic, 404, null);
        lic.setRevocationURL(licenceUrlTemplate);
        license.setLicenseId(new UUID(0, 2L));
        Assertions.assertTrue(lic.isRevoked());
    }

    @Test
    public void licenseIsRevokedWhenUrlIsMalformed() {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        mockHttpFetch(lic, 0, new IOException());
        lic.setRevocationURL("ftp://index.hu/");
        Assertions.assertTrue(lic.isRevoked(true));
    }

    @Test
    public void notSettingRevocationUrlResultNullRevocationUrl()
        throws MalformedURLException {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        Assertions.assertNull(lic.getRevocationURL());
    }

    @Test
    public void licenseWithNoRevocationUrlIsNotRevoked() {
        final var license = new License();
        final var lic = new RevocableLicense(license);
        Assertions.assertFalse(lic.isRevoked(true));
    }

    private static class MockHttpHandler extends RevocableLicense.HttpHandler {
        private int responseCode;
        private IOException exception = null;

        public void setException(IOException exception) {
            this.exception = exception;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

        int responseCode(final HttpURLConnection connection)
            throws IOException {
            if (exception != null) {
                throw exception;
            }
            return responseCode;
        }

        URLConnection open(URL url) {
            return new HttpURLConnection(url) {

                @Override
                public void disconnect() {
                }

                @Override
                public boolean usingProxy() {
                    return false;
                }

                @Override
                public void connect() throws IOException {
                    if (exception != null) {
                        throw exception;
                    }
                }
            };
        }
    }
}
