import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

/**
 * Manages resources that shouldn't be pushed to the repository
 * but shouldn't be downloaded over and over everytime they are
 * needed.
 */
public abstract class Cacheable {
    private final File file;
    private final URI uri;

    /**
     * Constructs a Cacheable instance.
     *
     * @param file the location to cache it to on the file system
     * @param uri the uri to load it from if it's not cached
     */
    public Cacheable(File file, URI uri) {
        this.file = file;
        this.uri = uri;
    }

    /**
     * Returns the contents of the wed resource checking memory first,
     * then the file system, and lastly fetching it from the URI and
     * saving it to the file system.
     */
    abstract public void sync() throws IOException, InterruptedException;

    public File getFile() {
        return file;
    }

    public URI getUri() {
        return uri;
    }
}
