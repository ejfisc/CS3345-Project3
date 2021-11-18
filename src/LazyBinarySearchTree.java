public class LazyBinarySearchTree {

    //private inner TreeNode class
    private class TreeNode {
        private int key;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private boolean deleted;

        //constructors
        TreeNode(int key) {
            this(key, null, null);
        }

        TreeNode(int key, TreeNode leftChild, TreeNode rightChild) {
            this.key = key;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            deleted = false;
        }
    }

    //private instance variable for lazy binary search tree
    private TreeNode root;

    //default constructor for an empty tree
    public LazyBinarySearchTree() {
        root = null;
    }

    //constructor for tree with root value x
    public LazyBinarySearchTree(int x) {
        root = new TreeNode(x);
    }

    //public methods

    //inserts a new node with value key into the tree
    public boolean insert(int key) {
        if(validateKey(key))
            //if the tree is empty, insert key into root and return true
            if(root == null) {
                root = new TreeNode(key);
                return true;
            }
            else
                return insert(key, root); //calls the private insert method
        else
            throw new IllegalArgumentException("Value must be between 1 and 99");
    }

    //marks node with value key as deleted but does not physically remove that node from the tree
    public boolean delete(int key) {
        if(validateKey(key))
            return delete(key, root); //calls the private recursive delete method
        else
            throw new IllegalArgumentException("Value must be between 1 and 99");
    }

    //returns the value of the minimum non-deleted node, returns -1 if there isn't one
    public int findMin() {
        int min = -1; //variable to hold min value
        //if tree is nonempty
        if(root != null) {
            //if root is not deleted, set min to root key
            if(!root.deleted)
                min = root.key;

            //if root has a left child
            if(root.leftChild != null) {
                //findMin
                min = findMin(min, root.leftChild);
                //check if minimum is not found
                if(min == -1) {
                    //-1 means that root is also deleted so check right subtree for minimum
                    TreeNode nptr = root;
                    while(nptr.rightChild != null) {
                        //findMin
                        min = findMin(min, nptr.rightChild);

                        //check if min is found
                        if(min != -1)
                            break;

                        //move to next node
                        nptr = nptr.rightChild;
                    }
                }
            }
        }
        return min;
    }

    //returns the value of the maximum non-deleted node, or -1 if there isn't one
    public int findMax() {
        int max = -1; //variable to hold max value
        //if tree is nonempty
        if(root != null) {
            //if root is not deleted, set max to root key
            if(!root.deleted)
                max = root.key;

            //if root has a right child
            if(root.rightChild != null) {
                //findMax
                max = findMax(max, root.rightChild);
                //check if max is not found
                if(max == -1) {
                    //-1 means that the root is also deleted so check the left subtree
                    TreeNode nptr = root;
                    while(nptr.leftChild != null) {
                        max = findMax(max, nptr.leftChild);
                        //check if max was found
                        if(max != -1)
                            break;
                        
                        //move to next node
                        nptr = nptr.leftChild;
                    }
                }
            }
        }
        return max;
    }

    //returns true if the element is in the list and not deleted, otherwise false
    public boolean contains(int key) {
        if(validateKey(key))
            return contains(key, root);
        else
            throw new IllegalArgumentException("Value must be between 1 and 99");
    }

    //prints the tree in pre-order, elements marked as deleted are printed following a *
    public String toString() {
        String treeString = "";
        if(root == null)
            return "Empty Tree";
        else
            return toString(treeString, root);
    }

    //returns the height of the tree, including deleted nodes
    public int height() {
        return height(root);
    }

    //returns the number of nodes in the tree, including deleted nodes
    public int size() {
        if(root != null)
            return size(root, 0);
        
        return 0; //tree is empty
    }

    //private helper methods

    //checks that the key is a valid key between 1 and 99
    private boolean validateKey(int key) {
        if(key < 1 || key > 99)
            return false;
        else
            return true;
    }

    //recursively loops through the tree until the value is appropriately inserted into the tree
    private boolean insert(int key, TreeNode t) {
        if(key < t.key) {
            if(t.leftChild != null)
                return insert(key, t.leftChild); //recursively call insert with left child node
            else {
                t.leftChild = new TreeNode(key);
                return true;
            }
        }
        else if(key > t.key) {
            if(t.rightChild != null)
                return insert(key, t.rightChild); //recursively call insert with right child node
            else {
                t.rightChild = new TreeNode(key);
                return true;
            }
        }
        else if(key == t.key && t.deleted) { //if key is a duplicate value but marked as deleted, "undelete" it by setting deleted to false and return true
            t.deleted = false;
            return true;
        }
        else //key is a duplicate value and not deleted, so key is not inserted into the tree
            return false;
    }

    //recursively loops through the tree until the node with the value is marked as deleted
    private boolean delete(int key, TreeNode t) {
        //if node is null, item is not found, return false
        if(t == null)
            return false;

        if(key < t.key)
            return delete(key, t.leftChild); //recursively call delete with left child node
        else if(key > t.key)
            return delete(key, t.rightChild); //recursively call delete with right child node
        else {
            t.deleted = true; //mark the node as deleted and return true
            return true;
        }
    }

    //recursively loops through the left subtree to find the smallest (leftmost) item that isn't deleted
    private int findMin(int min, TreeNode t) {
        //if node is not deleted, set min to t.key
        if(!t.deleted)
            min = t.key;
        
        //check if t has a left child
        if(t.leftChild != null)
            return findMin(min, t.leftChild);
        else {
            //check if t has a right child and t is deleted
            if(t.rightChild != null && t.deleted)
                return findMin(min, t.rightChild);
            else
                return min;
        }
    }

    //recursively loops through the right subtree to find the largest (rightmost) item that isn't deleted
    private int findMax(int max, TreeNode t) {
        //if node is not deleted, set max to t.key
        if(!t.deleted)
            max = t.key;

        //check if t has a right child
        if(t.rightChild != null)
            return findMax(max, t.rightChild);
        else {
            //check if t has a left child and t is deleted
            if(t.leftChild != null && t.deleted)
                return findMax(max, t.leftChild);
            else
                return max;
        }
    }

    //recursively loops through the tree to find match, returns false if match is marked as deleted or not found
    private boolean contains(int key, TreeNode t) {
        if(t == null)
            return false; //item not found
        
        if(key < t.key)
            return contains(key, t.leftChild); //recursively calls contains on the left child node
        else if(key > t.key)
            return contains(key, t.rightChild); //recursively calls contains on the right child node
        else if(t.deleted) //if match is found but marked as deleted, return false
            return false;
        else 
            return true; //match is found and not deleted, return true
    }

    //recursively loops through the tree, adding to the string in preorder
    private String toString(String s, TreeNode t) {
        if(t != null) {
            //print parent
            if(t.deleted)
                s += "*" + String.valueOf(t.key) + " "; //adds deleted node element to string
            else
                s += String.valueOf(t.key) + " "; //adds non deleted node element to string
            //print left child
            if(t.leftChild != null)
                s = toString(s, t.leftChild); //recursively calls tostring with left child node
            //print right child
            if(t.rightChild != null)
                s = toString(s, t.rightChild); //recursively calls to string with right child node
        }
        return s; //returns the resultant string
    }

    //recursively calculates height of the tree including deleted nodes
    private int height(TreeNode t) {
        if(t == null)
            return -1;
        else
            return 1 + Math.max(height(t.leftChild), height(t.rightChild));
    }

    //recursively calculates the number of nodes in the tree, including deleted noodes
    private int size(TreeNode t, int count) {
        if(t != null) {
            count++;
            count = size(t.leftChild, count); //recursively traverse left subtree
            count = size(t.rightChild, count); //recurisively traverse right subtree
        }
        return count;
    }

}