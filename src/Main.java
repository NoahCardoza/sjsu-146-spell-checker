import java.io.*;
import java.net.URI;

class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Cacheable dictionaryString = new Cacheable(
                new File("dictionary.txt"),
                URI.create("http://www.math.sjsu.edu/~foster/dictionary.txt")
        );

        Dictionary dictionary = new Dictionary();
        dictionary.extend(dictionaryString.get().split("\\s+"));

        System.out.println(dictionary.contains("dog"));
    }
}