package com.ds;

import java.util.Arrays;

public class PatternMatch {
	public static void main(String args[]) {
		String txt = "sdfdfgh";
		String pattern = "df";
		 
		int index = doNaive(txt, pattern);
		if(index == -1) {
			System.out.println("Pattern not found!");
		} else {
			System.out.printf("Pattern found at index %d: %s\n", index, txt.substring(index, index+pattern.length()));
		}
		
		index = doRabinKarp(txt, pattern);
		if(index == -1) {
			System.out.println("Pattern not found!");
		} else {
			System.out.printf("Pattern found at index %d: %s\n", index, txt.substring(index, index+pattern.length()));
		}
		
		index = doKMP(txt, pattern);
		if(index == -1) {
			System.out.println("Pattern not found!");
		} else {
			System.out.printf("Pattern found at index %d: %s\n", index, txt.substring(index, index+pattern.length()));
		}
		
		index = doZAlgorithm(txt, pattern);
		if(index == -1) {
			System.out.println("Pattern not found!");
		} else {
			System.out.printf("Pattern found at index %d: %s\n", index, txt.substring(index, index+pattern.length()));
		}
	}
	
	public static int doNaive(String txt, String pattern) {
		char[] txtArr = txt.toCharArray();
		char[] patArr = pattern.toCharArray();
		
		int j;
		for(int i = 0; i < txtArr.length; i++) {
			for(j = 0; j < patArr.length && i+j < txtArr.length; j++) {
				if(txtArr[i+j] != patArr[j]) {
					break;
				}
			}
			if(j == patArr.length) {
				return i; //Found the index
			}
		}
		
		return -1;
	}
	
	//It will do start comparing the characters only if their hashes matches
	//There is a worst case possibility of all the hashes matches with pattern's hash and generating a complexity of
	//O(m*n) as Naive algorithm
	public static int doRabinKarp(String txt, String pattern) {
		char[] txtArr = txt.toCharArray();
		char[] patArr = pattern.toCharArray();
		
		int prime = 101;
		int ph = 0; //pattern hash
		int th = 0; //text hash
		for(int i = 0; i < patArr.length; i++) {
			ph += ((int)patArr[i])*((int)Math.pow(prime, i));
			th += ((int)txtArr[i])*((int)Math.pow(prime, i));
		}
		
		int j;
		for(int i = 0; i < txtArr.length; i++) {		
			if(ph == th) { //Check if their hash matches, and then start comparing
				for(j = 0; j < patArr.length && (i+j) < txtArr.length; j++) {
					if(txtArr[i+j] != patArr[j]) {
						break;
					}
				}
				if(j == patArr.length) {
					return i;
				}
			}
			
			//Generate the hash of the next part (after sliding by one char) using the oldHash
			if((i+patArr.length) < txtArr.length) {
				//Rehashing technique
				int oldHash = th - txtArr[i]; //Remove the first char
				oldHash /= prime;
				th = (int) (oldHash + (txtArr[i+patArr.length] * Math.pow(prime, patArr.length-1))); //Add the last char
			} else {
				return -1; //Avoid extra looping
			}
			
		}
		
		return -1;
	}
	
	//Knuth Morris Pratt algorithm uses the Longest Prefix which is also suffix (LPS) concept
	//to efficiently slide over the text array, instead of searching from every next character
	//This almost generates a complexity of O(n)
	public static int doKMP(String txt, String pattern) {
		char[] txtArr = txt.toCharArray();
		char[] patArr = pattern.toCharArray();
		
		//Generate the longest prefix suffix array
		int[] lps = getLPS(patArr); //Length of longest substring which is also a suffix of the substring
		int i = 0;
		int j = 0;
		while(i < txtArr.length) {
			while(i < txtArr.length && j < patArr.length && 
					txtArr[i] == patArr[j]) {
				i++;
				j++;
			}
			if(j == patArr.length) {
				return i-j; //Pattern found here
			} else { //Partial match
				if(j == 0) {
					i++; //Move to next character
				} else {
					j = lps[j-1];
				}
			}
		}

		return -1;
	}

	//Longest prefix which is a suffix of a substring
	//For KMP algorithm
	private static int[] getLPS(char[] patArr) {
		int i = 1;
		int len = 0;
		int[] lps = new int[patArr.length];
		while(i < patArr.length) {
			if(patArr[i] == patArr[len]) {
				len++;
				lps[i] = len;
				i++;
			} else {
				if(len == 0) {
					lps[i] = 0;
					i++;
				} else {
					//Backtrack the len to new position to start comparing with i again
					len = lps[len-1];
				}
			}
			
		}
		
		return lps;
	}
	
	//Pattern matching using Z Algorithm
	//We generate a Z array in order to perform the pattern matching
	//Efficiency of Z algorithm lies in the efficient generation of Z array
	public static int doZAlgorithm(String txt, String pattern) {
		String combine = pattern + "$" + txt;
		int[] zArr = generateZArray(combine.toCharArray());
		for(int i = 0; i < zArr.length; i++) {
			if(zArr[i] == pattern.length()) { //Match found
				return i-pattern.length()-1;
			}
		}
		
		return -1;
	}

	private static int[] generateZArray(char[] arr) {
		//Use the left and right pointer and use the old values to avoid recalculating the array
		int[] zArr = new int[arr.length];
		int left = 0, right = 0;
		int k1;
		for(int k = 1; k < arr.length; k++) {
			if(k > right) {
				left = right = k;
				while(right < arr.length && arr[right-left] == arr[right]) {
					right++;
				}
				zArr[k] = right-left;
				right--;
			} else {
				k1 = k - left; //equivalent position in the previous match
				if(zArr[k1] > right-k+1) { //Left value has exceeded the right boundary, thus we need to reinitialize
					left = k;
					//Start comparing from the right, since the l to r chars are already matched
					while(right < arr.length && arr[right-left] == arr[right]) { 
						right++;
					}
					zArr[k] = right-left;
					right--;
				} else {
					zArr[k] = zArr[k1];
				}
			}
		}
		
		return zArr;
	}
}
