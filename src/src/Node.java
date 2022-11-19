public class Node<T extends Comparable<T>> {
    T key;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    boolean isRed;

    public Node(T data) {
        this.key = data;
        parent = null;
        left = null;
        right = null;
    }

    public Node(T data, Node<T> parent) {
        this(data);
        this.parent = parent;
    }


    public boolean isRoot() {
        return parent == null;
    }

    public int compareTo(Node<T> n) {  //this < that  <0
        return key.compareTo(n.key);   //this > that  >0
    }


    public boolean isLeaf() {
        if (isRoot() && this.left == null && this.right == null) {
            return true;
        }
        if (isRoot()) {
            return false;
        }
        if (this.left == null && this.right == null) {
            return true;
        }
        return false;
    }


    public Node<T> getGrandparent() {
        return this.parent.parent;
    }

    public boolean isEmpty() {
        return this.key == null;
    }

    public Node<T> getParent() {
        return parent;
    }
}