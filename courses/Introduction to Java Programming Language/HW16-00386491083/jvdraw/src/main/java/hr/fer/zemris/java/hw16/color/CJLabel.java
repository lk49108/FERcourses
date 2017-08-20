package hr.fer.zemris.java.hw16.color;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Implements {@link ColorChangeListener}.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CJLabel extends JLabel implements ColorChangeListener {


    /**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Array containing colors.
	 */
	private Color[] colors = new Color[2];

    /**
     * {@link CJLabel} constructor.
     * @param foreground Foreground color {@link JColorArea}.
     * @param background Background color {@link JColorArea}.
     */
    public CJLabel(JColorArea foreground, JColorArea background) {
        colors[0] = foreground.getCurrentColor();
        colors[1] = background.getCurrentColor();
        foreground.addColorChangeListener(this);
        background.addColorChangeListener(this);
        updateText();
    }

    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
        colors[source.isFgColor() ? 0 : 1] = newColor;
        updateText();
    }

    /**
     * Returns (r, g, b) representation of the color <code>c</code>
     * 
     * @param c color
     * @return string representation of the color
     */
    public String getColor(final Color c) {
        return "(" + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue() + ")";
    }
    
    /**
     * Updates the text in the label to <code>Foreground color: (r,g,b), background color: (r,g,b).</code>
     */
    private void updateText() {
        setText("Foreground color: " + getColor(colors[0]) + ", background color: " + getColor(colors[1]) + ".");
    }
}