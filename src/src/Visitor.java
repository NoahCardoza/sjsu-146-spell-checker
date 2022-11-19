public interface Visitor {
    /**
     * This method is called at each node.
     *
     * @param n the visited node
     */
    void visit(Node<?> n);
}