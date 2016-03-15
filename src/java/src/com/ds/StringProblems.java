package com.ds;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StringProblems {

	private static char[] chars = null;
	
	public static void main(String args[]) {
		
		String str = "abcd";
		chars = str.toCharArray();
		Arrays.sort(chars);
		Set<String> permWords = doAnagram(chars.length);
		
		
		System.out.println(permWords);
		System.out.println(permWords.size());
		
		chars = "abcd".toCharArray();
		System.out.println(doSortedAnagram(chars.length));
		
		String txt = "AABAACAADAABAAABAA";
		String pat = "AABA";
		int index = findPattern(txt, pat);
		if(index == -1) {
			System.out.println("Pattern not found!");
		} else {
			System.out.printf("Pattern found at %d: [%s]\n", index, txt.substring(index, index+pat.length()));
		}
		
		index = findKMPPattern(txt, pat);		
		if(index == -1) {
			System.out.println("Pattern not found!");
		} else {
			System.out.printf("Pattern found at %d: [%s]\n", index, txt.substring(index, index+pat.length()));
		}
		
		for(int i = 0; i < 10; i++)
			System.out.println(generateRandom());
	}
	
	public static Set<String> doAnagram(int newsize) {
		Set<String> set = new HashSet<>();
		if(newsize == 1) 
			return set;
		
		for(int i = 0; i < newsize; i++) {
			//First recursively anagram the remaining letters
			Set<String> retSet = doAnagram(newsize-1);
			set.addAll(retSet);
			//This is where the unique combination is created 
			//Shuffle of last two letters
			if(newsize == 2)  {
				set.add(new String(chars));
				displayWord();
			}
			//Every position is rotated for (n - index) times
			//So that every other letter will come to this position
			//to create a unique combination
			rotateRight(newsize);
		}
		
		return set;
	}
	
	//Topological order of all the permutations
	public static Set<String> doSortedAnagram(int newsize) {
		Set<String> toRet = new HashSet<>();
		if(newsize == 1)
			return toRet; //single letter is anagrammed - base case
		
		for(int i = 0; i < newsize; i++) {
			//First recursively anagram the remaining letters
			toRet.addAll(doSortedAnagram(newsize-1));
			
			if(newsize == 2) {
				toRet.add(new String(chars));
				System.out.println(new String(chars));
			}
			
			rotateNextPerm(newsize); 
			if(i == newsize-1) { //Every character will be given an offer to this position
				Arrays.sort(chars, chars.length-newsize+1, chars.length); //Sort the right part of the array
			}
		}
		
		return toRet;
	}

	private static void rotateRight(int newsize) {
		int pos = chars.length - newsize;
		char temp = chars[pos];
		int j;
		for(j = pos+1; j < chars.length; j++) {
			chars[j-1] = chars[j];
		}
		chars[j-1] = temp;
	}
	
	//It will take the next greater character and swap to this position
	private static void rotateNextPerm(int newsize) {
		int pos = chars.length - newsize; //this is where we are going to insert
		
		int max = pos + 1;
		for(int i = pos + 2; i < chars.length; i++) { //Find the next great character from this pos and swap it
			if(chars[i] > chars[pos] && (chars[max] < chars[pos] || chars[i] < chars[max])) {
				max = i;
			}
		} 
		
		//Swap with next great character
		char temp = chars[pos];
		chars[pos] = chars[max];
		chars[max] = temp;
	}
	
	private static void displayWord() {
		System.out.println(new String(chars));
	}
	
	
	//Return the index of pattern match found in the txt
	//It uses naive algorithm to search for the pattern
	public static int findPattern(String txt, String pattern) {
		char[] txtArr = txt.toCharArray();
		char[] patArr = pattern.toCharArray();
		
		int idx = -1;
		int j;
		for(int i = 0; i < txtArr.length; i++) {
			for(j = 0; j < patArr.length && (i+j) < txtArr.length; j++) {
				if(txtArr[i+j] != patArr[j]) {
					break;
				}
			}
			if(j == patArr.length) { //Match found
				System.out.println("Naive pattern found at " + i);
				idx = i;
				//return i;
			}
		}
		
		return idx;
	}
	
	//Perform the pattern matching on the txt using Knuth Morris Patt algorithm
	//Return the index of the match
	public static int findKMPPattern(String txt, String pattern) {
		char[] txtArr = txt.toCharArray();
		char[] patArr = pattern.toCharArray();
		
		int[] lps = getLPSArray(patArr);
		int idx = -1;
		
		int i = 0; 
		int j = 0;
		int len = txtArr.length;
		while(i < len) {
			while(j < patArr.length && i < len && txtArr[i] == patArr[j]) {
				i++;
				j++;
			}

			if(j == patArr.length) {
				System.out.println("KMP pattern found at " + (i-j));
				idx = i - j;
				j = lps[j-1]; //Start the next pattern matching
			} else { //Partial match or no match at all
				if(j == 0) {
					i++; //Move to the next character as there is no match at all
				} else {					
					j = lps[j-1]; //Reinitialize
				}
			}
		}		
		
		return idx;
	}

	private static int[] getLPSArray(char[] patArr) {
		int[] lpsArr = new int[patArr.length];
		lpsArr[0] = 0;
		int len = 0, i = 1;
		while(i < patArr.length) {
			if(patArr[i] == patArr[len]) {
				len++;
				lpsArr[i] = len;
				i++;
			} else {
				if(len == 0) {
					lpsArr[i] = 0;
					i++;
				} else {
					//Backtrack the len to new position to start comparing with i again
					len = lpsArr[len-1]; 
				}
			}
		}
		
		return lpsArr;
	}
	
	public static int generateRandom() {
		Random rand = new Random();
		
		AtomicLong lon = new AtomicLong((long) Math.random());
		for(int i = 0; i < 10; i++) {
			System.out.println("Rand: " + rand.nextDouble());
			System.out.println(lon.get());
		}
		
		long cur = System.currentTimeMillis();
		long micros = TimeUnit.NANOSECONDS.toNanos(cur);
		System.out.println("Nano: " + micros);
		return (int) ((micros+TimeUnit.HOURS.toHours(cur)) % 101);
	}
	
	
}


