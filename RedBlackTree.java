import edu.princeton.cs.algs4.Queue;

/*
 * @author Fatma Cemile Serce (adapted from textbook, Algorithms, 4th Edition, by
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * 
 * Date: Spring, 2019
 * Description: A symbol table implemented using a left-leaning red-black BST.
 */

public class RedBlackTree<Key extends Comparable<Key>, Value> {
	
	private Node root; //root of BST
	
	private static boolean RED=true;
	private static boolean BLACK = false;
	
	// BST helper node data type
	private class Node{
		Key key;			// key
		Value value;		//associated data/value
		Node left, right;	// links to left and right subtrees
		int size;			// subtree count
		boolean color;		// color of parent link
		Node(Key key, Value value, boolean color){
			this.key = key;
			this.value = value;
			this.color = color;
		}
	}
	
	//Returns the number of key-value pairs in this symbol table.
	public int size(){
		return size(root);
	}
	
	// number of node in subtree rooted at x; 0 if x is null
	private int size(Node x) {
		if(x==null)
			return 0;
		else 
			return x.size;		
	}
	
	//Does this symbol table contain the given key?
	public boolean contains(Key key) {
		if(key==null) throw new IllegalArgumentException();
		return get(key)!=null;
	}
	
	//Returns the value associated with the given key.
	public Value get(Key key) {
		if(key==null) throw new IllegalArgumentException();
		return get(root, key);
	}
	
	// value associated with the given key in subtree rooted at x; null if no such key
	private Value get(Node x, Key key) {
		if(x==null)
			return null;
		int compare = key.compareTo(x.key);
		if(compare<0)
			return get(x.left, key);
		else if(compare>0)
			return get(x.right, key);
		else
			return x.value;
	}
	/*
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
	 */
	public void put(Key key, Value value) {
		if(key==null) throw new IllegalArgumentException();
		if(value ==null)
			return; //delete(key);
		root = put(root, key, value);
		root.color = BLACK;
	}
	
	// insert the key-value pair in the subtree rooted at h
	private Node put(Node x, Key key, Value value) {
		if(x==null) {
			return new Node(key, value, RED);
		}
		int compare = key.compareTo(x.key);
		if(compare<0)
			x.left = put(x.left, key, value);
		else if(compare>0)
			x.right = put(x.right, key, value);
		else
			x.value = value;
		if(!isRed(x.left) && (isRed(x.right)))
			x = rotateLeft(x);
		if(isRed(x.left) && isRed(x.left.left))
			x= rotateRight(x);
		if(isRed(x.left) && isRed(x.right))
			flipColors(x);
		return x;
	}
	

	
	// make a right-leaning link lean to the left
	private Node rotateLeft(Node x) {
		Node temp = x.right;
		x.right = temp.left;
		temp.left = x;
		temp.color = x.color;
		x.color = RED;
		temp.size = x.size;
		x.size = size(x.left) + size(x.right) + 1;
		return temp;
	}
	
	// make a left-leaning link lean to the right
	private Node rotateRight(Node x) {
		Node temp = x.left;
		x.left = temp.right;
		temp.right = x;
		temp.color = x.color;
		x.color = RED;
		temp.size = x.size;
		x.size = size(x.left) + size(x.right) + 1;
		return temp;
	}
	
	// flip the colors of a node and its two children
	public void flipColors(Node x) {
		x.color = RED;
		x.left.color = BLACK;
		x.right.color = BLACK;
	}
	
	// is node x red; 
	// false if x is null.
	private boolean isRed(Node x) {
		if(x==null)
			return false;
		return x.color==RED;
	}
	
	/*
	 * Returns all keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
	 */
	public Iterable<Key> keys(){
		Queue<Key> q = new Queue<Key>();
		keys(root, q);
		return q;
	}

	//add keys to the queue
	private void keys(Node x, Queue<Key> q){
		if(x==null)
			return;
		keys(x.left, q);
		q.enqueue(x.key);
		keys(x.right, q);
	}
	
	// Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
	public boolean is23() {
		return is23(root);
	}
	private boolean is23(Node x) {
		if(x==null)
			return true;
		if(isRed(x.right))
			return false;
		if(isRed(x) && isRed(x.left))
			return false;
		return is23(x.left) && is23(x.right);
	}

	// do all paths from root to leaf have same number of black edges?
	public boolean isBalanced() {
		int blackHeight = 0; // number of black links on path from root to min
		Node x = root;
		while(x!=null) {
			blackHeight++;
			x = x.left;
		}
		return isBalanced(root, blackHeight);
	}
	
	// does every path from the root to a leaf have the given number of black links?
	private boolean isBalanced(Node x, int blackHeight) {
		if(x==null)
			return blackHeight==0;
		if(!isRed(x))
			blackHeight--;
		
		return isBalanced(x.left, blackHeight) && isBalanced(x.right, blackHeight);	
	}

	//a sample test case

	
	//more methods such as height(), min(), max()...

}
