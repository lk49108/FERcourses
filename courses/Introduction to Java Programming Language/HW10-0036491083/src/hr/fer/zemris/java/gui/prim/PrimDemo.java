/**
 * 
 */
package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * This program/class allows user to add arbitrarily
 * big number of prime numbers by clicking on button
 * "sljedeæi". Those primes are shown on two separate
 * lists. This class extends {@link JFrame}.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class PrimDemo extends JFrame {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which creates instance of {@link PrimDemo} class.
	 * It is used for defining main specifications of {@link PrimDemo} {@link JFrame}.
	 */
	public PrimDemo(){
		
		setLocation(20, 50);
		setSize(300, 200);
		setTitle("PrimDemo_1.0");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		initGUI();
		
	}
	
	/**
	 * Method used for initialization of GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		@SuppressWarnings("unchecked")
		JList<Integer> listFirst = new JList<>(model);
		@SuppressWarnings("unchecked")
		JList<Integer> listSecond = new JList<>(model);
		
		JPanel listsPanel = new JPanel(new GridLayout(1, 2));
		
		listsPanel.add(new JScrollPane(listFirst));
		listsPanel.add(new JScrollPane(listSecond));
		
		JButton next = new JButton("sljedeæi");
		
		next.addActionListener(e -> {
			model.next();
		});
		
		cp.add(listsPanel, BorderLayout.CENTER);
		cp.add(next, BorderLayout.SOUTH);
		
	}
	
	/**
	 * Method from which program starts its execution.
	 * @param args Command-line arguments, not used here.
	 */
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new PrimDemo();
			frame.pack();
			frame.setVisible(true);
		});
	}

	/**
	 * This private static class
	 * is used as a model in Model-View-Controller design 
	 * pattern used for rendering prime numbers onto the screen.
	 * Consequently this class implements ListModel interface.
	 * @author Leonardo Kokot
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	private static class PrimListModel implements ListModel {

		/**
		 * List of prime numbers. All prime numbers contained
		 * in this list are shown displayed on both lists on screen.
		 */
		private List<Integer> prims;
		
		/**
		 * Contains all observers which are observing instance of this class.
		 */
		private List<ListDataListener> observers;
		
		/**
		 * Constructor which creates instance of {@link PrimListModel}
		 * class and initializes both instance variables.
		 * It also adds first prime number to {@link #prims} {@link List}.
		 */
		private PrimListModel(){
			prims = new ArrayList<>();
			prims.add(1);
			observers = new ArrayList<>();
		}
		
		@Override
		public int getSize() {
			return this.prims.size();
		}

		@Override
		public Object getElementAt(int index) {
			if(index >= prims.size() || index < 0){
				throw new IllegalArgumentException("Allowed index is between 0 and"
						+ " " + prims.size() + "(excluded).");
			}
			return prims.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			observers.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			observers.remove(l);
		}
		
		/**
		 * Calculates next prime number, stores it in {@link #prims},
		 * creates new {@link ListDataEvent} instance and sends it to
		 * all observers currently observing this instance of {@link PrimListModel}
		 * class.
		 */
		public void next(){
			int pos = prims.size();
			int currentPrim = prims.get(pos - 1);
			int nextPrim = findNextPrim(currentPrim);
			prims.add(nextPrim);
			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
			for(ListDataListener listener : observers){
				listener.intervalAdded(event);
			}
		}

		/**
		 * Method used for calculating next prime number, starting from
		 * current one provided as argument.
		 * @param current Current prime number, last one in {@link #prims} {@link List}.
		 * @return Next prime number.
		 */
		private int findNextPrim(int current) {
			if(current == 1) {
				return 2;
			}
			if(current == 2) {
				return 3;
			}
			while(true){
				current += 2;
				if(checkIfPrim(current)) break;
			}
			return current;
		}

		/**
		 * Checks if provided number is prime number.
		 * @param number Number to be checked for being prime.
		 * @return True if number is prime. False is returned otherwise.
		 */
		private boolean checkIfPrim(int number) {
			for(int i = 3; i <= Math.sqrt(number); i += 2){
				if(number % i == 0){
					return false;
				}
			}
			return true;
		}
		
	}
	
}
