public class Node<T extends Comparable<T>> {
    public enum Color { RED, BLACK }
    T key;
    Node<T> parent;
    Node<T> left;
    Node<T> right;
    Color color;
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

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}