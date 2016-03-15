package com.ds;

public class Matrix {

	public static void main(String args[]) {
		int[][] matArr = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}, {17, 18, 19, 20}};
		printSpiral(matArr);
	}
	
	public static void printSpiral(int[][] matArr) {
		int k = 0; 
		int m = matArr.length;
		int l = 0;
		int n = matArr[0].length;
		
		while(k < m && l < n) {
			//print first row
			for(int i = l; i < n; i++) {
				System.out.print(matArr[k][i] + " ");
			}
			k++;
			
			//print last column
			for(int i = k; i < m; i++) {
				System.out.print(matArr[i][n-1] + " ");
			}
			n--;

			//print last row
			if(l < n) {
				for(int i = n-1; i >= l; i--) {
					System.out.print(matArr[m-1][i] + " ");
				}
				m--;
			}
			
			//print first column
			if(k < m) {
				for(int i = m-1; i >= k; i--) {
					System.out.print(matArr[i][l] + " ");
				}
				l++;
			}
		}
	}
}
