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
public class CacheableString extends Cacheable {
    private String contents;

    /**
     * Constructs a Cacheable instance.
     *
     * @param file the location to cache it to on the file system
     * @param uri the uri to load it from if it's not cached
     */
    public CacheableString(File file, URI uri) {
        super(file, uri);
        this.contents = null;
    }

    /**
     * Returns the contents of the wed resource checking memory first,
     * then the file system, and lastly fetching it from the URI and
     * saving it to the file system.
     *
     * @throws IOException if an error occurs making the http request
     * @throws InterruptedException if the http request is interrupted
     */
    public void sync() throws IOException, InterruptedException {
        if (contents != null) return;

        if (getFile().exists()) {
            contents = Files.readString(getFile().toPath());
            return;
        }

        String contents = fetch();
        Files.writeString(getFile().toPath(), contents);
    }

    /**
     * Makes an HTTP request to download the uri provided.
     *
     * @return the contents of the uri as a string
     *
     * @throws IOException if an error occurs making the http request
     * @throws InterruptedException if the http request is interrupted
     */
    private String fetch() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(getUri())
                .header("accept", "text/plain")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String getContents() {
        return contents;
    }
}
