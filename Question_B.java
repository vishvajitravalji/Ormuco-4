import java.util.Scanner;

/*
 * The goal of this question is to write a software library that accepts 2 version string as input
 * and returns whether one is greater than, equal, or less than the other.
 * As an example: “1.2” is greater than “1.1”. Please provide all test cases you could think of.
 */
public class Question_B {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		// Get String 1
		System.out.println("String 1:");
		String s1 = scanner.nextLine();
		System.out.println("Strng 2:");
		String s2 = scanner.nextLine();
		
		parser(s1, s2);
	}
	
	public static String parser(String A, String B) {
		/* 
		 * NOTE: I am using the Java DOUBLE library.
		 * If the java float library was not to be used. I would create a 
		 * String Tokenizer. Parse each token of the string Get the acsii value
		 * and compare the two strings.
		 * 
		 *  The string parser would be more accurate as the code below is 
		 *  limited by the 64 bit range of Double. 
		 */
		double a = Double.parseDouble(A);
		
		double b = Double.parseDouble(B);
		
		if ( a == b) {
			System.out.println( a + " is equal to " + b);
		} 
		else if ( a > b) {
			System.out.println( a + " is greater to " + b);
		} 
		else {
			System.out.println( a + " is less to " + b);
		}
		return B;
		
	}
}
