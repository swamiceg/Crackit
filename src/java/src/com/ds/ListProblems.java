package com.ds;

import java.util.ArrayList;
import java.util.List;

public class ListProblems {
	public static void main(String args[]) {
		List<Integer> ls = new ArrayList<>();
		buildList(ls, 0);
		System.out.println("Build list: " + ls);
		LinkedList list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(3));
		list.add(new Node(4));
		list.add(new Node(5));
		list.add(new Node(6));

		System.out.println("Pairwise swapped using reference:");
		Node temp = swapPairWise(list.getRoot());
		print(temp);		
		
		System.out.println("Rotate every:");
		list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(3));
		list.add(new Node(4));
		list.add(new Node(5));
		list.add(new Node(6));
		list.add(new Node(7));
		list.add(new Node(8));
		temp = rotateEvery(list.getRoot(), 2);
		print(temp);
		
		list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(3));
		list.add(new Node(4));
		list.add(new Node(5));
		list.add(new Node(6));
		
		System.out.println("Pairwise swapped with data:");
		temp = swapPairWiseData(list.getRoot());
		print(temp);
		
		list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(3));
		list.add(new Node(4));
		list.add(new Node(5));
		list.add(new Node(6));
		
		System.out.println("Pairwise swapped recursive:");
		temp = recSwap(list.getRoot(), null);
		print(temp);
		
		int a= 10;
		int b = 5;
		
		System.out.println("\nBefore swaping: " + a + "::" + b);
		a = a + b;
		b = a - b;
		a = a - b;
		System.out.println("After swaping: " + a + "::" + b);

		a = 123; b = 321;
		a = a ^ b;
		b = a ^ b;
		a = a ^ b;
		System.out.println("After swaping: " + a + "::" + b);
		
		//Let's do a single line swaping
		a = 100; b = 200;
		a = a ^ b ^ (b = a);
		System.out.println("After swaping: " + a + "::" + b); //Left to right evaluation in Java
		a = 123; b = 321;
		a = a + b - (b = a); //(a = 123 + 321 (b = 123))
		System.out.println("After swaping: " + a + "::" + b);
		
		
		System.out.println("\nRotating linked list:");
		list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(3));
		list.add(new Node(4));
		list.add(new Node(5));
		list.add(new Node(6));
		
		temp = rotateByNumber(list.getRoot(), 2);
		print(temp);
		
		list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(2));
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(0));
		list.add(new Node(2));
		list.add(new Node(2));
		
		System.out.println("Sorted on 0s, 1s, 2s:");
		temp = sortOnNumbers(list.getRoot());
		print(temp);
		
