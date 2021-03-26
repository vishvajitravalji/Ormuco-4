import java.util.Scanner;

/*
 * Your goal for this question is to write a program
 * that accepts two lines (x1,x2) and (x3,x4) on the x-axis and returns whether they overlap.
 * As an example, (1,5) and (2,6) overlaps but not (1,5) and (6,8).
 * @author MUSTAFA
 *
 */
public class Question_A {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		// Provide clear instructions to user to get valid input
		// Get coordinates of line 1
		System.out.println("Enter coordinates of first line. Coordinates must be integers and x2 > x1");
		System.out.println("x1:");
		int x1 = scanner.nextInt();			// TO DO: add catch exception incase the input is not integer
		System.out.println("x2:");
		int x2 = scanner.nextInt();
		// Get coordinates of line 2
		System.out.println("Enter coordinates of second line. Coordinates must be integers and x4 > x3");
		System.out.println("x3:");
		int x3 = scanner.nextInt();
		System.out.println("x4:");
		int x4 = scanner.nextInt();	
		
		if (lines_overlap(x1,x2,x3,x4)) {
			System.out.println("Lines overlap");
		}
		else {
			System.out.println("Lines do not overlap");
		}
	}

	private static boolean lines_overlap(int x1, int x2, int x3, int x4) {
		// Check for these cases of overlap
		
		// 1) Both lines are same. i.e.
		// 1st coordinate of Line1 = 1st coordinate of Line 2 
		//  and 2st coordinate of Line1 = 2st coordinate of Line 2
		// OR vice versa (line 2 is inverted line 1)
		
		if ( ( (x1 == x3) && (x2 == x4) ) || ( (x1 == x4) && (x2 == x3) )  ) {
			return true;
		}
		// If the line 2 is smaller than line 1 and sits within L1
		else if ( (x1 <= x3) && (x2 >= x4) ) {
			return true;
		}
		// If 1st coordinate of L1 lies in line 2
		else  if ( x1 >= x3 && x1 < x4 ){
			return true;
		}
		// If 2st coordinate of L1 lies in line 2
		else  if ( x2 > x3 && x2 <= x4 ){
			return true;
		}
		
		// In all other cases return false
		return false;
	}

}
