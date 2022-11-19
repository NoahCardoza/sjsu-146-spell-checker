/**
 * Used by RedBlackTree to allow setters to be passed to the rotate method.
 *
 * @param <T> the type of the data stored in the nodes
 */
public interface Setter<T extends Comparable<T>> {
    /**
     * Method signature for a setter on the node class.
     *
     * @param node the instance to preform the set on
     * @param value the value to set to
     */
    void set(Node<T> node, Node<T> value);
}
