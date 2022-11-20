import java.util.ArrayList;

public class DictionaryEntry implements Comparable<DictionaryEntry> {
    private final String word;
    private final ArrayList<String> definitions;

    public DictionaryEntry(String word) {
        this.word = word;
        this.definitions = null;
    }

    public DictionaryEntry(String word, ArrayList<String> definitions) {
        this.word = word;
        this.definitions = definitions;
    }

    public String getWord() {
        return word;
    }

    public ArrayList<String> getDefinitions() {
        return definitions;
    }

    @Override
    public int compareTo(DictionaryEntry entry) {
        int v = word.compareTo(entry.word);
//        System.out.printf("'%s' <%s> '%s'%n",word, v, entry.word);
        return v;
    }
}
