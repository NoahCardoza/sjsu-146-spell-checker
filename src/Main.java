import java.io.*;
import java.net.URI;
import java.util.Random;

class Main {


    static private String[] fisherYatesShuffle(String[] array) {
        Random random = new Random(Double.doubleToLongBits(Math.random()));
        String tmp;
        int randomIndex;
        for (int i = array.length - 1; i > 0; i--) {
            randomIndex = random.nextInt(i);
            tmp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = tmp;
        }
        return array;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Cacheable dictionaryString = new Cacheable(
                new File("dictionary.txt"),
                URI.create("http://www.math.sjsu.edu/~foster/dictionary.txt")
        );

        File file = new File("dictionary.dat");
//        Dictionary dictionary = Dictionary.load(file);
        Dictionary dictionary = new Dictionary();
        dictionary.extend(fisherYatesShuffle(dictionaryString.get().split("\\s+")));

        System.out.println(dictionary.contains("dog"));
        dictionary.save(file);
    }
}