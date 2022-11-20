import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {
    @Test
    public void providedTest() {
        RedBlackTree<String> tree = new RedBlackTree<>();
        Stream.of("D","B","A","C","F","E","H","G","I","J").forEachOrdered(tree::insert);
        assertEquals("DBACFEHGIJ", makeString(tree));
        String str= """
                Color: BLACK, Key:D Parent: \s
                Color: BLACK, Key:B Parent: D
                Color: BLACK, Key:A Parent: B
                Color: BLACK, Key:C Parent: B
                Color: BLACK, Key:F Parent: D
                Color: BLACK, Key:E Parent: F
                Color: RED, Key:H Parent: F
                Color: BLACK, Key:G Parent: H
                Color: BLACK, Key:I Parent: H
                Color: RED, Key:J Parent: I
                """;
        assertEquals(str, makeStringDetails(tree));
    }

    @Test
    public void spellCheckerTest() throws IOException, InterruptedException {
        CacheableString dictionaryCache = new CacheableString(
                new File("dictionary.txt"),
                URI.create("http://www.math.sjsu.edu/~foster/dictionary.txt")
        );
        dictionaryCache.sync();


        StringDictionary dictionary = new StringDictionary();
        dictionary.extend(dictionaryCache.getContents().split("\\s+"));
        
        String text = Files.readString(Path.of("tests/prologues.txt"));
        HashSet<String> unidentified = new HashSet<>();
        
        Arrays.stream(text.split("\\W+")).map(String::toLowerCase).forEach(word -> {
            if (!dictionary.contains(word)) {
                unidentified.add(word);   
            }
        });

        for (String word : unidentified) {
            System.out.println(word);
        }
        System.out.printf("%s words could not be found%n", unidentified.size());
    }

    @Test
    public void localSpellCheckerTest() throws IOException {
        File localDictionary = new File("/usr/share/dict/words");

        if (!localDictionary.exists()) return;

        StringDictionary dictionary = new StringDictionary();
        dictionary.extend(Files.readString(localDictionary.toPath()).split("\\s+"));

        String text = Files.readString(Path.of("tests/prologues.txt"));
        HashSet<String> unidentified = new HashSet<>();

        Arrays.stream(text.split("\\W+")).map(String::toLowerCase).forEach(word -> {
            if (!dictionary.contains(word)) {
                unidentified.add(word);
            }
        });

        for (String word : unidentified) {
            System.out.println(word);
        }
        System.out.printf("%s words could not be found%n", unidentified.size());
    }

    @Test
    public void advancedSpellCheckerTest() throws IOException, ParserConfigurationException, InterruptedException, SAXException {
        Dictionary dictionary = new Dictionary();

        dictionary.extend(loadDictionaryEntries());

        String text = Files.readString(Path.of("tests/prologues.txt"));
        HashSet<String> unidentified = new HashSet<>();

        Arrays.stream(text.split("\\W+")).map(String::toLowerCase).forEach(word -> {
            if (!dictionary.contains(word)) {
                unidentified.add(word);
            }
        });

        for (String word : unidentified) {
            System.out.println(word);
        }
        System.out.printf("%s words could not be found%n", unidentified.size());

        Stream.of("dog", "hut", "tear", "attack", "awesome").forEach(word -> {
            System.out.printf("> %s%n", word);
            List<String> definitions = dictionary.define(word).getDefinitions();
            for (int i = 0; i < definitions.size(); i++) {
                System.out.printf("| %2d: %s%n", i + 1, definitions.get(i));
            }
        });
    }

    public static ArrayList<DictionaryEntry> loadDictionaryEntries() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        ArrayList<DictionaryEntry> dictionaryEntries = new ArrayList<>();

        CacheableZip cacheable = new CacheableZip(
                new File("dictionary"),
                URI.create("http://rali.iro.umontreal.ca/GCIDE/new-entries.zip")
        );
        cacheable.sync();

        for (char section = 'a'; section <= 'z' ; section++) {
            File xmlFile = new File(String.format("dictionary/gcide_%s-entries.xml", section));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("entry");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;

                    ArrayList<String> definitions = new ArrayList<>();

                    NodeList innerNodeList = el.getElementsByTagName("def");
                    for (int j = 0; j < innerNodeList.getLength(); j++) {
                        Node innerNode = innerNodeList.item(j);
                        if (innerNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element innerEl = (Element) innerNode;
                            definitions.add(innerEl.getTextContent());
                        }
                    }

                    dictionaryEntries.add(new DictionaryEntry(el.getAttribute("key").strip().toLowerCase(), definitions));
                }
            }
        }

        return dictionaryEntries;
    }

    public static String makeString(RedBlackTree<String> t) {
        class MyVisitor implements Visitor<String> {
            String result = "";

            @Override
            public void visit(RBNode<String> n) {
                result = result + n.getData();
            }
        }

        MyVisitor v = new MyVisitor();
        t.preOrderVisit(v);
        return v.result;
    }

    public static String makeStringDetails(RedBlackTree<String> t) {
        class MyVisitor implements Visitor<String> {
            String result = "";
            @Override
            public void visit(RBNode<String> n)
            {
                result = result + "Color: " + n.getColor() + ", Key:" + n.getData() + " Parent: " + (n.isRoot() ? " " : n.getParent().getData()) + "\n";
            }
        }

        MyVisitor v = new MyVisitor();
        t.preOrderVisit(v);
        return v.result;
    }
}
