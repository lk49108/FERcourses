package hr.fer.zemris.java.webserver;

import static org.junit.Assert.*;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Class with requestContext tests.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class RequestContextTest {

   
 
    @Test
    public void testRequestContext() {
        new RequestContext(outputStream, null, persistentParameters, outputCookies);
        new RequestContext(outputStream, parameters, null, outputCookies);
        new RequestContext(outputStream, parameters, persistentParameters, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequestContextExc() {
        new RequestContext(null, null, persistentParameters, outputCookies);
    }


    @Test
    public void testGetParameter() {
        assertEquals("k", rc.getParameter("hej"));
    }
    
    @Test
    public void testGetParameterNames() {
        rc.getParameterNames();
    }

    
    @Test
    public void testAddRCCookie() {
        rc.addRCCookie(cookie);
    }

    @Test
    public void testGetParameterNullPointer() {
        assertEquals(null, rc.getParameter("z"));
    }

    @Test
    public void testGetPersistentParameter() {
        assertEquals("h", rc.getPersistentParameter("haj"));
    }

    @Test
    public void testRemoveTemporaryParameter() {
        rc.removeTemporaryParameter("a");
    }

    @Test
    public void testSetEncoding() {
        rc.setEncoding("UTF-16");
    }


    @Test
    public void testSetPersistentParameter() {
        rc.setPersistentParameter("gh", "m");
        assertEquals("m", rc.getPersistentParameter("gh"));
    }

    @Test
    public void testRemovePersistentParameter() {
        rc.removePersistentParameter("P1");
    }

    @Test
    public void testGetTemporaryParameter() {
        rc.setTemporaryParameter("hej:D", "k");
        assertEquals("k", rc.getTemporaryParameter("hej:D"));
    }

    @Test
    public void testGetTemporaryParameterNames() {
        rc.getTemporaryParameterNames();
    }

    @Test
    public void testSetTemporaryParameter() {
        rc.setTemporaryParameter("a", "b");
    }
    
    @Test
    public void testSetStatusCode() {
        rc.setStatusCode(500);
    }

    @Test
    public void testSetStatusText() {
        rc.setStatusText("status text");
    }

    @Test
    public void testSetMimeType() {
        rc.setMimeType("text/plain");
    }
    
    
    
    RCCookie cookie;

    OutputStream outputStream;
    
    List<RequestContext.RCCookie> outputCookies;
    
    Map<String, String> parameters;
    
    Map<String, String> persistentParameters;
    
    RequestContext rc;

    @Before
    public void setUp() throws Exception {
        
    	outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {}
        };

        parameters = new HashMap<>();
        persistentParameters = new HashMap<>();
        outputCookies = new ArrayList<>();
        cookie = new RequestContext.RCCookie("cookie", "cValue", 200, "domain", "path");

        parameters.put("hej", "k");
        persistentParameters.put("haj", "h");
        outputCookies.add(cookie);

        rc = new RequestContext(outputStream, parameters, persistentParameters, outputCookies);
    }


}