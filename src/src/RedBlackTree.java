public class RedBlackTree {
    private static RedBlackTree.Node root;
    public static class Node {
        String key;
        Node parent;
        Node leftChild;
        Node rightChild;
        boolean isRed;
        int color;

        public Node(String data) {
            this.key = data;
            leftChild = null;
            rightChild = null;
        }

        public int compareTo(Node n) {  //this < that  <0
            return key.compareTo(n.key);   //this > that  >0
        }


        public boolean isLeaf() {
            if (this.equals(root) && this.leftChild == null &&
                    this.rightChild == null) return true;
            if (this.equals(root)) return false;
            if (this.leftChild == null && this.rightChild == null) {
                return true;
            }
            return false;
        }
    }

        public boolean isLeaf(RedBlackTree.Node n) {
            if (n.equals(root) && n.leftChild == null && n.rightChild ==
                    null) return true;
            if (n.equals(root)) return false;
            if (n.leftChild == null && n.rightChild == null) {
                return true;
            }
            return false;
        }

        public interface Visitor {
            /**
             * This method is called at each node.
             *
             * @param n the visited node
             */
            void visit(Node n);
        }

        public void visit(Node n) {
            System.out.println(n.key);
        }

        public void printTree() {  //preorder: visit, go left, go right
            RedBlackTree.Node currentNode = root;
            printTree(currentNode);
        }

        public void printTree(RedBlackTree.Node node) {
            System.out.print(node.key);
            if (node.isLeaf()) {
                return;
            }
            printTree(node.leftChild);
            printTree(node.rightChild);
        }

        // place a new node in the RB tree with data the parameter and color it red.
        public void addNode(String data) {   //this < that  <0.  this > that  >0
            // fill
            Node temp = root;
            if(root == null) {
                root = new Node(data);
            }
            else{
                while(true){
                    if(data.compareTo(temp.key) < 0){ //if data is smaller than temp
                        if(temp.leftChild == null) {
                            temp.leftChild = new Node(data);
                            temp.leftChild.parent = temp;
                            break;
                        }
                        else
                            temp = temp.leftChild;
                    }
                    else{
                        if(temp.rightChild == null) {
                            temp.rightChild = new Node(data);
                            temp.rightChild.parent = temp;
                            break;
                        }
                        else
                            temp = temp.rightChild;
                    }
                }
            }
        }

        public void insert(String data) {
            addNode(data);
            Node newNode = lookup(data);
            if(newNode == root){
                newNode.isRed = false;
            }
            else{
                newNode.isRed = true;
                if(newNode.parent.isRed){ //if parent is red
                    if(getAunt(newNode) != null) {
                        if (getAunt(newNode).isRed)//both parent and uncle are red
                            fixTree(newNode); //recolor parent and uncle to black and newNode is red
                    }
                    else{ //parent is red, uncle is black, a rotation must happen
                            //CASE 1 - NEW NODE IS LEFT CHILD OF PARENT IS LEFT CHILD OF GRANDPARENT LL CASE
                        if(getGrandparent(newNode).leftChild == newNode.parent && newNode.parent.leftChild == newNode)
                                rotateRight(getGrandparent(newNode));
                            //CASE 2 - NEW NODE IS RIGHT CHILD OF PARENT IS LEFT CHILD OF GRANDPARENT
                        if(getGrandparent(newNode).leftChild == newNode.parent && newNode.parent.rightChild == newNode) {
                                rotateLeft(newNode.parent);
                                rotateRight(getGrandparent(newNode));
                            }
                            //CASE 3 - NEW NODE IS RIGHT CHILD OF PARENT IS RIGHT CHILD OF GRANDPARENT RR CASE
                        if(getGrandparent(newNode).rightChild == newNode.parent && newNode.parent.rightChild == newNode)
                                rotateRight(getGrandparent(newNode));
                            //CASE 4 - NEW NODE IS LEFT CHILD OF PARENT IS RIGHT CHILD OF GRANDPARENT
                        if(getGrandparent(newNode).rightChild == newNode.parent && newNode.parent.leftChild == newNode){
                                rotateRight(newNode.parent);
                                rotateLeft(getGrandparent(newNode));
                            }
                            if(newNode.leftChild == null && newNode.rightChild == null) //root node
                                newNode.isRed = false; //roots are black
                        }
                    }
                }
            }


        public RedBlackTree.Node lookup(String k) {
            //fill
            Node temp = root;
            if(root == null)
                return null;
            while(true) {
                if (temp.key.equals(k))
                    return temp;
                else {
                    if (temp.key.compareTo(k) < 0) {
                        if(temp.rightChild == null)
                            return null;
                        temp = temp.rightChild;
                    }
                    else {
                        if(temp.leftChild == null)
                            return null;
                        temp = temp.leftChild;
                    }
                }
            }
        }

        public RedBlackTree.Node getSibling(RedBlackTree.Node n) {
            //fill
            if(n.parent.leftChild != n)
                return n.parent.leftChild;
            else{
                return n.parent.rightChild;
            }
        }

        public RedBlackTree.Node getAunt(RedBlackTree.Node n) {
            //fill
            return getSibling(n.parent);
        }

        public RedBlackTree.Node getGrandparent(RedBlackTree.Node n) {
            return n.parent.parent;
        }

        public void rotateLeft(RedBlackTree.Node n) {
            //fill
            //n is the grandparent
            Node parent = n.rightChild;
            parent.leftChild = n;
            parent.parent = n.parent;
            n.parent = parent;
            n.rightChild = parent.leftChild;
            parent.leftChild.parent = n;
        }

        public void rotateRight(RedBlackTree.Node n) {
            //fill
        }

        public void fixTree(RedBlackTree.Node current) {
            //fill
            //if the uncle and parent are red and the current node is red
            if(current != null){
                if(current == root)
                    current.isRed = false;
                else if(current.isRed && current.parent.isRed && getAunt(current).isRed){
                    current.parent.isRed = false;
                    getAunt(current).isRed = false;
                    fixTree(getGrandparent(current));
                }
                //add rotation cases' color fixes



            }

        }

        public boolean isEmpty(RedBlackTree.Node n) {
            return n.key == null;
        }

        public boolean isLeftChild(RedBlackTree.Node parent, RedBlackTree.Node child) {
            //child is less than parent
            return child.compareTo(parent) < 0;
        }

        public void preOrderVisit(Visitor v) {
            preOrderVisit(root, v);
        }


        private static void preOrderVisit(RedBlackTree.Node n, Visitor v) {
            if (n == null) {
                return;
            }
            v.visit(n);
            preOrderVisit(n.leftChild, v);
            preOrderVisit(n.rightChild, v);
        }
    }

