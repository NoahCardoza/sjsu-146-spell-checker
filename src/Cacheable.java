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
public class Cacheable {
    private final File file;
    private final URI uri;
    private String contents;

    /**
     * Constructs a Cacheable instance.
     *
     * @param file the location to cache it to on the file system
     * @param uri the uri to load it from if it's not cached
     */
    Cacheable(File file, URI uri) {
        this.file = file;
        this.uri = uri;
        this.contents = null;
    }

    /**
     * Returns the contents of the wed resource checking memory first,
     * then the file system, and lastly fetching it from the URI and
     * saving it to the file system.
     *
     * @return the contents of the uri provided
     *
     * @throws IOException if an error occurs making the http request
     * @throws InterruptedException if the http request is interrupted
     */
    public String get() throws IOException, InterruptedException {
        if (contents != null) {
            return contents;
        }

        if (file.exists()) {
            contents = Files.readString(file.toPath());
            return contents;
        }

        String contents = fetch();
        Files.writeString(file.toPath(), contents);

        return contents;
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

        HttpRequest request = HttpRequest.newBuilder(uri)
                .header("accept", "text/plain")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
