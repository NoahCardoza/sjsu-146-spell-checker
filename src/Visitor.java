public interface Visitor<T extends Comparable<T>> {
    /**
     * This method is called at each node.
     *
     * @param n the visited node
     */
    void visit(Node<T> n);
}