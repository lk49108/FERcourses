package hr.fer.zemris.java.hw14.servlets.glasanje;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

import hr.fer.zemris.java.hw14.Util.ImageUtil;
import hr.fer.zemris.java.hw14.dao.DAOPollOptionProvider;
import hr.fer.zemris.java.hw14.model.PollOptionModel;

/**
 * Creates PieChart from voting results and sends this chart
 * to appropriate JSP-file.
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebServlet(name = "glasanjeGrafikaServlet", urlPatterns={"/glasanje-grafika"})
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
		
		try {
			long pollID = Long.parseLong(req.getParameter("pollID"));
			
			List<PollOptionModel> results = DAOPollOptionProvider.getDao().getBasicInputList();
			
			List<PollOptionModel> rightResults = results.stream()
					.filter(t -> t.getPollID() == pollID)
					.collect(Collectors.toList());
			
			if(rightResults.isEmpty()){
				throw new IllegalArgumentException();
			}
			
			List<Long> voteCounts = rightResults.stream()
					.map(t -> t.getVotesCount())
					.collect(Collectors.toList());
			
			List<String> optionTitles = rightResults.stream()
					.map(t -> t.getOptionTitle())
					.collect(Collectors.toList());
						
			PieDataset dataset = createDataset(voteCounts, optionTitles);

	        JFreeChart chart = createChart(dataset);

	        resp.setContentType("image/png");

			ServletOutputStream outputStream = resp.getOutputStream();
	        
	        ImageUtil.createAndSendImage(chart, outputStream);		
	        
		} catch (IllegalArgumentException | NullPointerException ex){
			req.setAttribute("error", "Wrong pollID, or there is no provided pollID.");
			req.getRequestDispatcher("/WEB-INF/pages/wrongParameters.jsp").forward(req, resp);
		}
       
		
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
	private PieDataset createDataset(List<Long> votesCount, List<String> optionTitles) {
        DefaultPieDataset result = new DefaultPieDataset();

        try{
        	for(int i = 0; i < votesCount.size(); i++){
    	        result.setValue(optionTitles.get(i), votesCount.get(i));
    		}
        } catch (NumberFormatException ex){
        	System.out.println("Wrong format of some .txt file used in survey.");
        }
        
		return result;
	}
}
