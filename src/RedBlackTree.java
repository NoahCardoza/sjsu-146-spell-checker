import java.io.Serial;
import java.io.Serializable;

/**
 * An extension of the binary tree implementing red black tree balancing logic.
 *
 * @param <T> the data type to store in the nodes
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Inserts to and balances the tree.
     *
     * @param data the data to place into the tree
     */
    @Override
    public void insert(T data) {
        RBNode<T> node = new RBNode<>(data);
        place(node);
        repair(node);
    }

    /**
     * Determines if repairs are required by testing a number of cases
     * and making the necessary rotations and/or recoloring calls.
     *
     * @param node the node to preform the repair on
     */
    private void repair(RBNode<T> node) {
        if (node.isRoot()) {
            node.setColor(RBNode.Color.BLACK);
            return;
        }

        if (node.getParent().isBlack()) {
            return;
        }

        if (node.getAunt() != null && node.getAunt().isRed()) {
            recolor(node);
            return;
        } // else: parent is red, uncle is black, a rotation must happen

        // CASE 1 - NEW NODE IS LEFT CHILD OF PARENT IS LEFT CHILD OF GRANDPARENT LL CASE
        if (node.getParent().isLeftChild() && node.isLeftChild()) {
            rotateRight(node.getGrandparent());

        // CASE 2 - NEW NODE IS RIGHT CHILD OF PARENT IS LEFT CHILD OF GRANDPARENT
        } else if (node.getParent().isLeftChild() && node.isRightChild()) {
            rotateLeft(node.getParent());
            rotateRight(node.getParent());

        // CASE 3 - NEW NODE IS RIGHT CHILD OF PARENT IS RIGHT CHILD OF GRANDPARENT RR CASE
        } else if (node.getParent().isRightChild() && node.isRightChild()) {
            rotateLeft(node.getGrandparent());

        // CASE 4 - NEW NODE IS LEFT CHILD OF PARENT IS RIGHT CHILD OF GRANDPARENT
        } else if (node.getParent().isRightChild() && node.isLeftChild()){
            rotateRight(node.getParent());
            rotateLeft(node.getParent());
        }
    }

    /**
     * The generalization of the rotation methods. By slotting in the
     * right getters/setters you can achieve both left and right rotations.
     *
     * @param node the node to preform the operation on
     * @param direction a getter accessing children in the direction you want to rotate
     * @param opposite a getter accessing children in the opposite direction you want to rotate
     * @param nodeSetter the setter to be called on the node
     * @param pivotSetter the setter to be called on the pivot
     */
    private void rotate(RBNode<T> node, Getter<T> direction, Getter<T> opposite, Setter<T> nodeSetter, Setter<T> pivotSetter) {
        RBNode<T> pivot = opposite.get(node);
        if (node.isRoot()) {
            setRoot(pivot);
            pivot.setParent(null);
        } else {
            pivot.setParent(node.getParent());
            if (node.isLeftChild()) {
                node.getParent().setLeft(pivot);
            } else {
                node.getParent().setRight(pivot);
            }
        }

        pivot.setColor(RBNode.Color.BLACK);
        node.setColor(RBNode.Color.RED);

        node.setParent(pivot);

        nodeSetter.set(node, direction.get(pivot));
        if (direction.get(pivot) != null) {
            direction.get(pivot).setParent(node);
        }
        pivotSetter.set(pivot, node);
    }

    /**
     * Preforms a left-rotation on a node.
     *
     * @param node the node to preform the operation on
     */
    private void rotateLeft(RBNode<T> node) {
        rotate(node, RBNode::getLeft, RBNode::getRight, RBNode::setRight, RBNode::setLeft);
    }

    /**
     * Preforms a right-rotation on a node.
     *
     * @param node the node to preform the operation on
     */
    private void rotateRight(RBNode<T> node) {
        rotate(node, RBNode::getRight, RBNode::getLeft, RBNode::setLeft, RBNode::setRight);
    }

    /**
     * Recolors a level of nodes and calls <code>repair</code>
     * on the node's grandparent.
     * <p>
     * Colors the parent and aunt BLACK and the grandparent RED.
     *
     * @param node the node to preform the operation on
     */
    private void recolor(RBNode<T> node) {
        node.getParent().setColor(RBNode.Color.BLACK);
        node.getAunt().setColor(RBNode.Color.BLACK);
        node.getGrandparent().setColor(RBNode.Color.RED);
        repair(node.getGrandparent());
    }
}

