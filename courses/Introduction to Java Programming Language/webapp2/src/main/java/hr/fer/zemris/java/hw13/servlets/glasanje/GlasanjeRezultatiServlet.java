package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.Util.FileUtil;

/**
 * Reads survey results from .txt file and forwards request to
 * glasanjeRez.jsp file with voting results packed up with request.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
@WebServlet(name = "glasanjeRezultatiServlet", urlPatterns = {"/glasanje-rezultati", "/glasanje-rezultati.jsp"})
public class GlasanjeRezultatiServlet extends HttpServlet{

	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

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
		
		List<GlasanjeRezultatiServlet.Band> resultParam = new ArrayList<>();
		
		for(int i = 0; i < results.size(); i++){
			String[] bandInfo = bands.get(i).split("\t");
			String bandName = bandInfo[1];
			String reprSongLink = bandInfo[2];
			int numOfVotes = Integer.parseInt(results.get(i).split("\t")[1]);
			resultParam.add(new Band(bandName, numOfVotes, reprSongLink));
		}
		
		Collections.sort(resultParam);
		
		req.setAttribute("bands", resultParam);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanje/glasanjeRez.jsp").forward(req, resp);
	}
	
	/**
	 * This class is used as JavaBean for each band which participated in survey.
	 * @author Leonardo Kokot
	 * @version 1.0
	 */
	public static class Band implements Comparable<Band> {

		/**
		 * Name of the band.
		 */
		private String name;
		
		/**
		 * Link to representative song of this particular band.
		 */
		private String songRepr;
		
		/**
		 * Number of votes which this band got through survey.
		 */
		private int numOfVotes;
		
		/**
		 * Constructor.
		 * @param name Name to be set.
		 * @param numOfVotes Number of votes.
		 * @param songRep Representative song.
		 */
		private Band(String name, int numOfVotes, String songRep) {
			if(name == null){
				throw new IllegalArgumentException("Name must not be null reference.");
			}
			this.name = name;
			
			if(songRep == null){
				throw new IllegalArgumentException("Representative song String must not be null reference.");
			}
			this.songRepr = songRep;
			
			this.numOfVotes = numOfVotes;
		}
		
		
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return the songRepr
		 */
		public String getSongRepr() {
			return songRepr;
		}

		/**
		 * @return the numOfVotes
		 */
		public int getNumOfVotes() {
			return numOfVotes;
		}

		@Override
		public int compareTo(Band o) {
			return -(this.numOfVotes - o.numOfVotes);
		}
		
	}
}
