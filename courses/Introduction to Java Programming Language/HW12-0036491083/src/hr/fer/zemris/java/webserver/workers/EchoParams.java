package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class implements IWebWorker and represents
 * a worker which causes table filled 
 * with provided pairs(nameOrdinalNumber, correspondingName) to be rendered
 * on screen.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class EchoParams implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) {
        
        Set<String> parameters = context.getParameterNames();
                
        String text = "<html><body>";

        if (parameters.size() != 0) {
            text += "<h1>Names</h1><table border=\"2\" style=\"width:700px;text-align:left\">";
            text += "<tr><th>" + "Name</th><th>Value</th></tr>";
            for (String n : parameters) {
                text += "<tr><td>" + n + "</td><td>" + context.getParameter(n) + "</td></tr>";
            }
            text += "</table>";
        } else {
            text += "<h3>No parameters</h3>";
        }
        text += "</body></html>";

        try {
            context.write(text);
        } catch (IOException e) {
        	System.out.println("Error occured while writing answer to client in"
        			+ " EchoParams class method processRequest.");
        }
    }
}