package hr.fer.zemris.java.hw16.color;

import java.awt.Color;

/**
 * Provides color used by user.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface IColorProvider {

    /**
     * Returns the current color of this {@link IColorProvider}.
     * 
     * @return current color
     */
    public Color getCurrentColor();

    /**
     * If the {@link IColorProvider} provides <i>foreground</i> color.
     * 
     * @return <code>true</code> if it provides foreground color
     */
    public boolean isFgColor();

}