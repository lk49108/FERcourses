package hr.fer.zemris.java.hw16.components;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;


import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class is a super class of all objects that can be drawn in {@link JVDraw}.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class GeometricalObject extends JComponent {

    private static final String TITLE = "Change object";

    /**
     * This action is performed when a object is clicked in the object list.
     * 
     * @param parent parent component from which the dialog will be shown
     */
    public void onClick(final Component parent, final JComponent comp) {
        final JPanel panel = createMessage();
        int answer = JOptionPane.showConfirmDialog(parent, panel, TITLE, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.OK_OPTION) {
            updateObject(comp);
        }
    }

    /**
     * Creates message that will be shown in the dialog.
     * 
     * @return message
     */
    public abstract JPanel createMessage();

    /**
     * Updates the given object.
     */
    public abstract void updateObject(final JComponent comp);

    /**
     * Used to change objects using just the ending point.
     * 
     * @param ending end point
     */
    public abstract void changeEndPoint(final Point ending);

    /**
     * Draws the given object as would with paintComponent, but shifted to the left for <code>x</code> and up for
     * <code>y</code>
     * 
     * @param g graphics used
     * @param x horizontal shift
     * @param y vertical shift
     */
    public abstract void drawShifted(final Graphics g, final int x, final int y);

    /**
     * Every geometrical shape must implement it's own method for painting.
     */
    @Override
    public abstract void paintComponent(final Graphics g);

}