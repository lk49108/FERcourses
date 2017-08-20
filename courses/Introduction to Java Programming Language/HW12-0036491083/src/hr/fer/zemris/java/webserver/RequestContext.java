package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Method used in providing functionality of
 * server implemented in {@link SmartHttpServer}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class RequestContext {

	/**
	 * Private {@link OutputStream} instance property.
	 */
	private final OutputStream outputStream;
	
	/**
	 * Private {@link Charset} instance property.
	 */
	private Charset charset;
	
	/**
	 * Write-only {@link String} property.  Default value is "UTF-8".
	 */
	private String encoding = "UTF-8";
	
	/**
	 * Write-only int property. Default value is 200.
	 */
	private int statusCode = 200; 
	
	/**
	 * Write-only {@link String} property. Default value is "OK".
	 */
	private String statusText = "OK";
	
	/**
	 * Write-only {@link String} property. Default value is "text/html".
	 */
	private String mimeType = "text/html";
	
	/**
	 * Private {@link Map} holding (String, String) pairs.
	 */
	private Map<String, String> parameters;
	
	/**
	 * Private {@link Map} holding (String, String) pairs.
	 */
	private Map<String, String> temporaryParameters = new HashMap<>();

	/**
	 * Private {@link Map} holding (String, String) pairs.
	 */
	private Map<String, String> persistentParameters;

	/**
	 * Private {@link List} holding instances of {@link RCCookie} class.
	 */
	private List<RequestContext.RCCookie> outputCookies;
	
	/**
	 * Private boolean property with default value set to false. Used in checking
	 * if header was already generated.
	 */
	private boolean headerGenerated = false;
	

    /**
     * Creates a new {@link RequestContext} with given parameters.
     * 
     * @param outputStream can't be null, used for writing context
     * @param parameters parameters stored within this context, can be null
     * @param persistentParameters persistent parameters stored within this context, can be null
     * @param outputCookies cookies that will be added to the header of this context, can be null
     */
    public RequestContext(final OutputStream outputStream, final Map<String, String> parameters,
            final Map<String, String> persistentParameters, final List<RequestContext.RCCookie> outputCookies) {
        if (outputStream == null) {
            throw new IllegalArgumentException("Output stream can not be null.");
        }
        this.outputStream = outputStream;
        if(parameters == null){
        	this.parameters = new HashMap<String, String>();
        } else {
        	this.parameters = parameters;
        }
        if(persistentParameters == null){
        	this.persistentParameters = new HashMap<>();
        } else {
        	this.persistentParameters = persistentParameters;
        }
        if(outputCookies == null){
        	this.outputCookies = new ArrayList<RequestContext.RCCookie>();
        } else {
        	this.outputCookies = outputCookies;
        }
    }
    

    /**
	 * @param encoding the encoding to set
	 * @throws RuntimeException if header was already generated.
	 */
	public void setOutputCookies(String encoding) {
		if(headerGenerated){
			throw new RuntimeException("Header was already created so this action"
					+ " is illegal.");
		}
		this.encoding = encoding;
		this.charset = Charset.forName(encoding);
	}
	
	/**
	 * @param encoding the encoding to set
	 * @throws RuntimeException if header was already generated.
	 */
	public void setEncoding(String encoding) {
		if(headerGenerated){
			throw new RuntimeException("Header was already created so this action"
					+ " is illegal.");
		}
		this.encoding = encoding;
		this.charset = Charset.forName(encoding);
	}

	/**
	 * @param statusCode the statusCode to set
	 * @throws RuntimeException if header was already generated.
	 */
	public void setStatusCode(int statusCode) {
		if(headerGenerated){
			throw new RuntimeException("Header was already created so this action"
					+ " is illegal.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * @param statusText the statusText to set
	 * @throws RuntimeException if header was already generated.
	 */
	public void setStatusText(String statusText) {
		if(headerGenerated){
			throw new RuntimeException("Header was already created so this action"
					+ " is illegal.");
		}
		this.statusText = statusText;
	}

	/**
	 * @param mimeType the mimeType to set
	 * @throws RuntimeException if header was already generated.
	 */
	public void setMimeType(String mimeType) {
		if(headerGenerated){
			throw new RuntimeException("Header was already created so this action"
					+ " is illegal.");
		}
		this.mimeType = mimeType;
	}
	
	/**
	 * Retrieves value from parameters map (or null if no association exists).
	 * @param name {@link String} associated with name.
	 * @return Corresponding {@link String} instance.
	 */
	public String getParameter(String name){
		return parameters.get(name);
	}
	
	/**
	 * Method that retrieves names of all parameters in parameters map.
	 * @return {@link UnmodifiableClassException} {@link Set}.
	 */
	public Set<String> getParameterNames(){
		return parameters.keySet();
	}
	
	/**
	 * method that retrieves value from 
	 * persistentParameters map (or null if no association exists).
	 * @param name Key for which appropriate String is returned.
	 * @return Appropriate String.
	 */
	public String getPersistentParameter(String name){
		return persistentParameters.get(name);
	}
	
	/**
	 * Retrieves names of all parameters in persistent parameters map.
	 * Those names are arranged in read-only {@link Set}.
	 * @return {@link Set}.
	 */
	public Set<String> getPeristentParameterNames(){
		return persistentParameters.keySet();
	}
	
	/**
	 * Method that stores a value to persistentParameters map.
	 * @param name Key for value.
	 * @param value Corresponding value.
	 */
	public void setPersistentParameter(String name, String value){
		persistentParameters.put(name, value);
	}
	
	/**
	 * Method that removes a value from persistentParameters map.
	 * @param name Key for which appropriate value is removed.
	 */
	public void removePersistentParameter(String name){
		persistentParameters.remove(name);
	}
	
	/**
	 * Retrieves value from temporaryParameters map 
	 * (or null if no association exists).
	 * @param name Key for which appropriate value from temporaryParameters
	 * is returned.
	 * @return Appropriate String.
	 */
	public String getTemporaryParameter(String name){
		return temporaryParameters.get(name);
	}
	
	/**
	 * Retrieves names of all parameters in temporary parameters map.
	 * Names are arranged in read-only set.
	 * @return Appropriate {@link Set}.
	 */
	public Set<String> getTemporaryParameterNames(){
		return temporaryParameters.keySet();
	}
	
	/**
	 * Stores a value to temporaryParameters map.
	 * @param name Key for corresponding value.
	 * @param value Value.
	 */
	public void setTemporaryParameter(String name, String value){
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes a value from temporaryParameters map.
	 * @param name Key for which corresponding value 
	 * is removed from temporaryParameters map.
	 */
	public void removeTemporaryParameter(String name){
		temporaryParameters.remove(name);
	}
	
	/**
	 * Adds {@link RCCookie} to outputCookies list.
	 * @param cookie Cookie which is being added to outputCookies list.
	 * @throws RuntimeException if header was already generated.
	 */
	public void addRCCookie(RCCookie cookie){
		if(headerGenerated){
			throw new RuntimeException("Header was already generated so this action is illegal.");
		}
		outputCookies.add(cookie);
	}
	

    /**
     * @param data data you want to write to the output stream
     * @return this request context
     * @throws IOException if there was a problem with writing data to output stream
     */
    public RequestContext write(final String data) throws IOException {
        if (charset == null) {
            charset = Charset.forName(encoding);
        }
        return write(data.getBytes(charset));
    }

    /**
     * @param data data you want to write to the output stream
     * @return this request context
     * @throws IOException if there was a problem with writing data to output stream
     */
    public RequestContext write(final byte[] data) throws IOException {
        if (!headerGenerated) {
            outputStream.write(generateHeader());
        }

        outputStream.write(data);
        return this;
    }

    /**
     * Generates the header for this request context.
     * 
     * @return byte representation of the header in <code>ISO_8859_1</code> encoding
     */
    private byte[] generateHeader() {

        StringBuilder bd = new StringBuilder();

        bd.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");

        bd.append("Content-type: " + mimeType);
        if (mimeType.startsWith("text/")) {
            bd.append("; charset=" + encoding);
        }
        bd.append("\r\n");

        for (RCCookie cookie : outputCookies) {
            bd.append("Set-Cookie: " + cookie + "\r\n");
        }
        bd.append("\r\n");

        headerGenerated = true;

        return bd.toString().getBytes(StandardCharsets.ISO_8859_1);

    }



		/**
		 * Class which instance represents Cookie.
		 * @author Leonardo Kokot
		 * @version 1.0
		 */
		public static class RCCookie {
			
			/**
			 * Read-only property.
			 */
			private final String name;
			
			/**
			 * Read-only property.
			 */
			private final String value;
			
			/**
			 * Read-only property.
			 */
			private final String domain;
			
			/**
			 * Read-only property.
			 */
			private final String path;
			
			/**
			 * Read-only property.
			 */
			private final Integer maxAge;
			
			/**
			 * Checks if there should be http only.
			 */
			private boolean httpOnly = false;

			/**
			 * Constructor which initializes all of this class's properties.
			 * @param name String type object.
			 * @param value String type object.
			 * @param domain String type object.
			 * @param path String type object.
			 * @param maxAge Integer type object.
			 */
			public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
				super();
				if(name == null){
					throw new IllegalArgumentException("Name must not be null reference.");
				}
				this.name = name;
				this.value = value;
				this.domain = domain;
				this.path = path;
				this.maxAge = maxAge;
			}

			/**
			 * @return httpOnly instance variable.
			 */
			public boolean httpOnly() {
				return httpOnly;
			}

			/**
			 * Sets httpOnly instance variable.
			 * @param value to which {@link #httpOnly} is set to.
			 */
			public void setOnlyHttp(boolean http) {
				this.httpOnly = http;
			}
			
			/**
			 * Getter for {@link #name}
			 * @return name property.
			 */
			public String getName() {
				return name;
			}

			/**
			 * Getter for {@link #value}
			 * @return value property.
			 */
			public String getValue() {
				return value;
			}

			/**
			 * Getter for {@link #domain}
			 * @return domain property.
			 */
			public String getDomain() {
				return domain;
			}

			/**
			 * Getter for {@link #path}
			 * @return path property.
			 */
			public String getPath() {
				return path;
			}

			/**
			 * Getter for {@link #maxAge}
			 * @return maxAge property.
			 */
			public Integer getMaxAge() {
				return maxAge;
			}
			
			@Override
	        public String toString() {
	            String text = name + "=\"" + value + "\"";
	            if (domain != null) {
	                text += "; Domain=" + domain;
	            }
	            if (path != null) {
	                text += "; Path=" + path;
	            }
	            if (maxAge != null) {
	                text += "; Max-Age=" + maxAge;
	            }
	            if (httpOnly) {
	                text += "; HttpOnly";
	            }
	            return text;
	        }
			
		}
}