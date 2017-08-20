package hr.fer.zemris.java.hw16.drawing;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.models.DrawingModel;
import hr.fer.zemris.java.hw16.models.DrawingModelListener;

/**
 * Main class, i.e. component in this program.
 * @author Leonardo Kokot
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * {@link DrawingModel} instance.
	 */
    private DrawingModel model;
    
    /**
     * Constant used when getPreferredSize method is called.
     */
	private int PREFERRED_SIZE = 200;

    /**
     * Creates a new {@link JDrawingCanvas} that listens to <code>model</code>
     * 
     * @param model model to listen to
     */
    public JDrawingCanvas(DrawingModel model) {
        this.model = model;
       
        model.addDrawingModelListener(this);
        
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
    }

    /**
     * Paints every component from model.
     */
    @Override
    protected void paintComponent(Graphics g) {
    	int limit = model.getSize();
    	
        for (int i = 0; i < limit; i++) {
            model.getObject(i).paintComponent(g);
        }
    }

    /**
     * Returns 200,200 dimension for preferred size.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREFERRED_SIZE, PREFERRED_SIZE );
    }

    
    /**
     * Draws added objects.
     */
    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        final Graphics g = getGraphics();
        for (int i = index0; i <= index1; i++) {
            model.getObject(i).paintComponent(g);
        }
        repaint();
    }

    /**
     * Not supported by this program, just repaints
     */
    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
    }

    /**
     * Repaints changed objects.
     */
    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        Graphics g = getGraphics();
        
        for (int i = index0; i <= index1; i++) {
            model.getObject(i).paintComponent(g);
        }
    }
}