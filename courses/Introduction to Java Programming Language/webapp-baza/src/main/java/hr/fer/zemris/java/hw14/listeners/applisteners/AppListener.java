/**
 * 
 */
package hr.fer.zemris.java.hw14.listeners.applisteners;

import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.model.PollOptionModel;

import javax.servlet.annotation.WebListener;

/**
 * {@link ServletContextEvent} which when application is launched
 * creates Connection pool from which connections to apache derby database are obtained.
 * It also checks weather appropriate tables exist in database and if not, creates them.  
 * @author Leonardo Kokot
 * @version 1.0
 */
@WebListener
public class AppListener implements ServletContextListener {
	
	/**
	 * State which indicates that table already exists.
	 */
	private static String tableAlreadyExistSQLState = "X0Y32";

	/**
	 * Pool message.
	 */
	private static String MESSAGE = "Od sljedećih bendova, koji Vam je bend najdraži? "
			+ "Kliknite na link kako biste glasali!";
	
	/**
	 * Title of Poll.
	 */
	private static String TITLE = "Glasanje za omiljeni band:";
	
	/**
	 * Statement which is used for creation of PollOptions table in database.
	 */
	private static String tablePollOptionsCreateStatement = "CREATE TABLE PollOptions"
			+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
			+ "optionTitle VARCHAR(100) NOT NULL, "
			+ "optionLink VARCHAR(150) NOT NULL, "
			+ "pollID BIGINT, "
			+ "votesCount BIGINT, "
			+ "FOREIGN KEY (pollID) REFERENCES Polls(id))";
	
	/**
	 * Statement which is used for creation of create Polls table in database.
	 */
	private static String tablePollsCreateStatement = "CREATE TABLE Polls"
   + "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
   + "title VARCHAR(150) NOT NULL, "
   + "message CLOB(2048) NOT NULL)";
	
	/**
	 * Name of first table used in this web application.
	 */
	private static String tablePolls = "Polls";
	
	/**
	 * Name of second table used in this web application.
	 */
	private static String tablePollOptions = "PollOptions";
	
	/**
	 * Password key.
	 */
	private static String passwordKey = "password";
	
	/**
	 * User key.
	 */
	private static String userKey = "user";
	
	/**
	 * Name key.
	 */
	private static String nameKey = "name";
	
	/**
	 * Port key.
	 */
	private static String portKey = "port";
	
	/**
	 * Host key.
	 */
	private static String hostKey = "host";
	
	/**
	 * File name which contains properties for database used in this application.
	 */
	private static String fileName = "dbsettings.properties";
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
		
		String pathToPropFile = sce.getServletContext().getRealPath("/WEB-INF/"+fileName);

		BufferedInputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(pathToPropFile));
		} catch (FileNotFoundException e) {
		}
		
		Properties prop = new Properties();
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			System.out.println("Exception occured while reading from " + fileName + " file.");
			System.exit(1);
		}
		
		if(!(prop.containsKey(hostKey) 
				&& prop.containsKey(portKey) 
				&& prop.containsKey(nameKey) 
				&& prop.containsKey(userKey)
				&& prop.containsKey(passwordKey))){
			System.exit(1);
		}
		
		
		String dbName = prop.getProperty(nameKey);
		String port = prop.getProperty(portKey);
		String host = prop.getProperty(hostKey);
		String user = prop.getProperty(userKey);
		String password = prop.getProperty(passwordKey);
		
		String connectionURL = "jdbc:derby://"+host+":"+port+"/"+dbName+";user="+user+";password="+password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		//Checking if necessary tables exist and formats them if needed.
		checkTables(cpds);
		
		sce.getServletContext().setAttribute("dbpool", cpds);
		
		
	}
	
	/**
	 * Checks if appropriate tables exist in database, and if necessary creates them.
	 * Tables which are checked for existing are 
	 * POLLS and POLLOPTIONS.
	 * @param cpds Database connection pool.
	 */
	private static void checkTables(ComboPooledDataSource cpds) {
		Connection con = null;
		
		try {
			con = cpds.getConnection();
				
			createTablePolls(con);
			
			createTablePollOptions(con);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	}

	/**
	 * Inserts appropriate poll options to
	 * PollOptions table.
	 * @param con Connection to database.
	 */
	private static void insertPollOptions(Connection con) {
		long pollID = getPoolID(con);
		
		if(pollID == -1){
			throw new RuntimeException("Error while reading pool table.");
		}
		
		List<PollOptionModel> options = createOptions(pollID);
		
		for(PollOptionModel option : options){
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement(
						"INSERT INTO "+tablePollOptions+" (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)", 
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, option.getOptionTitle());
				pst.setString(2, option.getOptionLink());
				pst.setLong(3, option.getPollID());
				pst.setLong(4, option.getVotesCount());

				int numOfAffRows = pst.executeUpdate();
				
				if(numOfAffRows != 1) {
					throw new RuntimeException("Inserting row into " + tablePollOptions + " table "
							+ "did not go as expected.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	/**
	 * Creates poll options, i.e. options which will be available to be voted for as
	 * part of a poll.
	 * @param pollID pollID.
	 * @return List of poll options.
	 */
	private static List<PollOptionModel> createOptions(long pollID) {
		List<PollOptionModel> options = new ArrayList<>();
		
		options.add(new PollOptionModel("The Beatles", "https://www.youtube.com/watch?v=96YQdiMV-Jc", pollID, 0, -1));
		options.add(new PollOptionModel("Red Hot Chili Pepers", "https://www.youtube.com/watch?v=rn_YodiJO6k", pollID, 0, -1));
		options.add(new PollOptionModel("Nirvana", "https://www.youtube.com/watch?v=vabnZ9-ex7o", pollID, 0, -1));
		options.add(new PollOptionModel("The Beach Boys", "https://www.youtube.com/watch?v=Eab_beh07HU", pollID, 0, -1));
		options.add(new PollOptionModel("The Four Seasons", "https://www.youtube.com/watch?v=kYBZqfOZiS4", pollID, 0, -1));
		options.add(new PollOptionModel("The Marcels", "https://www.youtube.com/watch?v=BziGPUGjhIE", pollID, 0, -1));
		options.add(new PollOptionModel("The Everly Brothers", "https://www.youtube.com/watch?v=lTYe9eDqxe8", pollID, 0, -1));

		return options;
	}

	/**
	 * Checks for ID of specific pool.
	 * @param con Connection to database.
	 * @return Long value of pool ID.
	 */
	private static long getPoolID(Connection con) {
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id from "+tablePolls);
			ResultSet rset = pst.executeQuery();
			
			if(rset.next()){
				return rset.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pst != null){
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	/**
	 * Creates poll.
	 * @param con Connection to database.
	 */
	private static void createPoll(Connection con) {
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("INSERT INTO "+tablePolls+" ("
					+"title, message) values (?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, TITLE);
			pst.setString(2, MESSAGE);
			
			int numOfAffRows = pst.executeUpdate();
			
			if(numOfAffRows != 1) {
				throw new RuntimeException("Inserting row into " + tablePolls + " table "
						+ "did not go as expected.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates Polls table.
	 * @param con Connection to database.
	 */
	private static void createTablePolls(Connection con) {
		Statement sta = null;
		try {
			sta = con.createStatement();
			sta.executeUpdate(tablePollsCreateStatement);
			
			//If table already does not exist...
			createPoll(con);
		} catch (SQLException e) {
			if(!e.getSQLState().equals(tableAlreadyExistSQLState)){
				System.out.println("Something went wrong.");
				System.exit(1);
			}
			
			//Check if table is empty...
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement("SELECT id from "+tablePolls);
				ResultSet rset = pst.executeQuery();
				if(!rset.next()){
					
					//If table is empty...
					createPoll(con);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					pst.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates PollOptions table.
	 * @param con Connection to database.
	 */
	private static void createTablePollOptions(Connection con) {
		Statement sta = null;
		try {
			sta = con.createStatement();
			sta.executeUpdate(tablePollOptionsCreateStatement);
			
			//If table already does not exist...
			insertPollOptions(con);
		} catch (SQLException e) {
			if(!e.getSQLState().equals(tableAlreadyExistSQLState)){
				System.out.println("Something went wrong.");
				System.exit(1);
			}
			
			//Check if table is empty...
			PreparedStatement pst = null;
			try {
				pst = con.prepareStatement("SELECT id from "+tablePollOptions);
				ResultSet rset = pst.executeQuery();
				if(!rset.next()){
					
					//If table is empty...
					insertPollOptions(con);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					pst.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			try {
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	

}
