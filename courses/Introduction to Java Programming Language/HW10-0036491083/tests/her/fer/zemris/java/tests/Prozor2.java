package her.fer.zemris.java.tests;

import java.awt.Color;
import java.awt.Container;

import java.awt.GridLayout;


import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Test class for {@link CalcLayout}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Prozor2 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Prozor2(){
	super();
	setDefaultCloseOperation(
		WindowConstants.DISPOSE_ON_CLOSE		
		);
	setLocation(20, 20);
	setTitle("Prozor1");
	setSize(500, 200);
	initGUI();
	
	}
	
	private void initGUI(){
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(1, 1));
		
		JPanel p = new JPanel(new CalcLayout(10, 10));
		
		JLabel labela = new JLabel("1,1");
		labela.setOpaque(true);
		labela.setBackground(Color.BLUE);
		labela.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labela2 = new JLabel("2,3");
		labela2.setOpaque(true);
		labela2.setBackground(Color.BLUE);
		labela2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labela3 = new JLabel("2,7");
		labela3.setOpaque(true);
		labela3.setBackground(Color.BLUE);
		labela3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labela4 = new JLabel("4,2");
		labela4.setOpaque(true);
		labela4.setBackground(Color.BLUE);
		labela4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labela5 = new JLabel("4,5");
		labela5.setOpaque(true);
		labela5.setBackground(Color.BLUE);
		labela5.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labela6 = new JLabel("4,7");
		labela6.setOpaque(true);
		labela6.setBackground(Color.BLUE);
		labela6.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel labela7 = new JLabel("1,6");
		labela7.setOpaque(true);
		labela7.setBackground(Color.BLUE);
		labela7.setHorizontalAlignment(SwingConstants.CENTER);
		
		JCheckBox box = new JCheckBox("Hej");
		box.setBackground(Color.blue);
		
		p.add(labela, new RCPosition(1,1));
		p.add(labela2, new RCPosition(2,3));
		p.add(labela3, new RCPosition(2,7));
		p.add(labela4, new RCPosition(4,2));
		p.add(labela5, new RCPosition(4,5));
		p.add(labela6, "4,7");
		p.add(labela7, "1,6"); 
		p.add(box, "5,4");		
		cp.add(p);

		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				Prozor2 prozor = new Prozor2();
				prozor.setVisible(true);
			}
			
		});
	}
}