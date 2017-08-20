package hr.fer.zemris.java.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;
import hr.fer.zemris.java.graphics.shapes.Ellipse;
import hr.fer.zemris.java.graphics.shapes.GeometricShape;
import hr.fer.zemris.java.graphics.shapes.Rectangle;
import hr.fer.zemris.java.graphics.shapes.Square;
import hr.fer.zemris.java.graphics.views.RasterView;
import hr.fer.zemris.java.graphics.views.SimpleRasterView;

/**
 * This class is used for manipulating
 * with Geometric shapes.
 * One or two command line arguments are accepted in this program.
 * If one argument N is provided, then raster is initialized with a size of
 * N*N pixels. After that, one integer should be provided.
 * It represents how many geometric shapes will be created and on raster(Switching flipping mode is also 
 * considered as a creation of new geometric shape). After that integer is provided user should provide 
 * that number of arguments (each argument describes one geometric shape, i.e. Circle 4 5 2 will create circle
 * with a center at (x,y) = (4,5) and its radius set to 2, FLIP would mean to switch flipping mode...). Maximum 7 arguments are allowed. 
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class Demo {

	/**
	 * This is a main method from which program starts its execution.
	 * @param args Command line arguments, described in JavaDoc for this class. 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		BWRaster raster = null;
		
		if(args.length == 1){
			try{
				isNumber(args[0].trim());
			} catch (NumberFormatException ex) {
				System.err.println("Parameter provided is not an integer.");
				System.exit(1);
			}
			raster = new BWRasterMem(Integer.parseInt(args[0]), Integer.parseInt(args[0]));
		}
		else if(args.length == 2){
			try{
				isNumber(args[0].trim());
				isNumber(args[1].trim());
			} catch (NumberFormatException ex) {
				System.err.println("Parameters provided are not integers.");
				System.exit(1);
			}
			raster = new BWRasterMem(Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()));		}
		else{
			System.err.println("Wrong number of parameters. There should be two Integer type parameters.");
			System.exit(1);
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String numbOfLines = reader.readLine().trim();
		
		try{
			isNumber(numbOfLines);
			if(Integer.parseInt(numbOfLines) < 1){
				throw new IllegalArgumentException();
			}
		} catch (NumberFormatException ex) {
			System.err.println("Parameter provided is not an integer.");
			System.exit(1);
		} catch (IllegalArgumentException ex){
			System.err.println("Parameter provided needs to be greater or equal 1.");
			System.exit(1);
		}
		
		GeometricShape[] shapes = null;
		if(Integer.parseInt(numbOfLines) < 7){
			shapes = new GeometricShape[Integer.parseInt(numbOfLines)];
			for(int i = 0; i < Integer.parseInt(numbOfLines); i++){
				try{
				shapes[i] = nextShape(reader);
				} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex){
					System.err.println("Wrong input.");
					System.exit(1);
				}
			}
		}
		else{
			shapes = new GeometricShape[7];
			for(int i = 0; i < 7; i++){
				try{
				shapes[i] = nextShape(reader);
				} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex){
					System.err.println("Wrong input.");
					System.exit(1);
				}
			}
		}
		
		int checker = 0;
		
		if(Integer.parseInt(numbOfLines) < 7){
			for(int i = 0; i < Integer.parseInt(numbOfLines); i++){
				if(shapes[i] == null && checker == 0){
					raster.enableFlipMode();
					checker = 1;
				}
				else if(shapes[i] == null && checker == 1){
					raster.enableFlipMode();
					checker = 0;
				}
				else{
					shapes[i].draw(raster);
				}
			}
		}
		else{
			for(int i = 0; i < 7; i++){
				if(shapes[i] == null && checker == 0){
					raster.enableFlipMode();
					checker = 1;
				}
				else if(shapes[i] == null && checker == 1){
					raster.enableFlipMode();
					checker = 0;
				}
				else{
					shapes[i].draw(raster);
				}
			}
		}
		
		RasterView view = new SimpleRasterView();
		
		view.produce(raster);
		
	}
	
	
	/**
	 * This method is used for checking if number is an integer.
	 * If it is not this method throws an exception.
	 * @param string String which is being checked for being an integer.
	 * @throws NumberFormatException if string does not represent an integer.
	 */
	private static void isNumber(String string){
		try{
			Integer.parseInt(string);
		} catch (NumberFormatException ex){
			throw new NumberFormatException("Number is not an integer.");
		}
	}
	
	/**
	 * This method is used for decoding commands obtained from input and creation of
	 * appropriate GeometricShape instance which is returned. 
	 * @param reader reference to BufferedReader instance which is used for reading
	 * commands from input. 
	 * @return Geometric shape instance that is newly created in this method.
	 * Null is returned if command is FLIP.
	 * @throws IOException 
	 * @throws IllegalArgumentException, ArrayIndexOutOfBoundsException if read command is not legal(invalid).
	 * 
	 */
	private static GeometricShape nextShape(BufferedReader reader) throws IOException {
		String[] paramsForShape;
		String shapeLine = reader.readLine();
		paramsForShape = getParams(shapeLine);
		if(paramsForShape.length == 0){
			throw new IllegalArgumentException("Empty string was inputed.");
		}
		if(paramsForShape[0].toUpperCase().equals("FLIP")){
			if(paramsForShape.length != 1){
				throw new IllegalArgumentException("Empty string was inputed.");
			}
			return null;
		}
		else if(paramsForShape[0].toUpperCase().equals("RECTANGLE")){
			try{
				return new Rectangle(Integer.parseInt(paramsForShape[1]), Integer.parseInt(paramsForShape[2]), Integer.parseInt(paramsForShape[3]), Integer.parseInt(paramsForShape[4]));
				} catch (ArrayIndexOutOfBoundsException ex) {
					throw new ArrayIndexOutOfBoundsException("Wrong input.");
				}
		}
		else if(paramsForShape[0].toUpperCase().equals("SQUARE")){
			try{
				return new Square(Integer.parseInt(paramsForShape[1]), Integer.parseInt(paramsForShape[2]), Integer.parseInt(paramsForShape[3]));
			} catch (ArrayIndexOutOfBoundsException ex) {
				throw new ArrayIndexOutOfBoundsException("Wrong input.");
			}
		}
		else if(paramsForShape[0].toUpperCase().equals("ELLIPSE")){
			try{
				return new Ellipse(Integer.parseInt(paramsForShape[1]), Integer.parseInt(paramsForShape[2]), Integer.parseInt(paramsForShape[3]), Integer.parseInt(paramsForShape[4]));
			} catch (ArrayIndexOutOfBoundsException ex) {
				throw new ArrayIndexOutOfBoundsException("Wrong input.");
			}
		}
		else if(paramsForShape[0].toUpperCase().equals("CIRCLE")){
			try{
				return new Circle(Integer.parseInt(paramsForShape[1]), Integer.parseInt(paramsForShape[2]), Integer.parseInt(paramsForShape[3]));
			} catch (ArrayIndexOutOfBoundsException ex) {
				throw new ArrayIndexOutOfBoundsException("Wrong input.");
			}
		}
		else{
			throw new IllegalArgumentException("Wrong input.");
		}
    }

	/**
	 * This method is used for getting parameters of command 
	 * from a string representing that same command.
	 * @param shapeLine String which contains string representation of a command.
	 * @return String array containing parameters/parts of a command.
	 */
	private static String[] getParams(String shapeLine){
		String[] shapes = shapeLine.split(" ");
		for(int i = 0; i < shapes.length; i++){
			shapes[i] = shapes[i].trim();
		}
		String[] shapesEnd = new String[shapes.length];
		for(int i = 0, j = 0; i < shapes.length; i++){
			if(!shapes[i].isEmpty()){
				shapesEnd[j] = shapes[i];
				j++;
			}
		}
		return shapesEnd;
	}
}
