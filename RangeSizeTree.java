import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class RangeSizeTree<E> implements RSTreeInterface<E> {
    public Node<E> root;
	private Node<E> nodeToRemove;
	private List<Node<E>> getList = new ArrayList<Node<E>>(); // List to store gotten values
	private int rangeSize;

    public RangeSizeTree() {
        this.root = null;
    }
	
	/**
	* Checks whether the tree is empty
	* @return true if tree is empty, false otherwise
	*/
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	* Method that reduces the subtree size of each parent node above the given current node
	* @param current node from which to recursively bubble up
	*/
	public void reduceParentsSubtreeSize(Node<E> current) {
		while (current.getParent() != root) {
			current.getParent().decrementSubtreeSize();
			current = current.getParent();
		}
		root.decrementSubtreeSize();
	}
	
	/**
	* This method gets the node with the smallest key in a given subtree
	* @param current is the node from which to search its subtree's smallest node
	* @return node is the smallest node
	*/
	public Node<E> getSmallest(Node<E> current) {
		if (isEmpty()) // If tree is empty, return null
			return null;
		
		if (current == null) // If the current node is null, return null
			return null;
		
		Node<E> smallest = current; // Set temporary smallest node
		
		while(smallest.getLeft() != null) // Find smallest node
			smallest = smallest.getLeft();
		
		return smallest; // Return the smallest node
	}


    /**
     * Put the value K into the tree.
     *
     * Hint McHintFace: watch out for duplicates!
     * @param k - the key to insert into the tree.
     */
    public void put(int k) {
		if (isEmpty()) // If the tree is empty
			root = new Node<E>(k); // Set the root to k if tree empty
		else
			putHelper(root, new Node<E>(k)); // Otherwise, put the new key into the tree
										 // Use the recursive putHelper() method
    }
	
	/**
	* This recursive helper method is used to help put a new node into the tree
	* @param current is the current node under which we are putting the new node
	* @param newNode is the new node we are putting into the tree
	*/
	private void putHelper(Node<E> current, Node<E> newNode) {
		current.incrementSubtreeSize(); // Increment subtree size of the current node being added to
		
		// If the new node has a key less than or equal to the current node, put it to the left
		if (newNode.getKey() <= current.getKey()) {
			if (current.getLeft() == null) { // If no left child of current node exists
				current.setLeft(newNode); // Add new node to the left
				newNode.setParent(current); // Set the new node's parent
				newNode.setLeft(null); // Set the new node's left child
				newNode.setRight(null); // Set the new node's right child
				return;
			} else { // Otherwise, if the current node has a left child
				putHelper(current.getLeft(), newNode); // Recursively call this helper method
			}
		}
		
		// If the new node has a key less than or equal to the current node, put it to the left
		if (newNode.getKey() > current.getKey()) {
			if (current.getRight() == null) { // If no right child of current node exists
				current.setRight(newNode); // Add new node to the right
				newNode.setParent(current); // Set the new node's parent
				newNode.setLeft(null); // Set the new node's left child
				newNode.setRight(null); // Set the new node's right child
				return;
			} else { // Otherwise, if the current node has a right child
				putHelper(current.getRight(), newNode); // Recursively call this helper method
			}
		}
	}


    /**
     * Get the node(s) with key `k`.
     *            - If there is none, return an empty array.
     *
     * E.g.
     *       3
     *    /     \
     *   1       5
     *  / \     / \
     * 1   2   4   6
     *
     *
     * get(1) => returns array of [1 (parent 1), 1 (parent 3)]
     * NOTE: ordered LEFT TO RIGHT!
     *
     * @param k - the key to get.
     * @return Array of node's with key K.
     */
    public List<Node<E>> get(int k) {
		getList.clear(); // Clear the list that stores gotten values before getting a new key
		
		if (isEmpty()) // Return null if the root is null
			return null;
		
		if (root.getSubtreeSize() == 1) { // If the tree contains only the root
			if (root.getKey() == k) // Add the root to the list if its key equals given k
				getList.add(root);
			return getList;
		}
		
		return getHelper(root, k);
    }
	
	/**
	* Helper method for getting nodes with a given key
	* @param current is the current node under which we are searching
	* @param k is the key we are searching for
	* @return List<Node<E>> is the list of nodes that have the given key k
	*/
	private List<Node<E>> getHelper(Node<E> current, int k) {
		
		if (current == null) { // If the current node is null, return the list
			return getList;
		}
		
		// If the node being visited has the given key
		if (current.getKey() == k) {
			getList.add(0, current); // Add this node to the list
			return getHelper(current.getLeft(), k); // Call getHelper() to recursivley find other nodes with same key
		}
		
		if (current.getKey() > k) { // If current node's key is greater than given key
			return getHelper(current.getLeft(), k); // Visit left key
		} else {
			return getHelper(current.getRight(), k); // Otherwise, visit right key
		}
		
	}


    /**
     *         Remove's the value from the tree.
     *
     *   Note: with duplicates, find the "FIRST DEEPEST OCCURRENCE"
     *
     *   E.g.
     *         3
     *      /     \
     *     1       5
     *    / \     / \
     *   1   2   4   6
     *
     *   Remove (1)
     *
     *         3
     *      /     \
     *     1       5
     *      \     / \
     *       2   4   6
     *
     * @param k - the value to remove from the tree.
     * @return The removed node. None if node not found or cannot be removed.
     */
    public Node<E> remove(int k) {
		Node<E> nodeToRemove = null; // Set the node to be removed to null
		
        // Special cases:
		
		if (root == null) // If the tree is empty, return null
			return null;
		
		if (get(k).isEmpty()) // If key is not in tree, return null
			return null;
		
		if (get(k).size() == 1 && root.getKey() == k) { // If the node we are removing is the root
			nodeToRemove = root;
			
			Node<E> smallest;
			
			// Set the root to null if the root is the only node
			if (nodeToRemove.getSubtreeSize() == 1) {
				root = null;
				return nodeToRemove;
			}
			
			if (nodeToRemove.getSubtreeSize() == 1)
				smallest = null;
			else if (nodeToRemove.getRight() == null)
				smallest = nodeToRemove.getLeft();
			else if (nodeToRemove.getLeft() == null)
				smallest = nodeToRemove.getRight();
			else
				smallest = getSmallest(nodeToRemove.getRight());
			
			if (smallest != null)
				smallest.decrementSubtreeSize();
			
			// Update node references
			root = smallest;
			root.setParent(null);
			root.setRight(nodeToRemove.getRight());
			root.setRight(nodeToRemove.getLeft());
			
			return nodeToRemove;
		}
		
		// Otherwise, call the remove helper method and return the node returned by it
        return removeHelper(root, k);
    }
	
	/**
	* Helper method to remove a key from the tree
	* @param current is the node below which we are searching for the node to be removed
	* @param k is the integer key we want to remove from the tree
	*/
	private Node<E> removeHelper(Node<E> current, int k) {
		
		// If we've gotten to the node we wish to remove
		if (current == null) {
			// Local variables
			int subtreeSize = nodeToRemove.getSubtreeSize(); // Subtree size of the node to remove
			Node<E> parent; // Parent node of the node to remove
			Node<E> smallest; // The node we'll be moving to take the removed node's position
			Node<E> rightOfRemoved = null; // The node to the right of the node to remove
			Node<E> leftOfRemoved = null; // The node to the left of the node to remove
			
			if (nodeToRemove != root)
				parent = nodeToRemove.getParent(); // Set the parent of the node to remove, given it isn't the root
			else
				parent = null; // If we're removing the root, set the parent to null
			
			if (nodeToRemove.getSubtreeSize() == 1) // If no children
				smallest = null;
			else if (nodeToRemove.getRight() == null) // If only a left child
				smallest = nodeToRemove.getLeft(); // Set the node we'll move to the left child
			else if (nodeToRemove.getLeft() == null) // If only a right child
				smallest = nodeToRemove.getRight(); // Set the node we'll move to the right child
			else // Otherwise, if there are both children
				smallest = getSmallest(nodeToRemove.getRight()); // Set the node we'll move to the smallest in the right subtree
			
			if (nodeToRemove != root) // Reduce the subtree size of each parent node, recursively (if the node isn't the root)
				reduceParentsSubtreeSize(nodeToRemove);
			
			if (nodeToRemove.getRight() != null)
				rightOfRemoved = nodeToRemove.getRight();
			if (nodeToRemove.getLeft() != null)
				leftOfRemoved = nodeToRemove.getLeft();
			
			if (nodeToRemove != root) {
				if (parent.getLeft() == nodeToRemove) {
					parent.setLeft(smallest);
					if (smallest != null) {
						smallest.setParent(parent);
						smallest.setRight(rightOfRemoved);
						smallest.setLeft(leftOfRemoved);
					}
				}
				if (parent.getRight() == nodeToRemove) {
					parent.setRight(smallest);
					if (smallest != null) {
						smallest.setParent(parent);
						smallest.setRight(rightOfRemoved);
						smallest.setLeft(leftOfRemoved);
					}
				}
			} else {
				if (smallest != null) {
					smallest.setRight(rightOfRemoved);
					smallest.setLeft(leftOfRemoved);
				}
			}
			
			if (smallest != null)
				smallest.decrementSubtreeSize();
			
			
			
			return nodeToRemove; // Return the lowest node that has the given key
		}
		
		if (current.getKey() == k) { // If the current node has the key to be removed
			nodeToRemove = current; // Set the node to remove to the current node
			return removeHelper(current.getLeft(), k); // Keep searching the tree for duplicates
		} else if (k < current.getKey()) { // If the key to remove is less than that of the current node's
			return removeHelper(current.getLeft(), k); // Search left subtree
		} else if (k > current.getKey()) { // If the key to remove is greater than that of the current node's
			return removeHelper(current.getRight(), k); // Search the right subtree
		}
		
		return nodeToRemove;
	}


    /**
     * Calculates the size between two keys.
     *   (Inclusive!)
     *
     *   e.g.
     *     2
     *    / \
     *   1  3
     *
     *   range_size(1, 1) => 1
     *
     *   e.g. #2
     *               5
     *           /       \
     *         3          7
     *       /   \      /   \
     *     2      4    6     8
     *    / \     \        /  \
     *   1   3     5      8   10
     *
     *   range_size(3, 7) => 7
     *
     * @param a - A key to search between.
     * @param b - A key to search between.
     * @return Number of nodes between the two keys
     */
    public int rangeSize(int a, int b) {
		if (isEmpty()) // If tree is empty, return 0
			return 0;
		
        return rangeSizeHelper(root, a, b); // Otherwise, call the recursive rangeSizeHelper() method
    }
	
	/**
	* Helper method for getting the amount of nodes within a range (inclusive)
	* @param current is the current node under which we are searching
	* @param a is one limit of the range
	* @param b is the other limit of the range
	* @return int amount of nodes within the range
	*/
	private int rangeSizeHelper(Node<E> current, int a, int b) {
		
		if (current == null) // If the current node is null, return 0
			return 0;
		
		if (a < b) { // If a < b
			if (current.getKey() >= a && current.getKey() <= b) // Look for values between (a, b)
				return 1 + rangeSizeHelper(current.getLeft(), a, b) + rangeSizeHelper(current.getRight(), a, b);
			else
				return + rangeSizeHelper(current.getLeft(), a, b) + rangeSizeHelper(current.getRight(), a, b);
		} else if (b < a) { // If a > b
			if (current.getKey() >= b && current.getKey() <= a) // Look for values between (b, a)
				return 1 + rangeSizeHelper(current.getLeft(), a, b) + rangeSizeHelper(current.getRight(), a, b);
			else
				return + rangeSizeHelper(current.getLeft(), a, b) + rangeSizeHelper(current.getRight(), a, b);
		} else { // If a == b
			return get(a).size();
		}
	}


    /**
     * The following function is not tested, it is provided for you to run/test your code.
     */

    public static void main(String[] args) {
        System.out.println("Running Main Method of RangeSizeTree");
		RangeSizeTree<Integer> tree = new RangeSizeTree<>();
		
		tree.put(5);
		tree.put(1);
		tree.put(2);
		
		System.out.println("remove called");
		tree.remove(5); // works
		
		//System.out.println(tree.rangeSize(3, 4)); // ppop up
		
// 		tree.put(5);
// 		tree.put(1);
// 		tree.put(2);
// 		tree.put(6);
// 		tree.put(7);
// 		tree.put(3);
// 		tree.put(1);
		
// 		//System.out.println(tree.root.getSubtreeSize());
// 		//System.out.println(tree.root.getRight().getSubtreeSize());
// 		//System.out.println(tree.root.getRight().getRight().getSubtreeSize());
// 		//System.out.println(tree.root.getKey());
// 		//System.out.println(tree.root.getRight().getKey());
// 		//System.out.println(tree.root.getRight().getRight().getKey());
		
// 		tree.remove(5);
		
// 		System.out.println(tree.root.getKey()); // 5
		
// 		System.out.println(tree.root.getRight().getKey());
		
    }

}
