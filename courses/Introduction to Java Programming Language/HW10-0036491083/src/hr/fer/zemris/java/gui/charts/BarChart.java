/**
 * 
 */
package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * This class through its constructor
 * gets {@link XYValue} objects {@link List},
 * description of x-axis (as a {@link String}), 
 * description of y-axis (as a {@link String}),
 * minimum y-value shown on chart, maximum
 * y-value shown on chart and difference between 
 * two y's showed on a chart. If yMax - yMin
 * do not share {@link #yDiff}, i.e. if {@link #yMax} - {@link #yMin}
 * < {@link #yDiff} then, for {@link #yDiff} is taken 
 * whole number which equals {@link #yMax} - {@link #yMin}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class BarChart {

	/**
	 * Instance variable of type {@link List}
	 * which holds list of {@link XYValue} instances.
	 */
	private List<XYValue> listOfXYValues;
	
	/**
	 * XAxis chart description.
	 */
	private String xAxisDescription;
	
	/**
	 * YAxis chart description.
	 */
	private String yAxisDescription;
	
	/**
	 * Minimum y value shown on chart.
	 */
	private int yMin;
	
	/**
	 * Maximum y value shown on chart.
	 */
	private int yMax;
	
	/**
	 * Distance between two y-axis values
	 * shown on chart.
	 */
	private int yDiff;
	
	/**
	 * Constructor which creates instance of {@link BarChart}
	 * and initializes all of its instance variables.
	 * @param listOfXYValues Value to which {@link #listOfXYValues} is set to.
	 * @param xAxisDescription Value to which {@link #xAxisDescription} is set to.
	 * @param yAxisDescription Value to which {@link #yAxisDescription} is set to.
	 * @param yMin Value to which {@link #yMin} is set to.
	 * @param yMax Value to which {@link #yMax} is set to.
	 * @param yDiff Value to which {@link #yDiff} is set to.
	 * @throws IllegalArgumentException if null reference for parameters listOfXYValues,
	 * xAxisDescription and yAxisDescription provided.
	 */
	public BarChart(List<XYValue> listOfXYValues, String xAxisDescription, String yAxisDescription, int yMin, int yMax,
			int yDiff) {
		if(listOfXYValues == null){
			throw new IllegalArgumentException("List of x-y values must not be null reference.");
		}
		if(xAxisDescription == null || yAxisDescription == null){
			throw new IllegalArgumentException("x-axis desciption and y-axis description must not be null reference.");
		}
		if(yMax - yMin < yDiff){
			yDiff = yMax - yMin;
		}
		this.listOfXYValues = listOfXYValues;
		this.xAxisDescription = xAxisDescription;
		this.yAxisDescription = yAxisDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDiff = yDiff;
	}

	/**
	 * Getter for {@link #listOfXYValues} instance variable.
	 * @return {@link #listOfXYValues} instance variable.
	 */
	public List<XYValue> getListOfXYValues() {
		return listOfXYValues;
	}

	/**
	 * Setter for {@link #listOfXYValues} instance variable.
	 * @param listOfXYValues value to which {@link #listOfXYValues} instance variable is set to.
	 * @throws IllegalArgumentException if null reference provided.
	 */
	public void setListOfXYValues(List<XYValue> listOfXYValues) {
		if(listOfXYValues == null){
			throw new IllegalArgumentException("List of x-y values must not be null reference.");
		}
		this.listOfXYValues = listOfXYValues;
	}

	/**
	 * Getter for {@link #listOfXYValues} instance variable.
	 * @return {@link #listOfXYValues} instance variable.
	 */
	public String getxAxisDescription() {
		return xAxisDescription;
	}

	/**
	 * Setter for {@link #xAxisDescription} instance variable.
	 * @param xAxisDescription value to which {@link #xAxisDescription} instance variable is set to.
	 * @throws IllegalArgumentException if null reference provided.
	 */
	public void setxAxisDescription(String xAxisDescription) {
		if(xAxisDescription == null){
			throw new IllegalArgumentException("x-axis desciption must not be null reference.");
		}
		this.xAxisDescription = xAxisDescription;
	}

	/**
	 * Getter for {@link #listOfXYValues} instance variable.
	 * @return {@link #listOfXYValues} instance variable.
	 */
	public String getyAxisDescription() {
		return yAxisDescription;
	}

	/**
	 * Setter for {@link #yAxisDescription} instance variable.
	 * @param yAxisDescription value to which {@link #yAxisDescription} instance variable is set to.
	 * @throws IllegalArgumentException if null reference provided.
	 */
	public void setyAxisDescription(String yAxisDescription) {
		if(yAxisDescription == null){
			throw new IllegalArgumentException("y-axis description must not be null reference.");
		}
		this.yAxisDescription = yAxisDescription;
	}

	/**
	 * Getter for {@link #yMin} instance variable.
	 * @return {@link #yMin} instance variable.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Setter for {@link #yMin} instance variable.
	 * @param yMin value to which {@link #yMin} instance variable is set to.
	 */
	public void setyMin(int yMin) {
		this.yMin = yMin;
	}

	/**
	 * Getter for {@link #yMax} instance variable.
	 * @return {@link #yMax} instance variable.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Setter for {@link #yMax} instance variable.
	 * @param yMax value to which {@link #yMax} instance variable is set to.
	 */
	public void setyMax(int yMax) {
		this.yMax = yMax;
	}

	/**
	 * Getter for {@link #yDiff} instance variable.
	 * @return {@link #yDiff} instance variable.
	 */
	public int getyDiff() {
		return yDiff;
	}

	/**
	 * Setter for {@link #yDiff} instance variable.
	 * @param yDiff value to which {@link #yDiff} instance variable is set to.
	 */
	public void setyDiff(int yDiff) {
		this.yDiff = yDiff;
	}
	
	

}
