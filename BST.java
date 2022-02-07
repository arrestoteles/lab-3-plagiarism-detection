import java.util.ArrayList;
import java.util.Iterator;

/**
 *  The {@code BST} class represents an ordered map from keys to values.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>containsKey</em>,
 *  <em>size</em> and <em>isEmpty</em> methods.
 *  It does not support <em>remove</em>, however.
 *  It also supports <em>min</em> and <em>max</em> for finding the
 *  smallest and largest keys in the map.
 *  It also provides a <em>iterator</em> method for iterating over all of the keys.
 *  <p>
 *  This implementation uses an (unbalanced) binary search tree. It requires that
 *  the key type implements the {@code Comparable} interface and calls the
 *  {@code compareTo()} and method to compare two keys. It does not call either
 *  {@code equals()} or {@code hashCode()}.
 *  The <em>put</em>, <em>get</em>, and <em>containsKey</em> operations each take
 *  linear time in the worst case, if the tree becomes unbalanced.
 *  The <em>size</em> operation takes constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/32bst">Section 3.2</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
    private Node root;             // root of BST

    private class Node {
        private Key key;           // sorted by key
        private Value val;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    /**
     * Initializes an empty map.
     */
    public BST() {
    }

    /**
     * Returns true if this map is empty.
     * @return {@code true} if this map is empty, {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this map.
     * @return the number of key-value pairs in this map
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.size;
    }

    /**
     * Does this map contain the given key?
     *
     * @param  key the key
     * @return {@code true} if this map contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean containsKey(Key key) {
        if (key == null)
            throw new IllegalArgumentException("argument to containsKey() is null");
        return get(key) != null;
    }

    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the map
     *         and {@code null} if the key is not in the map
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (key == null)
            throw new IllegalArgumentException("calls get() with a null key");
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    /**
     * Inserts the specified key-value pair into the map, overwriting the old
     * value with the new value if the map already contains the specified key.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null)
            throw new IllegalArgumentException("calls put() with a null key");

        root = put(root, key, val);
        assert check();
    }

    private Node put(Node node, Key key, Value val) {
        if (node == null)
            return new Node(key, val, 1);

        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = put(node.left,  key, val);
        else if (cmp > 0)
            node.right = put(node.right, key, val);
        else
            node.val = val;

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    /**
     * Returns all keys in the map as an {@code Iterable}.
     * To iterate over all of the keys in the map named {@code st},
     * use the foreach notation: {@code for (Key key : st)}.
     *
     * @return all keys in the map
     */
    public Iterator<Key> iterator() {
        ArrayList<Key> queue = new ArrayList<Key>();
        keys(root, queue);
        return queue.iterator();
    }

    private void keys(Node x, ArrayList<Key> queue) {
        if (x == null)
            return;

        keys(x.left, queue);
        queue.add(x.key);
        keys(x.right, queue);
    }

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a an empty tree has height 0)
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null)
            return 0;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns some statistics about the BST (for debugging).
     *
     * @return information about the BST.
     */
    public String statistics() {
        return String.format("BST, size %d, height %d", size(), height());
    }

  /*************************************************************************
    *  Check integrity of BST data structure.
    ***************************************************************************/
    private boolean check() {
        if (!isBST())            System.out.println("Not in symmetric order");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        return isBST() && isSizeConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        return x.size == size(x.left) + size(x.right) + 1
            && isSizeConsistent(x.left)
            && isSizeConsistent(x.right);
    }

}
