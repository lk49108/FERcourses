package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program which allows user to 
 * write a file with values which are then displayed on
 * chart. Format of file should be as follows:
 * First line -> x-axis description.
 * Second line -> y-axis description.
 * Third line -> (x,y) values pairs. Pairs are separated
 * with space (' ') and x value(which comes first) is separated
 * with comma (',') from y value. User must provide path to this file
 * as command-line argument to allow this program to draw its corresponding
 * chart onto the screen.
 * On top of the chart there is also shown path to file
 * which data is shown on chart.
 * This class extends JFrame and is us used for rendering chart onto the screen.
 * @author Leonardo Kokot 
 * @version 1.0
 */
public class BarChartDemo extends JFrame {
	
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * Constructor which creates instance of {@link BarChartComponent}
	 * and sets how its first appearance to user will look *its siye, positioning
	 * relative to screen etc.). It also calls method {@link #initGUI(BarChart, String)}
	 * which then do all the rest of the job.
	 * @param chart {@link BarChart} instance holding informations about chart which will be
	 * displayed onto the screen.
	 * @param path String holding path to file which chart will be rendered
	 * onto the screen.
	 */
	public BarChartDemo(BarChart chart, String path){
	super();
	setDefaultCloseOperation(
		WindowConstants.DISPOSE_ON_CLOSE		
		);
	setLocation(20, 20);
	setTitle("Chart");
	setSize(700, 700);
	setLocationRelativeTo(null);
	
	initGUI(chart, path);
	
	}
	
	/**
	 * Initializes how {@link BarChartDemo} {@link JFrame} will
	 * look when rendered.
	 * @param chart {@link BarChart} instance holding informations about 
	 * chart which will be displayed onto the screen.
	 * @param path String holding path to file which chart will be rendered
	 * onto the screen.
	 */
	private void initGUI(BarChart chart, String path){
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		BarChartComponent komponenta1 = new BarChartComponent(chart);
		komponenta1.setOpaque(true);
		komponenta1.setBackground(Color.white);
		
		cp.add(komponenta1, BorderLayout.CENTER);
		
		JLabel label = new JLabel(path);
		label.setSize(label.getPreferredSize());
		label.setBackground(Color.white);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		cp.add(label, BorderLayout.NORTH);
	} 
	
	
	
	/**
	 * Main method, program starts its execution from this method.
	 * @param args Command-line arguments, used for obtaining
	 * path(one argument) to file from which informations
	 * about chart are taken and then displayed onto the screen.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1){
			System.err.println("One command-line argument should be provided\n(path to"
					+ " file in which is description of chart in apropriate format).\n"
					+ args.length + " command-line arguments were provided instead.");
			System.exit(1);
		}
		
		try (BufferedReader inputStream = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(
								args[0].trim())))) {
			
			BarChart chart = readFileAndCreateBarChart(inputStream);
			
			
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					BarChartDemo prozor = new BarChartDemo(chart, args[0]);
					prozor.setVisible(true);
				}
				
			});
			
			
		} catch (FileNotFoundException e) {
			System.err.println("File path provided: " + args[0] + " was not found.");
			System.exit(1);
		} catch(Exception ex){
			System.err.println("Wrong format of file: " + args[0]);
			System.exit(1);
		}
			
	}

	/**
	 * Reads file and creates appropriate {@link BarChart} instance.
	 * @param inputStream {@link BufferedReader} which reads file.
	 * @return New {@link BarChart} instance created based on
	 * read data.
	 * @throws IOException
	 */
	private static BarChart readFileAndCreateBarChart(BufferedReader inputStream) throws IOException {
		String xAxisDescription = inputStream.readLine().trim();
		String yAxisDescription = inputStream.readLine().trim();
		String xYValuePairs = inputStream.readLine().trim();
		String yMin = inputStream.readLine().trim();
		String yMax = inputStream.readLine().trim();
		String yDiff = inputStream.readLine().trim();
		
		int yMinValue = parseInt(yMin);
		int yMaxValue = parseInt(yMax);
		int yDiffValue = parseInt(yDiff);
		
		XYValue[] xYValuesArray = findPairs(xYValuePairs);
		
		return new BarChart(Arrays.asList(xYValuesArray),
				xAxisDescription,
				yAxisDescription,
				yMinValue, 
				yMaxValue, 
				yDiffValue);
	}

	/**
	 * Parses {@link String} to int.
	 * @param number String representation of number.
	 * @return Number obtained from parsing String.
	 */
	private static int parseInt(String number) {
		int result = -1;
		try  {
			result = Integer.parseInt(number);
		} catch(NumberFormatException ex){
			System.err.println("Wrong format of " + number + " value.");
			System.exit(1);
		}
		return result;
	}

	/**
	 * Finds pairs of (x,y) value from given string.
	 * @param xYValuePairs String containing (x,y) values.
	 * @return {@link String} array Each element of this array
	 * represents one (x,y) pair.
	 */
	private static XYValue[] findPairs(String xYValuePairs) {
		String[] xYPairsArray = xYValuePairs.split(" ");
		XYValue[] valuesArray = new XYValue[xYPairsArray.length];
		for(int i = 0; i < xYPairsArray.length; i++){
			String[] xYPair = xYPairsArray[i].split(",");
			valuesArray[i] = createPair(xYPair);
		}
		return valuesArray;
	}

	/**
	 * Creates {@link XYValue} instance from
	 * given {@link String} representation of (x,y) value.
	 * @param pair {@link String} representation of (x,y) value.
	 * @return New {@link XYValue} instance.
	 */
	private static XYValue createPair(String[] pair) {
		int x = 0;
		int y = 0;
		try {
			x = Integer.parseInt(pair[0]);
			y = Integer.parseInt(pair[1]);
		} catch (NumberFormatException ex){
			System.err.println("Wrong format of " + pair[0] 
					+ " " + pair[1] + " values pair.");
		}
		return new XYValue(x, y);
	}

}
