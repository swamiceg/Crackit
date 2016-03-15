package com.ds;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BST {
	BinaryNode root = null;
	
	public void insert(int value) {
		BinaryNode newNode = new BinaryNode(value);
		if(root == null) {
			root = newNode;
		} else {
			BinaryNode temp = root;
			BinaryNode prev = null;
			while(temp != null) {
				prev = temp;
				if(value < temp.value) {
					temp = temp.left;
				} else {
					temp = temp.right;
				}
			}
			if(value < prev.value) 
				prev.left = newNode;
			else
				prev.right = newNode;
		}
	}
	
	public BinaryNode find(int key) {
		BinaryNode temp = root;
		while(temp != null) {
			if(temp.value == key) {
				return temp;
			} else if(key < temp.value) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
		
		return null;
	}
	
	public void displayInorder() {
		System.out.println("\nInorder traversal:");
		displayInorder(root);
	}
	
	private void displayInorder(BinaryNode root) {
		if(root == null) {
			return;
		}
		displayInorder(root.left);
		System.out.print(root + " ");
		displayInorder(root.right);
	}
	
	public void displayPreorder() {
		System.out.println("\nPreorder traversal:");
		displayPreorder(root);
	}
	
	private void displayPreorder(BinaryNode root) {
		if(root == null)
			return;
		
		System.out.print(root + " ");
		displayPreorder(root.left);
		displayPreorder(root.right);
	}
	
	//This is a level order traversal of the tree
	//Visit the nearby nodes first and then expand outwards using Queue
	public void breadthFirstSearch() {	
		System.out.println("\nBreadth First search on Tree:");
		Queue<BinaryNode> queue = new LinkedList<>();
		queue.add(root);
		System.out.print(queue.peek() + " ");
		root.isVisited = true;
		while(!queue.isEmpty()) {
			BinaryNode node = queue.peek();
			BinaryNode next = getNextUnvisited(node);
			if(next == null) {
				queue.poll().isVisited = false; //Remove the node
			} else {
				System.out.print(next + " ");
				next.isVisited = true;
				queue.add(next);
			}
		}
		
		//disableVisited(root);
	}
	
	public void breadthFirstSearch(BinaryNode root) {	
		System.out.println("\nBreadth First search on Tree:");
		Queue<BinaryNode> queue = new LinkedList<>();
		queue.add(root);
		System.out.print(queue.peek() + " ");
		root.isVisited = true;
		while(!queue.isEmpty()) {
			BinaryNode node = queue.peek();
			BinaryNode next = getNextUnvisited(node);
			if(next == null) {
				queue.poll().isVisited = false; //Remove the node
			} else {
				System.out.print(next + " ");
				next.isVisited = true;
				queue.add(next);
			}
		}
		
		//disableVisited(root);
	}

	private void disableVisited(BinaryNode root) {
		if(root == null) {
			return;
		}
		disableVisited(root.left);
		root.isVisited = false;
		disableVisited(root.right);
	}

	//It uses stack to traverse the node
	//It immediately goes away from the root unlike BFS
	public void depthFirstSearch() {	
		System.out.println("\nDepth first search on Tree:");
		boolean[] visited = new boolean[6];
		Stack<BinaryNode> stack = new Stack<>();
		stack.add(root);
		System.out.print(stack.peek() + " ");
		visited[0] = true;
		root.isVisited = true;
		while(!stack.isEmpty()) {
			BinaryNode node = stack.peek();
			BinaryNode next = getNextUnvisited(node);
			if(next == null) {
				stack.pop(); //Remove the node
			} else {
				System.out.print(next + " ");
				next.isVisited = true;
				stack.add(next);
			}
		}
		disableVisited(root);
	}
	private BinaryNode getNextUnvisited(BinaryNode node) {
		if(node.left != null && node.left.isVisited == false) {
			return node.left;
		} else if(node.right != null && node.right.isVisited == false) {
			return node.right;
		} else {
			return null;
		}
	}

	//It returns the element at pos in ascending order
	public BinaryNode findByPosition(int pos) {
		this.pos = pos;
		List<BinaryNode> arr = new ArrayList<>();
		findByPosition(root, arr);
		return arr.get(pos-1);
	}
	
	int pos = 0;
	private BinaryNode findByPosition(BinaryNode node, List<BinaryNode> arr) {
		if(node == null || pos == 0)
			return null;
		findByPosition(node.left, arr);
		arr.add(node);
		findByPosition(node.right, arr);
		
		return null;
	}

	public void inorderWithStack() {
		System.out.println("\nInorder traversal with stack:");
		BinaryNode current = root;
		Stack<BinaryNode> stack = new Stack<>();
		//Traverse to the extreme left
		//Since the left most element is the smallest element
		while(current != null) {
			stack.push(current);
			current = current.left;
		}
		//On top of the stack is the smallest element
		while(!stack.isEmpty()) {
			BinaryNode top = stack.pop();
			System.out.print(top + " ");
			
			//Push till the left for the right subtree 
			if(top.right != null) {
				current = top.right;
				while(current != null) {
					stack.push(current);
					current = current.left;
				}
			}
		}
		
	}
	
	public BinaryNode findKthSmallestElement(int k) {
		BinaryNode current = root;
		Stack<BinaryNode> stack = new Stack<>();
		//Traverse to the extreme left
		//Since the left most element is the smallest element
		while(current != null) {
			stack.push(current);
			current = current.left;
		}
		//On top of the stack is the smallest element
		while(!stack.isEmpty()) {
			BinaryNode top = stack.pop();
			if(--k == 0) {
				return top;
			}
			//Push till the left for the right subtree 
			if(top.right != null) {
				current = top.right;
				while(current != null) {
					stack.push(current);
					current = current.left;
				}
			}
		}
		
		return null;
	}
	
	public void reverseInorderWithStack() {
		System.out.println("\nDescending order: (Reverse inorder)");
		BinaryNode current = root;
		Stack<BinaryNode> stack = new Stack<>();

		while(true) {
			//Push the current node until the extreme right
			if(current != null) {
				stack.push(current);
				current = current.right;
			} else {
				//if current is null, pop the element and goto the left node
				if(!stack.isEmpty()) {
					BinaryNode tops = stack.pop();
					System.out.print(tops + " ");
					current = tops.left;
				} else {
					break;
				}
			}
		}
	}
	
	public BinaryNode findKthLargestElement(int k) {
		BinaryNode current = root;
		Stack<BinaryNode> stack = new Stack<>();
		
		while(true) {
			if(current != null) {
				stack.push(current);
				current = current.right;
			} else if(!stack.isEmpty()) { 
				BinaryNode tops = stack.pop();
				if(--k == 0) {
					return tops;
				}
				current = tops.left;
			} else {
				break;
			}
		}
		
		return null;
	}
	
	public int getHeight() {
		return getHeight(root);
	}
	
	private int getHeight(BinaryNode node) {
		if(node == null) {
			return 0;
		}
		
		int lheight = getHeight(node.left);
		int rheight = getHeight(node.right);
		
		if(lheight > rheight) {
			return lheight+1;
		} else {
			return rheight+1;
		}
	}
	
	//It does the level order traversal, printing all nodes at each level in a loop
	//it takes o(n2)
	//Breadth first search can be used to do level order traversal which takes o(n)
	public void levelOrderTraversal() {
		int height = getHeight();
		
		for(int i = 1; i <= height; i++) {
			printNodesAtLevel(root, i);
			//System.out.println();
		}
	}
	
	//Print nodes at level
	private void printNodesAtLevel(BinaryNode node, int level) {
		if(node == null) 
			return;
		if(level == 1)
			System.out.print(node + " ");
		else {
			printNodesAtLevel(node.left, level-1);
			printNodesAtLevel(node.right, level-1);
		}
	}

	//This method swaps the left and right child recursively and build the mirror image of the BST
	public void mirrorImage(BinaryNode node) {
		if(node == null)
			return;
		
		mirrorImage(node.left);
		mirrorImage(node.right);
		
		if(node.left != null && node.right != null) {
			//swap left and right children
			BinaryNode temp = node.left;
			node.left = node.right;
			node.right = temp;
		}
	}
	
	//This will do a boundary traversal (in anticlockwise) of a tree as follows,
	//1. Print all the left nodes from top to bottom
	//2. Print the leaf nodes on the bottom,
	//3. Print the right nodes from bottom to top
	public void boundaryTraversal(BinaryNode node) {
		if(node != null) {
			System.out.print(node + " ");
			printLeftBoundary(node.left);
			printLeaves(node);
			printRightBoundary(node.right);
		}
	}
	
	private void printRightBoundary(BinaryNode node) {
		if(node == null) 
			return;
		
		if(node.right != null) {
			printRightBoundary(node.right);
			System.out.print(node + " ");
		} else if(node.left != null) {
			printRightBoundary(node.left);
			System.out.print(node + " ");
		} else {
			//We are not doing anything for the leaf nodes
			//Let the PrintLeaves handle it
		}
	}

	//Recursively print all leaf nodes
	private void printLeaves(BinaryNode node) {
		if(node == null)
			return;
		
		//Similar to inorder traversal
		printLeaves(node.left);
		if(node.left == null && node.right == null) {
			System.out.print(node + " ");
		}
		printLeaves(node.right);
	}

	private void printLeftBoundary(BinaryNode node) {
		if(node == null)
			return;
		
		if(node.left != null) {
			System.out.print(node + " "); //top to bottom
			printLeftBoundary(node.left);
		} else if(node.right != null) {
			System.out.print(node + " ");
			printLeftBoundary(node.right);
		} else { 
			//We are not doing anything for the leaf nodes
			//Let the PrintLeaves handle it
		}
	}
	public static void main(String args[]) {
		BST bst = new BST();
		bst.insert(10);
		bst.insert(8);
		bst.insert(11);
		bst.insert(3);
		bst.insert(5);
		bst.insert(100);
		
		System.out.println(bst.find(100));
		bst.displayInorder();
		bst.displayPreorder();
		bst.breadthFirstSearch();
		bst.depthFirstSearch();
		
		System.out.println("\nFound: " + bst.findByPosition(3));
		
		bst.inorderWithStack();
		bst.reverseInorderWithStack();
		
		System.out.println();
		for(int k = 1; k <= 6; k++) {
			System.out.printf("\nSmallest element at position %d: %s", k, bst.findKthSmallestElement(k));
		}
		
		for(int k = 1; k <= 6; k++) {
			System.out.printf("\nLargest element at position %d: %s", k, bst.findKthLargestElement(k));
		}
		
		System.out.println("\nBinary tree height: " + bst.getHeight());
		System.out.println("\nLevel order traversal:"); 
		bst.levelOrderTraversal();
		
		bst.mirrorImage(bst.root);
		System.out.println("\nMirror image of the tree:");
		bst.levelOrderTraversal();
		
		bst = new BST();
		bst.insert(20);
		bst.insert(8);
		bst.insert(22);
		bst.insert(4);
		bst.insert(12);
		bst.insert(25);
		bst.insert(10);
		bst.insert(14);
		System.out.println("\nNew tree:");
		bst.levelOrderTraversal();
		System.out.println("\nBoundary traversal:");
		bst.boundaryTraversal(bst.root);
	}
}

class BinaryNode {
	public int value; //This is the key
	public BinaryNode left;
	public BinaryNode right;
	public boolean isVisited = false;
	public BinaryNode(int value) {
		this.value = value;
	}
	
	public String toString() {
		return new Integer(value).toString();
	}
}