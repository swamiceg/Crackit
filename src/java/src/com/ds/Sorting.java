package com.ds;

import java.util.Arrays;

public class Sorting {

	public static void main(String[] args) {
		int[] arr = {5, 4, 10, 1, 100};
		doBubbleSort(arr.clone());
		doSelectionSort(arr.clone());	
		doInsertionSort(arr.clone());
		
		mergeSort(Sorting.arr, 0, Sorting.arr.length-1);
		System.out.println("Merge sorted array: " + Arrays.toString(Sorting.arr));
		
		arr = new int[]{500, 400, 300, 200, 100, 1};
		doQuickSort(arr, 0, arr.length-1);
		System.out.println("Quick sorted arrays: " + Arrays.toString(arr));
		
		arr = new int[]{500, 400, 1000, 300, 200, 100, 1, 43, 20, 120, 10, 2};
		doMedianQuicksort(arr, 0, arr.length-1);
		System.out.println("Quick sorted arrays with median: " + Arrays.toString(arr));

		arr = new int[]{100, 120, 400, 150, 600, 200};
		doHeapSort(arr);
	}
	
	//Elements bubble to the right 
	//Swap and comparision of each elements
	public static void doBubbleSort(int[] arr) {
		for(int i = arr.length-1; i >= 0; i--) {
			for(int j = 0; j < i; j++) {
				if(arr[j] > arr[j+1]) {
					swap(arr, j, j+1);
				}
			}
		}
		
		System.out.println("Bubble sorted array: " + Arrays.toString(arr));
	}
	
	//Select the min elements and swap them in the order  
	public static void doSelectionSort(int[] arr) {
		int min = -1;
		for(int i = 0; i < arr.length-1; i++) {
			min = i;
			for(int j = i+1; j < arr.length; j++) {
				if(arr[j] < arr[min]) 
					min = j;
			}
			if(min != i) 
				swap(arr, min, i);
		}
		
		System.out.println("Selection sorted array: " + Arrays.toString(arr));
	}
	
	//Select the element (card) and insert them in proper position
	public static void doInsertionSort(int[] arr) {		
		for(int i = 1; i < arr.length; i++) {
			int key = arr[i];
			int j = i-1;
			while(j >= 0 && arr[j] > key) { //take the card and insert into right position
				arr[j+1] = arr[j--];	
			}
			arr[j+1] = key;
		}
		
		System.out.println("Insertion sorted array: " + Arrays.toString(arr));
	}
	
	
	static int arr[] = {10, 5, 100, 2, 1, 6, 200, 6};
	public static void mergeSort(int[] arr, int low, int high) {
		int[] tempArr = new int[arr.length];
		doMergeSort(tempArr, low, high);
	}
	
	
	public static void doMergeSort(int[] tempArr, int low, int high) {
		//System.out.println("\n Dividing: " + low + "->" + high);
		if(low == high)
			return;
		else {
			int mid = (low + high) / 2;
			doMergeSort(tempArr, low, mid); //Merge the first half
			doMergeSort(tempArr, mid+1, high); //Merge the second half
		
			//System.out.println("Merging: a[" + low + ":" +  mid +"]" + "-> a[" + (mid+1) + ":" + high + "]");
			//Merge two sorted arrays
			mergeArrays(tempArr, low, mid, high);
		}
	}

	//It merges the two sorted arrays
	//First array - arr[low to mid]
	//Second array - arr[mid+1 to high]
	private static void mergeArrays(int[] tempArr, int low, int mid, int high) {
		int j = 0;
		int fidx = low;
		int sidx = mid + 1;
		
		while(fidx <= mid && sidx <= high) {
			if(arr[fidx] < arr[sidx]) {
				tempArr[j++] = arr[fidx++];
			} else {
				tempArr[j++] = arr[sidx++];
			}
		}
		
		while(fidx <= mid) tempArr[j++] = arr[fidx++]; //Copy the left overs
		while(sidx <= high) tempArr[j++] = arr[sidx++];
		
		int n = (high - low) + 1;
		for(int i = 0; i < n; i++) { //Copy the sorted part back to main array
			arr[low+i] = tempArr[i];
		}
	}
	
	public static void doQuickSort(int[] arr, int low, int high) {
		if((high - low) <= 0)
			return;
		else {
			int partition = partition(arr, low, high);
			doQuickSort(arr, low, partition-1);
			doQuickSort(arr, partition+1, high);
		}
		
	}
	
	//It partition the array into two halves with the rightmost element as the pivot
	//Return the index of the starting element of right side array
	private static int partition(int[] arr, int low, int high) {
		int pivot = arr[high];
		int lowPtr = low -1;
		int highPtr = high;
		while(true) {
			while(arr[++lowPtr] < pivot && lowPtr <= high-1);
			while(arr[--highPtr] > pivot && highPtr > low);
			
			if(lowPtr >= highPtr)
				break;
			else
				swap(arr, lowPtr, highPtr);
		}
		//Put the pivot element to its fixed final position. This element is sorted.
		swap(arr, lowPtr, high);
		return lowPtr;
	}

	private static void doMedianQuicksort(int[] arr, int low, int high) {
		if(low == high)
			return;
		else if (high-low+1 <= 3) { //For three elements do insertion sort
			for(int i = low+1; i <= high; i++) {
				int key = arr[i];
				int j = i-1;
				while(j >= low && arr[j] > key) {
					arr[j+1] = arr[j--];
				}
				arr[j+1] = key;
			}
		}
		else {
			int pivot = getMedian(arr, low, high);
			int partition = medianPartition(arr, low, high, pivot);
			doMedianQuicksort(arr, low, partition-1);
			doMedianQuicksort(arr, partition+1, high);
		}
	}
	
	private static int getMedian(int[] arr, int low, int high) {
		int mid = (low + high)/2;
		if(arr[low] > arr[mid]) {
			swap(arr, low, mid);
		}
		if(arr[mid] > arr[high]) {
			swap(arr, mid, high);
		}
		swap(arr, mid, high-1); //Put the pivot to the last but one position
		
		return arr[high-1];
	}

	private static int medianPartition(int[] arr, int low, int high, int pivot) {
		int leftPtr = low-1;
		int rightPtr = high-1;
		while(true) {
			while(arr[++leftPtr] < pivot);
			while(arr[--rightPtr] > pivot);
			if(leftPtr >= rightPtr)
				break;
			else 
				swap(arr, leftPtr, rightPtr);
		}
		swap(arr, leftPtr, high-1); //Put the pivot into its position
		
		return leftPtr;
	}
	
	public static void doHeapSort(int[] arr) {
		HeapDS heap = new HeapDS(arr.length);
		//Just insert all the elements in order
		for(int i = 0; i < arr.length; i++) {
			heap.insertAt(i, arr[i]);
			heap.incrCount();
		}
		
		//Heapify the array using trickleDown from middle
		for(int i = arr.length/2; i >= 0; i--) {
			heap.trickleDown(i);
		}
		
		int len = arr.length;
		for(int i=len-1; i >= 0; i--) {
			heap.insertAt(i, heap.remove()); //Should not increment the count, as the largest element moved down and excluded from the process
		}
		for(int i=len-1; i >= 0; i--) {
			heap.incrCount();
		}
		
		System.out.print("Heap sorted array: ");
		heap.display();
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
