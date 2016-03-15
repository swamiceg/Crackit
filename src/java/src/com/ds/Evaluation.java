package com.ds;

import java.util.Stack;

public class Evaluation {
	//It converts the infix expression to postfix expression using 
	//Shunting Yard Algorithm - Operators in Stack
	public String toPostfix(String infixExp) {
		char[] chars = infixExp.toCharArray();
		Stack<Character> operators = new Stack<>();
		StringBuilder postfix = new StringBuilder();
		for(int i = 0; i < chars.length; i++) {
			switch(chars[i]) {
			case ' ':
				break;
			case '(':
				operators.push(chars[i]);
				break;
			case ')':
				while(!operators.isEmpty()) {
					char op = operators.pop();
					if(op == '(') {
						break;
					}
					postfix.append(op);
				}
				break;
			case '+':
			case '-':
			case '*':
			case '/':
				//Check the top to see if its precedence is higher than this operator
				char thisOp = chars[i];
				while(!operators.isEmpty()) {
					//If the top operator has higher precedence than thisOp, then pop
					//and append to postfix
					if(checkPrecedence(operators.peek(), thisOp) >= 0) {
						postfix.append(operators.pop());
					} else {
						break;
					}
				}
				operators.push(thisOp);
				break;
			default: //Should be digit
				postfix.append(chars[i]);
			}
		}
		
		//copy the remaining operators from stack
		while(!operators.isEmpty()) {
			postfix.append(operators.pop());
		}
		
		return postfix.toString();
	}
	
	//Positive a > b
	//negative a < b
	//zero a = b
	private int checkPrecedence(char a, char b) {
		return getPrecedence(a) - getPrecedence(b);
	}
	
	private int getPrecedence(char a) {
		switch(a) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default:
			return 0;
		}
	}
	
	//Here the operands are put into stack for expression evaluation
	//Reverse polish notation
	public int evaluatePostfix(String postfix) {
		char[] chars = postfix.toCharArray();
		Stack<Integer> operands = new Stack<>();
		for(int i = 0; i < chars.length; i++) {
			char thisOp = chars[i];
			switch(thisOp) {
			case '+':
			case '-':
			case '*':
			case '/':
				int c1 = operands.pop();
				int c2 = operands.pop();
				int res = eval(c2, c1, thisOp);
				operands.push(res);
				break;
			default:
				if(Character.isDigit(thisOp)) { //Single digit
					operands.push(Integer.parseInt(new StringBuilder().append(thisOp).toString()));
				} 
				break;
			}
		}
		
		int result = operands.pop();
		if(!operands.isEmpty()) {
			throw new ArithmeticException("Invalid postfix string provided.");
		}
		
		return result;
	}

	private int eval(int a, int b, char operator) {
		switch(operator) {
		case '+':
			return a+b;
		case '-':
			return a-b;
		case '*':
			return a*b;
		case '/':
			if(b == 0) {
				throw new ArithmeticException("Divide by zero exception.");
			}
			return a/b;
		default:
			return 0;
		}
	}

	//Combine the postfix conversion and evaluation into single part
	public int evaluate(String infix) {
		char[] chars = infix.toCharArray();
		Stack<Character> operators = new Stack<>();
		Stack<Integer> operands  = new Stack<>();

		for(int i = 0; i < chars.length; i++) {
			switch(chars[i]) {
				case ' ':
					break;
				case '(':
					operators.push(chars[i]);
					break;
				case ')':
					while(!operators.isEmpty()) {
						char op = operators.pop();
						if(op == '(') {
							break;
						}
						int c1 = operands.pop();
						int c2 = operands.pop();
						int res = eval(c2, c1, op);
						operands.push(res);
					}
					break;
				case '+':
				case '-':
				case '*':
				case '/':
					//Check the top to see if its precedence is higher than this operator
					char thisOp = chars[i];
					while(!operators.isEmpty()) {
						//If the top operator has higher precedence than thisOp, then pop
						//and append to postfix
						if(checkPrecedence(operators.peek(), thisOp) >= 0) {
							//Let's perform the operation
							int c1 = operands.pop();
							int c2 = operands.pop();
							int res = eval(c2, c1, operators.pop());
							operands.push(res);
							break;
						} else {
							break;
						}
					}
					operators.push(thisOp);
					break;
				default: //Should be digit
					if(Character.isDigit(chars[i])) {
						operands.push(new Integer(new StringBuilder().append(chars[i]).toString()));
					}
					break;
			}
		}
		
		//Check the remaining operators from stack
		while(!operators.isEmpty()) {
			int c1 = operands.pop();
			int c2 = operands.pop();
			int res = eval(c2, c1, operators.pop());
			operands.push(res);
			break;
		}
		
		int result = operands.pop();
		if(!operands.isEmpty()) {
			throw new ArithmeticException("Invalid expression provided");
		}
		
		return result;
	}
	
	//Print the greatest element on the immediate right for each element in the array
	//If there are no such elements, print -1
	public void printGreatestOnRight(int[] arr) {
		//Simple solution:
		//which takes O(n2) in worst case when the elements are in descending order
		System.out.println("Simple solution:");
		int nextGreater;
		for(int i = 0; i < arr.length-1; i++) {
			nextGreater = -1;
			for(int j = i+1; j < arr.length; j++) {
				if(arr[j] > arr[i]) {
					nextGreater = arr[j];
					break;
				}
			}
			System.out.printf("%d --> %d\n", arr[i], nextGreater);
		}
		System.out.printf("%d --> %d\n", arr[arr.length-1], -1);
		
		//Efficient solution using stack, which takes O(n)
		System.out.println("Efficient solution(stack):");
		Stack<Integer> stack = new Stack<>();
		stack.push(arr[0]);
		int i = 1;
		while(i < arr.length) {
			int ele = arr[i];
			
			while(!stack.isEmpty() && stack.peek() < ele) {
				int lesser = stack.pop();
				System.out.printf("%d --> %d\n", lesser, ele); //This is the largest element for the popped element
			}
			stack.push(ele); //Find the greatest element for this element
			i++;
		}
		while(!stack.isEmpty()) {
			System.out.printf("%d --> %d\n", stack.pop(), -1);
		}
	}

	public static void main(String[] args) {
		Evaluation eval = new Evaluation();
		
		String infix = "(1+2)*3+5+(6*2)";
		String postfix = eval.toPostfix(infix);
		int result = eval.evaluatePostfix(postfix);
		System.out.println("Infix: " + infix);
		System.out.println("Postfix: " + postfix);
		System.out.println("Result: " + result);
		System.out.println("Result (of single evaluation): " + eval.evaluate(infix));
		
		eval.printGreatestOnRight(new int[]{4, 5, 2, 25});
		eval.printGreatestOnRight(new int[]{5, 4, 3, 2, 1});
	}
}
