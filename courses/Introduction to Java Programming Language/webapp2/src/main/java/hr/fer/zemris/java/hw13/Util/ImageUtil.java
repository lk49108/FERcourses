/**
 * 
 */
package hr.fer.zemris.java.hw13.Util;

import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * Image utility class.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ImageUtil {

	/**
	 * Creates PNG representation of provided chart and sends it to JSP page,
	 * which {@link OutputStream} is provided as parameter o.
	 * @param chart {@link ChartPanel}.
	 * @param OutputStream o.
	 */
	public static void createAndSendImage(JFreeChart chart, OutputStream o) {
		
		try{
			ChartUtilities.writeChartAsPNG(o, chart, 300, 300);
		} catch(IOException ex) {
			System.out.println("Error while creating PNG image from JFreeChart.");
		}
			
	}
}
