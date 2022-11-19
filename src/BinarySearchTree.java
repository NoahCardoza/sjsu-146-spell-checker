import java.io.Serial;
import java.io.Serializable;

/**
 * A standard binary search tree implementation.
 *
 * @param <T> the type of data stored in the nodes
 */
public class BinarySearchTree<T extends Comparable<T>> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Node<T> root;

    /**
     * Constructs a BinarySearchTree instance.
     */
    BinarySearchTree() {
        root = null;
    }

    /**
     * Inserts data into the tree.
     *
     * @param data the data to place into the tree
     */
    public void insert(T data) {
        place(new Node<>(data));
    }

    /**
     * Place a new node in the tree using a standard binary search tree
     * insertion algorithm.
     *
     * @param node the node to insert into the tree
     */
    public void place(Node<T> node) {   //this < that  <0.  this > that  >0 fill
        if (root == null) {
            root = node;
            return;
        }

        Node<T> temp = root;
        while (true) {
            if (node.compareTo(temp) < 0) { //if data is smaller than temp
                if (temp.getLeft() == null) {
                    temp.setLeft(node);
                    node.setParent(temp);
                    break;
                } else {
                    temp = temp.getLeft();
                }
            } else {
                if (temp.getRight() == null) {
                    temp.setRight(node);
                    node.setParent(temp);
                    break;
                } else {
                    temp = temp.getRight();
                }
            }
        }
    }

    /**
     * Searches for a node by its contents.
     *
     * @param data the data to look for
     *
     * @return a reference to the node if found or null
     */
    public Node<T> find(T data) {
        Node<T> temp = root;
        if (root == null)
            return null;
        while (true) {
            if (temp.getKey().equals(data))
                return temp;
            else {
                if (temp.getKey().compareTo(data) < 0) {
                    if (temp.getRight() == null)
                        return null;
                    temp = temp.getRight();
                } else {
                    if (temp.getLeft() == null)
                        return null;
                    temp = temp.getLeft();
                }
            }
        }
    }

    /**
     * Determines if the tree contains a value or not.
     *
     * @param data the value to search for
     *
     * @return whether it is contained within the tree
     */
    public boolean contains(T data) {
        return find(data) != null;
    }

    /**
     * Pre-order traversal of the tree.
     *
     * @param v the callback to use on each node
     */
    public void preOrderVisit(Visitor<T> v) {
        preOrderVisit(root, v);
    }

    /**
     * In-order traversal of the tree.
     *
     * @param v the callback to use on each node
     */
    public void inOrderVisit(Visitor<T> v) {
        inOrderVisit(root, v);
    }

    /**
     * Post-order traversal of the tree.
     *
     * @param v the callback to use on each node
     */
    public void postOrderVisit(Visitor<T> v) {
        postOrderVisit(root, v);
    }

    private void preOrderVisit(Node<T> n, Visitor<T> v) {
        if (n == null) {
            return;
        }
        v.visit(n);
        preOrderVisit(n.getLeft(), v);
        preOrderVisit(n.getRight(), v);
    }

    private void inOrderVisit(Node<T> n, Visitor<T> v) {
        if (n == null) {
            return;
        }
        inOrderVisit(n.getLeft(), v);
        v.visit(n);
        inOrderVisit(n.getRight(), v);
    }

    private void postOrderVisit(Node<T> n, Visitor<T> v) {
        if (n == null) {
            return;
        }
        inOrderVisit(n.getLeft(), v);
        inOrderVisit(n.getRight(), v);
        v.visit(n);
    }

    protected Node<T> getRoot() {
        return root;
    }

    protected void setRoot(Node<T> root) {
        this.root = root;
    }
}
