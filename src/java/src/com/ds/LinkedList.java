package com.ds;

public class LinkedList {
	private Node root = null;
	public void add(Node newNode) {
		if(root == null) {
			root = newNode;
		} else {
			newNode.next = root;
			root = newNode;
		}
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void display() {
		Node temp = root;
		while(temp != null) {
			System.out.print(temp + "->");
			temp = temp.next;
		}
	}
}

class Node {
	public int value;
	public Node next = null;
	public Node(int value) {
		this.value = value;
	}
	
	public String toString() {
		return new Integer(value).toString();
	}
}