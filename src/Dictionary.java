import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Dictionary class that utilizes a Red Black tree under the
 * hood to preform word lookups on.
 */
public class Dictionary {
    private final RedBlackTree<DictionaryEntry> tree;

    /**
     * Constructs an empty dictionary.
     */
    public Dictionary() {
        tree = new RedBlackTree<>();
    }

    private Dictionary(RedBlackTree<DictionaryEntry> tree) {
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
    public static Dictionary load(File file) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        RedBlackTree<DictionaryEntry> tree = (RedBlackTree<DictionaryEntry>) objectInputStream.readObject();
        objectInputStream.close();
        return new Dictionary(tree);
    }

    /**
     * Adds a singular word to the dictionary.
     *
     * @param word the word to add
     */
    public void add(DictionaryEntry word) {
        tree.insert(word);
    }

    /**
     * Extends the dictionary with an array of words.
     *
     * @param words the array of words to be added
     */
    public void extend(List<DictionaryEntry> words) {
        fisherYatesShuffle(words).forEach(this::add);
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
        return define(word) != null;
    }

    /**
     * Search up a word and return a list of definitions.
     *
     * @param word the word to search for
     *
     * @return a list of definitions or null if not found
     */
    public DictionaryEntry define(String word) {
        word = word.strip().toLowerCase();
        Node<DictionaryEntry> node = tree.find(new DictionaryEntry(word.strip().toLowerCase()));
        if (node == null) {
            if (word.endsWith("ing")) {
                String substr = word.substring(0, word.length() - 3);
                // calling -> call
                DictionaryEntry entry = define(substr);
                if (entry == null) {
                    // coming -> come
                    return define(substr + "e");
                }
                return entry;
            }
            if (word.endsWith("ed")) {
                String substr = word.substring(0, word.length() - 1);
                // managed -> manage
                DictionaryEntry entry = define(substr);
                if (entry == null) {
                    // colored -> color
                    entry = define(substr.substring(0, substr.length() - 1));
                }
                if (entry == null) {
                    // kidnapped -> kidnap
                    return define(substr.substring(0, substr.length() - 1));
                }
                return entry;
            }
            if (word.endsWith("s")) {
                String substr = word.substring(0, word.length() - 1);
                // starts -> start
                DictionaryEntry entry = define(substr);
                if (entry == null && substr.endsWith("e")) {
                    // dispatches -> dispatch
                    return define(substr.substring(0, substr.length() - 1));
                }
                return entry;
            }
            return null;
        }
        return node.getData();
    }

    private List<DictionaryEntry> fisherYatesShuffle(List<DictionaryEntry> array) {
        Random random = new Random(Double.doubleToLongBits(Math.random()));
        DictionaryEntry tmp;
        int randomIndex;
        for (int i = array.size() - 1; i > 0; i--) {
            randomIndex = random.nextInt(i);
            tmp = array.get(i);
            array.set(i, array.get(randomIndex));
            array.set(randomIndex, tmp);
        }
        return array;
    }
}
