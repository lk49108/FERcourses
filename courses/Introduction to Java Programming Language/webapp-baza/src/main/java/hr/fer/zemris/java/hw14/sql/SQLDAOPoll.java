/**
 * 
 */
package hr.fer.zemris.java.hw14.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.PollModel;

/**
 * Implements {@link DAO} interface and implements all methods so they
 * suit best for accessing table in database which contains available polls.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SQLDAOPoll implements DAO{

	/**
	 * statement for obtaining one element.
	 */
	private static final String elemStatementString = "SELECT id, title, message from Polls where id = ?";
	
	/**
	 * Statement for obtaining all elements from table.
	 */
	private static final String inputStatementString = "SELECT id from Polls order by id";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PollModel> getBasicInputList() throws DAOException {
		List<PollModel> input = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(inputStatementString);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollModel elem = this.getInput(rs.getLong(1));
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
	public PollModel getInput(long id) throws DAOException {
		PollModel elem = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(elemStatementString);
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						elem = new PollModel();
						elem.setId(rs.getLong(1));
						elem.setTitle(rs.getString(2));
						elem.setMessage(rs.getString(3));
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

}
