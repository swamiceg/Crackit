package com.ds;

import java.util.*;




class LexicoRank {	
	
	private static int MAX_CHAR = 256;
	public static void main(String args[]) {
		String string = "string";
		System.out.println("Rank: " + getRank(string));
	}
	
	public static int getRank(String string) {
		int[] countArr = new int[MAX_CHAR];
		int len = string.length();
		int comb = fact(len);
		int lesser = -1;
		int rank = 0;
		
		countArr = fillArray(countArr, string);
		
		char[] chars = string.toCharArray();
		
		System.out.println("Lesser characters");
		for(char c: chars) {
			System.out.println(c + " = " + countArr[(int)c]);	
		}
		
		for(int i = 0; i < len; i++) {
			comb /= (len-i);
			lesser = countArr[(int)chars[i]];
			rank += Math.min(len-(i+1), lesser) * comb; 
		}
		
		return rank;
	}

	private static int[] fillArray(int[] countArr, String string) {
		char[] chars = string.toCharArray();
		Arrays.fill(countArr, 0);
		for(char c: chars) {
			if(countArr[(int)c + 1] == 0)
				countArr[(int)c + 1]++; //add to next character position
		}
		
		//Add up from the left to count the lesser characters
		for(int i = 1; i < countArr.length; i++) {
			countArr[i] += countArr[i-1];
		}
		return countArr;
	}

	private static int fact(int num) {
		return (num <= 1)?1:num*fact(num-1);
	}
}