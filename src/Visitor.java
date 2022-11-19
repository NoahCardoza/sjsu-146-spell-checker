/**
 * Used to traverse the tree.
 *
 * @param <T> the type of data stored in the nodes of the tree
 */
public interface Visitor<T extends Comparable<T>> {
    /**
     * This method is called at each node.
     *
     * @param n the visited node
     */
    void visit(Node<T> n);
}