package hr.fer.zemris.java.hw13.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import hr.fer.zemris.java.hw13.Util.ImageUtil;

/**
 * Creates Chart containing informations
 * about the "OS-usage" survey. Transforms this chart to PNG image
 * and sends it to appropriate JSP-file.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name="reportImageServlet", urlPatterns={"/reportImage.jsp", "/reportImage"})
public class ReportImage extends HttpServlet {

	/**
	 * Title of chart being generated by this class.
	 */
	private static final String CHART_TITLE = "OS survey";
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {						
        PieDataset dataset = createDataset();

        JFreeChart chart = createChart(dataset);
                
        resp.setContentType("image/png");

		ServletOutputStream outputStream = resp.getOutputStream();
        
        ImageUtil.createAndSendImage(chart, outputStream);		
	}

	/**
	 * Creates dataset for chart.
	 * @return {@link PieDataset} instance as dataset used for chart creation.
	 */
	private  PieDataset createDataset() {
	        DefaultPieDataset result = new DefaultPieDataset();
	        result.setValue("Linux", 29);
	        result.setValue("Mac", 20);
	        result.setValue("Windows", 51);
	        return result;
	        
	 }

	/**
	 * Creates pie Chart.
	 * @param dataset {@link PieDataset} instance, determines what kind of pie chart will be created.
	 * @return {@link JFreeChart} instance, representing created pie chart.
	 */
	private JFreeChart createChart(PieDataset dataset) {
	        JFreeChart chart = ChartFactory.createPieChart3D(CHART_TITLE,     // chart title
	            dataset,        // data
	            true,           // include legend
	            true,
	            false);

	        PiePlot3D plot = (PiePlot3D) chart.getPlot();
	        plot.setStartAngle(290);
	        plot.setDirection(Rotation.CLOCKWISE);
	        plot.setForegroundAlpha(0.5f);
	        return chart;
	        
	    }
	
}