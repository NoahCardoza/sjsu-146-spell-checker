public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;

    public void visit(Node<T> n) {
        System.out.println(n.key);
    }

    public void printTree() {  //preorder: visit, go left, go right
        printTree(root);
    }

    public void printTree(Node<T> node) {
        if (node == null) return;

        System.out.print(node.key);
        if (node.isLeaf()) {
            return;
        }
        printTree(node.left);
        printTree(node.right);
    }

    /**
     * Place a new node in the tree using a standard binary search tree
     * insertion algorithm.
     *
     * @param data the data to insert into the
     *
     * @return the new node that was just added
     */
    public Node<T> addNode(T data) {   //this < that  <0.  this > that  >0 fill
        if(root == null) {
            root = new Node<>(data);
            return root;
        }

        Node<T> temp = root;
        while (true) {
            if(data.compareTo(temp.key) < 0){ //if data is smaller than temp
                if(temp.left == null) {
                    temp.left = new Node<>(data, temp);
                    return temp.left;
                }
                else {
                    temp = temp.left;
                }
            } else {
                if(temp.right == null) {
                    temp.right = new Node<>(data, temp);
                    return temp.right;
                }
                else {
                    temp = temp.right;
                }
            }
        }
    }

    public void insert(T data) {
        Node<T> newNode = addNode(data);

        if(newNode.isRoot()){
            newNode.isRed = false;
            return;
        }

        newNode.isRed = true;
        if(newNode.parent.isRed){ //if parent is red
            if(getAunt(newNode) != null) {
                if (getAunt(newNode).isRed)//both parent and uncle are red
                    fixTree(newNode); //recolor parent and uncle to black and newNode is red
            } else { //parent is red, uncle is black, a rotation must happen
                //CASE 1 - NEW NODE IS LEFT CHILD OF PARENT IS LEFT CHILD OF GRANDPARENT LL CASE
                if(newNode.getGrandparent().left == newNode.parent && newNode.parent.left == newNode)
                        rotateRight(newNode.getGrandparent());
                    //CASE 2 - NEW NODE IS RIGHT CHILD OF PARENT IS LEFT CHILD OF GRANDPARENT
                if(newNode.getGrandparent().left == newNode.parent && newNode.parent.right == newNode) {
                        rotateLeft(newNode.parent);
                        rotateRight(newNode.getGrandparent());
                    }
                    //CASE 3 - NEW NODE IS RIGHT CHILD OF PARENT IS RIGHT CHILD OF GRANDPARENT RR CASE
                if(newNode.getGrandparent().right == newNode.parent && newNode.parent.right == newNode)
                        rotateRight(newNode.getGrandparent());
                    //CASE 4 - NEW NODE IS LEFT CHILD OF PARENT IS RIGHT CHILD OF GRANDPARENT
                if(newNode.getGrandparent().right == newNode.parent && newNode.parent.left == newNode){
                        rotateRight(newNode.parent);
                        rotateLeft(newNode.getGrandparent());
                    }
                if(newNode.left == null && newNode.right == null) //root node
                        newNode.isRed = false; //roots are black
                }
        }
    }


    public Node<T> lookup(T k) {
        //fill
        Node<T> temp = root;
        if(root == null)
            return null;
        while(true) {
            if (temp.key.equals(k))
                return temp;
            else {
                if (temp.key.compareTo(k) < 0) {
                    if(temp.right == null)
                        return null;
                    temp = temp.right;
                }
                else {
                    if(temp.left == null)
                        return null;
                    temp = temp.left;
                }
            }
        }
    }

    public Node<T> getSibling(Node<T> n) {
        //fill
        if(n.parent.left != n)
            return n.parent.left;
        else{
            return n.parent.right;
        }
    }

    public Node<T> getAunt(Node<T> n) {
        //fill
        return getSibling(n.parent);
    }


    public void rotateLeft(Node<T> n) {
        //fill
        //n is the grandparent
        Node<T> parent = n.right;
        parent.left = n;
        parent.parent = n.parent;
        n.parent = parent;
        n.right = parent.left;
        parent.left.parent = n;
    }

    public void rotateRight(Node<T> n) {
        //fill
    }

    public void fixTree(Node<T> current) {
        //fill
        //if the uncle and parent are red and the current node is red
        if(current != null){
            if(current == root)
                current.isRed = false;
            else if(current.isRed && current.parent.isRed && getAunt(current).isRed){
                current.parent.isRed = false;
                getAunt(current).isRed = false;
                fixTree(current.getGrandparent());
            }
            //add rotation cases' color fixes



        }

    }

    public boolean isLeftChild(Node<T> parent, Node<T> child) {
        //child is less than parent
        return child.compareTo(parent) < 0;
    }

    public void preOrderVisit(Visitor v) {
        preOrderVisit(root, v);
    }


    private void preOrderVisit(Node<T> n, Visitor v) {
        if (n == null) {
            return;
        }
        v.visit(n);
        preOrderVisit(n.left, v);
        preOrderVisit(n.right, v);
    }
}

