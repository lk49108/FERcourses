package hr.fer.zemris.java.hw16.drawing;



import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.color.IColorProvider;
import hr.fer.zemris.java.hw16.color.JColorArea;
import hr.fer.zemris.java.hw16.components.Circle;
import hr.fer.zemris.java.hw16.components.FCircle;
import hr.fer.zemris.java.hw16.components.GeometricalObject;
import hr.fer.zemris.java.hw16.components.Line;
import hr.fer.zemris.java.hw16.models.DrawingModel;

/**
 * This class is used for drawing objects on {@link JDrawingCanvas}.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Drawer implements ColorChangeListener {

	/**
	 * Constant used by this class.
	 */
    public static final int LINE = 0;
   
    /**
	 * Constant used by this class.
	 */
    public static final int CIRCLE = 1;
    
    /**
	 * Constant used by this class.
	 */
    public static final int FCIRCLE = 2;

    /**
     * Status variable.
     */
    private int status;
    
    /**
     * Contains informations about whether provided mouse click is the first one or not.
     */
    private boolean firstClick;
    
    /**
     * {@link DrawingModel} instance.
     */
    private DrawingModel model;
    
    /**
     * {@link GeometricalObject} instance.
     */
    private GeometricalObject obj;
    
    /**
     * Array containing colors.
     */
    private final Color[] colors = new Color[2];
    
    /**
     * Boolean variable holding informations about whether drawing has changed.
     */
    private boolean changed;
    
    /**
     * {@link JComponent} instance.
     */
    private JComponent parent;

    /** coordinates of the first point */
    private Point start;

    /**
     * Creates a new drawer.
     * 
     * @param parent component on which drawer will add mouse event listeners
     * @param model model to which drawer will add drawn objects
     * @param foreground {@link JColorArea} that is responsible for foreground color
     * @param background {@link JColorArea} that is responsible for background color
     */
    public Drawer(final JComponent parent, final DrawingModel model, final JColorArea foreground,
            final JColorArea background) {

        this.parent = parent;
        this.parent.addMouseListener(mouseClick);
        this.model = model;

        colors[0] = foreground.getCurrentColor();
        colors[1] = background.getCurrentColor();
        foreground.addColorChangeListener(this);
        background.addColorChangeListener(this);

        status = LINE;
        firstClick = true;
        changed = false;
    }

    /**
     * Changes the status of the drawer.
     * 
     * @param status new status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Mouse listener that is added to the drawing canvas and is used to know when user clicks on the canvas.
     */
    private MouseListener mouseClick = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if (firstClick) {
                firstClick = false;
                start = e.getPoint();
                setObjectToDraw();
                Drawer.this.parent.addMouseMotionListener(mouseMove);
            } else {
                firstClick = true;
                Drawer.this.parent.removeMouseMotionListener(mouseMove);
                changed = true;
                model.add(obj);
            }
        };
    };
    
    /**
     * Returns if the drawing has changed since last save / at all.
     * 
     * @return <code>true</code> if it has changed
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * Used to set change to false, when save was made.
     */
    public void saveWasMade() {
        changed = false;
    }

    /**
     * Added to the canvas after a first click has been made, removed when second click is made.
     */
    private MouseMotionListener mouseMove = new MouseMotionAdapter() {
        public void mouseMoved(MouseEvent e) {
            // obj.removeObject(parent.getGraphics());
            obj.changeEndPoint(e.getPoint());
            parent.repaint();
            obj.paintComponent(parent.getGraphics());
        }
    };

    /**
     * Sets new object to draw depending on the <code>status</code>.
     */
    private void setObjectToDraw() {
        switch (status) {
            case LINE:
                obj = new Line(start.x, start.y, start.x, start.y, colors[0]);
                break;
            case CIRCLE:
                obj = new Circle(start.x, start.y, 0, colors[0]);
                break;
            default:
                obj = new FCircle(start.x, start.y, 0, colors[0], colors[1]);
        }
    }

    /**
     * Changes the color that <code>source</code> was providing until now.
     */
    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
        colors[source.isFgColor() ? 0 : 1] = newColor;
    }

}