package javax0.license3j.hardware;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

/**
 * Obtain unique and immutable cloud instance/virtual machine Id, suitable for licensing purposes.
 * <p>
 * Implementation note:<br>
 * Some cloud providers implement a GET rest endpoint (based on a dedicated, non-routable ip address)
 * that can report back the instance id easily. In some, cases such a rest endpoint is accessible with special tokens only.
 * In other cases, specific OS specific commands exists to obtain the instance id.
 * </p>
 */
public enum CloudProvider {

    /**
     * Obtain a Azure instance id.
     * <p>
     * Refer to <a href="https://docs.microsoft.com/en-us/azure/virtual-machines/windows/instance-metadata-service?tabs=windows">Azure documentation</a>
     * Refer to <a href="https://gist.github.com/dreamorosi/50cbfd622b478c2433602c16b7321c5d">Examples</a>
     */
    Azure {
        @Override
        public String getInstanceId() {
            return instanceIdFor("http://169.254.169.254/metadata/instance/compute/vmId?api-version=2021-02-01&format=text", "Metadata", "true");
        }
    },

    /**
     * Obtain a AWS instance id.
     * <p>
     * Refer to <a href="https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/instancedata-data-retrieval.html">AWS documentation</a>
     * Refer to <a href="https://gist.github.com/dreamorosi/50cbfd622b478c2433602c16b7321c5d">Examples</a>
     * Refer to <a href="https://stackoverflow.com/questions/625644/how-to-get-the-instance-id-from-within-an-ec2-instance">Stack overflow</a>
     */
    AWS {
        @Override
        public String getInstanceId() {
            return instanceIdFor("http://169.254.169.254/latest/meta-data/instance-id", "Metadata", "true");
        }
    },

    /**
     * Obtain a Google cloud instance id.
     * <p>
     * Refer to <a href="https://cloud.google.com/compute/docs/metadata/overview">Google cloud documentation</a>
     * Refer to <a href="https://cloud.yandex.com/en/docs/compute/operations/vm-info/get-info">Examples</a>
     * Refer to <a href="https://stackoverflow.com/questions/31688646/get-the-name-or-id-of-the-current-google-compute-instance">Stack overflow</a>
     */
    Google {
        @Override
        public String getInstanceId() {
            return instanceIdFor("http://metadata.google.internal/computeMetadata/v1/instance?alt=text", "Metadata-Flavor", "Google");
        }
    },

    /**
     * Obtain an Oracle cloud instance id.
     * <p>
     * Refer to <a href="https://docs.oracle.com/en-us/iaas/Content/Compute/Tasks/gettingmetadata.htm#metadata-keys">Oracle Cloud docs</a>
     * Refer to <a href="https://docs.oracle.com/en-us/iaas/Content/Compute/Tasks/gettingmetadata.htm#metadata-keys">Oracle Cloud metadata keys</a>
     * <p/>
     */
    Oracle {
        @Override
        public String getInstanceId() {
            return instanceIdFor("http://169.254.169.254/opc/v2/instance/id", "Authorization", "Bearer Oracle");
        }
    },

    /**
     * Obtain an Digital Ocean instance id.
     * <p>
     * Refer to <a href="https://www.alibabacloud.com/help/doc-detail/108460.htm?spm=a2c63.p38356.b99.196.4e3b3828rFutRB">How to Access Droplet Metadata</a>
     * </p>
     */
    DigitalOcean {
        @Override
        public String getInstanceId() {
            return instanceIdFor("http://169.254.169.254/metadata/v1/id", (String[]) null);
        }
    },

    /**
     * Obtain Alibaba cloud instance id.
     * <p>
     * Refer to <a href="https://www.alibabacloud.com/help/doc-detail/108460.htm?spm=a2c63.p38356.b99.196.4e3b3828rFutRB">View instance metadata</a>
     * </p>
     */
    AliBaba {
        @Override
        public String getInstanceId() {
            return instanceIdFor("http://100.100.100.200/latest/meta-data/instance-id", (String[]) null);
        }
    };

    /**
     * Get instance id, but can throw run-time exception.
     *
     * @return the instance id.
     */
    abstract public String getInstanceId();

    /**
     * Get the instance id.
     *
     * @return the instance id or NULL if there is a problem getting it.
     */
    public String getInstanceIdIgnoreException() {
        try {
            return getInstanceId();
        } catch (Exception e) {
            return null;
        }
    }


    private static String instanceIdFor(String instanceIdUrl, String... headers) {
        Objects.requireNonNull(instanceIdUrl);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(new URI(instanceIdUrl));
            if (headers != null && headers.length > 0)
                requestBuilder.headers(headers);
            HttpRequest request = requestBuilder.GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}