package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

import hr.fer.zemris.java.hw13.Util.FileUtil;
import hr.fer.zemris.java.hw13.Util.ImageUtil;

/**
 * Creates PieChart from voting results and sends this chart
 * to appropriate JSP-file.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanjeGrafikaServlet", urlPatterns={"/glasanje-grafika", "/glasanje-grafika.jsp"})
public class GlasanjeGrafika extends HttpServlet {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Chart title.
	 */
	private static final String CHART_TITLE = "Band-voting results";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		
		String fileNameBand = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		
		Path file = Paths.get(fileName);
		
		Path fileBand = Paths.get(fileNameBand);
		
		if(!Files.exists(file)){
			FileUtil.createVoteResultsFile(file);
		}
		
		List<String> results = FileUtil.readFileRows(file);
		
		List<String> bands = FileUtil.readFileRows(fileBand);
       
		PieDataset dataset = createDataset(results, bands);

        JFreeChart chart = createChart(dataset);

        resp.setContentType("image/png");

		ServletOutputStream outputStream = resp.getOutputStream();
        
        ImageUtil.createAndSendImage(chart, outputStream);		
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

	/**
	 * Creates dataset for chart.
	 * @return {@link PieDataset} instance as dataset used for chart creation.
	 */
	private PieDataset createDataset(List<String> results, List<String> bands) {
        DefaultPieDataset result = new DefaultPieDataset();

        try{
        	for(int i = 0; i < FileUtil.NUM_OF_BANDS; i++){
    	        result.setValue(bands.get(i).split("\t")[1], Integer.parseInt(results.get(i).split("\t")[1]));
    		}
        } catch (NumberFormatException ex){
        	System.out.println("Wrong format of some .txt file used in survey.");
        }
        
		return result;
	}
}
