import java.io.Serial;
import java.io.Serializable;

public class RBNode<T extends Comparable<T>> implements Comparable<RBNode<T>>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public enum Color { RED, BLACK }
    private final T data;
    private RBNode<T> parent;
    private RBNode<T> left;
    private RBNode<T> right;
    private Color color;

    /**
     * Constructs a node. The color is set to RED by default.
     *
     * @param data the data the node will hold
     */
    public RBNode(T data) {
        this.data = data;
        color = Color.RED;
        parent = null;
        left = null;
        right = null;
    }

    /**
     * Tests to determine if it is a left child node.
     *
     * @return whether is a left child or not
     */
    public boolean isLeftChild() {
        return parent.left == this;
    }

    /**
     * Tests to determine if it is a right child node.
     *
     * @return whether is a right child or not
     */
    public boolean isRightChild() {
        return !isLeftChild();
    }

    /**
     * Tests to determine if it is a root node.
     *
     * @return whether is a root node or not
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * Compares two nodes by their inner data.
     *
     * @param node the other node to compare against
     *
     * @return -1, 0, 1 depending on the relation between the nodes
     */
    @Override
    public int compareTo(RBNode<T> node) {
        return data.compareTo(node.data);
    }

    /**
     * Gets the aunt of the node.
     *
     * @return the parent's sibling
     */
    public RBNode<T> getAunt() {
        return parent.getSibling();
    }

    /**
     * Gets the sibling node.
     *
     * @return the parents other child
     */
    public RBNode<T> getSibling() {
        if (isLeftChild()) {
            return parent.right;
        } else {
            return parent.left;
        }
    }

    /**
     * Gets the grandparent node.
     *
     * @return two parent nodes above the current node
     */
    public RBNode<T> getGrandparent() {
        return this.parent.parent;
    }

    public RBNode<T> getParent() {
        return parent;
    }

    public T getData() {
        return data;
    }

    public void setParent(RBNode<T> parent) {
        this.parent = parent;
    }

    public RBNode<T> getLeft() {
        return left;
    }

    public void setLeft(RBNode<T> left) {
        this.left = left;
    }

    public RBNode<T> getRight() {
        return right;
    }

    public void setRight(RBNode<T> right) {
        this.right = right;
    }

    public boolean isBlack() {
        return color == Color.BLACK;
    }

    public boolean isRed() {
        return color == Color.RED;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}