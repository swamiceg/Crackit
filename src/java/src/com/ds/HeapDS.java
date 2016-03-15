package com.ds;

import java.util.Arrays;

public class HeapDS {
	private int[] heapArr = null;
	private int max = 15;
	private int count = 0;
	
	public HeapDS(int max) {
		heapArr = new int[max];
		this.max = max;
	}
	
	public void insert(int val) {
		if(count == max) {
			System.err.println("Overflow!");
			return;
		}
		
		//insert to last position
		heapArr[count++] = val;
		if(count > 1) {
			trickleUp(count-1);
		}
	}
	
	public void trickleUp(int index) {
		int parent = (index - 1)/2;
		int key = heapArr[index]; //Element to trickle up
		while(index > 0) {
			if(heapArr[parent] < key) {
				heapArr[index] = heapArr[parent]; //move down
				index = parent;
				parent = (parent - 1)/2;
			} else {
				break;
			}
		}
		heapArr[index] = key;
		//System.out.println("inserted  " + key + " at: " + index);
	}

	public int remove() {
		if(count < 1) {
			System.err.println("Underflow!");
			return -1;
		}
		
		int removedMax = heapArr[0];
		int moveUp = heapArr[--count];
		if(count > 0) {
			heapArr[0] = moveUp; //Copy the last element to top
			trickleDown(0); //trickleDown the first element to a proper position
		}
					
		return removedMax;
	}

	public void trickleDown(int index) {
		int key = heapArr[index];
		int childIndex = -1;
		while(index < count/2) {
			int left = (2*index) + 1;
			int right = left + 1;
			
			if(right < count && heapArr[right] > heapArr[left])
				childIndex = right;
			else 
				childIndex = left;
			
			if(heapArr[childIndex] > key) { //Move up one position
				heapArr[index] = heapArr[childIndex];
				index = childIndex;
			} else
				break;
		}
		
		heapArr[index] = key; //Put the trickled down node to its proper position
	}
	
	public int getLength() {
		return count;
	}

	
	public static void main(String args[]) {
		
		HeapDS heap = new HeapDS(6);
		heap.insert(100);
		heap.insert(200);
		heap.insert(110);
		heap.insert(10);
		heap.insert(400);
		heap.insert(500);
		
		System.out.println("After insertion: " + Arrays.toString(heap.heapArr));
//		System.out.println("inserted " + heap.getLength());
		while(heap.getLength() > 0) {
			System.out.println(heap.remove());
		}
	}

	//This is needed for heap sorting
	public void insertAt(int i, int val) {
		if(i > max-1)
			return;
		heapArr[i] = val;
	}
	
	public void incrCount() {
		count++;
	}
	
	public void display() {
		for(int i = 0; i < count; i++)
			System.out.print(heapArr[i] + " ");
	}
}
