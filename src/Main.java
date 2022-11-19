import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

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

    static private void fisherYatesShuffle(String[] array) {
        Random random = new Random(Double.doubleToLongBits(Math.random()));
        String tmp;
        int randomIndex;
        for (int i = array.length - 1; i > 0; i--) {
            randomIndex = random.nextInt(i);
            tmp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = tmp;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        RedBlackTree<String> tree = new RedBlackTree<>();

        String[] words = fetchDictionary().split("\\s+");
        fisherYatesShuffle(words);

//        String[] words = """
//              iodization
//              senkaku
//              orinoko
//                """.split("\\s+");

        for (int i = 0; i < words.length; i++) {
//            System.out.println(words[i]);
            tree.insert(words[i]);
            if (i % 10000 == 0) {

                System.out.println(i);
            }
        }

        tree.inOrderVisit(node -> System.out.print(node.getKey()));
    }
}