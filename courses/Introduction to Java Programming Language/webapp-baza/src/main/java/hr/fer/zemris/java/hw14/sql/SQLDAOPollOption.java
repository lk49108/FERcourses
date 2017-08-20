package hr.fer.zemris.java.hw14.sql;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.PollOptionModel;
import hr.fer.zemris.java.hw14.servlets.glasanje.IncrementElems;

/**
 * Implements {@link DAO} interface and implements all methods so they
 * suit best for accessing table in database which contains available poll options.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SQLDAOPollOption implements DAO {
	
	/**
	 * String instance used as a part of access to database.
	 */
	private static final String getVoteCounterStringFirst = "SELECT "; 

	/**
	 * String instance used as a part of access to database.
	 */
	private static final String getVoteCounterStringSecond = " from PollOptions where id = ?";
	
	/**
	 * String instance used as a part of access to database, used for updating elements.
	 */
	private static final String incrementVoteCounterStringFirst = "UPDATE PollOptions set ";  
	
	/**
	 * String instance used as a part of access to database.
	 */
	private static final String incrementVoteCounterStringSecond = " = ? WHERE id = ?";  
	
	/**
	 * String instance used as a part of access to database, used for selecting particular element.
	 */
	private static final String elemStatementString = "SELECT id, optionTitle, optionLink, pollID, votesCount from PollOptions where id = ?";
	
	/**
	 * String instance used as a part of access to database, used for obtaining all elements(just their's id) from table.
	 */
	private static final String inputStatementString = "SELECT id from PollOptions order by id";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PollOptionModel> getBasicInputList() throws DAOException {
		List<PollOptionModel> input = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(inputStatementString);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOptionModel elem = this.getInput(rs.getLong(1));
						input.add(elem);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste korisnika.", ex);
		}
		return input;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PollOptionModel getInput(long id) throws DAOException {
		PollOptionModel elem = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(elemStatementString);
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						elem = new PollOptionModel();
						elem.setId(rs.getLong(1));
						elem.setOptionTitle(rs.getString(2));
						elem.setOptionLink(rs.getString(3));
						elem.setPollID(rs.getLong(4));
						elem.setVotesCount(rs.getLong(5));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return elem;
	}
	
	@Override
	public void incrementElement(long iDvote, IncrementElems elem){
		Connection con = SQLConnectionProvider.getConnection();
				
		long curVotes = getCurrentVotesNum(iDvote, con, elem.getElem());
		
		if(curVotes < 0) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.");
		}
		
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement(incrementVoteCounterStringFirst + elem.getElem() + incrementVoteCounterStringSecond);
			pst.setLong(2, iDvote);
			pst.setLong(1, ++curVotes);
			int numOfAffectedRows = pst.executeUpdate();
			
			//Expected only one row affected...
			if(numOfAffectedRows != 1){
				throw new DAOException();
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		} finally {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Obtains current vote number for specific database table element which is then increased(not in this method).
	 * @param iDvote ID of element which property is to be increased.
	 * @param con Connection to database.
	 * @param elem Property which is to be obtained from database table element.
	 * @return
	 */
	private long getCurrentVotesNum(long iDvote, Connection con, String elem) {
		PreparedStatement pst = null;

		long curVotes = -1;
		
		try {
			pst = con.prepareStatement(getVoteCounterStringFirst + elem + getVoteCounterStringSecond);
			pst.setLong(1, iDvote);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						curVotes = rs.getLong(1);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		
		return curVotes;
	}


}