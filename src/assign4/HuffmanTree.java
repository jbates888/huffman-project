package assign4;

import java.util.*;
public class HuffmanTree {
//DO NOT include the frequency or priority in the tree
	private class Node {
		private Node left;
		private char data;
		private Node right;
		private Node parent;
		private Node(Node L, char d, Node R, Node P) {
			left = L;
			data = d;
			right = R;
			parent = P;
		}
	 }
	
	private Node root;
	private Node current; 
	
	public HuffmanTree() {
		root = null;
		current = null;
	}
	public HuffmanTree(char d) {
	//makes a single node tree
		root = new Node(null, d, null, null);
	}
	public HuffmanTree(String t, char nonLeaf) {
	//Assumes t represents a post order representation of the tree as discussed in class nonLeaf is the char value of the data in the non-leaf nodes
	//in class we used (char) 128 for the non-leaf value
		//create a char array from the postorder string representation of string
		char a[] = t.toCharArray();
		//creat a stack of type Node
		Stack<Node> stack = new Stack<Node>();
		//loop through each char in the post order string
		for(int i = 0; i < a.length; i++) {
			//if the value is a nonleaf, push a new node with nonleaf value on stack
			if(a[i] != nonLeaf) { 
				stack.push(new Node(null, a[i], null, null));
			} else {
				//right is top of stack
				Node right = stack.pop();
				//left is next thing on stack
				Node left = stack.pop();
				//create a node from those two
				Node x = new Node(left, a[i], right, null);
				//set their parents to the new node
				right.parent = x;
				left.parent = x;
				//push the new node back on to stack
				stack.push(x);
			}	
		}
		//root is the last thing left on the stack
		root = stack.pop();
		current = root;
	}
	
	public HuffmanTree(HuffmanTree b1, HuffmanTree b2, char d) {
	//makes a new tree where b1 is the left subtree and b2 is the right subtree d is the data in the root
		//if left and right trees are null, make a tree with just one node
		if(b1.equals(null) && b2.equals(null)) {
			root = new Node(null, d, null, null);
			current = root;
		//if the left child tree is null, make tree with only right child
		} else if(b1.equals(null)) {
			root = new Node(null, d, b2.root, null);
			b2.root.parent = root;
			current = root;
		//if the right child tree is null, make tree with only left child
		} else if(b2.equals(null)) {
			root = new Node(b1.root, d, null, null);
			b1.root.parent = root;
			current = root;
		//else make a tree with both children
		} else {
			root = new Node(b1.root, d, b2.root, null);
			b1.root.parent = root;
			b2.root.parent = root;
			current = root;
		}
	}
	//use the move methods to traverse the tree
	//the move methods change the value of current
	 //use these in the decoding process
	public void moveToRoot() {
	 //change current to reference the root of the tree
		current = root;
	}
	public void moveToLeft() {
	 //PRE: the current node is not a leaf
	 //change current to reference the left child of the current node
		current = current.left;
	}
	public void moveToRight() {
	 //PRE: the current node is not a leaf
	 //change current to reference the right child of the current node
		current = current.right;
	}

	 public void moveToParent() {
	 //PRE: the current node is not the root
	 //change current to reference the parent of the current node
		 current = current.parent;
	 }	
	 public boolean atRoot() {
	 //returns true if the current node is the root otherwise returns false
		 if(root.equals(current)) {
			 return true;
		 }
		 return false;		 
	 }
	 
	public boolean atLeaf() {
	//returns true if current references a leaf other wise returns false
		 if(current.left == null && current.right == null) {
			 return true;
		 }
		 return false;
	}
	
	public char current() {
	//returns the data value in the node referenced by current
		return current.data;
	}
	
	public String[] pathsToLeaves() {
	/*returns an array of strings with all paths from the root to the leaves each value in the array contains a leaf value followed by a seqeunce of
	0s and 1s. The 0s and 1s represent the path from the root to the node containing the leaf value.*/
		//create a linkedlist of strings
		LinkedList<String> list = new LinkedList<String>();
		String p = "";
		//set the linkedlist to the paths of leafs
		list = pathsHelper(list, p, root);
		//create an array with length of the arraylist
		String arr[] = new String[list.size()];
		
		int i = 0;
		//while the linkedlist is not empty
		while(!list.isEmpty()) {
			//fill the string array with each value in the linkedlist
			arr[i] = list.remove();
			i++;
		}
		return arr;
	} 
	//create a linked list of the paths to leaves
	private LinkedList<String> pathsHelper(LinkedList<String> ll, String p, Node x) {
		//base case
		if(x == null) {
			return null;
		}
		//if at a leaf, add value to the linked list
		if(x.right == null && x.left == null) {
			ll.add(x.data + /* " " + */ p);
			p = "";
		}
		//call for left and right nodes , each time add 1 for right or 0 for left to the string
		pathsHelper(ll, p + "0", x.left);
		pathsHelper(ll, p + "1", x.right);
		//return the list
		return ll;
	}
	
	public String toString() {
	//returns a string representation of the tree using the postorder format
	// discussed in class
		return toString(root);
	}
	//private toString helper method that takes in the root node
	private String toString(Node x) {
		//if at a leaf, return the nodes data as a string
		if(x.left == null && x.right == null) {
			return Character.toString(x.data);
		} else {
			//return left, right, and data as a string
			return toString(x.left) + toString(x.right) + Character.toString(x.data);
		}
	}
}
		 
		 