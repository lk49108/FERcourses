package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class implements IWebWorker and represents
 * a worker which causes current time to be displayed on screen.
 * Also, it will give a different message 
 * depending if a parameter called “name” 
 * was provided in URL that started this worker.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class HelloWorker implements IWebWorker {

    /**
     * Creates a html document that has current time and a message depending on whether a parameter name was given or
     * not.
     */
    @Override
    public void processRequest(RequestContext context) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();

        context.setMimeType("text/html");
        String name = context.getParameter("name");

        try {
            context.write("<html><body>");
            context.write("<h1>Hello!!!</h1>");
            context.write("<p>Now is: " + sdf.format(now) + "</p>");

            if (name == null || name.trim().isEmpty()) {
                context.write("<p>You did not send me your name!</p>");
            } else {
                context.write("<p>Your name has " + name.trim().length() + " letters.</p>");
            }
            context.write("</body></html>");
        } catch (IOException ex) {
            // Log exception to servers log...
            ex.printStackTrace();
        }
    }
}