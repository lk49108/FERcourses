package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Demo program for trying out {@link SmartScriptEngine} class 
 * implementation.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class ScriptEngineDemo {

	//When u will start this demo program, sinus results will not be
	//the same as in task sheet. Reason for that is that mine program
	//calculates sinus function for radians, and the one in
	//task sheet example calculates sinus function for degrees.
	/**
	 * Method from which program starts its execution.
	 * @param args Command line arguments, not used here.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
	
		Path filePath = Paths.get("E:/Java_sve/DZ/HW12-0036491083/HW12-0036491083/tests/osnovni.smscr");
		byte[] documentBodyByteArray = Files.readAllBytes(filePath);
		String documentBody = new String(documentBodyByteArray);
		
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		SmartScriptEngine engine = new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)
				);
		
		engine.execute();
	}
}
