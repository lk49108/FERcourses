package hr.fer.zemris.java.tecaj.hw1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.io.IOException;

/**
 * Program calculates rectangle's area 
 * and perimeter. If program gets valid data from
 * command line arguments, it uses them in its execution.
 * If valid data is not provided as command line arguments, 
 * program asks user again to provide data. 
 * At the end it prints the result of calculated 
 * perimeter and area of corresponding rectangle.
 * @author Leonardo Kokot
 * @version 1.0
 */

public class Rectangle {
	/**
	 * Method through which programs starts with its execution.
	 * @param args Arguments from a command line. 2 or zero arguments 
	 * are expected. If any arguments are provided, first one should be 
	 * rectangle's width and second one rectangle's height.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		double[] dimension = new double[2];
		
		DecimalFormat formatter = new DecimalFormat("#0.0#####");
		
		if (args.length != 2 && args.length != 0){
			System.err.println("Invalid number of arguments was provided.");
			System.exit(1);
		}
		else if(args.length == 2){
			dimension[0] = Double.parseDouble(args[0]);
			dimension[1] = Double.parseDouble(args[1]);
			if(dimension[0] >= 0.0 && dimension[1] >= 0.0){
				System.out.println("You have specified a rectangle with width " + dimension[0] + 
						" and height " + dimension[1] + ".");
				System.out.println("Its area is " + formatter.format(area(dimension[0], dimension[1])) +
						" and its perimeter is " + formatter.format(perimeter(dimension[0], dimension[1])) + ".");
				System.exit(0);
			}
		}
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(System.in)));
				
		int success = 0;
		
		while(true){
			if(success == 0){
			System.out.print("Please provide width: ");
			String readed_width = reader.readLine();
			if(readed_width == null){
				System.err.println("Programs standard input is closed.");
			    System.exit(1);
			}
			readed_width = readed_width.trim();
			if(readed_width.isEmpty()){
				System.out.println("Nothing was given.");
			}
			else if((dimension[0] = Double.parseDouble(readed_width)) >= 0)
				success++;
			else
				System.out.println("Width is negative.");
			}
			else if(success == 1){
				System.out.print("Please provide height: ");
				String readed_height = reader.readLine();
				if(readed_height == null){
					System.err.println("Programs standard input is closed.");
				    System.exit(1);
				}
				readed_height = readed_height.trim();
				if(readed_height.isEmpty()){
					System.out.println("Nothing was given.");
				}
				else if((dimension[1] = Double.parseDouble(readed_height)) >= 0)
					success++;
				else
					System.out.println("Height is negative.");
			}
			else
				break;
		}
		System.out.println("You have specified a rectangle with width " + dimension[0] + 
				" and height " + dimension[1] + ".");
		System.out.println("Its area is " + formatter.format(area(dimension[0], dimension[1])) +
				" and its perimeter is " + formatter.format(perimeter(dimension[0], dimension[1])) + ".");
		
	}
	
	/**
	 * Calculates area of a rectangle from given 
	 * width and height.
	 * @param width Width of the rectangle.
	 * @param height Height of the rectangle.
	 * @return Area of the rectangle. 
	 */
	private static double area(double width, double height){
		return width*height;
	}
	/**
	 * Calculates perimeter of a rectangle from given
	 * width and height.
	 * @param width Width of the rectangle.
	 * @param height Height of the rectangle.
	 * @return Perimeter of the rectangle.
	 */
	private static double perimeter(double width, double height){
		return 2*(width + height);
	}

}
