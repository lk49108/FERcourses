package hr.fer.zemris.java.hw16.util;

import java.awt.Color;

/**
 * Utilization class.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public final class Util {

	/**
	 * Private constructor. Prevents user from creating instance of this class.
	 */
    private Util() {
    }

    /**
     * Returns string parsed to int. If the string cannot be parsed to int, 0 is returned.
     * 
     * @param arg string to parse
     * @return int value of the string
     */
    public final static int getInt(String arg) {
        int result = 0;
        try {
            result = Integer.parseInt(arg);
        } catch (NumberFormatException ignore) {
        	throw new IllegalArgumentException("Wrong parameter.");
        }
        return result;
    }

    /**
     * Returns string parsed to double. If the string cannot be parsed to double, 0 is returned.
     * 
     * @param arg string to parse
     * @return double value of the string
     */
    public static double getDouble(final String arg) {
        double result = 0;
        try {
            result = Double.parseDouble(arg);
        } catch (NumberFormatException ignore) {
        	throw new IllegalArgumentException("Wrong parameter.");
        }
        return result;
    }

    /**
     * Returns a color that is parsed from a string. String should look like (r,g,b)
     * 
     * @param arg string to parse
     * @return color represented by this string
     */
    public final static Color getColor(final String arg) {
        String[] args = arg.substring(1, arg.length() - 1).split(",");
        if (args.length != 3) {
            return Color.BLACK;
        }
        final int r = getInt(args[0]);
        final int g = getInt(args[1]);
        final int b = getInt(args[2]);

        return new Color(r, g, b);
    }

}