package hr.fer.zemris.java.hw16.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import hr.fer.zemris.java.hw16.components.Circle;
import hr.fer.zemris.java.hw16.components.FCircle;
import hr.fer.zemris.java.hw16.components.GeometricalObject;
import hr.fer.zemris.java.hw16.components.Line;
import hr.fer.zemris.java.hw16.models.DrawingModel;

/**
 * Utilization class for things like, saving, reading from file, etc.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
public class FileHandler {

	/**
	 * Private constructor. Stops user from creating instance of this class.
	 */
    private FileHandler() {
    }

    /**
     * Saves model to the given path if possible.
     * 
     * @param model model to save
     * @param path path to save to
     */
    public static void saveModelToFile(DrawingModel model, Path path) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            for (int i = 0, size = model.getSize(); i < size; i++) {
                
            	String obj = model.getObject(i).toString();
                
            	bos.write(obj.getBytes(StandardCharsets.UTF_8));
                
            	if (i + 1 < size) {
                    bos.write("\n".getBytes(StandardCharsets.UTF_8));
                }
            }

            byte[] content = bos.toByteArray();
            bos.close();

            Files.write(path, content);
            JOptionPane.showMessageDialog(null, "File was successfully saved", null, JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ignore) {
            JOptionPane.showMessageDialog(null, "Saving failed", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Adds objects read from file to the wanted model.
     * 
     * @param path file path
     * @param model model you want to add objects to
     */
    public static void addObjectsToModelFromFile(DrawingModel model, Path path) {
        final String[] rows = readFromFile(path).split("\n");
        for (String row : rows) {
            model.add(getObject(row));
        }
    }

    /**
     * Returns a {@link GeometricalObject} that is a representation of the given row
     * 
     * @param row row that is parsed
     * @return a geometrical object that is parsed from given row
     */
    private static GeometricalObject getObject(String row) {
        if (row.startsWith("LINE")) {
            return parseLine(row);
        } else if (row.startsWith("CIRCLE")) {
            return parseCircle(row);
        } else {
            return parseFCircle(row);
        }
    }

    /**
     * Parses a given string as a {@link Line}
     * 
     * @param row row to parse
     * @return line parsed from string
     */
    private static GeometricalObject parseLine(String row) {
        String[] args = row.split(" ");

        int sx = Util.getInt(args[1]);
        int sy = Util.getInt(args[2]);
        int ex = Util.getInt(args[3]);
        int ey = Util.getInt(args[4]);
        int r = Util.getInt(args[5]);
        int g = Util.getInt(args[6]);
        int b = Util.getInt(args[7]);

        return new Line(sx, sy, ex, ey, new Color(r, g, b));
    }

    /**
     * Parses a given string as a {@link Circle}
     * 
     * @param row row to parse
     * @return circle parsed from string
     */
    private static GeometricalObject parseCircle(String row) {
        String[] args = row.split(" ");

        int x = Util.getInt(args[1]);
        int y = Util.getInt(args[2]);
        int radius = Util.getInt(args[3]);
        int r = Util.getInt(args[4]);
        int g = Util.getInt(args[5]);
        int b = Util.getInt(args[6]);

        return new Circle(x, y, radius, new Color(r, g, b));
    }

    /**
     * Parses a given string as a {@link FCircle}
     * 
     * @param row row to parse
     * @return fcircle parsed from string
     */
    private static GeometricalObject parseFCircle(String row) {
        String[] args = row.split(" ");

        int x = Util.getInt(args[1]);
        int y = Util.getInt(args[2]);
        int radius = Util.getInt(args[3]);
        int r1 = Util.getInt(args[4]);
        int g1 = Util.getInt(args[5]);
        int b1 = Util.getInt(args[6]);
        int r2 = Util.getInt(args[7]);
        int g2 = Util.getInt(args[8]);
        int b2 = Util.getInt(args[9]);

        return new FCircle(x, y, radius, new Color(r1, g1, b1), new Color(r2, g2, b2));
    }

    /**
     * Reads entire file content to a string.
     * 
     * @param file path to the file being read
     * @return string content of the file
     */
    private static String readFromFile(Path file) {
        if (file == null) {
            return "";
        }

        if (!Files.isReadable(file)) {
            JOptionPane.showMessageDialog(null, "File is not readable", "Error while reading",
                    JOptionPane.ERROR_MESSAGE);
            return "";
        }

        byte[] content;
        try {
            content = Files.readAllBytes(file);
            return new String(content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File is not readable", "Error while reading",
                    JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    /**
     * Exports given model as a image.
     * 
     * @param model model to export
     */
    public static void export(DrawingModel model) {

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3, 1));

        JRadioButton[] buttons = new JRadioButton[3];

        buttons[0] = new JRadioButton("PNG");
        buttons[1] = new JRadioButton("JPG");
        buttons[2] = new JRadioButton("GIF");

        ButtonGroup grupa = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            grupa.add(buttons[i]);
            p.add(buttons[i]);
        }

        JOptionPane.showMessageDialog(null, p, "Select image type to export", JOptionPane.INFORMATION_MESSAGE);

        String ext = "";
        for (int i = 0; i < 3; i++) {
            if (buttons[i].isSelected()) {
                ext = buttons[i].getText();
                break;
            }
        }

        if (ext.isEmpty()) {
            return;
        }

        createImage(ext, model);

    }

    /**
     * Creates an image with extension <code>ext</code>, fills it with objects from <code>model</code>
     * 
     * @param ext extension of the image
     * @param model model that provides objects to draw
     */
    private static void createImage(String ext, DrawingModel model) {
        Rectangle boundingBox = model.getBoundingBox();
        BufferedImage image = new BufferedImage(boundingBox.width, boundingBox.height, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g = image.createGraphics();
        // g.setBackground(Color.white);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, boundingBox.width, boundingBox.height);

        for (int i = 0, size = model.getSize(); i < size; i++) {
            model.getObject(i).drawShifted(g, boundingBox.x, boundingBox.y);
        }
        g.dispose();

        final JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        final File file = Paths.get(fc.getSelectedFile().toPath() + "." + ext.toLowerCase()).toFile();

        try {
            ImageIO.write(image, ext, file);
            JOptionPane.showMessageDialog(null, "Image successfully created!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while creating image.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}