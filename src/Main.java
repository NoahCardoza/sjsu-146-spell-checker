import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) throws IOException, InterruptedException, SAXException, ParserConfigurationException {
        CacheableZip cacheable = new CacheableZip(
                new File("dictionary"),
                URI.create("http://rali.iro.umontreal.ca/GCIDE/new-entries.zip")
        );
        cacheable.sync();

        Dictionary dictionary = new Dictionary();

        ArrayList<DictionaryEntry> dictionaryEntries = new ArrayList<>();
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

        dictionary.extend(dictionaryEntries);

        String text = Files.readString(Path.of("tests/prologues.txt"));
        HashSet<String> unidentified = new HashSet<>();

        Arrays.stream(text.split("\\W+")).map(String::toLowerCase).forEach(word -> {
            if (!dictionary.contains(word)) {
                unidentified.add(word);
            }
        });

        for (String word :
                unidentified) {
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
}