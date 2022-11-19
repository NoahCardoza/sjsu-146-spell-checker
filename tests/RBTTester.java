import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RBTTester {
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
                Color: 1, Key:D Parent: \s
                Color: 1, Key:B Parent: D
                Color: 1, Key:A Parent: B
                Color: 1, Key:C Parent: B
                Color: 1, Key:F Parent: D
                Color: 1, Key:E Parent: F
                Color: 0, Key:H Parent: F
                Color: 1, Key:G Parent: H
                Color: 1, Key:I Parent: H
                Color: 0, Key:J Parent: I
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
        {
            class MyVisitor implements Visitor<String> {
                String result = "";
                @Override
                public void visit(Node<String> n)
                {
                    if(!(n.key).equals(""))
                        result = result + "Color: " + n.getColor() + ", Key:" + n.getKey() + " Parent: " + n.getParent().getKey() + "\n";

                }
            };
            MyVisitor v = new MyVisitor();
            t.preOrderVisit(v);
            return v.result;
        }
    }

    // add this in your class
    //  public static interface Visitor
    //  {
    //   /**
    //     This method is called at each node.
    //     @param n the visited node
    //    */
    //   void visit(Node n);
    //  }


    // public void preOrderVisit(Visitor v)
    //  {
    //   preOrderVisit(root, v);
    //  }


    // private static void preOrderVisit(Node n, Visitor v)
    //  {
    //   if (n == null) return;
    //   v.visit(n);
    //   preOrderVisit(n.left, v);
    //   preOrderVisit(n.right, v);
    //  }

//                  if(!(n.key).equals(""))
//                          result = result +"Color: "+n.color+", Key:"+n.key+"
//                          Parent: "+n.parent.key+\n";
}
