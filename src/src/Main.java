import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class Main {
    /**
     * Makes an HTTP request to download the dictionary words.
     *
     * @throws IOException if an error occurs making the http request
     * @throws InterruptedException if the http request is interrupted
     *
     * @return the dictionary words as a string
     */
    public static String fetchDictionary() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("http://www.math.sjsu.edu/~foster/dictionary.txt"))
                .header("accept", "text/plain")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static void main(String[] args) {
        RedBlackTree<String> tree = new RedBlackTree<>();
        tree.insert("G");
        tree.insert("U");
        tree.insert("P");
        tree.insert("X");
    }
}