//		System.out.println("Delete 'n' nodes after 'm' nodes: ");
//		temp = deleteNnodesAfterM(temp, 0, 7);
//		print(temp);
		
		list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(3));
		list.add(new Node(4));
		list.add(new Node(5));
		list.add(new Node(6));
		list.add(new Node(7));
		list.add(new Node(8));
		
		System.out.println("Delete 'n' nodes after 'm' nodes throughout the list: ");
		temp = deleteNnodesAfterMRec(list.getRoot(), 2, 1);
		print(temp);
		
		list = new LinkedList();
		list.add(new Node(1));
		list.add(new Node(2));
		list.add(new Node(1));
		list.add(new Node(1));
		
		System.out.println("Count number:: " + countNumber(list.getRoot(), 3));
		
		//Segregating the even numbers followed by odd numbers in order
		list = new LinkedList();
		list.add(new Node(2));
		list.add(new Node(4));
		list.add(new Node(6));
		list.add(new Node(8));
		temp = segregateOddEven(list.getRoot());
		print(temp);
		
		temp = reverseLinkedList(temp);
		System.out.println("Reversed linked list:");
		print(temp);
	}
	
	//Rotating linked list counter clockwise 'n' nodes
	public static Node rotateByNumber(Node head, int n) {
		Node headRef = head;
	
		//Reach the nth node
		while(headRef != null && n > 1) {
			headRef = headRef.next;
			n--;
		}
		Node save = headRef;
		
		while(headRef != null && headRef.next != null) {
			headRef = headRef.next;
		}
		headRef.next = head;
		head = save.next;
		save.next = null;
		
		return head;
	}
	
	//Sort the linked list on the number of 0s, 1s, 2s
	public static Node sortOnNumbers(Node head) {
		Node headRef = head;
		int[] count = new int[3];
		
		while(headRef != null) {
			count[headRef.value]++;
			headRef = headRef.next;
		}
		
		headRef = head;
		/*for(int i = 0; i < count.length; i++) {
			for(int j = 0; j < count[i]; j++) {
				headRef.value = i;
				headRef = headRef.next;
			}
		}*/
		
		int i = 0;
		while(headRef != null) {
			if(count[i] == 0) {
				i++; //Next element
			} else {
				headRef.value = i;
				--count[i];
				headRef = headRef.next;
			}
		}
		
		return head;
	}
	
	//Delete 'n' nodes after 'm' nodes
	public static Node deleteNnodesAfterM(Node head, int m, int n) {
		Node headRef = head;
		
		Node prev = null;
		while(headRef != null && m > 0) {
			prev = headRef;
			headRef = headRef.next;
			m--;
		}
		
		if(prev != null) { //Check if there are nodes to delete after m	
			Node next = prev;
			while(next != null && n > 0) {
				next = next.next;
				n--;
			}
			
			if(next != null) {
				prev.next = next.next;
			} else {
				prev.next = null;
			}
		} else { //Delete from starting
			Node next = head; //First node
			while(next != null && n > 0) {
				next = next.next;
				n--;
			}
			head = next;
		}
		
		return head;
	}
	
	//Delete 'n' nodes after 'm' nodes
	//Recursively till the end
	public static Node deleteNnodesAfterMRec(Node head, int m, int n) {
		if(head == null) 
			return head; //Base case
		
		int i = m;
		int j = n;
		
		Node headRef = head;
		
		Node prev = null;
		while(headRef != null && i > 0) {
			prev = headRef;
			headRef = headRef.next;
			i--;
		}
		
		Node next = null;
		if(prev != null) { //Check if there are nodes to delete after m	
			next = prev;
			while(next != null && j > 0) {
				next = next.next;
				j--;
			}
			
			if(next != null) {
				prev.next = next.next;
			} else {
				prev.next = null;
			}
		} else { //Delete from starting
			next = head; //First node
			while(next != null && j > 0) {
				next = next.next;
				j--;
			}
			head = next;
		}
		
		//return of the recursive call should not be used. Base case returns null.		
		deleteNnodesAfterMRec((next!=null?next.next:null), m, n);
		
		return head;
	}
	
	//Swap the elements pair-wise in a linked list
	public static Node swapPairWise(Node head) {
		Node headRef = head;
		if(headRef.next == null)
			return headRef; //base case
				
		Node prev = null;
		while(headRef != null && headRef.next != null) {
			if(prev != null) {
				prev.next = headRef.next;										
			} else {
				head = headRef.next;
			}
			Node next = headRef.next;
			headRef.next = next.next;
			next.next = headRef;
			
			prev = headRef;
			headRef = headRef.next;
		}

		return head;
	}
	
	public static Node recSwap(Node head, Node prev) {
		Node headRef = null;
		if(head == null || head.next == null) {
			return head;
		}
		
		Node next = head.next;
		if(prev != null) {
			prev.next = next; 
		} else {
			headRef = next;
		}
		head.next = next.next;
		next.next = head;
		
		recSwap(head.next, head);
		return headRef;
	}
	
	//This swapping will swap the data itself without updating the references
	//This is not efficient
	public static Node swapPairWiseData(Node head) {
		Node headRef = head;
		while(headRef != null && headRef.next != null) {
			int temp = headRef.value;
			headRef.value = headRef.next.value; //Just swap the data
			headRef.next.value = temp;
			headRef = headRef.next.next;
		}
		
		return head;
	}
	
	//Count the number of occurrences of num in the list
	public static int countNumber(Node head, int num) {
		int count = 0;
		while(head != null) {
			if(head.value == num)
				count++;
			head = head.next;
		}
		
		return count;
	}
	
	//It segregate the even numbers followed by the odd numbers
	public static Node segregateOddEven(Node head) {
		Node headRef = head;
		Node evenRef = null;
		Node oddRef = null;
		Node oddHead = null;
		while(headRef != null) {
			if(headRef.value % 2 == 0) { //Even
				if(evenRef == null) {
					evenRef = head = headRef;
				} else {
					evenRef.next = headRef;
					evenRef = evenRef.next;
				}
			} else {
				if(oddRef == null) {
					oddRef = oddHead = headRef;
				} else {
					oddRef.next = headRef;
					oddRef = oddRef.next;
				}
			}
			
			headRef = headRef.next;
		}
		
		if(evenRef != null) {
			evenRef.next = oddHead;
		} else {
			head = oddHead;
		}
		
		return head;
	}
	
	public static Node reverseLinkedList(Node head) {
		Node curr = head;
		Node prev = null, next = null;
		
		while(curr != null) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		
		return prev;
	}
	
	public static Node rotateEvery(Node head, int k) {
		if(head == null) {
			return null;
		}
		int s = k;
		Node curr = head;
		Node prev = null, next = null;
		while(curr != null && s > 0) {
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
			s--;
		}		
		if(prev != null)
			head.next = rotateEvery(curr, k);
		return prev;
	}
	
	private static void print(Node headRef) {
		Node temp = headRef;
		while(temp != null) {
			System.out.print(temp.value + "->");
			temp = temp.next;
		}
		System.out.println();
	}
	
	public static void buildList(List<Integer> list, int i) {
		if(i == 5) {
			return;
		}
		list.add(i);
		buildList(list, ++i);
	}
	
}
