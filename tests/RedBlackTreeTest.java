import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
        Cacheable dictionaryString = new Cacheable(
                new File("dictionary.txt"),
                URI.create("http://www.math.sjsu.edu/~foster/dictionary.txt")
        );

        Dictionary dictionary = new Dictionary();
        dictionary.extend(dictionaryString.get().split("\\s+"));
        
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
    }


    public static String makeString(RedBlackTree<String> t) {
        class MyVisitor implements Visitor<String> {
            String result = "";

            @Override
            public void visit(Node<String> n) {
                result = result + n.getKey();
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
            public void visit(Node<String> n)
            {
                result = result + "Color: " + n.getColor() + ", Key:" + n.getKey() + " Parent: " + (n.isRoot() ? " " : n.getParent().getKey()) + "\n";
            }
        }

        MyVisitor v = new MyVisitor();
        t.preOrderVisit(v);
        return v.result;
    }
}
