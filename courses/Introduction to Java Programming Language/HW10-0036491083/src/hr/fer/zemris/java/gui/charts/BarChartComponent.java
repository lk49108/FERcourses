package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * This class extends {@link JComponent} class.
 * It got one constructor which accepts reference to {@link BarChart} instance
 * and stores it to appropriate instance variable. Instance of this
 * class shows provided chart on screen. All methods used in
 * {@link #paintComponent(Graphics)} method save {@link Graphics} color used
 * for drawing "things" on component.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class BarChartComponent extends JComponent {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Height of triangles placed on top of x-axis and y-axis.
	 */
	private final static int heightOfTriangle = 6;

	/**
	 * Constant space between description values written next to
	 * y-axis and that same axis.
	 */
	private final static int yAxisValuesChartGap = 5;
	
	/**
	 * Constant space between description of y-axis and 
	 * values written next to y-axis.
	 */
	private final static int yAxisDescriptorValuesGap = 5;

	/**
	 * Constant space between description values written next to
	 * x-axis and that same axis.
	 */
	private final static int xAxisValuesChartGap = 5;
	
	/**
	 * Constant space between description of x-axis and 
	 * values written next to x-axis.
	 */
	private final static int xAxisDescriptorValuesGap = 5;
	
	/**
	 * Constant space between top of x-axis and right end of this {@link JComponent},
	 * not including {@link Insets}.
	 */
	private final static int xAxisEndToEdgeGap = 5;
	
	/**
	 * Constant space between top of y-axis and top of this {@link JComponent},
	 * not including {@link Insets}.
	 */
	private final static int yAxisEndToEdgeGap = 5;
	
	/**
	 * Color used for rendering chart columns.
	 */
	private final static Color colorOfChartColumns = Color.ORANGE;
	
	/**
	 * Color used for rendering triangles on top of axis.
	 */
	private final static Color triangleOnTopOfAxisColor = Color.DARK_GRAY;
	
	/**
	 * Color used for drawing numbers next to both, y-axis and x-axis.
	 */
	private final static Color axisNumbersAndLinesColor = Color.DARK_GRAY;
	
	/**
	 * Color used for drawinf both, x-axis and y-axis description.
	 */
	private final static Color descriptionColor = Color.GRAY;
	
	/**
	 * This instance variable is used for saving color when specific {@link Color}
	 * is used in some method for drawing elements of this {@link BarChartComponent} instance.
	 * When method finish its job, it takes saved {@link Color} instance
	 * from this variable and sets this {@link Color} back to {@link Graphics}
	 * instance.
	 */
	private Color saveColor;
	
	/**
	 * Reference to {@link BarChart} instance which data is shown on
	 * chart. Provided through constructor.
	 */
	private BarChart barChart;
	
	/**
	 * Constructor which creates instance of {@link BarChartComponent}
	 * instance and initializes {@link #barChart} instance variable.
	 * @param barChart {@link BarChart} reference which is joined to 
	 * {@link #barChart} instance variable.
	 * @throws IllegalArgumentException if provided parameter is null reference. 
	 */
	public BarChartComponent(BarChart barChart) {
		if(barChart == null){
			throw new IllegalArgumentException("Bar chart should not be null reference.");
		}
		this.barChart = barChart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Insets ins = getInsets();
		Dimension dim = getSize();
				
		drawBackground(g, ins, dim);
		
		ins.right += xAxisEndToEdgeGap;
		ins.top += yAxisEndToEdgeGap;
		
		ins.left += drawYAxisDescriptor(g, dim, ins) + yAxisDescriptorValuesGap;
		ins.bottom += drawXAxisDescriptor(g, dim, ins) + xAxisDescriptorValuesGap;
		
		ins.bottom += drawXAxisNumbersAndLines(g, dim, ins) + xAxisValuesChartGap;
		ins.left += drawYAxisNumbersAndLines(g, dim, ins) + yAxisValuesChartGap;
		
		putTriangleOnTopOfYAxis(ins, g);
		putTriangleOnTopOfXAxis(dim, ins, g);
			
		drawChartColumns(dim, ins, g);

	}

	/**
	 * Draws rectangular which is used as a background for
	 * chart. Color used in rendering rectangular is color which
	 * is set to be background color. This method also saves 
	 * {@link Graphics} color.
	 * @param g {@link Graphics} instance used to draw rectangular on
	 * {@link BarChartComponent}.
	 * @param ins Insects of this {@link BarChartComponent} instance.
	 * @param dim {@link Dimension} instance which holds information about current
	 * dimension of {@link BarChartComponent} on which rectangular is drawn on.
	 */
	private void drawBackground(Graphics g, Insets ins, Dimension dim) {
		saveAndObtainColor(g, getBackground());

		g.fillRect(ins.left,
				ins.right,
				dim.width - ins.left - ins.right,
				dim.height - ins.bottom - ins.top);
		
		saveAndObtainColor(g, this.saveColor);
	}

	
	/**
	 * Draws triangle on top of x-axis filled with color defined in 
	 * {@link #triangleOnTopOfAxisColor} variable. This method also saves color 
	 * of {@link Graphics} instance provided.
	 * @param dim {@link Dimension} instance holding informations about dimension of
	 * this {@link BarChartComponent} component.
	 * @param ins {@link Insets} instance holding informations about {@link BarChartComponent}
	 * insets.
	 * @param g {@link Graphics} instance used for drawing triangle.
	 */
	private void putTriangleOnTopOfXAxis(Dimension dim, Insets ins, Graphics g) {
		saveAndObtainColor(g, triangleOnTopOfAxisColor);
		
		FontMetrics fm = g.getFontMetrics();
		
		int difference = fm.getAscent() + fm.getAscent()/2;
		int n = 3;
		int[] x = new int[n];
		int[] y = new int[n];
		x[0] = dim.width - ins.right + xAxisEndToEdgeGap;
		y[0] = dim.height - ins.bottom - difference;
		x[1] = x[0] - heightOfTriangle;
		y[1] = y[0] + (int)(heightOfTriangle/Math.sqrt(3));
		x[2] = x[1];
		y[2] = y[0] - (int)(heightOfTriangle/Math.sqrt(3));
		
		g.fillPolygon(x, y, 3);
		
		saveAndObtainColor(g, this.saveColor);
	}
	
	/**
	 * Method used for saving and obtaining color from {@link #saveColor}
	 * instance variable.
	 * @param g Graphics from which old {@link Color} is taken, stored in
	 * {@link #saveColor} instance variable and then new {@link Color} is
	 * set to this {@link Graphics} instance. 
	 * @param next New {@link Color} which is set to {@link Graphics}
	 * instance provided by parameter p.
	 */
	private void saveAndObtainColor(Graphics g, Color next){
		Color c = g.getColor();
		g.setColor(next);
		this.saveColor = c;
	}
	
	/**
	 * Draws triangle on top of y-axis filled with color defined in 
	 * {@link #triangleOnTopOfAxisColor} variable. This method also saves color 
	 * of {@link Graphics} instance provided.
	 * @param ins {@link Insets} instance holding informations about {@link BarChartComponent}
	 * insets.
	 * @param g {@link Graphics} instance used for drawing triangle.
	 */
	private void putTriangleOnTopOfYAxis(Insets ins, Graphics g) {
		saveAndObtainColor(g, triangleOnTopOfAxisColor);
		
		int n = 3;
		int[] x = new int[n];
		int[] y = new int[n];
		x[0] = ins.left;
		y[0] = ins.top;
		x[1] = (int) (ins.left + heightOfTriangle/Math.sqrt(3));
		y[1] = y[0] + heightOfTriangle;
		x[2] = (int) (ins.left - heightOfTriangle/Math.sqrt(3));
		y[2] = y[1];
		
		g.fillPolygon(x, y, 3);

		saveAndObtainColor(g, this.saveColor);
	}

	/**
	 * Draws numbers under x-axis with color defined in {@link #axisNumbersAndLinesColor}
	 * variable. It also draws vertical lines positioned on background of this
	 * {@link BarChartComponent} with color defined in {@link #axisNumbersAndLinesColor}.
	 * @param g {@link Graphics} instance used for drawing numbers next to x-axis.
	 * @param dim Dimension instance holding informations about dimensions of
	 * this {@link BarChartComponent}.
	 * @param ins {@link Insets} instance holding informations about insets of
	 * this {@link BarChartComponent} instance.
	 * @return Value used for setting bottom inset of this {@link BarChartComponent} instance.
	 */
	private int drawXAxisNumbersAndLines(Graphics g, Dimension dim, Insets ins) {
		saveAndObtainColor(g, axisNumbersAndLinesColor);

		int numOfSteps = barChart.getListOfXYValues().size();
		
		FontMetrics fm = g.getFontMetrics();
		
		int firstLinePos = findSpaceForYAxisNumbers(barChart.getyMin(),
				barChart.getyMax(), fm) + xAxisValuesChartGap;
		int xStep = (dim.width - ins.left - ins.right - firstLinePos)/numOfSteps;
		int xBegin = ins.left + firstLinePos + xStep/2;
		
		int height = fm.getAscent();
		
		//Draws first vertical line
		g.drawLine(xBegin - xStep/2,
					dim.height - ins.bottom - height*2, 
					xBegin - xStep / 2,
					ins.top);
		
		for(int i = 0; i < numOfSteps; i++){
			g.drawLine(xBegin + xStep/2,
					dim.height - ins.bottom - height*2, 
					xBegin + xStep / 2,
					ins.top);
			
			g.drawString(Integer.toString(i + 1),
					xBegin,
					dim.height - ins.bottom - height);
			xBegin += xStep;
		}
		
		saveAndObtainColor(g, this.saveColor);
		
		return fm.getAscent();
	}

	/**
	 * Defines width which will be taken by biggest number written next to y-axis.
	 * This information is used in positioning those y-axis numbers.
	 * @param minYAxis Minimum number drawn next to y-axis.
	 * @param maxYAxis Maximum number drawn next to y-axis.
	 * @param fm {@link FontMetrics} used by {@link Graphics} instance which draws
	 * "things" on this {@link BarChartComponent}.
	 * @return
	 */
	private int findSpaceForYAxisNumbers(int minYAxis, int maxYAxis, 
			FontMetrics fm) {
		int dim1 = fm.stringWidth(Integer.toString(minYAxis));
		int dim2 = fm.stringWidth(Integer.toString(maxYAxis));
		return dim1 > dim2 ? dim1 : dim2;
	}

	/**
	 * Draws numbers next to y-axis with color defined in {@link #axisNumbersAndLinesColor}
	 * variable. It also draws horizontal lines positioned on background of this
	 * {@link BarChartComponent} with color defined in {@link #axisNumbersAndLinesColor}.
	 * @param g {@link Graphics} instance used for drawing numbers next to y-axis.
	 * @param dim Dimension instance holding informations about dimensions of
	 * this {@link BarChartComponent}.
	 * @param ins {@link Insets} instance holding informations about insets of
	 * this {@link BarChartComponent} instance.
	 * @return Value used for setting left inset of this {@link BarChartComponent} instance.
	 */
	private int drawYAxisNumbersAndLines(Graphics g, Dimension dim, Insets ins) {
		saveAndObtainColor(g, axisNumbersAndLinesColor);
		
		FontMetrics fm = g.getFontMetrics();
		
		int minYAxis = barChart.getyMin();
		int maxYAxis = barChart.getyMax();
		int diffYAxis = barChart.getyDiff();
		int widthMax = findSpaceForYAxisNumbers(minYAxis, maxYAxis, fm);
		int yPosition = dim.height - ins.bottom - fm.getAscent();
		int numOfSteps = (maxYAxis - minYAxis) / diffYAxis + 1;
		int yDiff = (dim.height - ins.top - ins.bottom) / numOfSteps;
		
		String toBePrinted = null;
		int lineNumbDiff = fm.getAscent()/2;
		
		for(int i = 0; i < numOfSteps; i++){
			toBePrinted = Integer.toString(minYAxis);
			
			g.drawLine(dim.width - ins.right,
					yPosition - lineNumbDiff, 
					ins.left + widthMax,
					yPosition - lineNumbDiff);
			
			g.drawString(toBePrinted,
					ins.left + (widthMax - fm.stringWidth(toBePrinted)),
					yPosition);
			minYAxis += diffYAxis;
			yPosition -= yDiff;
		}
		
		saveAndObtainColor(g, this.saveColor);
		
		return widthMax;
	}
	
	/**
	 * Draws chart columns filled with {@link Color}
	 * defined in {@link #colorOfChartColumns}.
	 * @param dim Dimension instance holding informations about dimensions of
	 * this {@link BarChartComponent}.
	 * @param ins {@link Insets} instance holding informations about insets of
	 * this {@link BarChartComponent} instance.	 
	 * @param g {@link Graphics} instance used for drawing chart columns.
	 */
	private void drawChartColumns(Dimension dim, Insets ins, Graphics g) {
		saveAndObtainColor(g, colorOfChartColumns);
		
		FontMetrics fm = g.getFontMetrics();
		int lineNumbDiff = fm.getAscent()/2;
		
		int numOfColumns = barChart.getListOfXYValues().size();
		int widthOfColumn = (dim.width - ins.left - ins.right)/numOfColumns;
		int maxYAxis = barChart.getyMax();
		int minYAxis = barChart.getyMin();
		int diffYAxis = barChart.getyDiff();
		int yPosition = dim.height - ins.bottom - fm.getAscent();
		int numOfParts = (maxYAxis - minYAxis) / diffYAxis + 1;
		int yDiff = (dim.height - ins.top - ins.bottom) / numOfParts;
		
		List<XYValue> values = barChart.getListOfXYValues();
		
		for(int i = 0; i < numOfColumns; i++){
				g.fill3DRect(ins.left + i * widthOfColumn,
						yPosition - values.get(i).getY() * yDiff / diffYAxis - lineNumbDiff,
						widthOfColumn,
						values.get(i).getY() * yDiff / diffYAxis,
						true
						);
		}
		
		saveAndObtainColor(g, this.saveColor);
	}


	/**
	 * Draws x-axis description on bottom of this {@link BarChartComponent}.
	 * Color used for drawing description is color stored in 
	 * {@link #descriptionColor} variable.
	 * @param g {@link Graphics} instance used for drawing x-axis description.
	 * @param dim Dimension instance holding informations about dimensions of
	 * this {@link BarChartComponent}.
	 * @param ins {@link Insets} instance holding informations about insets of
	 * this {@link BarChartComponent} instance.
	 * @return Value used for setting bottom inset of this {@link BarChartComponent}.
	 */
	private int drawXAxisDescriptor(Graphics g, Dimension dim, Insets ins) {
		saveAndObtainColor(g, descriptionColor);
		
		FontMetrics fm = g.getFontMetrics();
		
		int width = fm.stringWidth(barChart.getxAxisDescription());
		int height = fm.getAscent();
		
		g.drawString(barChart.getxAxisDescription(),
				ins.left + (dim.width - ins.left - ins.right - width)/2
				, dim.height - ins.bottom - height);
		
		saveAndObtainColor(g, this.saveColor);

		return height;
	}

	/**
	 * Draws y-axis description on left side of this {@link BarChartComponent}.
	 * Color used for drawing description is color stored in 
	 * {@link #descriptionColor} variable.
	 * @param g {@link Graphics} instance used for drawing numbers next to y-axis.
	 * @param dim Dimension instance holding informations about dimensions of
	 * this {@link BarChartComponent}.
	 * @param ins {@link Insets} instance holding informations about insets of
	 * this {@link BarChartComponent} instance.
	 * @return Value used for setting left inset of this {@link BarChartComponent}.
	 */
	private int drawYAxisDescriptor(Graphics g, Dimension dim, Insets ins) {
	    saveAndObtainColor(g, descriptionColor);

		FontMetrics fm = g.getFontMetrics();
		
		int width = fm.stringWidth(barChart.getyAxisDescription());
		int height = fm.getAscent();
		
		Graphics2D g2d = (Graphics2D) g;
        AffineTransform defaultAt = g2d.getTransform();
         
        AffineTransform at = new AffineTransform();
        at.rotate(- Math.PI / 2);
        g2d.setTransform(at);
        g2d.drawString(barChart.getyAxisDescription(),
        		-ins.top - (dim.height -ins.top - ins.bottom + width)/2, 
        		height + ins.left);
         
        g2d.setTransform(defaultAt);
         
        saveAndObtainColor(g2d, this.saveColor);
        
        return height; 
	}
}
