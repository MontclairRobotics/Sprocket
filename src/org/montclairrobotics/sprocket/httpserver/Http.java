package org.montclairrobotics.sprocket.httpserver;
/*
 * TO USE (in eclipse):
 * Right click on project name
 * Click Build Path > Configure Build Path
 * Select Libraries
 * Click the Down Arrow next to JRE System Library
 * Double Click "Access Rules"
 * Click Add
 * Change Resolution to Accessible
 * Use this pattern: com/sun/**
 * Click OK
 * Click OK
 * Click OK
 */
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;

public abstract class Http {

    public String r="DEFAULT";
    
    private HttpServer server;
    private boolean on=false;
    
    public Http(int port, String name)
    {
    	start(port,name);
    }
    
    public void start(int port,String name){
        try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
        server.createContext("/"+name, new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        on=true;
    }
    
    public void stop()
    {
    	if(server!=null && on)
    	{
    		server.stop(0);
    		on=false;
    	}
    }
    
    class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            request(t);
        }
    }
    public void request(HttpExchange t) {
        Headers h=t.getResponseHeaders();
        byte[]response=getResponse(h,t);
        try {
			t.sendResponseHeaders(200, response.length);
			OutputStream os = t.getResponseBody();
	        os.write(response);
	        os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * Processes an http request,
     * filling out header and returning the response
     * @param h the Headers, add() each header
     * @param exchange the HttpExchange (don't have to use)
     * @return the byte[] that you want to send
     */
    public byte[] getResponse(Headers h,HttpExchange exchange){return null;}
}
