import java.io.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Dictionary class that utilizes a Red Black tree under the
 * hood to preform word lookups on.
 */
public class StringDictionary {
    private final RedBlackTree<String> tree;

    /**
     * Constructs an empty dictionary.
     */
    public StringDictionary() {
        tree = new RedBlackTree<>();
    }

    private StringDictionary(RedBlackTree<String> tree) {
        this.tree = tree;
    }

    /**
     * Loads a previously serialized dictionary.
     *
     * @param file the location of the serialized dictionary
     *
     * @return the newly loaded dictionary instance
     *
     * @throws IOException if there was an error reading the file
     * @throws ClassNotFoundException if a class could not be found when deserializing
     */
    public static StringDictionary load(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        RedBlackTree<String> tree = (RedBlackTree<String>) objectInputStream.readObject();
        objectInputStream.close();
        return new StringDictionary(tree);
    }

    /**
     * Adds a singular word to the dictionary.
     *
     * @param word the word to add
     */
    public void add(String word) {
        tree.insert(word);
    }

    /**
     * Extends the dictionary with an array of words.
     *
     * @param words the array of words to be added
     */
    public void extend(String[] words) {
        Arrays.stream(fisherYatesShuffle(words)).forEach(this::add);
    }

    /**
     * Serialize the dictionary to a file.
     *
     * @param file the file to save to
     *
     * @throws IOException thrown if an error occurs while writing
     */
    public void save(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(tree);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /**
     * Check if a word in contained in the dictionary.
     *
     * @param word the word to look for
     *
     * @return whether the word exists in the dictionary
     */
    public boolean contains(String word) {
        return tree.contains(word);
    }

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
}
