package hr.fer.zemris.java.hw16.color;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Used for color changing in {@link JVDraw}.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JColorArea extends JComponent implements IColorProvider {

	 /**
     * Default size 2.
     */
    private static final int DEF_SIZE2 = 20;
	
    /** Default size of this component is 20x20 */
    private static final Dimension DIM = new Dimension(DEF_SIZE2, DEF_SIZE2);
    
    /**
     * Default size.
     */
    private static final int DEF_SIZE = 30;

    /**
     * Color that this component represents.
     */
    private Color color = Color.DARK_GRAY;

    /**
     * This is true if this color are is responsible for foreground color.
     */
    private boolean isFgColor;

    /**
     * A list of listeners (observers).
     */
    private final List<ColorChangeListener> listeners = new ArrayList<>();

    /**
     * When there's a click on this component, a {@link JColorChooser} is opened to choose a new color.
     */
    private final MouseAdapter myMouse = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            final Color c = JColorChooser.showDialog(JColorArea.this, "Choose a color", color);
            if (c != null) {
                setNewColor(c);
            }
        };
    };

    /**
     * Creates a new {@link JColorArea} with given color.
     * 
     * @param color initial color
     */
    public JColorArea(final Color color, final boolean isFgColor) {
        this.color = color;
        this.isFgColor = isFgColor;
        addMouseListener(myMouse);
    }

    @Override
    public boolean isFgColor() {
        return isFgColor;
    }

    /**
     * Used for color changing.
     * 
     * @param c new color to set
     */
    private void setNewColor(final Color c) {

        for (ColorChangeListener l : listeners) {
            l.newColorSelected(this, color, c);
        }
        color = c; 
        repaint(); 
    }

    @Override
    public Color getCurrentColor() {
        return color;
    }

    /**
     * Adds the given listener to the listeners list.
     * 
     * @param l listener to add
     */
    public void addColorChangeListener(final ColorChangeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    /**
     * Removes the given listener from listeners list.
     * 
     * @param l listener to remove
     */
    public void removeColorChangeListener(final ColorChangeListener l) {
        listeners.remove(l);
    }

    /**
     * Paints this component in the center of it's 'space'.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        g.setColor(color);
        final Insets ins = getInsets();
        final Dimension size = getSize();
        g.fillRect(ins.left + (size.width - (DIM.width)) / 2, ins.top + (size.height - (DIM.height)) / 2, DIM.width,
                DIM.height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEF_SIZE, DEF_SIZE);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

}