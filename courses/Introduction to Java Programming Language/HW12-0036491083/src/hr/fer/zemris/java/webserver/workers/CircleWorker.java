package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class implements IWebWorker and represents
 * a worker which causes circle to be rendered on screen.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class CircleWorker implements IWebWorker {

    /**
     * Constant holding informations about how big framework for
     * circle rendering is available.
     */
    private static final int SIZE = 200;

    /**
     * Constant holding informations 
     * about color of circle which will be displayed on screen.
     */
    private static final Color CIRCLECOLOR = Color.BLUE;
    

    /**
     * Constant holding informations 
     * about color of background if image which will be displayed on screen.
     */
    private static final Color BACKGROUNDCOLOR = Color.WHITE;
    
    /**
     * Constant holding informations 
     * about color of background if image which will be displayed on screen.
     */
    private static final Color STRINGCOLOR = Color.BLACK;
    
    @Override
    public void processRequest(RequestContext context) {
        
    	BufferedImage bim = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2d = bim.createGraphics();
        
        g2d.setColor(BACKGROUNDCOLOR);
        g2d.fillRect(0, 0, SIZE, SIZE);
        
        g2d.setColor(CIRCLECOLOR);

        g2d.fillOval(0, 0, SIZE, SIZE);
        
        g2d.setColor(STRINGCOLOR);
         
        String hello = "Hello ^^";
        
        FontMetrics metrics = g2d.getFontMetrics();
        
        int width = metrics.stringWidth(hello);
        
        g2d.drawString(hello, SIZE / 2 - width / 2, SIZE / 2 - g2d.getFontMetrics().getAscent());
        
        g2d.dispose();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bim, "png", bos);
            context.setMimeType("image/png");
            context.write(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}