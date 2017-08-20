package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.webserver.workers.IWebWorker;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is implementation of simple web server.
 * It can be start through command line. One command line argument, i.e.
 * path to main configuration file is expected.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SmartHttpServer {
	

	/**
	 * Workers map.
	 */
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	
	/**
	 * IP address on which this server runs.
	 */
	private String address;
	
	
	/**
	 * Port on which this server runs.
	 */
	private int port;
	
	/**
	 * Number of threads serving clients.
	 */
	private int workerThreads;
	
	/**
	 * SessionTimeout for each cookie.
	 */
	private int sessionTimeout;
	
	/**
	 * MimeTypes.
	 */
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	
	/**
	 * {@link ServerThread} instance, thread which is in charge for server execution, i.e.
	 * it accepts client requests and delegates client serving to another {@link Thread} instance.
	 */
	private ServerThread serverThread;
	
	/**
	 * ThreadPool.
	 */
	private ExecutorService threadPool;
	
	/**
	 * {@link Path} instance representing root file of all files used by this server.
	 */
	private Path documentRoot;
	

	/**
	 * Map holding sessions.
	 */
    private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
   
   
    /**
     * RandomNumberGenerator.
     */
    private Random sessionRandom = new Random();

    /**
     * Thread which occasionally (e.g. every 5 minutes) goes through
     * all session records and removes expired sessions from session map.
     */
    private GarbageThread garbageThread;
  

    /**
     * Main method that starts the server.
     * 
     * @param args only one argument is expected: path to server.properties file
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	if(args.length != 1){
			System.out.println("Path to main configuration file was expected.");
			System.exit(1);
		}

		new SmartHttpServer(args[0]);
		
    }

    /**
     * Creates a new {@link SmartHttpServer} with given config file
     * 
     * @param configFileName path to the config file
     * @throws IOException
     */
    public SmartHttpServer(String configFileName)throws IOException {
    	
        Properties properties = new Properties();
        
        try {
            properties.load(Files.newInputStream(Paths.get(configFileName)));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load server config.");
        }

        readMainConfigFile(configFileName);
        
    	this.start();
    	
    	System.out.println("Type in exit for turning server off.");
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						System.in
						)
				);

		while(true){
			if(reader.readLine().toLowerCase().equals("exit")) break;
		}
		
		reader.close();

		stop();
    }
    
    /**
	 * Reads main server configuration file
	 * and initializes appropriate instance variables.
	 * @param configFileName Path to main configuration file.
	 * @throws FileNotFoundException 
	 */
	private void readMainConfigFile(String configFileName){
		try(InputStream fileStream = Files.newInputStream(Paths.get(configFileName))){ 
			Properties properties = new Properties();
			properties.load(fileStream);
			
			this.address = properties.getProperty("server.address");
			
			this.port = Integer.parseInt(properties.getProperty("server.port"));
		
			this.workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
			
			this.sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));

			addMimeTypes(properties.getProperty("server.mimeConfig"));
			
			this.documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
	
	        readWorkerConfig(Paths.get(properties.getProperty("server.workers")));

			
		} catch (FileNotFoundException ex) {
			System.out.println("There is no " + configFileName + " file.");
			System.exit(1);
		} catch (Exception ex){
			System.out.println("Error occured while reading from " + configFileName + " file.");
			System.exit(1);
		}
	}


	
	/**
	 * Reads mime.properties file and stores mime types to {@link #mimeTypes} map.
	 * @param mimeFilePath String representation to file.
	 */
	private void addMimeTypes(String mimeFilePath) {
		try(InputStream fileStream = new FileInputStream(mimeFilePath)){
			Properties properties = new Properties();
			properties.load(fileStream);
			
			Iterator<Object> keyIterator = properties.keySet().iterator();
			while(keyIterator.hasNext()){
				String key = (String)keyIterator.next();
				this.mimeTypes.put(key, (String)properties.get(key));
			}
			
		} catch (FileNotFoundException ex) {
			System.out.println("There is no " + mimeFilePath + "file.");
			System.exit(1);
		} catch (Exception ex){
			System.out.println("Error occured while reading from " + mimeFilePath + " file.");
			System.exit(1);
		}
	}
	
    /**
     * Reads workers.config and stores tham into workers map.
     * 
     * @param workersPath path of the workers.config file
     */
    private void readWorkerConfig(Path workersPath) {

        List<String> duplicateCheck = new ArrayList<>();

        try {
            BufferedReader reader = Files.newBufferedReader(workersPath, StandardCharsets.UTF_8);
            
            
            List<String> lines = new ArrayList<>();
            
            String line;
            
            while (true) {
                
            	line = reader.readLine();
                
            	if (line == null || line.isEmpty()) {
                    break;
                }
                
            	lines.add(line);
            }            
  
            reader.close();

            for (String l : lines) {
                l = l.trim();
                if (duplicateCheck.contains(l)) {
                	continue;
                }
                duplicateCheck.add(l);

                if (!l.startsWith("#") && !l.isEmpty()) {
                    final String[] args = l.split("\\s+=\\s+");
                    if (args.length != 2) {
                        throw new RuntimeException("Wrong format of worker.config file.");
                    }

                    final String path = args[0];
                    final String fqcn = args[1];

                    workersMap.put(path, getWorkerByItsPath(fqcn));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading worker config.");
        }

    }
    
    /**
     * 
     * Returns a appropriate {@link IWebWorker} instance, based on provided String. 
     * @param fqcn path to class.
     * @return Appropriate {@link IWebWorker} instance.
     */
    private IWebWorker getWorkerByItsPath(String fqcn) {
        Object newObject = null;;
        try {
            Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
            newObject = referenceToClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Class with provided name '" + fqcn + "' do not exist.");
        }
        return (IWebWorker) newObject;
    }

    /**
	 * Starts server.
	 */
    protected synchronized void start() {
    	this.serverThread = new ServerThread();
        serverThread.setDaemon(true);
    	this.serverThread.start();

        threadPool = Executors.newFixedThreadPool(workerThreads);

        garbageThread = new GarbageThread();
        garbageThread.setDaemon(true);
        garbageThread.start();
    }

    /**
     * Stops this server. Stops the server thread and shuts down the thread pool.
     */
    protected synchronized void stop() {
        threadPool.shutdown();
        this.serverThread.finish = true;
    }

    /**
     * 
     * Instance of this thread periodically cleans expired sessions.
     * @author Leonardo Kokot
     * @version 1.0
     */
    protected class GarbageThread extends Thread {

        @Override
        public void run() {
            while (true) {

                final Set<String> sids = new HashSet<>(sessions.keySet());

                for (String sid : sids) {
                    SessionMapEntry entry = sessions.get(sid);
                    final long currTime = new Date().getTime() / 1000;
                    if (entry.validUntil < currTime) {
                        sessions.remove(sid);
                    }
                }

                //sleep for 5 minutes
                try {
                    Thread.sleep(5 * 1000 * 3600);
                } catch (InterruptedException ignorable) {
                }

            }
        }
    }

    /**
     * This class is used as a thread that server runs to accept clients.
     * 
     * @author Leonardo Kokot
     * @version 1.0
     */
    protected class ServerThread extends Thread {

        /**
         * Variable which is set to true when ServerThread needs to be stoped.
         */
        private boolean finish;

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);

                while (!finish) {
                    Socket client = serverSocket.accept();
                    
                    ClientWorker cw = new ClientWorker(client);
                    
                    threadPool.submit(cw);
                }

                serverSocket.close();
            } catch (IOException e) {
            	System.out.println("Error while saving client occured.");
            }
        }

    }

    /**
     * This class represents a client on this server. It has a single public method that declares what to do when some
     * client connects.
     * 
     * @author Leonardo Kokot
     * @version 1.0
     */
    private class ClientWorker implements Runnable {

    	
		/**
		 * Default mime type.
		 */
		private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

		/**
		 * Client's socket.
		 */
		private Socket csocket;
		
		/**
		 * {@link InputStream} obtained from client socket.
		 */
		private InputStream istream;
		
		/**
		 * {@link OutputStream} obtained from client socket.
		 */
		private OutputStream ostream;
		
		/**
		 * Version, i.e. part of client http request.
		 */
		private String version;
		
		/**
		 *Method, i.e. part of client http request.
		 */
		private String method;
		
		/**
		 * Parameters.
		 */
		private Map<String,String> params = new HashMap<String, String>();
		
		/**
		 * Permanent parameters.
		 */
		private Map<String,String> permParams = null;
		
		/**
		 * OutputCookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
        /**
         * Creates a new {@link ClientWorker} that works with given client socket.
         * 
         * @param csocket client's socket
         */
        public ClientWorker(Socket csocket) {
            this.csocket = csocket;
        }

        @Override
        public void run() {

        	
        	try {
        		this.istream = new PushbackInputStream(
    					this.csocket.getInputStream()
    				);
        		
				this.ostream = this.csocket.getOutputStream();
			} catch (IOException e1) {
				System.out.println("Error occured while creating input stream.");
			}
        	
            List<String> request = null;
            
            try {
                request = readRequest();
            } catch (IOException e) {
            	System.out.println("Error occured while reading request.");
            }

            String[] firstLine = request.get(0).split(" ");
            if (firstLine.length != 3) {
                sendError(400, "Bad request");
                return;
            }
            
            method = firstLine[0];
            String requestedPath = firstLine[1];
            version = firstLine[2];

            if (!method.equals("GET") || !(version.equals("HTTP/1.0") || version.equals("HTTP/1.1"))) {
                sendError(400, "Bad request");
                return;
            }

            checkSession(request);

            String path = extractPath(requestedPath);
            Path reqPath = Paths.get(documentRoot + path);
            if (!reqPath.startsWith(documentRoot)) {
                sendError(403, "Forbidden");
                return;
            }

            RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);

            if (path.startsWith("/ext/")) {
                final String workerName = path.substring(5);
                getWorkerByItsPath("hr.fer.zemris.java.webserver.workers" + "." + workerName).processRequest(rc);
                return;
            }

            final String worker = "/" + reqPath.getFileName().toString();
            if (workersMap.containsKey(worker)) {
            	workersMap.get(worker).processRequest(rc);
                return;
            }

            final boolean exists = Files.exists(reqPath);
            final boolean isFile = Files.isRegularFile(reqPath);
            final boolean isRead = Files.isReadable(reqPath);

            if (!(exists && isFile && isRead)) {
                sendError(404, "File not found");
                return;
            }

            final Path file = reqPath.getFileName();
            final String fileName = file.toString();
            final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

            String mime = mimeTypes.get(extension);
            if (mime == null) {
                mime = DEFAULT_MIME_TYPE;
            }

            rc.setStatusCode(200);

            if (extension.equals("smscr")) {
                new SmartScriptEngine(getDocNode(reqPath), rc).execute();
            } else {
                rc.setMimeType(mime);
                try {
                    byte[] data = Files.readAllBytes(reqPath);
                    rc.write(data);
                } catch (IOException e) {
                    throw new RuntimeException("Error while writing to output stream or reading file.");
                }
            }

            try {
				csocket.close();
			} catch (IOException unhandled) {}       
        }

        /**
         * Checks this session.
         * 
         * @param headerLines lines of the request that was sent
         */
        private synchronized void checkSession(List<String> headerLines) {
            String sidCandidate = null;

            for (String s : headerLines) {
                if (s.startsWith("Cookie:")) {
                    String[] cookies = s.replaceFirst("Cookie:\\s+", "").split(";");
                    for (String c : cookies) {
                        String[] cookie = c.split("=");

                        String name = cookie[0];
                        String cookieValue = cookie[1].replace("\"", "");

                        if (name.toLowerCase().equals("sid")) {
                            sidCandidate = cookieValue;
                        }
                    }
                }
            }

            SessionMapEntry entry;

            if (sidCandidate == null || (entry = sessions.get(sidCandidate)) == null) {
                entry = createCookie();
            } else {
                long currTime = new Date().getTime() / 1000;
                entry.setValidUntil(currTime + sessionTimeout);
            }

            permParams = entry.getMap();
        }

        /**
         * Creates a new new session entry for this session.
         * 
         * @return session entry
         */
        private SessionMapEntry createCookie() {
            String newSid = ""; 
            for (int i = 0; i < 20; i++) {
                newSid += "ABCDEFGHIJKLMNOPQRSTUVXYZ".charAt(sessionRandom.nextInt("ABCDEFGHIJKLMNOPQRSTUVXYZ".length()));
            }

            long validUntil = (new Date().getTime() / 1000) + sessionTimeout;

            SessionMapEntry entry = new SessionMapEntry(newSid, validUntil);
            sessions.put(newSid, entry);

            RCCookie cookie = new RCCookie("sid", newSid, null, address, "/");
            cookie.setOnlyHttp(true);
            outputCookies.add(cookie);

            return entry;
        }

        /**
         * Splits the given request to check if it has some parameters with it.
         * 
         * @param request request that was sent
         * @return extracted path
         */
        private String extractPath(String request) {
            final int indexOfSplit = request.indexOf('?');
            String path;
            if (indexOfSplit == -1) {
                path = request;
            } else {
                path = request.substring(0, indexOfSplit);
                parseParameters(request.substring(indexOfSplit + 1));
            }
            return path;
        }

        /**
         * Used to parse given paramString as multiple parameters. Puts parsed parameters to the 'params' map of
         * paramters.
         * 
         * @param paramString string to parse
         */
        private synchronized void parseParameters(String paramString) {
            String[] parameters = paramString.split("&");
            for (String s : parameters) {
                String[] sSplit = s.split("=");
                if (sSplit.length != 2) {
                    throw new RuntimeException("Wrong parameter format: " + s);
                }
                params.put(sSplit[0], sSplit[1]);
            }
        }
        

        /**
         * Returns a list of lines read from the request.
         * 
         * @return list of lines
         * @throws IOException
         */
        private List<String> readRequest() throws IOException {
            BufferedReader r = new BufferedReader(new InputStreamReader(new BufferedInputStream(istream),
                    StandardCharsets.ISO_8859_1));
            
            List<String> lines = new ArrayList<>();
            
            String line;
            
            while (true) {
                
            	line = r.readLine();
                
            	if (line == null || line.isEmpty()) {
                    break;
                }
                
            	lines.add(line);
            }
           
            return lines;
            
         }

        /**
         * Gets the document node of the given script.
         * 
         * @param fileName file name of the given script to run
         * @return document node of the given script
         * @throws IOException
         */
        private DocumentNode getDocNode(final Path path) {
            List<String> documentBody;
            try {
                documentBody = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException("Couldn't read script: " + path.getFileName());
            }

            String doc = "";
            for (String s : documentBody) {
                doc += s + "\r\n";
            }

            return new SmartScriptParser(doc).getDocumentNode();
        }

        /**
         * Sends some status code to the client.
         * 
         * @param statusCode status code to send
         */
        private void sendError(int statusCode, String text) {
            String statusText = "http/1.1" + " " + statusCode;
           
            statusText += "\r\n";

            
            RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
           
            rc.setStatusCode(statusCode);
            
            rc.setStatusText(statusText);
            
            try {
                rc.write(statusText);
            } catch (IOException e) {
            	System.out.println("Error occured while writing to client.");
            }

            try {
				csocket.close();
			} catch (IOException unhandled) {}
        }

    }

    /**
     * 
     * This class instance represents one session, cookie.
     * @author Leonardo Kokot
     * @version 1.0
     */
    private static class SessionMapEntry {
        String sid;
        long validUntil;
        Map<String, String> map = new ConcurrentHashMap<>();

        /**
         * @param sid session id
         * @param validUntil expiration time
         * @param map
         */
        public SessionMapEntry(String sid, long validUntil) {
            this.sid = sid;
            this.validUntil = validUntil;
        }

        /**
         * Sets the valid-until parameter to the given parameter.
         * 
         * @param newValidUntil parameter to set
         */
        public void setValidUntil(long newValidUntil) {
            validUntil = newValidUntil;
        }

        /**
         * Returns this entry's map.
         * 
         * @return map
         */
        public Map<String, String> getMap() {
            return map;
        }

    }

}