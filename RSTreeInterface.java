import java.util.List;

/**
 * Basic interface for the RangeSearchTree.
 */


public interface RSTreeInterface<E> {

    /**
     * Places the `key` into the tree.
     * @param key - the key to place into the tree.
     */
    void put(int key);

    /**
     * Remove's the `key` from the tree.
     *
     * @param key - the key to remove from the tree.
     * @return The node that was removed.
     */
    Node<E> remove(int key);

    /**
     * Return the node's with key `k`.
     * @param key - The value to search for.
     * @return - An array of nodes with key K.
     */
    List<Node<E>> get(int key);

    /**
     * Get the number of nodes between two values.
     * @param a: A value.
     * @param b: A value.
     * @return - The number of nodes between the two values.
     */
    int rangeSize(int a, int b);

}