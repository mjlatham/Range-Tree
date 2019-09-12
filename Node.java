/**
 *  Simple Binary Tree Node to be used in the Binary Tree
 */

public class Node<E> {
    /**
     * Simple node in the binary tree
     */

    private Node<E> parent;
    private Node<E> left;
    private Node<E> right;
    private int key;
    private int subtreeSize;

    public Node(int key) {
        this.key = key;
        this.subtreeSize = 1;
    }

    public int getKey() {
        /**
         * Returns the value of the node's key.
         *
         * @return: size of the key
         */
        return key;
    }

    public int getSubtreeSize() {
        /**
         * Return the size of the subtree below this node.
         *
         * @return: size of the subtree
         */
        return this.subtreeSize;
    }

    public void incrementSubtreeSize() {
        /**
         * Increment the size of the subtree.
         */

        this.subtreeSize++;
    }

    public void decrementSubtreeSize() {
        /**
         * Decrement the size of the subtree.
         */

        if (this.subtreeSize == 1) {
            return;
        }

        this.subtreeSize--;
    }

    public Node<E> getLeft() {
        /**
         * Get the left child.
         *
         * @return: Left child node
         */
        return this.left;
    }

    public Node<E> getRight() {
        /**
         * Get the right child.
         *
         * @return: Right child node.
         */
        return this.right;
    }

    public Node<E> getParent() {
        /**
         * Get the parent of the node.
         *
         * @return: the parent.
         */
        return parent;
    }

    public void setParent(Node<E> parent) {
        /**
         * Set the parent node.
         */
        this.parent = parent;
    }

    public void setLeft(Node<E> left) {
        /**
         * Set the left child of the node.
         */
        this.left = left;
    }

    public void setRight(Node<E> right) {
        /**
         * Set the right child of the node.
         */
        this.right = right;
    }
}
