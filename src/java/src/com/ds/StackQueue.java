package com.ds;

import java.util.*;
import java.util.LinkedList;

public class StackQueue {

	public static void main(String args[]) {
		int[] arr = {16, 15, 29, 19, 18};
		StackMin sm = new StackMin();
		for(int i: arr) 
			sm.push(i);

		while(!sm.isEmpty()) {
			System.out.printf("Min (%s): %d\n",  sm.displayStack(), sm.getMinElement());
			sm.pop();
		}

		System.out.println("Queue min:");
		QueueMin qm = new QueueMin();
		for(int i: arr) {
			qm.insert(i);
		}
		while(!qm.isEmpty()) {
			System.out.printf("Min (%s): %d\n", qm.displayQueue(), qm.getMinElement());
			qm.remove();
		}

		System.out.println("Stack as Queue implementation:");
		StackAsQueue saq = new StackAsQueue();
		for(int i: arr) {
			saq.insert(i);
			if(i%2 == 0) {
				saq.removeRecursive();
            			//saq.remove();
			}
		}

		while(!saq.isEmpty()) {
			System.out.println("Removed: " + saq.removeRecursive());
		}
		
	}

	
}

//To find the minimum element from the stack in O(1)
class StackMin {
	Stack<Integer> mainStack = new Stack<>();
	Stack<Integer> minStack = new Stack<>();
	public void push(int element) {
		mainStack.push(element);
		if(minStack.isEmpty()) {
			minStack.push(element);
		} else { //Maintain the smallest element on top
			/*if(minStack.peek() < element) {	
				minStack.push(minStack.peek()); 
			} else {
				minStack.push(element);
			}*/
			//Storing only when the new element is a smaller
			if(minStack.peek() >= element) {
				minStack.push(element);
			}
		}
	}

	public int pop() {
		int ret = mainStack.pop();
		if(minStack.peek() == ret) { //Pop the min stack only when the values are same
			minStack.pop();
		}
		return ret;
	}

	public int getMinElement() {
		return minStack.peek();
	}
	
	public String displayStack() {
		return mainStack.toString();
	}

	public boolean isEmpty() {
		return mainStack.isEmpty();
	}
}

//To find the minimum element from Queue in O(1)
class QueueMin {
	Queue<Integer> mainQueue = new LinkedList<>();
	Queue<Integer> minQueue = new LinkedList<>();

	public void insert(int element) {
		mainQueue.add(element);	
		
		int i = 0;
		while(!minQueue.isEmpty()) {
			if(minQueue.peek() > element) {
				minQueue.remove();
				i++;		
			} else {
				break;
			}
		}
		while(i-- > 0) { //If we have got new min, then the entire queue is replaced with this min
			//minQueue.add(element);
		}
		
		minQueue.add(element);
	}

	public int remove() {
		int ret = mainQueue.remove();
		if(minQueue.peek() == ret)	
			minQueue.remove();
		return ret;
	}
	
	public int getMinElement() {
		return minQueue.peek();
	}
	
	public String displayQueue() {
		return mainQueue.toString();
	}
		
	public boolean isEmpty() {
		return mainQueue.isEmpty();
	}
}

class StackAsQueue {
	
	Stack<Integer> stack = new Stack<>();
	Stack<Integer> second = new Stack<>();

	public void insert(int element) {
		stack.push(element);
	}
		
	//Unlike stack, this should remove the head, bottom element of the stack
	public int remove() {
		if(stack.isEmpty() && second.isEmpty()) {
			return -1; //Underflow
		}

		if(second.isEmpty()) {
			while(!stack.isEmpty()) {
				second.push(stack.pop());
			}
		}

		return second.pop();
	}

	public boolean isEmpty() {
		return stack.isEmpty() && second.isEmpty();
	}
	
	//Instead of using a secondary stack, can we recursively find and remove the bottom element alone
	public int removeRecursive() {
		if(stack.isEmpty()) {
			return -1; 
		} else if(stack.size() == 1) { //bottom element
			return stack.pop();
		} else {
			int current = stack.pop();
			int ret = removeRecursive();
			stack.push(current); //push the other elements again
			return ret; //bottom  element will be returned
		}
	}		

}
