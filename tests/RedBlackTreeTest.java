import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {
    @Test
    //Test the Red Black Tree
    public void test() {
        RedBlackTree<String> rbt = new RedBlackTree<>();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
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
        assertEquals(str, makeStringDetails(rbt));

    }

    //add tester for spell checker

    public static String makeString(RedBlackTree<String> t) {
        class MyVisitor implements Visitor<String> {
            String result = "";

            @Override
            public void visit(Node<String> n) {
                result = result + n.getKey();
            }
        };
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
        };
        MyVisitor v = new MyVisitor();
        t.preOrderVisit(v);
        return v.result;
    }
}
