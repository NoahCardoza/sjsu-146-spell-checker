/**
 * Used by RedBlackTree to allow getters to be passed to the rotate method.
 *
 * @param <T> the type of the data stored in the nodes
 */
public interface Getter<T extends Comparable<T>> {
    /**
     * Method signature for a getter on the node class.
     *
     * @param node the instance to get from
     *
     * @return either the left or right node
     */
    RBNode<T> get(RBNode<T> node);
}
