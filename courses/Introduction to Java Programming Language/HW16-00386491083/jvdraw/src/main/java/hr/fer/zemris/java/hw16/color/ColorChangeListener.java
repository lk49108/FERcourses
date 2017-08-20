package hr.fer.zemris.java.hw16.color;

import java.awt.Color;

/**
 * Classes which implements this interface listen for
 * color (background and foreground) changes triggered by
 * user.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface ColorChangeListener {

    /**
     * Called when {@link IColorProvider} changes its color.
     * 
     * @param source source that changed it's color
     * @param oldColor color that was previously source's color
     * @param newColor new color
     */
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}