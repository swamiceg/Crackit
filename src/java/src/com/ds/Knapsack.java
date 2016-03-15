package com.ds;

import java.util.ArrayList;
import java.util.List;

public class Knapsack {

    public static void main(String[] args) {
    	doKnapSack(new int[]{11, 8, 7, 6, 5}, new int[]{30, 50, 60 ,80, 90}, 0, 20);
    	System.out.println("Max value: " + recKnapSack(20, new int[]{11, 8, 7, 6, 5}, new int[]{30, 50, 60 ,80, 90}, 5));
    	
        int N = Integer.parseInt(args[0]);   // number of items
        int W = Integer.parseInt(args[1]);   // maximum weight of knapsack

        int[] profit = new int[N+1];
        int[] weight = new int[N+1];

        // generate random instance, items 1..N
        for (int n = 1; n <= N; n++) {
            profit[n] = (int) (Math.random() * 1000);
            weight[n] = (int) (Math.random() * W);
        }

        // opt[n][w] = max profit of packing items 1..n with weight limit w
        // sol[n][w] = does opt solution to pack items 1..n with weight limit w include item n?
        int[][] opt = new int[N+1][W+1];
        boolean[][] sol = new boolean[N+1][W+1];

        for (int n = 1; n <= N; n++) {
            for (int w = 1; w <= W; w++) {

                // don't take item n
                int option1 = opt[n-1][w];

                // take item n
                int option2 = Integer.MIN_VALUE;
                if (weight[n] <= w) option2 = profit[n] + opt[n-1][w-weight[n]];

                // select better of two options
                opt[n][w] = Math.max(option1, option2);
                sol[n][w] = (option2 > option1);
            }
        }

        // determine which items to take
        boolean[] take = new boolean[N+1];
        for (int n = N, w = W; n > 0; n--) {
            if (sol[n][w]) { take[n] = true;  w = w - weight[n]; }
            else           { take[n] = false;                    }
        }

        // print results
        System.out.println("item" + "\t" + "profit" + "\t" + "weight" + "\t" + "take");
        for (int n = 1; n <= N; n++) {
            System.out.println(n + "\t" + profit[n] + "\t" + weight[n] + "\t" + take[n]);
        }
    }
    
    
    //I know this is a worst solution for KnapSack problem!! But it solves it.
    //Worst case complexity of O(n*n)
    public static void doKnapSack(int[] weights, int[] values, int index, int target) {
    	List<Integer> ls = new ArrayList<>();
    	int max = -1;
    	//Traverse from the left
    	for(int i = index; i < weights.length; i++) {
    		ls.add(i);
    		int total = weights[i];
    		int count = i+1;
    		int valTotal = values[i];
    		for(int j = count; j < weights.length; j++) {
    			if(total + weights[j] == target) {
    				ls.add(j);
    				valTotal += values[j];
    				if(valTotal > max) 
    					max= valTotal;
    				System.out.println("Total weights obtained: " + (total + weights[j]) + " ==> " + ls);
    				System.out.println("Total value: " + valTotal);
    				System.out.println("Max value: " + max);
    				//break; //Obtained
    			}
    			else if(total + weights[j] < target) {
    				ls.add(j);
    				valTotal += values[j];
    				total = total + weights[j];
    				continue; //Check next element
    			} else { //exceeded
    				if(j == weights.length) {
    					ls.clear();
    					ls.add(i);
    					valTotal = values[i];
    					
    					total = weights[i];
    					j = ++count;
    					j--; //Increment will happen again
    				}
    			}
    		}
    		ls.clear();
    	}
    }
    
    //Recursive approach
    public static int recKnapSack(int W, int[] w, int[] val, int n) {
    	
    	if(W == 0 || n == 0) 
    		return 0;
    	
    	if(w[n-1] > W)  
    		return recKnapSack(W, w, val, n-1); //Skip this element and Goto previous element
    	
    	//Return maximum of 2 cases;
    	//1. nth item included
    	//2. not excluded
    	return Math.max((val[n-1] + recKnapSack(W-w[n-1], w, val, n-1)), 
    			recKnapSack(W, w, val, n-1));
    }
}

